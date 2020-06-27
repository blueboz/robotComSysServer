package cn.boz.robotComSys.dao;

import cn.boz.robotComSys.config.AppConfiguration;
import cn.boz.robotComSys.pojo.ExamItem;
import cn.boz.robotComSys.pojo.ExamItemByDay;
import cn.boz.robotComSys.pojo.ExamItemByDetail;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class ExamItemDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<ExamItem> queryExamItem() {
        return null;
    }

    public ExamItem findItemToUpdate(String userId, String storyId, String storyItemId) {
        List<ExamItem> examItems = mongoTemplate.find(query(where("userid").is(userId).and("storyid").is(storyId)
                .and("storyitemid").is(storyItemId)), ExamItem.class);
        if (CollectionUtils.isEmpty(examItems)) {
            return null;
        }
        return examItems.get(0);
    }

    @Resource
    private AppConfiguration appConfiguration;

    public void addExamItem(ExamItem examItem) {
        ExamItem itemToUpdate = findItemToUpdate(examItem.getUserid(), examItem.getStoryid(), examItem.getStoryitemid());
        if (itemToUpdate != null) {
            Update update = new Update();
            update.set("soundurl", examItem.getSoundurl());
            update.set("updatetime", new Date());
            update.set("updatecount", itemToUpdate.getUpdatecount() == null ? 1 : itemToUpdate.getUpdatecount() + 1);
            File file = new File(appConfiguration.getProfile(), itemToUpdate.getSoundurl());
            if (file.exists()) {
                file.delete();
            }
            ;
            //TODO，删除被更新的文件
            mongoTemplate.updateFirst(query(where("_id").is(itemToUpdate.getId())), update, ExamItem.class);
            ;
        } else {
            examItem.setInserttime(new Date());
            examItem.setUpdatecount(0);
            examItem.setMark(-1);
            mongoTemplate.insert(examItem);
        }
    }


    public List<ExamItemByDay> findAllByDay() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project().
                        andExpression("{$dateToString: {format: '%Y-%m-%d', date:'$inserttime'}}").
                        as("insertday"),
                Aggregation.group("insertday")
                        .first("insertday").as("day")
                        .sum("insertday").as("count")
        );
        AggregationResults<ExamItemByDay> aggregate = mongoTemplate.aggregate(aggregation, ExamItem.class, ExamItemByDay.class);
        List<ExamItemByDay> mappedResults = aggregate.getMappedResults();
        return mappedResults;
    }

    public List<HashMap> studentItemsByDay(String day) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("userid", "storyid")
                        .andExpression("{$dateToString: {format: '%Y-%m-%d', date:'$inserttime'}}").as("insertday"),
//                .andExpression("{$concat:['$userid','$storyid']}").as("usid"),
                Aggregation.match(where("insertday").is(day)),
                Aggregation.group("userid", "storyid")
                        .first("userid").as("userid")
                        .first("storyid").as("storyid")
        );
        AggregationResults<HashMap> aggregate = mongoTemplate.aggregate(aggregation, ExamItem.class, HashMap.class);
        List<HashMap> mappedResults = aggregate.getMappedResults();
        return mappedResults;

    }

    public List<ExamItemByDetail> findExamItemsByDetail(String day, String userid, String storyid) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("storyitemid", "soundurl", "mark", "storyid", "userid")
                        .and("soundurl").as("answerurl")
                        .andExpression("{$dateToString: {format: '%Y-%m-%d', date:'$inserttime'}}").as("insertday"),
                Aggregation.match(where("insertday").is(day).and("storyid").is(storyid).and("userid").is(userid))
        );
        AggregationResults<ExamItemByDetail> agg = mongoTemplate.aggregate(aggregation, ExamItem.class, ExamItemByDetail.class);
        List<ExamItemByDetail> mappedResults = agg.getMappedResults();
        return mappedResults;
    }

    /**
     * 更新成绩
     * @param examitemid
     * @param mark
     */
    public void updateExamItemMark(String examitemid, Integer mark) {
        Update update = new Update();
        update.set("mark",mark);
        mongoTemplate.updateFirst(query(where("_id").is(examitemid)),update,ExamItem.class);
    }
}
