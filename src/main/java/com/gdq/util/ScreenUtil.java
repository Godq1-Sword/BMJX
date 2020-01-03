package com.gdq.util;

import java.awt.*;

/**
 * program : HighSpeedScanner
 * description: 适应性屏幕
 *
 * @author : gdq
 * data : 2019-11-11 11:27
 **/
public class ScreenUtil {
    private static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREENHIGHT = (int) screen.getHeight();
    private static final int SCREENWIDTH = (int) screen.getWidth();
    public static final int CAMERAHIGHT = (int) (SCREENHIGHT * 0.7);
    public static final int CAMERAWIDTH = (int) (SCREENWIDTH * 0.6);
    public static final int COMPAREHIGHT = (int) (SCREENHIGHT * 0.7);
    public static final int COMPAREWIDTH = (int) (SCREENWIDTH * 0.3);
    public static final int IMAGEWIDTH = (int) (SCREENWIDTH * 0.25);
    public static final int IMAGEHIGHT = (int) (SCREENHIGHT * 0.3);
    public static final int IMAGECOMPAREHIGHT = (int) (SCREENHIGHT * 0.1);
    public static final int CONTROLLERHIGHT = (int) (SCREENHIGHT * 0.2);
    public static final int CONTROLLERWIDTH = (int) (SCREENWIDTH * 0.95);
    public static final int BORDERWIDTH = (int) (SCREENWIDTH * 0.025);
    public static final int BORDERHIGHT = (int) (SCREENHIGHT * 0.025);
    public static final int VBOXSTRUCK = (int) (SCREENHIGHT * 0.01);
    public static final int TIPSWIDTH = (int) (SCREENWIDTH * 0.95);
    public static final int TIPSHIGHT = (int) (SCREENHIGHT * 0.1);
    public static final int BUTTONSWIDTH = (int) (SCREENWIDTH * 0.95);
    public static final int BUTTONSHIGHT = (int) (SCREENHIGHT * 0.1);
}
