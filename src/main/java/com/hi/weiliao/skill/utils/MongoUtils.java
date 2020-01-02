package com.hi.weiliao.skill.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.skill.vo.common.PageBean;
import com.mongodb.QueryOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoUtils {

    /**
     * 使用查询json构建查询参数对象
     * @param json 参数json
     * @return
     */
    public static Criteria buildQuery(String json){
        Criteria criteria = new Criteria();
        JSONObject jsonObject = JSON.parseObject(json);
        if(jsonObject == null){
            return criteria;
        }
        buildQueryBase(jsonObject, criteria);
        return criteria;
    }

    /**
     * 使用查询jsonObject构建查询参数对象
     * @param jsonObject 参数jsonObject
     * @return
     */
    public static Criteria buildQuery(JSONObject jsonObject){
        Criteria criteria = new Criteria();
        buildQueryBase(jsonObject, criteria);
        return criteria;
    }

    /**
     * 使用查询jsonObject构建查询参数对象
     * @param jsonObject 参数jsonObject
     * @param pageBean pageBean
     * @return
     */
    public static Query buildQueryByPage(JSONObject jsonObject, PageBean<? extends Object> pageBean){
        Criteria criteria = buildQuery(jsonObject);
        Query query = new Query(criteria);
        query.skip(pageBean.getPageSxize() * (pageBean.getPageIndex() - 1)).limit(pageBean.getPageSxize());
        return query;
    }

    /**
     * 使用查询jsonObject构建查询参数对象
     * @param json json
     * @param pageBean pageBean
     * @return
     */
    public static Query buildQueryByPage(String json, PageBean<? extends Object> pageBean){
        Criteria criteria = buildQuery(json);
        Query query = new Query(criteria);
        query.skip(pageBean.getPageSxize() * (pageBean.getPageIndex() - 1)).limit(pageBean.getPageSxize());
        return query;
    }

    /**
     * 使用查询jsonObject构建查询参数对象
     * @param jsonObject 参数jsonObject
     * @return
     */
    private static void buildQueryBase(JSONObject jsonObject, Criteria criteria){
        jsonObject.forEach((K, V) -> {
            K = K.equals("id") ? "_id" : K;
            switch (K){
                case QueryOperators.OR: //至少一个满足---数组
                    Criteria criteriaOr = new Criteria();
                    buildQueryBase((JSONObject)V, criteriaOr);
                    criteria.orOperator(criteriaOr);
                    break;
                case QueryOperators.AND: //全部满足---数组
                    buildQueryBase((JSONObject)V, criteria);
                    break;
                case QueryOperators.NOR: //全部不满足---数组
                    Criteria criteriaNor = new Criteria();
                    buildQueryBase((JSONObject)V, criteriaNor);
                    criteria.norOperator(criteriaNor);
                    break;
                case QueryOperators.NOT: //不为空
                    criteria.not();
                    break;
                case QueryOperators.GT: //大于
                    criteria.gt(V);
                    break;
                case QueryOperators.GTE: //大于等于
                    criteria.gte(V);
                    break;
                case QueryOperators.LT: //小于
                    criteria.lt(V);
                    break;
                case QueryOperators.LTE: //小于等于
                    criteria.lte(V);
                    break;
                case QueryOperators.ALL: //List字段中包含所有V中的数据
                    criteria.all(V);
                    break;
                case QueryOperators.IN: //批量in查询
                    criteria.in((List)V);
                    break;
                case QueryOperators.NIN: //批量not in 查询
                    criteria.nin(V);
                    break;
                case QueryOperators.NE: //不等于
                    criteria.ne(V);
                    break;
                case QueryOperators.EXISTS: //是否存在该字段
                    criteria.exists((boolean)V);
                    break;
                case QueryOperators.MOD: //取模，K字段可以被V[0]取余为V[1]的所有文档
                    List<Integer> value = (List<Integer>)V;
                    criteria.mod(value.get(0), value.get(1));
                    break;
                case QueryOperators.TYPE: //数据类型 BSON type 例如：BSON.ARRAY
                    criteria.type((byte)V);
                    break;
                case "$like": //此处默认为正则匹配
                    criteria.regex("^.*" + V + ".*$");
                    break;
                case "$regex": //正则表达式匹配， options i,m,s,x四个选项(具体意义百度)
                    if(V instanceof String){
                        criteria.regex(V.toString());
                        break;
                    }
                    List<String> values = (List<String>)V;
                    criteria.regex(values.get(0), values.get(1));
                    break;
                case QueryOperators.WHERE: //where 条件支持JS代码，也可以是JS方法
                    criteria.where(V.toString());
                    break;
                default:
                    if(V instanceof JSONObject){
                        buildQueryBase((JSONObject)V, criteria.and(K));
                        break;
                    }else {
                        criteria.and(K).is(V);
                    }
            }
        });
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"key\":\"aaa\",\n" +
                "    \"$or\":{\n" +
                "        \"createDate\":{\n" +
                "            \"$gte\":\"2019-09-01\",\n" +
                "            \"$lte\":\"2019-09-07\"\n" +
                "        },\n" +
                "        \"updateDate\":{\n" +
                "            \"$gte\":\"2019-09-01\",\n" +
                "            \"$lte\":\"2019-09-07\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Criteria criteria = buildQuery(json);
        System.out.println(JSON.toJSONString(criteria));
    }
}
