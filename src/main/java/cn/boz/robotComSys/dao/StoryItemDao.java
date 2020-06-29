package cn.boz.robotComSys.dao;

import cn.boz.robotComSys.pojo.StoryItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

/**
 * 单元测试详细条目Dao
 */
@Repository
public class StoryItemDao {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 通过storyId查询StoryItems
     * @param storyId 考验Id
     * @return
     */
    public List<StoryItem> queryStoryItems(String storyId) {
        if (storyId==null||storyId.isEmpty()) {
            return new ArrayList<>();
        }
        List<StoryItem> storyid = mongoTemplate.find(query(where("storyid").is(storyId)).with(Sort.by(
                Sort.Order.asc("order")
        )), StoryItem.class);

        return storyid;
    }

    /**
     * 保存
     * @param storyItem
     */
    public void save(StoryItem storyItem){
        mongoTemplate.insert(storyItem);
    }



}
