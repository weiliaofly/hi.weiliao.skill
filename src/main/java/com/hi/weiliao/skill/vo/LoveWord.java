package com.hi.weiliao.skill.vo;

import java.util.List;

public class LoveWord {
    private String id;
    private String skillName; //名字
    private String skillContent; //内容

    private Integer copyNum; //拷贝次数
    private Integer abulous; //赞
    private Integer comment; //评论数量

    private String creator;
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillContent() {
        return skillContent;
    }

    public void setSkillContent(String skillContent) {
        this.skillContent = skillContent;
    }

    public Integer getCopyNum() {
        return copyNum;
    }

    public void setCopyNum(Integer copyNum) {
        this.copyNum = copyNum;
    }

    public Integer getAbulous() {
        return abulous;
    }

    public void setAbulous(Integer abulous) {
        this.abulous = abulous;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
