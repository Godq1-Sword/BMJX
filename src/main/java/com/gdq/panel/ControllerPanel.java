package com.gdq.panel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdq.domain.FaceSearchRequest;
import com.gdq.domain.FaceSerachResponse;
import com.gdq.domain.IDCard;
import com.gdq.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * program : HighSpeedScanner
 * description: 控制面板
 *
 * @author : gdq
 * data : 2019-11-08 14:46
 **/
class ControllerPanel extends JPanel {
    //按钮
    private JButton tpButton;
    private JButton cdButton;
    private JButton cpButton;

    ControllerPanel() {
        //设置面板大小
        this.setMaximumSize(new Dimension(ScreenUtil.CONTROLLERWIDTH, ScreenUtil.CONTROLLERHIGHT));
        //提示信息 - 数据初始化
        JLabel tipsLable = new JLabel(PropertiesUtil.getProperty("tipText"), JLabel.CENTER);
        tipsLable.setBorder(BorderFactory.createTitledBorder(null, "操作流程提示", TitledBorder.TOP, TitledBorder.CENTER));
        tipsLable.setFont(new Font("宋体", Font.PLAIN, 16));
        tipsLable.setMaximumSize(new Dimension(ScreenUtil.TIPSWIDTH, ScreenUtil.TIPSHIGHT));
        tpButton = new JButton("拍照");
        cdButton = new JButton("身份识别");
        cpButton = new JButton("人脸比对");
        //分组布局
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        //箱布局
        Box hBox = Box.createHorizontalBox();
        hBox.setBorder(BorderFactory.createTitledBorder(null, "按钮操作区", TitledBorder.TOP, TitledBorder.CENTER));
        hBox.setMaximumSize(new Dimension(ScreenUtil.BUTTONSWIDTH, ScreenUtil.BUTTONSHIGHT));
        //填充空白区
        hBox.add(Box.createHorizontalGlue());
        hBox.add(Box.createHorizontalGlue());
        hBox.add(tpButton);
        hBox.add(Box.createHorizontalGlue());
        hBox.add(cdButton);
        hBox.add(Box.createHorizontalGlue());
        hBox.add(cpButton);
        hBox.add(Box.createHorizontalGlue());
        hBox.add(Box.createHorizontalGlue());
        //水平组串并行
        GroupLayout.ParallelGroup pGroupH = groupLayout.createParallelGroup().addComponent(tipsLable).addComponent(hBox);
        //垂直组串并行
        GroupLayout.SequentialGroup sGroupV = groupLayout.createSequentialGroup().addComponent(tipsLable).addComponent(hBox);
        //指定布局的水平组
        groupLayout.setHorizontalGroup(pGroupH);
        //指定布局的垂直组
        groupLayout.setVerticalGroup(sGroupV);
    }

    void addActionListener(ComparePanel comparePanel, CameraPanel cameraPanel, VideoCapture capture) {
        tpButton.addActionListener((event) -> {
            this.tpButton.setEnabled(false);
            new Thread(() -> {
                try {
                    Mat mat = new Mat();
                    capture.read(mat);
                    cameraPanel.setFaceImg(mat);
                    if (ObjectUtils.isNotEmpty(cameraPanel.getFaceImg())) {
                        comparePanel.setPhotoImage(new ImageIcon(cameraPanel.getFaceImg()));
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        BufferedImage photoBi = ImageUtil.Image2BufferImage(((ImageIcon) comparePanel.getPhotoImage().getIcon()).getImage());
                        ImageIO.write(photoBi, "jpg", out);
                        byte[] bytes = out.toByteArray();
                        String photoImageBase64 = Base64Util.encode(bytes);
                        comparePanel.setPhotoImageBase64(photoImageBase64);
                        comparePanel.setFlag(false);
                        comparePanel.setFlagPhoto();
                    }
                    this.tpButton.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.tpButton.setEnabled(true);
                }
            }).start();
        });
        cpButton.addActionListener((event) -> {
            this.cpButton.setEnabled(false);
            new Thread(() -> {
                try {
                    if (comparePanel.isComp()) {
                        JOptionPane.showMessageDialog(
                                cameraPanel,
                                "身份信息已核验,请重新拍照",
                                "提示",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        this.cpButton.setEnabled(true);
                        return;
                    }
                    IDCard idCard = comparePanel.getIdCard();
                    if (ObjectUtils.isEmpty(idCard)) {
                        JOptionPane.showMessageDialog(
                                cameraPanel,
                                "身份证信息未读取,请读取",
                                "提示",
                                JOptionPane.WARNING_MESSAGE
                        );
                        this.cpButton.setEnabled(true);
                        return;
                    }
                    if (comparePanel.isCompPhoto()) {
                        JOptionPane.showMessageDialog(
                                cameraPanel,
                                "尚未拍照,请拍照",
                                "提示",
                                JOptionPane.WARNING_MESSAGE
                        );
                        this.cpButton.setEnabled(true);
                        return;
                    }
                    if (ObjectUtils.isNotEmpty(idCard)) {
                        FaceSearchRequest faceSearchRequest = new FaceSearchRequest();
                        faceSearchRequest.setIdentifyCard(idCard.getIDCardNo());
                        faceSearchRequest.setIdentifyName(idCard.getName());
                        faceSearchRequest.setIdentifyPhoto(comparePanel.getFaceImageBase64());
                        faceSearchRequest.setCameraPhoto(comparePanel.getPhotoImageBase64());
                        String requestBody = JSON.toJSONString(faceSearchRequest);
                        System.out.println("请求信息:" + requestBody);
                        String result = null;
                        String url = PropertiesUtil.getProperty("url");
                        System.out.println("访问地址:" + url);
                        try {
                            result = HttpUtil.doPost(url, requestBody);
                            System.out.println("请求返回数据:" + result);
                        } catch (Exception e) {
                            this.cpButton.setEnabled(true);
                            e.printStackTrace();
                        }
                        if (StringUtils.isNoneBlank(result)) {
                            FaceSerachResponse faceSerachResponse = JSONObject.parseObject(result, FaceSerachResponse.class);
                            assert faceSerachResponse != null;
                            System.out.println(faceSerachResponse);
                            comparePanel.setIdentityInfo(faceSerachResponse);
                            comparePanel.setFlag(true);
                            if (faceSerachResponse.getScore() > Float.parseFloat(PropertiesUtil.getProperty("score"))) {
                                JOptionPane.showMessageDialog(
                                        cameraPanel,
                                        "身份信息核验成功,请重新拍照",
                                        "提示",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            } else {
                                JOptionPane.showMessageDialog(
                                        cameraPanel,
                                        "相似度过低,身份信息核验失败,请重新拍照",
                                        "提示",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        }
                    }
                    this.cpButton.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.cpButton.setEnabled(true);
                }
            }).start();
        });
        new Thread(() -> {
            int status = IDCardUtil.initDriver();
            System.out.println("身份证读卡器驱动加载失败");
            if (status != 0) {
                JOptionPane.showMessageDialog(
                        cameraPanel,
                        "身份证读卡器驱动加载失败",
                        "提示",
                        JOptionPane.WARNING_MESSAGE
                );
                this.cdButton.setEnabled(true);
            }
        }).start();
        cdButton.addActionListener((event) ->
                new Thread(() -> {
                    int status = IDCardUtil.initDriver();
                    System.out.println("身份证驱动加载状态码:" + status);
                    if (status != 0) {
                        JOptionPane.showMessageDialog(
                                cameraPanel,
                                "身份证读卡器驱动加载失败",
                                "提示",
                                JOptionPane.WARNING_MESSAGE
                        );
                        this.cdButton.setEnabled(true);
                        return;
                    }
                    this.cdButton.setEnabled(false);
                    try {
                        IDCard idCard = IDCardUtil.getIdcardInfo(false);
                        if (!ObjectUtils.isEmpty(idCard)) {
                            System.out.println("身份证图片正在解析");
                            byte[] wlt = idCard.getWlt();
                            comparePanel.setIdCard(idCard);
                            String filePath = PropertiesUtil.getProperty("fileWltPath");
                            File file;
                            try {
                                FileOutputStream outputStream = new FileOutputStream(filePath);
                                outputStream.write(wlt);
                                outputStream.close();
                                ReaderAPI.instanceDll.getPhotoBmpFromWlt(filePath.getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            file = new File(PropertiesUtil.getProperty("fileBmpPath"));
                            Image image = ImageUtil.loadBitmap(file);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            try {
                                assert image != null;
                                ImageIO.write(ImageUtil.Image2BufferImage(image), "jpg", out);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] bytes = out.toByteArray();
                            comparePanel.setFaceImageBase64(Base64Util.encode(bytes));
                            comparePanel.setFaceImage(new ImageIcon(image), idCard);
                            System.out.println("身份证图片解析成功");
                            JOptionPane.showMessageDialog(
                                    cameraPanel,
                                    "身份证检测成功",
                                    "提示",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                        this.cdButton.setEnabled(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.cdButton.setEnabled(true);
                    }
                }).start());
    }
}