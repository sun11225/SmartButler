package com.sun.smartbutler.entity;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.entity
 * 文件名:   WeChatData
 * 创建者:   sun
 * 创建时间: 2019/6/14 0014 10:57
 * 描述:    微信精选实体类
 */
public class WeChatData {

    //标题
    private String title;
    //出版社
    private String source;
    //图片URL
    private String imgUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
