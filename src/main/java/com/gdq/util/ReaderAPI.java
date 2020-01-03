package com.gdq.util;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * program : HighSpeedScanner
 * description: 读卡器驱动
 *
 * @author : gdq
 * data : 2019-11-13 09:31
 **/
public interface ReaderAPI extends Library {
    ReaderAPI instanceDll = Native.loadLibrary("/dll/Id_Synjones.dll", ReaderAPI.class);

    int initDriver(Object callback);

    int ReadRawInfo(byte[] resmsg, byte[] errmsg, int sameCardReadOnce);

    void getPhotoBmpFromWlt(byte[] wlt);
}
