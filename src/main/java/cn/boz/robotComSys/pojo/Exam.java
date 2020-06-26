package cn.boz.robotComSys.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("exam")
public class Exam {

  private String id;
  private String date;
  private String storyid;
  private String studentid;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public String getStoryid() {
    return storyid;
  }

  public void setStoryid(String storyid) {
    this.storyid = storyid;
  }


  public String getStudentid() {
    return studentid;
  }

  public void setStudentid(String studentid) {
    this.studentid = studentid;
  }

}
