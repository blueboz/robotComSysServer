import cn.boz.robotComSys.AppMain;
import cn.boz.robotComSys.config.AppConfiguration;
import cn.boz.robotComSys.dao.ExamItemDao;
import cn.boz.robotComSys.dao.StoryDao;
import cn.boz.robotComSys.dao.StoryItemDao;
import cn.boz.robotComSys.dao.UserDao;
import cn.boz.robotComSys.pojo.*;
import cn.boz.robotComSys.service.AccessTokenService;
import cn.boz.robotComSys.utils.MyUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
public class JunitTest {

    @Resource
    AccessTokenService accessTokenService;

    @Resource
    StoryDao storyDao;

    @Test
    public void doTest() {
        System.out.println(accessTokenService.isAccessTokenExpire("5eef29c2f09deb143a18de8d"));
    }

    @Test
    public void doTestFindStory() {
        List<Story> story = storyDao.findStory(2);
        story.forEach(it -> {
            System.out.println(JSON.toJSONString(it, true));
        });
    }

    @Test
    public void doTestAddStory() {
        Story story = new Story();
        story.setPubDate(new Date());
        story.setStorydesc("这个故事没什么可以说的");
        story.setStoryname("期中考试开始了!");
        story.setLv(2);
        story.setImg("938e9a73908a6f3b692a3a829abeca9d");
        storyDao.addStroy(story);
        ;

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

    @Test
    public void doTestFindUser() {
        User jj = userDao.queryByName("jj");
        System.out.println(JSON.toJSON(jj));
    }

    @Resource
    private AppConfiguration appConfiguration;

    @Test
    public void doTestAutoConfig() {
        System.out.println(appConfiguration.getProfile());
    }

    @Resource
    private StoryItemDao storyItemDao;

    @Test
    public void testFor() {
        List<StoryItem> storyItems = storyItemDao.queryStoryItems("5ef4caf48881ba204705e247");
        storyItems.stream().forEach(it -> {
            System.out.println(JSON.toJSONString(it));
        });

    }

    @Test
    public void testQueryUser() {
        User jj = userDao.queryByName("jj");
        System.out.println(JSON.toJSONString(jj));
    }

    @Resource
    private ExamItemDao examItemDao;

    @Test
    public void test() {
//        examItemDao.addExamItem();
        List<ExamItemByDay> allByDay = examItemDao.findAllByDay();
        if (allByDay != null) {
            allByDay.stream().forEach(it -> {
                System.out.println(JSON.toJSONString(it));
            });
        }

    }


    @Test
    public void testStudentByDay() {
        List<HashMap> hashMaps = examItemDao.studentItemsByDay("2020-06-27");
        if (hashMaps != null) {
            List<ExamItemByDayUserStory> collect = hashMaps.stream().map(it -> {
                String storyid = (String) it.get("storyid");
                String userid = (String) it.get("userid");
                Story story = storyDao.findById(storyid);
                User user = userDao.queryById(userid);
                ExamItemByDayUserStory examItemByDayUserStory = new ExamItemByDayUserStory();
                examItemByDayUserStory.setStory(story.getStoryname());
                examItemByDayUserStory.setIcon(user.getIcon());
                examItemByDayUserStory.setUsername(user.getUsername());
                examItemByDayUserStory.setStoryid(story.getId());
                examItemByDayUserStory.setId(storyid+userid);
                return examItemByDayUserStory;
            }).collect(Collectors.toList());
            collect.stream().forEach(it -> {
                System.out.println(JSON.toJSONString(it));
            });
        }
    }


    @Test
    public void testExamItemByDetail(){
        List<ExamItemByDetail> examItemsByDetail = examItemDao.findExamItemsByDetail("2020-06-25", "5ef5c802f2ac765319ef16ac", "5ef4caf48881ba204705e247");
        examItemsByDetail.stream().forEach(it->{
            System.out.println(JSON.toJSONString(it));
        });
    }

    @Test
    public  void testupdateExamItemMark(){
        examItemDao.updateExamItemMark("5ef71dd44d1acb27d0c8727a",10);
    }



}
