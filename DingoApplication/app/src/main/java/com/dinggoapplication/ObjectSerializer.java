/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by siungee on 06/03/15.
 */
public class ObjectSerializer {

    //private static final Log log = LogManager.getLogger(ObjectSerializer.class);

    /**
     * Method to encode a serializable object into byte array in string format
     * @param obj   Serializable object to be encoded into byte array in string format
     * @return      String value that contains byte array of the serializable object
     * @throws IOException
     */
    public static String serialize(Serializable obj) throws IOException {
        if (obj == null) return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return encodeBytes(serialObj.toByteArray());
        } catch (Exception e) {
            throw WrappedIOException.wrap("Serialization error: " + e.getMessage(), e);
        }
    }

    /**
     * Method to deserialize a string formatted byte array into an object
     * @param str   String value that contain byte array of an object
     * @return      Object that is being deserialize
     * @throws IOException
     */
    public static Object deserialize(String str) throws IOException {
        if (str == null || str.length() == 0) return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
            ObjectInputStream objStream = new ObjectInputStream(serialObj);
            return objStream.readObject();
        } catch (Exception e) {
            throw WrappedIOException.wrap("Deserialization error: " + e.getMessage(), e);
        }
    }

    /**
     * Encoding a byte array into string format
     * @param bytes An array of bytes
     * @return      Byte array in string format
     */
    public static String encodeBytes(byte[] bytes) {
        StringBuilder strBuf = new StringBuilder();

        for (byte aByte : bytes) {
            strBuf.append((char) (((aByte >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((aByte) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    /**
     * Decoding a string value into its original array of bytes
     * @param str   String value that contains byte array
     * @return      An array of bytes
     */
    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i+=2) {
            char c = str.charAt(i);
            bytes[i/2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i+1);
            bytes[i/2] += (c - 'a');
        }
        return bytes;
    }
}

/**
 * Class that handles exceptions for ObjectSerializer Class
 */
class WrappedIOException {

    /**
     * Wrap an IOException object with the throwable object provided
     * @param e     Throwable object that contains the stack flow
     * @return      IOException object to be thrown
     */
    public static IOException wrap(final Throwable e) {
        return wrap(e.getMessage(), e);
    }

    /**
     * Wrap an IOException object with the throwable object and error message provided
     * @param message   Error message to display
     * @param e         Throwable object that contains the stack flow
     * @return          IOException object to be thrown
     */
    public static IOException wrap(final String message, final Throwable e) {
        final IOException wrappedException = new IOException(message + " [" +
                e.getMessage() + "]");
        wrappedException.setStackTrace(e.getStackTrace());
        wrappedException.initCause(e);
        return wrappedException;
    }
}