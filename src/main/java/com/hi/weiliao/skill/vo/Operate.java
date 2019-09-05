package com.hi.weiliao.skill.vo;

public class Operate {
    private String id;
    private String object;
    private String objectId;
    private Integer operate;

    private String creator;
    private String createDate;

    public Operate(){}

    public Operate(String object, String objectId, Integer operate, String creator){
        this.object = object;
        this.objectId = objectId;
        this.operate = operate;
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
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
