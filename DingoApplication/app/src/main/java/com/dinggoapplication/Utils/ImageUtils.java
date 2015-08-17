package com.dinggoapplication.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Utility class that deal with images handling and other required operations related to images
 *
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 27/7/2015.
 */
public class ImageUtils {
    /**
     * Method to convert image from android resources into byte array
     *
     * @param resources     Resources object that contains all android resources
     * @param resourceId    ID of the image resource
     * @return              Byte array that defines the image
     */
    public static byte[] convertImageToBytes(Resources resources, int resourceId) {
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        Bitmap image = BitmapFactory.decodeResource(resources, resourceId);

        image.compress(Bitmap.CompressFormat.PNG, 20, imageStream);

        return imageStream.toByteArray();
    }

    /**
     * Method to convert image in Bitmap format into byte array
     * @param image     Bitmap object that defines the image
     * @return          Byte array that defines the image
     */
    public static byte[] convertImageToBytes(Bitmap image) {
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.PNG, 100, imageStream);
        return imageStream.toByteArray();
    }

    /**
     * Convert byte array into image in Bitmap format
     * @param bytes Byte array that defines the image
     * @return      Bitmap object that defines the image
     */
    public static Bitmap convertBytesToImage(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
