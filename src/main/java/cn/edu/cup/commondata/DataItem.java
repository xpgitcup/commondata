/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.cup.commondata;

/**
 *
 * @author LiXiaoping
 */
public class DataItem {

    private DataValueType dataValueType;
    private String unit;
    private String valueString;

    public DataItem(DataValueType dataValueType, String unit, String valueString) {
        this.dataValueType = dataValueType;
        this.unit = unit;
        this.valueString = valueString;
    }

    public DataItem() {
        this.dataValueType = DataValueType.scalar;
        this.unit = "";
        this.valueString = "";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(valueString);
        if (!unit.isEmpty()) {
            sb.append("(").append(unit).append(")");
        }
        return sb.toString();
    }

    public DataValueType getDataValueType() {
        return dataValueType;
    }

    public void setDataValueType(DataValueType dataValueType) {
        this.dataValueType = dataValueType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

}
