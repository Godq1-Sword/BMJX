package com.gdq.domain;

/**
 * program: HighSpeedScanner
 * description: 人脸比对返回参数
 *
 * @author : gdq
 * data : 2019-11-08 11:35
 **/
public class FaceSerachResponse {
    private String errorMsg;
    private String errorCode;
    private String identifyCard;
    private String identifyName;
    private float score;

    public FaceSerachResponse(String errorMsg, String errorCode, String identifyCard, String identifyName, float score) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.identifyCard = identifyCard;
        this.identifyName = identifyName;
        this.score = score;
    }

    public String getIdentifyCard() {
        return identifyCard;
    }

    public String getIdentifyName() {
        return identifyName;
    }

    public float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "FaceSerachResponse{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", identifyCard='" + identifyCard + '\'' +
                ", identifyName='" + identifyName + '\'' +
                ", score=" + score +
                '}';
    }
}
