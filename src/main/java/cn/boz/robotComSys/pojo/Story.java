package cn.boz.robotComSys.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("story")
public class Story {

  private String id;

  private String img;
  @NotNull
  private Integer lv;

  @Field("pubdate")
  @NotNull
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date pubDate;
  private String storydesc;
  @NotBlank
  private String storyname;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public Integer getLv() {
    return lv;
  }

  public void setLv(Integer lv) {
    this.lv = lv;
  }

  public Date getPubDate() {
    return pubDate;
  }

  public void setPubDate(Date pubDate) {
    this.pubDate = pubDate;
  }

  public String getStorydesc() {
    return storydesc;
  }

  public void setStorydesc(String storydesc) {
    this.storydesc = storydesc;
  }

  public String getStoryname() {
    return storyname;
  }

  public void setStoryname(String storyname) {
    this.storyname = storyname;
  }
}
