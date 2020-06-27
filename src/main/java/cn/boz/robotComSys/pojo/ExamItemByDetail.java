package cn.boz.robotComSys.pojo;

/**
 * 最终返回结果
 */
public class ExamItemByDetail {

    private String id;
    private String demourl;
    private String answerurl;
    private Integer mark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemourl() {
        return demourl;
    }

    public void setDemourl(String demourl) {
        this.demourl = demourl;
    }

    public String getAnswerurl() {
        return answerurl;
    }

    public void setAnswerurl(String answerurl) {
        this.answerurl = answerurl;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
