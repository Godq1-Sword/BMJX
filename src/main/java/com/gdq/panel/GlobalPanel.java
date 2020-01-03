package com.gdq.panel;

import com.gdq.util.ScreenUtil;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * program : HighSpeedScanner
 * description: 总面板
 *
 * @author : gdq
 * data : 2019-11-08 14:56
 **/
public class GlobalPanel extends JPanel {
    //摄像头面板
    private CameraPanel cameraPanel;
    //对比面板
    private ComparePanel comparePanel;
    //控制面板
    private ControllerPanel controllerPanel;

    public GlobalPanel() {
        //设置窗口与面板间距
        this.setBorder(new EmptyBorder(ScreenUtil.BORDERHIGHT, ScreenUtil.BORDERWIDTH, ScreenUtil.BORDERHIGHT, ScreenUtil.BORDERWIDTH));
        //布局设置
        comparePanel = new ComparePanel();
        cameraPanel = new CameraPanel();
        controllerPanel = new ControllerPanel();
        this.setOpaque(false);
    }

    public void setLayout() {
        GroupLayout groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        //水平串并行
        GroupLayout.SequentialGroup sGourpH = groupLayout.createSequentialGroup().addComponent(cameraPanel).addComponent(comparePanel);
        GroupLayout.ParallelGroup pGroupH = groupLayout.createParallelGroup().addGroup(sGourpH).addComponent(controllerPanel, GroupLayout.Alignment.CENTER);
//        指定布局的水平组
        groupLayout.setHorizontalGroup(pGroupH);
//        垂直串并行
        GroupLayout.ParallelGroup pGroupV = groupLayout.createParallelGroup().addComponent(cameraPanel).addComponent(comparePanel);
        GroupLayout.SequentialGroup sGroupV = groupLayout.createSequentialGroup().addGroup(pGroupV).addComponent(controllerPanel);
//        指定布局的垂直组
        groupLayout.setVerticalGroup(sGroupV);
    }

    public void addActionListener(VideoCapture capture) {
        controllerPanel.addActionListener(comparePanel, cameraPanel,capture);
    }

    public CameraPanel getCameraPanel() {
        return cameraPanel;
    }

}
