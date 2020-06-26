package cn.boz.robotComSys.pojo;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("examitem")
public class ExamItem {

  private String id;
  private String examid;
  private double mark;
  private String soundurl;
  private String storyitemid;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getExamid() {
    return examid;
  }

  public void setExamid(String examid) {
    this.examid = examid;
  }


  public double getMark() {
    return mark;
  }

  public void setMark(double mark) {
    this.mark = mark;
  }


  public String getSoundurl() {
    return soundurl;
  }

  public void setSoundurl(String soundurl) {
    this.soundurl = soundurl;
  }


  public String getStoryitemid() {
    return storyitemid;
  }

  public void setStoryitemid(String storyitemid) {
    this.storyitemid = storyitemid;
  }

}
