package com.sun.smartbutler.entity;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.entity
 * 文件名:   CourierData
 * 创建者:   sun
 * 创建时间: 2019/6/13 0013 15:25
 * 描述:    快递实体类
 */
public class CourierData {

    //时间
    private String dateTime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dataTime) {
        this.dateTime = dataTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "dateTime='" + dateTime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
