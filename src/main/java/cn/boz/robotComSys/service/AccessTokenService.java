package cn.boz.robotComSys.service;

import cn.boz.robotComSys.dao.AccessTokenDao;
import cn.boz.robotComSys.pojo.AccessToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccessTokenService {

    @Resource
    private AccessTokenDao accessTokenDao;

    /**
     * 校验accessToken是否过期
     * @param id
     * @return
     */
    public boolean isAccessTokenExpire(String id){
        AccessToken accessToken = accessTokenDao.queryAccessTokenById(id);
        Long expired = accessToken.getExpired();
        if(expired==null){
            expired= Long.valueOf(1000*60*60*24);//默认24小时超时
        }
        long current=System.currentTimeMillis();
        if(accessToken.getTime().getTime()+expired>current){
            return false;
        }
        return true;
    }
}
