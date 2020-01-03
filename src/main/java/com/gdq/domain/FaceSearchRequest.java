package com.gdq.domain;

import java.io.Serializable;

/**
 * program: HighSpeedScanner
 * description: 人脸比对请求参数
 *
 * @author : gdq
 * data : 2019-11-08 11:40
 **/
public class FaceSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 姓名
    private String identifyName;

    // 身份证号
    private String identifyCard;

    // 身份证照片Base64
    private String identifyPhoto;

    // 高拍仪照片Base64
    private String cameraPhoto;

    public FaceSearchRequest() {
    }


    public void setIdentifyName(String identifyName) {
        this.identifyName = identifyName;
    }


    public void setIdentifyCard(String identifyCard) {
        this.identifyCard = identifyCard;
    }


    public void setIdentifyPhoto(String identifyPhoto) {
        this.identifyPhoto = identifyPhoto;
    }

    public void setCameraPhoto(String cameraPhoto) {
        this.cameraPhoto = cameraPhoto;
    }

    @Override
    public String toString() {
        return "FaceSearchRequest{" +
                "identifyName='" + identifyName + '\'' +
                ", identifyCard='" + identifyCard + '\'' +
                ", identifyPhoto='" + identifyPhoto + '\'' +
                ", cameraPhoto='" + cameraPhoto + '\'' +
                '}';
    }
}
