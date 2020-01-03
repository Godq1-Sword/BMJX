package com.gdq.panel;

import com.gdq.domain.FaceSerachResponse;
import com.gdq.domain.IDCard;
import com.gdq.util.ScreenUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * program : HighSpeedScanner
 * description: 人脸对比面板
 *
 * @author : gdq
 * data : 2019-11-08 13:51
 **/
class ComparePanel extends JPanel {
    //拍照图片
    private JLabel photoImage;
    //信息库图片
    private JLabel faceImage;
    //身份信息
    private JLabel identityInfo;
    private String photoImageBase64;
    private String faceImageBase64;
    private IDCard idCard;
    private boolean flag;
    private boolean flagPhoto;

    ComparePanel() {
        flag = false;
        flagPhoto = false;
        this.setPreferredSize(new Dimension(ScreenUtil.COMPAREWIDTH, ScreenUtil.COMPAREHIGHT));
//        this.setBorder(new LineBorder(Color.red));
        this.setBorder(new EmptyBorder(0, ScreenUtil.BORDERWIDTH * 2, 0, 0));
        //设置布局
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //拍照图片渲染
        Border border = BorderFactory.createTitledBorder(null, "拍照图片", TitledBorder.LEFT, TitledBorder.TOP, Font.getFont("宋体"), Color.BLACK);
        photoImage = new JLabel();
        photoImage.setMinimumSize(new Dimension(ScreenUtil.IMAGEWIDTH, ScreenUtil.IMAGEHIGHT));
        photoImage.setBorder(border);
        Image defaultPhoto = Toolkit.getDefaultToolkit().getImage(ComparePanel.class.getResource("/image/photo.jpg"));
        photoImage.setIcon(new ImageIcon(defaultPhoto.getScaledInstance(ScreenUtil.IMAGEWIDTH - 10, ScreenUtil.IMAGEHIGHT - 20, Image.SCALE_DEFAULT)));
        this.add(photoImage);
        this.addStruk();
        //身份信息渲染
        identityInfo = new JLabel();
        identityInfo.setHorizontalAlignment(JLabel.CENTER);
        identityInfo.setText("暂无比对信息");
        identityInfo.setFont(new Font("宋体", Font.PLAIN, 16));
        identityInfo.setMaximumSize(new Dimension(ScreenUtil.IMAGEWIDTH, ScreenUtil.IMAGECOMPAREHIGHT));
        identityInfo.setVerticalAlignment(JLabel.TOP);
        border = BorderFactory.createTitledBorder(null, "对比信息", TitledBorder.LEFT, TitledBorder.TOP, Font.getFont("宋体"), Color.BLACK);
        identityInfo.setBorder(border);
        this.add(identityInfo);
        this.addStruk();
        //信息库图片渲染
        faceImage = new JLabel();
        faceImage.setFont(new Font("宋体", Font.PLAIN, 16));
        faceImage.setMinimumSize(new Dimension(ScreenUtil.IMAGEWIDTH, ScreenUtil.IMAGEHIGHT));
        faceImage.setMaximumSize(new Dimension(ScreenUtil.IMAGEWIDTH, ScreenUtil.IMAGEHIGHT));
        faceImage.setBorder(new LineBorder(Color.red));
        Image defaultFace = Toolkit.getDefaultToolkit().getImage(ComparePanel.class.getResource("/image/face.jpg"));
        faceImage.setIcon(new ImageIcon(defaultFace.getScaledInstance(ScreenUtil.IMAGEWIDTH - 10, ScreenUtil.IMAGEHIGHT - 20, Image.SCALE_DEFAULT)));
        border = BorderFactory.createTitledBorder(null, "身份库图片", TitledBorder.LEFT, TitledBorder.TOP, Font.getFont("宋体"), Color.BLACK);
        faceImage.setBorder(border);
        this.add(faceImage);
    }

    JLabel getPhotoImage() {
        return photoImage;
    }

    void setPhotoImage(ImageIcon photoImage) {
        photoImage.setImage(photoImage.getImage().getScaledInstance(ScreenUtil.IMAGEWIDTH - 10, ScreenUtil.IMAGEHIGHT - 20, Image.SCALE_DEFAULT));
        this.photoImage.setIcon(photoImage);
    }

    void setFaceImage(ImageIcon faceImage, IDCard idCard) {
        faceImage.setImage(faceImage.getImage().getScaledInstance(ScreenUtil.IMAGEWIDTH / 2, ScreenUtil.IMAGEHIGHT - 20, Image.SCALE_DEFAULT));
        this.faceImage.setIcon(faceImage);
        String sb = "<html>" + "身份证号:" + idCard.getIDCardNo() + "<br>姓名:" + idCard.getName() + "&nbsp;民族:" + idCard.getNation() + "<br>出生日期:" + idCard.getBirthday() + "<br>住址:" + idCard.getAddress() +
                "</html>";
        this.faceImage.setText(sb);
    }

    void setIdentityInfo(FaceSerachResponse faceSerachResponse) {
        String sb = "<html>" + "身份证号:" + faceSerachResponse.getIdentifyCard() + "<br>姓名:" + faceSerachResponse.getIdentifyName() + "<br>相似度:" + faceSerachResponse.getScore() +
                "</html>";
        identityInfo.setText(sb);
    }

    private void addStruk() {
        Component vStrut = Box.createVerticalStrut(ScreenUtil.VBOXSTRUCK);
        this.add(vStrut);
    }

    String getPhotoImageBase64() {
        return photoImageBase64;
    }

    void setPhotoImageBase64(String photoImageBase64) {
        this.photoImageBase64 = photoImageBase64;
    }

    String getFaceImageBase64() {
        return faceImageBase64;
    }

    void setFaceImageBase64(String faceImageBase64) {
        this.faceImageBase64 = faceImageBase64;
    }

    IDCard getIdCard() {
        return idCard;
    }

    void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }

    boolean isComp() {
        return flag;
    }


    void setFlagPhoto() {
        this.flagPhoto = true;
    }

    boolean isCompPhoto() {
        return !flagPhoto;
    }
}
