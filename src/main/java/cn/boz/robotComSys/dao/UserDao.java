package cn.boz.robotComSys.dao;

import cn.boz.robotComSys.pojo.User;
import com.mongodb.MongoTimeoutException;
import org.springframework.data.mongodb.core.ExecutableFindOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class UserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<User> queryUser() {
        return mongoTemplate.findAll(User.class);
    }

    public User queryById(String id){
        return mongoTemplate.findById(id,User.class);
    }

    public User queryByName(String username) {
        List<User> list = mongoTemplate.find(query(where("username").is(username)), User.class);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void addUser(User user) {
        User user1 = queryByName(user.getUsername());
        if (user1 != null) {
            Update update=new Update();
            update.set("username",user.getUsername());
            update.set("password",user.getPassword());
            update.set("role",user.getRole());
            update.set("icon",user.getIcon());
            mongoTemplate.updateFirst(query(Criteria.where("id").is(user.getId())),
                    Update.update("username", user.getUsername()), User.class);
        } else {
            mongoTemplate.insert(user);
        }

    }


}
