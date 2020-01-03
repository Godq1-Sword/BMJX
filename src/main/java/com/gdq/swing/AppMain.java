package com.gdq.swing;

import javax.swing.*;
import java.awt.*;

import com.gdq.panel.CameraPanel;
import com.gdq.panel.GlobalPanel;
import com.gdq.util.ImageUtil;
import com.gdq.util.PropertiesUtil;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 * @author God_q1 create in 2019/11/7
 */
public class AppMain {
    //总面板
    private static GlobalPanel globalPanel;
    //程序窗口
    private static JFrame jFrame;

    //初始化窗口
    static {
        try {
            String filePath = System.getProperty("user.dir") + "/conf/dll/opencv_java411.dll";
            System.load(filePath);
        } catch (Exception e) {
            System.out.println("加载OpenCV dll失败");
        }
        //屏幕宽高
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame = new JFrame("人脸识别高拍仪");
        //关闭执行退出
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(((int) screen.getWidth()), (int) (screen.getHeight()));
        //最大化
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //前置
        jFrame.setAlwaysOnTop(true);
        //大小固定
        jFrame.setResizable(true);
        //菜单栏照片
        jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(AppMain.class.getResource("/image/logo.png")));
        globalPanel = new GlobalPanel();
    }

    public static void main(final String[] args) {

        CameraPanel cameraPanel = globalPanel.getCameraPanel();
        //渲染绘制摄像头画面
        VideoCapture capture = new VideoCapture(Integer.parseInt(PropertiesUtil.getProperty("cameraFlag")));
        globalPanel.addActionListener(capture);
        jFrame.setContentPane(globalPanel);
        new Thread(() -> {
            Mat capImg = new Mat();
            //检测摄像头
            if (!capture.isOpened()) {
                cameraPanel.errorHandle();
                return;
            }
            while (true) {
                capture.read(capImg);
                try {
                    cameraPanel.faceDetector(capImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cameraPanel.setmImg(ImageUtil.mat2BI(capImg));
                cameraPanel.repaint();
            }
        }).start();
        globalPanel.setLayout();
        jFrame.setVisible(true);
    }
}
