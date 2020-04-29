/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.cup.commondata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiXiaoping
 */
public class RawData {

    private final Map<String, DataItem> dataItems;

    public RawData() {
        this.dataItems = new HashMap<>();
    }

    public String exportToString() {
        return JSON.toJSONString(dataItems);
    }

    public String exportToStringWithFormat() {
        return JSON.toJSONString(dataItems, SerializerFeature.PrettyFormat);
    }

    public Map<String, DataItem> getDataItems() {
        return dataItems;
    }

    public void setValue(String key, String value) {
        dataItems.get(key).setValueString(value);
    }

    public void newVector2D(String key, String unit) {
        newItem(key, DataValueType.vector2D, unit, "[{0.0, 0.0}]");
    }

    public void newVector2D(String key, String unit, String valueString) {
        newItem(key, DataValueType.vector2D, unit, valueString);
    }

    public void newVector(String key, String unit) {
        newVector(key, unit, new double[] {0.0});
    }

    public void newVector(String key, String unit, double[] x) {
        newItem(key, DataValueType.vector, unit, JSON.toJSONString(x));
    }

    public void newScalar(String key, String unit, String valueString) {
        newItem(key, DataValueType.scalar, unit, valueString);
    }

    public void newScalar(String key, String unit) {
        newItem(key, DataValueType.scalar, unit, "0.0");
    }

    public void newItem(String key, DataValueType dataValueType, String unit, String valueString) {
        dataItems.put(key, new DataItem(dataValueType, unit, valueString));
    }

}
