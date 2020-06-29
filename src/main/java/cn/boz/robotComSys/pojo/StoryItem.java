package cn.boz.robotComSys.pojo;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("storyitem")
public class StoryItem {

  private String id;
  private String soundurl;
  private String storyid;
  private String text;
  private String imgurl;
  private Integer order;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getSoundurl() {
    return soundurl;
  }

  public void setSoundurl(String soundurl) {
    this.soundurl = soundurl;
  }


  public String getStoryid() {
    return storyid;
  }

  public void setStoryid(String storyid) {
    this.storyid = storyid;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImgurl() {
    return imgurl;
  }

  public void setImgurl(String imgurl) {
    this.imgurl = imgurl;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }
}
