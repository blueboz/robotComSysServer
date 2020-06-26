package cn.boz.robotComSys.controller;

import cn.boz.robotComSys.AppMain;
import cn.boz.robotComSys.config.AppConfiguration;
import cn.boz.robotComSys.dao.AccessTokenDao;
import cn.boz.robotComSys.dao.StoryDao;
import cn.boz.robotComSys.dao.StoryItemDao;
import cn.boz.robotComSys.dao.UserDao;
import cn.boz.robotComSys.pojo.*;
import cn.boz.robotComSys.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class RobotController {

    private static Logger LOGGER = LoggerFactory.getLogger(AppMain.class);

    @Resource
    private StoryDao storyDao;

    @Resource
    private UserDao userDao;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private AppConfiguration appConfiguration;

    @Resource
    private StoryItemDao storyItemDao;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/responseResult")
    public ResponseResult queryResponseResult() {
        ResponseResult responseResult = new ResponseResult(200, "success", null);
        return responseResult;
    }

    @PostMapping("/addUser")
    public ResponseResult addUser(@Valid @RequestBody User user) throws NoSuchAlgorithmException {
        String password = user.getPassword();
        user.setPassword(MyUtils.encryptPassword(password));
        if (StringUtils.isEmpty(user.getRole())) {
            user.setRole("student");
        }
        if (userDao.queryByName(user.getUsername()) != null) {
            return new ResponseResult(400, "用户已经存在");
        } else {
            userDao.addUser(user);
            return new ResponseResult(200, "添加用户成功");
        }
    }

    @PostMapping("/login")
    public ResponseResult login(String username, String password) {
        LOGGER.info(username + " is logging...");
        Map<String, Object> map = new HashMap<>();
        User user = userDao.queryByName(username);
        if (user == null) {
            return new ResponseResult(400, "用户名或密码错误");
        }
        if (!user.getPassword().equals(password)
                && !user.getPassword().equals(MyUtils.encryptPassword(password))) {
            return new ResponseResult(400, "用户名或密码错误");
        }
        String s = UUID.randomUUID().toString().replace("-", "");
        byte[] encode = Base64.getEncoder().encode(s.getBytes());
        String token = new String(encode);
        accessTokenDao.addAccessToken(new AccessToken(null, new Date()
                , token, user.getId(), (long) (1000 * 60 * 24)));

        map.put("token", token);
        map.put("role", "student");
        map.put("lv",user.getLevel());
        map.put("icon",user.getIcon());
        map.put("id",user.getId());
        ResponseResult responseResult = new ResponseResult(200, "欢迎回来~", map);
        return responseResult;
    }

    @PostMapping( value={"/loadStory/{lv}","/loadStory"})
    public ResponseResult loadStory(@PathVariable(value = "lv",required = false) Integer lv){
        List<Story> story = storyDao.findStory(lv);
        return new ResponseResult(200,"获取成功!",story);
    }

    @GetMapping("/download/*")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("downloaded..");
        File file = new File(appConfiguration.getProfile() + "/imgs/938e9a73908a6f3b692a3a829abeca9d.jpeg");
        response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
        System.out.println(file.canRead());
//        response.setHeader("Content-Length",file.getTotalSpace()+"");
        try (FileInputStream fileInputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream(); ) {
            fileInputStream.transferTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/loadStoryItem/{storyId}")
    public ResponseResult findStoryItem(@PathVariable String storyId){
        List<StoryItem> storyItems = storyItemDao.queryStoryItems(storyId);
        return new ResponseResult(200,"获取成功!",storyItems);
    }

}
