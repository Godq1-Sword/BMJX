package com.gdq.util;

import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;

/**
 * program : HighSpeedScanner
 * description: 图像工具类
 *
 * @author : gdq
 * data : 2019-11-11 16:01
 **/
public class ImageUtil {

    /**
     * Mat图像容器转换BufferedImage
     *
     * @param mat 图像容器
     * @return BufferedImage 图像
     */
    public static BufferedImage mat2BI(Mat mat) {
        int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0, 0, data);
        //灰度和3Byte图像
        int type = mat.channels() == 1 ?
                BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        if (type == BufferedImage.TYPE_3BYTE_BGR) {
            for (int i = 0; i < dataSize; i += 3) {
                byte blue = data[i];
                data[i] = data[i + 2];
                data[i + 2] = blue;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }

    public static BufferedImage cutPhotoImg(Mat mat, int x, int y, int width, int height) {
        BufferedImage image = mat2BI(mat);
        if (x + width >= image.getWidth() || y + height >= image.getHeight() || y <= 0 || x <= 0) {
            return null;
        }
        return image.getSubimage(x, y, width, height);
    }

    public static BufferedImage Image2BufferImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        //通过 BufferedImage 绘制图像并保存在其对象中
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return bufferedImage;
    }

    public static Image loadBitmap(File filePath) {
        Image image;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            int bflen = 14; // 14 byte BITMAPFILEHEADER
            byte[] bf = new byte[bflen];
            System.out.println("BMP转换byte[]Size:" + fis.read(bf, 0, bflen));
            byte[] bi = new byte[40];
            System.out.println("BMP转换byte[]Size:" + fis.read(bi, 0, bflen));
            int nwidth = (((int) bi[7] & 0xff) << 24)
                    | (((int) bi[6] & 0xff) << 16)
                    | (((int) bi[5] & 0xff) << 8) | (int) bi[4] & 0xff;
            int nheight = (((int) bi[11] & 0xff) << 24)
                    | (((int) bi[10] & 0xff) << 16)
                    | (((int) bi[9] & 0xff) << 8) | (int) bi[8] & 0xff;
            int nbitcount = (((int) bi[15] & 0xff) << 8) | (int) bi[14] & 0xff;
            int nsizeimage = (((int) bi[23] & 0xff) << 24)
                    | (((int) bi[22] & 0xff) << 16)
                    | (((int) bi[21] & 0xff) << 8) | (int) bi[20] & 0xff;
            int nclrused = (((int) bi[35] & 0xff) << 24)
                    | (((int) bi[34] & 0xff) << 16)
                    | (((int) bi[33] & 0xff) << 8) | (int) bi[32] & 0xff;
            if (nsizeimage == 0) {
                nsizeimage = ((((nwidth * nbitcount) + 31) & ~31) >> 3);
                nsizeimage *= nheight;
            }

            if (nbitcount == 32) {
                int npad = (nsizeimage / nheight) - nwidth * 4;
                int[] ndata = new int[nheight * nwidth];
                byte[] brgb = new byte[(nwidth + npad) * 4 * nheight];
                System.out.println("BMP转换byte[]Size:" + fis.read(brgb, 0, (nwidth + npad) * 4 * nheight));
                int nindex = 0;
                for (int j = 0; j < nheight; j++) {
                    for (int i = 0; i < nwidth; i++) {
                        ndata[nwidth * (nheight - j - 1) + i] = (0xff) << 24
                                | (((int) brgb[nindex + 2] & 0xff) << 16)
                                | (((int) brgb[nindex + 1] & 0xff) << 8)
                                | (int) brgb[nindex] & 0xff;
                        nindex += 4;
                    }
                    nindex += npad;
                }

                image = Toolkit.getDefaultToolkit().createImage(
                        new MemoryImageSource(nwidth, nheight, ndata, 0, nwidth));
            } else if (nbitcount == 24) {
                int npad = (nsizeimage / nheight) - nwidth * 3;
                int[] ndata = new int[nheight * nwidth];
                byte[] brgb = new byte[(nwidth + npad) * 3 * nheight];
                System.out.println("BMP转换byte[]Size:" + fis.read(brgb, 0, (nwidth + npad) * 3 * nheight));
                int nindex = 0;
                for (int j = 0; j < nheight; j++) {
                    for (int i = 0; i < nwidth; i++) {
                        ndata[nwidth * (nheight - j - 1) + i] = (0xff) << 24
                                | (((int) brgb[nindex + 2] & 0xff) << 16)
                                | (((int) brgb[nindex + 1] & 0xff) << 8)
                                | (int) brgb[nindex] & 0xff;
                        nindex += 3;
                    }
                    nindex += npad;
                }
                image = Toolkit.getDefaultToolkit().createImage(
                        new MemoryImageSource(nwidth, nheight, ndata, 0, nwidth));
            } else if (nbitcount == 16) {
                int npad = (nsizeimage / nheight) - nwidth * 2;
                int[] ndata = new int[nheight * nwidth];
                byte[] brgb = new byte[(nwidth + npad) * 2 * nheight];
                System.out.println("BMP转换byte[]Size:" + fis.read(brgb, 0, (nwidth + npad) * 2 * nheight));
                int nindex = 0;
                for (int j = 0; j < nheight; j++) {
                    for (int i = 0; i < nwidth; i++) {
                        ndata[nwidth * (nheight - j - 1) + i] = (0xff) << 24
                                | (((((int) brgb[nindex + 1] >>> 2) & 0x3f) | 0x60) << 3 << 16)
                                | ((((brgb[nindex + 1] & 0x3) << 3) | ((brgb[nindex] & 0xe0) >>> 5) | 0x60) << 3 << 8)
                                | ((((int) brgb[nindex] & 0x1f) | 0x60) << 3);
                        nindex += 2;
                    }
                    nindex += npad;
                }
                image = Toolkit.getDefaultToolkit().createImage(
                        new MemoryImageSource(nwidth, nheight, ndata, 0, nwidth));
            } else if (nbitcount == 8) {
                int nNumColors;
                if (nclrused > 0) {
                    nNumColors = nclrused;
                } else {
                    nNumColors = (1 & 0xff) << nbitcount;
                }
                int[] npalette = new int[nNumColors];
                byte[] bpalette = new byte[nNumColors * 4];
                System.out.println("BMP转换byte[]Size:" + fis.read(bpalette, 0, nNumColors * 4));
                int nindex8 = 0;
                for (int n = 0; n < nNumColors; n++) {
                    npalette[n] = (0xff) << 24
                            | (((int) bpalette[nindex8 + 2] & 0xff) << 16)
                            | (((int) bpalette[nindex8 + 1] & 0xff) << 8)
                            | (int) bpalette[nindex8] & 0xff;
                    nindex8 += 4;
                }
                int npad8 = (nsizeimage / nheight) - nwidth;
                int[] ndata8 = new int[nwidth * nheight];
                byte[] bdata = new byte[(nwidth + npad8) * nheight];
                System.out.println("BMP转换byte[]Size:" + fis.read(bdata, 0, (nwidth + npad8) * nheight));
                nindex8 = 0;
                for (int j8 = 0; j8 < nheight; j8++) {
                    for (int i8 = 0; i8 < nwidth; i8++) {
                        ndata8[nwidth * (nheight - j8 - 1) + i8] = npalette[((int) bdata[nindex8] & 0xff)];
                        nindex8++;
                    }
                    nindex8 += npad8;
                }
                image = Toolkit.getDefaultToolkit().createImage(
                        new MemoryImageSource(nwidth, nheight, ndata8, 0, nwidth));
            } else if (nbitcount == 4) {
                int nNumColors;
                if (nclrused > 0) {
                    nNumColors = nclrused;
                } else {
                    nNumColors = (1 & 0xff) << nbitcount;
                }
                int[] npalette = new int[nNumColors];
                byte[] bpalette = new byte[nNumColors * 4];
                System.out.println("BMP转换byte[]Size:" + fis.read(bpalette, 0, nNumColors * 4));
                int nindex4 = 0;
                for (int n = 0; n < nNumColors; n++) {
                    npalette[n] = (0xff) << 24
                            | (((int) bpalette[nindex4 + 2] & 0xff) << 16)
                            | (((int) bpalette[nindex4 + 1] & 0xff) << 8)
                            | (int) bpalette[nindex4] & 0xff;
                    nindex4 += 4;
                }
                int scanLineSize = (((nwidth * nbitcount) + 31) & ~31) >> 3;
                int[] ndata4 = new int[nwidth * nheight];
                byte[] blinedata = new byte[scanLineSize];
                for (int j4 = 0; j4 < nheight; j4++) {
                    System.out.println("BMP转换byte[]Size:" + fis.read(blinedata, 0, scanLineSize));
                    nindex4 = 0;
                    for (int i4 = 0; i4 < nwidth; i4++) {
                        if (nwidth * (nheight - j4 - 1) + i4 > nwidth * nheight - 1) {
                            break;
                        }
                        if (nindex4 > scanLineSize * nheight - 1) {
                            break;
                        }
                        for (int pixPerByte = 0; pixPerByte < 2; pixPerByte++) {
                            if (pixPerByte == 0) {
                                ndata4[nwidth * (nheight - j4 - 1) + i4] = npalette[((blinedata[nindex4] >> 4) & 0xf)];
                                i4++;
                                if (i4 >= nwidth) {
                                    break;
                                }
                            } else {
                                ndata4[nwidth * (nheight - j4 - 1) + i4] = npalette[((int) blinedata[nindex4] & 0xf)];
                            }
                        }
                        nindex4++;
                    }
                }
                image = Toolkit.getDefaultToolkit().createImage(
                        new MemoryImageSource(nwidth, nheight, ndata4, 0, nwidth));
            } else if (nbitcount == 1) {
                int nNumColors;
                if (nclrused > 0) {
                    nNumColors = nclrused;
                } else {
                    nNumColors = (1 & 0xff) << nbitcount;
                }
                int[] npalette = new int[nNumColors];
                byte[] bpalette = new byte[nNumColors * 4];
                System.out.println("BMP转换byte[]Size:" + fis.read(bpalette, 0, nNumColors * 4));
                int nindex1 = 0;
                for (int n = 0; n < nNumColors; n++) {
                    npalette[n] = (0xff) << 24
                            | (((int) bpalette[nindex1 + 2] & 0xff) << 16)
                            | (((int) bpalette[nindex1 + 1] & 0xff) << 8)
                            | (int) bpalette[nindex1] & 0xff;
                    nindex1 += 4;
                }
                int scanLineSize = (((nwidth * nbitcount) + 31) & ~31) >> 3;
                int[] ndata1 = new int[nwidth * nheight];
                byte[] blinedata = new byte[scanLineSize];
                for (int j1 = 0; j1 < nheight; j1++) {
                    System.out.println("BMP转换byte[]Size:" + fis.read(blinedata, 0, scanLineSize));
                    nindex1 = 0;
                    for (int i1 = 0; i1 < nwidth; i1++) {
                        if (nwidth * (nheight - j1 - 1) + i1 > nwidth * nheight - 1) {
                            break;
                        }
                        if (nindex1 > scanLineSize * nheight - 1) {
                            break;
                        }
                        for (int pixPerByte = 0; pixPerByte < 8; pixPerByte++) {
                            int shift = 8 - pixPerByte - 1;
                            ndata1[nwidth * (nheight - j1 - 1) + i1] = npalette[((blinedata[nindex1] >> shift) & 0x1)];
                            if (pixPerByte != 7) {
                                i1++;
                                if (i1 >= nwidth) {
                                    break;
                                }
                            }
                        }
                        nindex1++;
                    }
                }
                image = Toolkit.getDefaultToolkit().createImage(
                        new MemoryImageSource(nwidth, nheight, ndata1, 0, nwidth));
            } else {
                image = null;
            }
            fis.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
