package com.simple.common.rest;

import com.simple.error.ErrorCode;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenkx
 * @date 2018-01-05.
 */
public class ResultData extends Result {

    public Object data;

    public ResultData() {
        super();
    }

    public ResultData(Object data) {
        this();
        this.data = data;
    }


    public static ResultData returnResultObject(int code) {
        String value = ErrorCode.getByCode(code).getMessage();
        return new ResultData(code, value);
    }


    public ResultData(Enum<?>[] ens) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Enum<?> en : ens) {
            Map<String, Object> map = enumToMap(en);
            list.add(map);
        }
        data = list;
    }


    private Map<String, Object> enumToMap(Enum<?> en) {
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo info = Introspector.getBeanInfo(en.getClass());
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor property : descriptors) {
                if (property.getPropertyType().getName().equals(Class.class.getName())) {
                    continue;
                }
                Object value = property.getReadMethod().invoke(en);
                map.put(property.getName(), value);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return map;
    }


    public ResultData(int code, Exception e) {
        super(code, e.toString());
    }


    public ResultData(int code, String message) {
        super(code, message);
    }

    public ResultData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("code", this.code);
        map.put("message", this.message);
        map.put("data", data);
        return map;
    }

}
