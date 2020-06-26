package cn.boz.robotComSys.dao;

import cn.boz.robotComSys.pojo.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * 课题Dao
 */
@Repository
public class StoryDao {

    private static Logger LOGGER=LoggerFactory.getLogger(StoryDao.class);

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 查询Story
     * @param lvl 按照登记查询
     * @return
     */
    public List<Story> findStory(Integer lvl){
        if(lvl==null){
            return mongoTemplate.findAll(Story.class);
        }else{
            return mongoTemplate.find(query(where("lv").is(lvl)),Story.class);
        }
    }

    /**
     * 添加Story
     * @param story
     */
    public void addStroy(Story story){
        mongoTemplate.insert(story);
    }

}
