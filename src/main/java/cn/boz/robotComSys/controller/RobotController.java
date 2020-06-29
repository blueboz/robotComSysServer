package cn.boz.robotComSys.controller;

import cn.boz.robotComSys.AppMain;
import cn.boz.robotComSys.config.AppConfiguration;
import cn.boz.robotComSys.dao.*;
import cn.boz.robotComSys.pojo.*;
import cn.boz.robotComSys.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @RequestMapping("/fuck")
    public ResponseResult simple() {
        return new ResponseResult(200, "文件上传成功了!");
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
        map.put("role", user.getRole());
        map.put("lv", user.getLevel());
        map.put("icon", user.getIcon());
        map.put("id", user.getId());
        ResponseResult responseResult = new ResponseResult(200, "欢迎回来~", map);
        return responseResult;
    }

    @PostMapping(value = {"/loadStory/{lv}", "/loadStory"})
    public ResponseResult loadStory(@PathVariable(value = "lv", required = false) Integer lv) {
        List<Story> story = storyDao.findStory(lv);
        return new ResponseResult(200, "获取成功!", story);
    }

    @RequestMapping("/download/{type}/{id}.mp3")
    public void downloadFile(@PathVariable String type, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("downloaded..");
        System.out.println(type);
        System.out.println(id);

        File file = new File(appConfiguration.getProfile() + '/' + type + '/' + id);
        if (!file.exists()) {
            response.getOutputStream().println("are you kidding me ?");
            return;
        }
        response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
        System.out.println(file.canRead());
//        response.setHeader("Content-Length",file.getTotalSpace()+"");
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream();) {
            fileInputStream.transferTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/loadStoryItem/{storyId}")
    public ResponseResult loadStoryItem(@PathVariable String storyId) {
        List<StoryItem> storyItems = storyItemDao.queryStoryItems(storyId);
        return new ResponseResult(200, "获取成功!", storyItems);
    }

    @Resource
    private ExamItemDao examItemDao;

    @RequestMapping("/fileupload")
    public ResponseResult upload(@RequestParam String userid, @RequestParam String storyid, @RequestParam String storyitemid, @RequestParam("file") MultipartFile file) {
        System.out.println("uid:" + userid);
        System.out.println("sid:" + storyid);
        System.out.println("siid:" + storyitemid);
        System.out.println("file:"+file.getOriginalFilename());
        String persisitDir = "";
        String suffix = "";
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                int idx=originalFilename.lastIndexOf(".");
                if(idx==-1){
                    return new ResponseResult(400, "文件类型不合法，已经丢弃!");
                }
                suffix = originalFilename.substring(idx);
                List<String> collect = Stream.of(".mp3", ".aac").collect(Collectors.toList());
                if (collect.contains(suffix)) {
                    persisitDir = "sound";
                }
            }
            if(persisitDir.isEmpty()){
                return new ResponseResult(400, "文件类型不合法，已经丢弃!");
            }
            String filenameAfter = UUID.randomUUID().toString().replace("-", "");
            File writeTo = new File(appConfiguration.getProfile(), persisitDir+"/"+filenameAfter+suffix);
            try {
                writeTo.createNewFile();
            } catch (IOException e) {
                LOGGER.error("创建文件失败");
                e.printStackTrace();
            }
            try (InputStream inputStream = file.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(writeTo);) {
                inputStream.transferTo(fileOutputStream);
                ExamItem examItem = new ExamItem(userid,storyid,storyitemid,'/'+persisitDir+"/"+filenameAfter+suffix,new Date());
                examItemDao.addExamItem(examItem);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("文件写入失败!");
            }
            System.out.println(file.getName());
        }
        return new ResponseResult(200, "文件上传成功了!");
    }

    @RequestMapping("/queryExamItemDays")
    public ResponseResult queryDay(){
        List<ExamItemByDay> itemByDays = examItemDao.findAllByDay();
        return new ResponseResult(200, "ok!",itemByDays);
    }

    @RequestMapping("/findStudentItemsByDay")
    public ResponseResult findStudentItemsByDay(@RequestParam String day){
        List<ExamItemByDayUserStory> collect=new ArrayList<>();
        List<HashMap> hashMaps = examItemDao.studentItemsByDay(day);
        if(hashMaps!=null){
            collect = hashMaps.stream().map(it -> {
                String storyid = (String) it.get("storyid");
                String userid = (String) it.get("userid");
                Story story = storyDao.findById(storyid);
                User user = userDao.queryById(userid);
                ExamItemByDayUserStory examItemByDayUserStory = new ExamItemByDayUserStory();
                examItemByDayUserStory.setStory(story.getStoryname());
                examItemByDayUserStory.setIcon(user.getIcon());
                examItemByDayUserStory.setUsername(user.getUsername());
                examItemByDayUserStory.setUserid(user.getId());
                examItemByDayUserStory.setStoryid(story.getId());
                examItemByDayUserStory.setId(storyid+userid);
                return examItemByDayUserStory;
            }).collect(Collectors.toList());
        }
        return new ResponseResult(200, "ok",collect);

    }

    @RequestMapping("/findExamItemsByDetail")
    public ResponseResult findExamItemsByDetail(@RequestParam String day, @RequestParam String userid, @RequestParam String storyid){
        List<ExamItemByDetail> examItemsByDetail = examItemDao.findExamItemsByDetail(day, userid, storyid);
        return new ResponseResult(200, "ok",examItemsByDetail);
    }

    @RequestMapping("/setMarkOfExamItem")
    public ResponseResult setMarkOfExamItem(@RequestParam String examitemid,@RequestParam Integer mark){
        examItemDao.updateExamItemMark(examitemid,mark);
        return new ResponseResult(200, "ok");
    }

}

