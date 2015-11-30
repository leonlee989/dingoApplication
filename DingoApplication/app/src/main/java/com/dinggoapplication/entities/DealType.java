package com.dinggoapplication.entities;

import android.graphics.Bitmap;

import com.dinggoapplication.utilities.ImageUtils;
import com.dinggoapplication.utilities.LogUtils;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Class that defines the different categories of deals to be used in Deal object
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 28/7/2015.
 */
@ParseClassName("deal_type")
public class DealType extends ParseObject {

    /** Name of the table */
    public static final String TABLE_NAME = "deal_type";

    /** Column name for cuisine name field */
    public static final String COLUMN_NAME = "typeName",
            COLUMN_IMAGE = "image",
            COLUMN_DESC = "description";

    /** Default constructor that instantiate the deal type object */
    public DealType() {}

    /**
     * Constructor to instantiate Deal Type object with the following argument
     * @param typeName      Name of the deal type
     * @param typeImage     Image that represent the deal type
     * @param description   Description about the deal type
     */
    public DealType(String typeName, byte[] typeImage, String description) {
        setTypeName(typeName);
        setTypeImage(typeImage);
        setDescription(description);

        saveInBackground(LogUtils.saveCallback(DealType.class.getName()));
    }

    /**
     * Retrieve the ID of the deal type from Parse database
     * @return  String value that contains the ID of the deal type
     */
    public String getTypeId() {
        return getObjectId();
    }

    /**
     * Retrieve the name of the deal type from Parse database
     * @return  String value that contains the name of the deal type
     */
    public String getTypeName() {
        return getString(COLUMN_NAME);
    }

    /**
     * Set and change the name of the deal type
     * @param typeName  String value that contains the new name for the deal type
     */
    public void setTypeName(String typeName) {
        put(COLUMN_NAME, typeName);
    }

    /**
     * Retrieve the image that represents the deal type
     * @return  Bitmap object that contains the image representing the deal type
     * @throws ParseException
     */
    public Bitmap getTypeImage() throws ParseException {
        ParseFile dealImage = getParseFile(COLUMN_IMAGE);
        return ImageUtils.convertBytesToImage(dealImage.getData());
    }

    /**
     * Set and change the image that represent the deal type
     * @param image Byte array that contains byte data that defines the image representing the deal type
     */
    public void setTypeImage(byte[] image) {
        ParseFile dealImage = new ParseFile(image);
        dealImage.saveInBackground(LogUtils.saveCallback(DealType.class.getName()));
        put(COLUMN_IMAGE, dealImage);
    }

    /**
     * Retrieve the description of the deal type
     * @return  String value that contains the description of the deal type
     */
    public String getDescription() {
        return getString(COLUMN_DESC);
    }

    /**
     * Set and change the description of the deal type
     * @param description   String value that contain the new description about the deal type
     */
    public void setDescription(String description) {
        put(COLUMN_DESC, description);
    }
}