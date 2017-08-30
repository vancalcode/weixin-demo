package com.minstone.wechat.domain;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Blob;


/**
 * Created by huangyg on 2017/8/28.
 */
public class WxPublic {

    private int wxPublicCode;
    @NotEmpty
    private String wxPublicOpenid;
    @NotEmpty
    private String wxPublicName;
    @NotEmpty
    private String wxPublicNickName;
    @NotEmpty
    private File wxPublicHeadImg;
    @NotEmpty
    private File wxPublicQrcode;
    @NotEmpty
    private String wxPublicAppid;
    @NotEmpty
    private String wxPublicAppSerct;
    @NotEmpty
    private String wxPublicToken;
    @NotEmpty
    private String wxPublicAeskey;
    @NotEmpty
    private String wxPublicUrl;
    @NotEmpty
    private String wxPublicHeadImgName;
    @NotEmpty
    private String wxPublicQrcodeName;

    public String getWxPublicHeadImgName(String imgname) {
        return wxPublicHeadImgName;
    }

    public void setWxPublicHeadImgName(String wxPublicHeadImgName) {
        this.wxPublicHeadImgName = wxPublicHeadImgName;
    }

    public String getWxPublicQrcodeName(String qrcodename) {
        return wxPublicQrcodeName;
    }

    public void setWxPublicQrcodeName(String wxPublicQrcodeName) {
        this.wxPublicQrcodeName = wxPublicQrcodeName;
    }

    public int getWxPublicCode() {
        return wxPublicCode;
    }

    public void setWxPublicCode(int wxPublicCode) {
        this.wxPublicCode = wxPublicCode;
    }

    public String getWxPublicOpenid() {
        return wxPublicOpenid;
    }

    public void setWxPublicOpenid(String wxPublicOpenid) {
        this.wxPublicOpenid = wxPublicOpenid;
    }

    public String getWxPublicName() {
        return wxPublicName;
    }

    public void setWxPublicName(String wxPublicName) {
        this.wxPublicName = wxPublicName;
    }

    public String getWxPublicNickName() {
        return wxPublicNickName;
    }

    public void setWxPublicNickName(String wxPublicNickName) {
        this.wxPublicNickName = wxPublicNickName;
    }

    public File getWxPublicHeadImg() {
        return wxPublicHeadImg;
    }

    public void setWxPublicHeadImg(File wxPublicHeadImg) {
        this.wxPublicHeadImg = wxPublicHeadImg;
    }

    public File getWxPublicQrcode() {
        return wxPublicQrcode;
    }

    public void setWxPublicQrcode(File wxPublicQrcode) {
        this.wxPublicQrcode = wxPublicQrcode;
    }

    public String getWxPublicAppid() {
        return wxPublicAppid;
    }

    public void setWxPublicAppid(String wxPublicAppid) {
        this.wxPublicAppid = wxPublicAppid;
    }

    public String getWxPublicAppSerct() {
        return wxPublicAppSerct;
    }

    public void setWxPublicAppSerct(String wxPublicAppSerct) {
        this.wxPublicAppSerct = wxPublicAppSerct;
    }

    public String getWxPublicToken() {
        return wxPublicToken;
    }

    public void setWxPublicToken(String wxPublicToken) {
        this.wxPublicToken = wxPublicToken;
    }

    public String getWxPublicAeskey() {
        return wxPublicAeskey;
    }

    public void setWxPublicAeskey(String wxPublicAeskey) {
        this.wxPublicAeskey = wxPublicAeskey;
    }

    public String getWxPublicUrl() {
        return wxPublicUrl;
    }

    public void setWxPublicUrl(String wxPublicUrl) {
        this.wxPublicUrl = wxPublicUrl;
    }

}
