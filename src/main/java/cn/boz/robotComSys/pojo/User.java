package cn.boz.robotComSys.pojo;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Document("user")
public class User {

    @Null(message = "id必须为空")
    private String id;

    //用户图标
    private String icon;

    //角色
    private String role;

    //用户名
    @NotBlank(message = "用户名不能为空")
    private String username;

    //密码
    @NotBlank(message = "密码不能为空")
    private String password;

    //学生登记
    @NotBlank(message = "学生等级不准为空")
    private Integer level;

    public User() {
    }

    public User(@Null(message = "id必须为空") String id, String icon, String role, @NotBlank(message = "用户名不能为空") String username, @NotBlank(message = "密码不能为空") String password, @NotBlank(message = "学生等级不准为空") Integer level) {
        this.id = id;
        this.icon = icon;
        this.role = role;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
