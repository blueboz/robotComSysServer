import cn.boz.robotComSys.AppMain;
import cn.boz.robotComSys.config.AppConfiguration;
import cn.boz.robotComSys.dao.StoryDao;
import cn.boz.robotComSys.dao.StoryItemDao;
import cn.boz.robotComSys.dao.UserDao;
import cn.boz.robotComSys.pojo.Story;
import cn.boz.robotComSys.pojo.StoryItem;
import cn.boz.robotComSys.pojo.User;
import cn.boz.robotComSys.service.AccessTokenService;
import cn.boz.robotComSys.utils.MyUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
public class JunitTest {

    @Resource
    AccessTokenService accessTokenService;

    @Resource
    StoryDao storyDao;

    @Test
    public void doTest(){
        System.out.println(accessTokenService.isAccessTokenExpire("5eef29c2f09deb143a18de8d"));
    }

    @Test
    public void doTestFindStory(){
        List<Story> story = storyDao.findStory(2);
        story.forEach(it->{
            System.out.println(JSON.toJSONString(it,true));
        });
    }

    @Test
    public void doTestAddStory(){
        Story story = new Story();
        story.setPubDate(new Date());
        story.setStorydesc("这个故事没什么可以说的");
        story.setStoryname("期中考试开始了!");
        story.setLv(2);
        story.setImg("938e9a73908a6f3b692a3a829abeca9d");
        storyDao.addStroy(story);;

    }

    @Resource
    private UserDao userDao;

    @Test
    public void doTestAddUser() throws NoSuchAlgorithmException {
        User user = new User(null, "", "student", "jj", "jj", 2);
        String s = MyUtils.encryptPassword(user.getPassword());
        user.setPassword(s);
        userDao.addUser(user);
    }

    @Resource
    private AppConfiguration appConfiguration;

    @Test
    public void doTestAutoConfig(){
        System.out.println(appConfiguration.getProfile());
    }

    @Resource
    private StoryItemDao storyItemDao;

    @Test
    public void testFor(){
        List<StoryItem> storyItems = storyItemDao.queryStoryItems("5ef4caf48881ba204705e247");
        storyItems.stream().forEach(it->{
            System.out.println(JSON.toJSONString(it));
        });

    }


}
