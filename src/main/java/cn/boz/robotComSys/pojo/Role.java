package cn.boz.robotComSys.pojo;


import org.springframework.data.mongodb.core.mapping.Document;

@Document("role")
public class Role {

  private String id;
  private String rolename;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getRolename() {
    return rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
  }

}
