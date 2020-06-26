package cn.boz.robotComSys.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("accesstoken")
public class AccessToken {
  private String id;
  private Date time;
  private String token;
  private String userid;
  private Long expired;

  public AccessToken() {
  }

  public AccessToken(String id, Date time, String token, String userid, Long expired) {
    this.id = id;
    this.time = time;
    this.token = token;
    this.userid = userid;
    this.expired = expired;
  }

  public AccessToken(String id, Date time, String token, String userid) {
    this.id = id;
    this.time = time;
    this.token = token;
    this.userid = userid;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public Long getExpired() {
    return expired;
  }

  public void setExpired(Long expired) {
    this.expired = expired;
  }
}
