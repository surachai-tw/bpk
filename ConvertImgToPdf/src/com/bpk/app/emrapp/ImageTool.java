package com.bpk.app.emrapp;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageTool
{

    public static void main(String args[])
    {
        try
        {
            for (Integer i : new Integer[]
                    {
                        0, 30, 70, 100
                    })
            {
                BufferedImage img = ImageIO.read(new File("D:\\BPK\\trunk\\BarCodeReader\\file\\SCAN.jpg"));
                ImageTool.toBlackAndWhite(img, i);
                ImageIO.write(img, "jpg", new File("D:\\BPK\\trunk\\BarCodeReader\\file\\CHECK_" + i + ".jpg"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void toBlackAndWhite(BufferedImage img)
    {
        toBlackAndWhite(img, 50);
    }

    public static void toBlackAndWhite(BufferedImage img, int precision)
    {
        int w = img.getWidth();
        int h = img.getHeight();

        precision = (0 <= precision && precision <= 100) ? precision : 50;

        int limit = 255 * precision / 100;

        for (int i = 0, j; i < w; ++i)
        {
            for (j = 0; j < h; ++j)
            {
                Color color = new Color(img.getRGB(i, j));
                if (limit <= color.getRed() || limit <= color.getGreen() || limit <= color.getBlue())
                {
                    img.setRGB(i, j, Color.WHITE.getRGB());
                }
                else
                {
                    img.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
    }
}
