package com.example.demosfile.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Img {
    private Integer id;
    private String imgName;
    private String imgUrl;
    private String ImgNativeName;
    private Integer ShengXiao;
    @DateTimeFormat
    @JsonFormat
    private Date createDate;
    @DateTimeFormat
    @JsonFormat
    private Date updateDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgNativeName() {
        return ImgNativeName;
    }

    public void setImgNativeName(String imgNativeName) {
        ImgNativeName = imgNativeName;
    }

    public Integer getShengXiao() {
        return ShengXiao;
    }

    public void setShengXiao(Integer shengXiao) {
        ShengXiao = shengXiao;
    }



}
