package com.gdq.panel;

import com.gdq.util.ImageUtil;
import com.gdq.util.ScreenUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * program: HighSpeedScanner
 * description: 摄像头面板
 *
 * @author : gdq
 * data : 2019-11-08 12:00
 **/
public class CameraPanel extends JPanel {
    private BufferedImage mImg;
    private BufferedImage faceImg;

    /**
     * 构造器 初始化参数
     */
    CameraPanel() {
        this.setPreferredSize(new Dimension(ScreenUtil.CAMERAWIDTH, ScreenUtil.CAMERAHIGHT));
        this.setMaximumSize(new Dimension(ScreenUtil.CAMERAWIDTH, ScreenUtil.CAMERAHIGHT));
        Border border = BorderFactory.createTitledBorder(new EmptyBorder(0, 0, 0, 0), "摄像头画面", TitledBorder.LEFT, TitledBorder.TOP, Font.getFont("宋体"), Color.RED);
        this.setBorder(border);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * 绘制组件
     *
     * @param g 画笔
     */
    public void paintComponent(Graphics g) {
        if (mImg != null) {
            g.drawImage(mImg, 0, 0, ScreenUtil.CAMERAWIDTH, ScreenUtil.CAMERAHIGHT, this);
        }
    }

    public void errorHandle() {
        JOptionPane.showMessageDialog(
                this,
                "未检测到摄像头",
                "系统错误",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public void setmImg(BufferedImage mImg) {
        this.mImg = mImg;
    }

    public void faceDetector(Mat capImg) {
        CascadeClassifier faceDetector = new CascadeClassifier(System.getProperty("user.dir") + "/conf/haarcascade_frontalface_alt.xml");
        // 在图片中检测人脸
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(capImg, faceDetections);
        Rect[] rects = faceDetections.toArray();
        if (ObjectUtils.isNotEmpty(rects)) {
            int maxIndex = handleMaxIndex(rects);
            if (rects[maxIndex].x <= 0 || rects[maxIndex].y <= 0) {
                return;
            }
            for (Rect rect : rects) {
                Imgproc.rectangle(capImg, new Point(rect.x - 15, rect.y - 40), new Point(rect.x + rect.width + 30, rect.y + rect.height + 30),
                        new Scalar(0, 0, 255), 1);
            }
        }
    }

    void setFaceImg(Mat capImg) {
        CascadeClassifier faceDetector = new CascadeClassifier(System.getProperty("user.dir") + "/conf/haarcascade_frontalface_alt.xml");
        // 在图片中检测人脸
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(capImg, faceDetections);
        Rect[] rects = faceDetections.toArray();
        if (ObjectUtils.isNotEmpty(rects)) {
            int maxIndex = handleMaxIndex(rects);
            if (rects[maxIndex].x <= 0 || rects[maxIndex].y <= 0) {
                return;
            }
            faceImg = ImageUtil.cutPhotoImg(capImg, rects[maxIndex].x - 20, rects[maxIndex].y - 40, rects[maxIndex].width + 30, rects[maxIndex].height + 60);
        }
    }

    private int handleMaxIndex(Rect[] rects) {
        int maxIndex = 0;
        double maxArea = 0;
        for (int i = 0; i < rects.length; i++) {
            if (maxArea < rects[i].width * rects[i].height) {
                maxArea = rects[i].width * rects[i].height;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    BufferedImage getFaceImg() {
        return faceImg;
    }
}