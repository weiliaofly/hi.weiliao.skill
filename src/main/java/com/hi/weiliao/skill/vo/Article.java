package com.hi.weiliao.skill.vo;

public class Article {
    private String id;
    private String articleName;
    private String title; //标题
    private String content; //内容

    private Integer view = 0; //查看次数
    private Integer abulous = 0; //赞
    private Integer comment = 0; //评论数量
    private Integer forward = 0; //转发数量

    private Integer like = 0; //喜欢数量

    private Integer status = 0; //状态，0-草稿，1-待审核，2-发布， 3-删除
    private Integer type = 3; //类型，0-自创，1-转载， 3-其他

    private String forwardOriginal; //转载原始文章
    private String description; //备注

    private String creator;
    private String lastUpdateDate;
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
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

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getForwardOriginal() {
        return forwardOriginal;
    }

    public void setForwardOriginal(String forwardOriginal) {
        this.forwardOriginal = forwardOriginal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
