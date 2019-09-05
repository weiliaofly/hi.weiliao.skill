package com.hi.weiliao.skill.vo;

public class ResponseBean {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String PARAM_ERROR = "PARAM_ERROR";
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 500;
    public static final Integer PARAM_ERROR_CODE = 400;

    private Integer code = SUCCESS_CODE;
    private String message = this.SUCCESS;
    private Object results;

    public ResponseBean(){

    }

    public ResponseBean(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseBean(Integer code, String message, Object results){
        this.code = code;
        this.message = message;
        this.results = results;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }
}
