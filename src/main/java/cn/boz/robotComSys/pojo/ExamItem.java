package cn.boz.robotComSys.pojo;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("examitem")
public class ExamItem {

  private String id;
  private String userid;
  private String storyid;
  private String storyitemid;
  private Integer mark;
  private String soundurl;
  private Date inserttime;
  private Date updateTime;
  private Integer updatecount ;

  public ExamItem() {
  }

  public ExamItem(String userid, String storyid, String storyitemid, String soundurl, Date inserttime) {
    this.userid = userid;
    this.storyid = storyid;
    this.storyitemid = storyitemid;
    this.soundurl = soundurl;
    this.inserttime = inserttime;
  }

  public ExamItem(String userid, String storyid, String storyitemid, String soundurl) {
    this.userid = userid;
    this.storyid = storyid;
    this.storyitemid = storyitemid;
    this.soundurl = soundurl;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStoryid() {
    return storyid;
  }

  public void setStoryid(String storyid) {
    this.storyid = storyid;
  }

  public String getStoryitemid() {
    return storyitemid;
  }

  public void setStoryitemid(String storyitemid) {
    this.storyitemid = storyitemid;
  }

  public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

  public String getSoundurl() {
    return soundurl;
  }

  public void setSoundurl(String soundurl) {
    this.soundurl = soundurl;
  }

  public Date getInserttime() {
    return inserttime;
  }

  public void setInserttime(Date inserttime) {
    this.inserttime = inserttime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getUpdatecount() {
    return updatecount;
  }

  public void setUpdatecount(Integer updatecount) {
    this.updatecount = updatecount;
  }
}
