package com.sun.smartbutler.entity;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.entity
 * 文件名:   ChatListData
 * 创建者:   sun
 * 创建时间: 2019/6/14 0014 8:52
 * 描述:    数据实体类
 */
public class ChatListData {
    private String text;
    private int type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
