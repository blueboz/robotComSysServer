package cn.boz.robotComSys.dao;

import cn.boz.robotComSys.pojo.AccessToken;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class AccessTokenDao {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 获取访问token
     * @param token
     * @return
     */
    public AccessToken queryAccessToken(String token){
        Query query = query(where("token").is(token));
        List<AccessToken> accessTokens = mongoTemplate.find(query, AccessToken.class);
        if(CollectionUtils.isEmpty(accessTokens)){
            return null;
        }
        return accessTokens.get(0);
    }

    /**
     * 获取访问token
     * @param userid
     * @return
     */
    public List<AccessToken> queryAccessTokenByUserId(String userid){
        Query query = query(where("userid").is(userid));
        List<AccessToken> accessTokens = mongoTemplate.find(query, AccessToken.class);
        return accessTokens;
    }

    /**
     * 添加一个accessToken ,为了保证accessToken 不过多，只保留一个
     * @param accessToken
     */
    public void addAccessToken(@Valid AccessToken accessToken ){
        String userid = accessToken.getUserid();
        List<AccessToken> accessTokens = queryAccessTokenByUserId(userid);
        mongoTemplate.remove(query(where("userid")
                .is(userid)), AccessToken.class);
        mongoTemplate.insert(accessToken);
    }

    /**
     * 通过id 进行查询
     * @param id
     * @return
     */
    public AccessToken queryAccessTokenById(String id){

        return mongoTemplate.findById(id, AccessToken.class);
    }


    /**
     * 按照指定的id 进行删除
     * @param id
     */
    public void removeAccessToken(String id){
        mongoTemplate.remove(query(where(id).is(id)), AccessToken.class);
    }

}
