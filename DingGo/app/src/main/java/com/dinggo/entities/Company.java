package com.dinggo.entities;

import android.graphics.Bitmap;
import android.util.Log;

import com.dinggo.utilities.ImageUtils;
import com.dinggo.utilities.LogUtils;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Company class that contains all the information about the company
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 26/7/2015.
 */
@ParseClassName("company")
public class Company extends ParseObject {
    /** Mame of the table */
    public static final String TABLE_NAME = "company";

    /** Column name in the Parse Database */
    private final String COLUMN_COMPANY_NAME = "companyName",
            COLUMN_REG_NUM = "regNo",
            COLUMN_DESC = "description",
            COLUMN_CUISINE_TYPE = "cuisineType",
            COLUMN_EMAIL = "email",
            COLUMN_WEBSITE_URL = "websiteURL",
            COLUMN_LOGO = "logoImage",
            COLUMN_COVER_IMAGE = "coverImage",
            COLUMN_NUM_OF_LIKES = "numOfLikes";

    /** Default constructor to instantiate Company Object and store into Parse Database without data */
    public Company() {}

    /**
     * Constructor to instantiate Company Object and store into Parse Database with data
     * @param companyName   Name of the company
     * @param regNum        Company's registration number
     * @param description   Description of the company
     * @param cuisineType   Type of cuisine the company serve
     * @param email         Company's email address
     * @param websiteUrl    Company's website URL
     * @param logoImage     Company's logo
     * @param coverImage    Company cover image
     */
    public Company(String companyName, String regNum, String description, CuisineType cuisineType,
                   String email, String websiteUrl, byte[] logoImage, byte[] coverImage) {
        setCompanyName(companyName);
        setRegNum(regNum);
        setDescription(description);
        setCuisineType(cuisineType);
        setEmail(email);
        setWebsiteUrl(websiteUrl);
        setLogoImage(logoImage);
        setCoverImage(coverImage);
        setNumOfLikes(0);

        saveInBackground(LogUtils.saveCallback(Company.class.getName()));
    }

    /**
     * Retrieve the ID of the company
     * @return  String value that contains the ID of the company
     */
    public String getCompanyId() {
        return getObjectId();
    }

    /**
     * Retrieve the name of the company
     * @return  String value that contains the name of the company
     */
    public String getCompanyName() {
        return getString(COLUMN_COMPANY_NAME);
    }

    /**
     * Set and change the name of the company
     * @param companyName   String value that contains the new name for the company
     */
    public void setCompanyName(String companyName) {
        put(COLUMN_COMPANY_NAME, companyName);
    }

    /**
     * Get the registration of the company
     * @return  String value that contains the registration number of the company
     */
    public String getRegNum() {
        return getString(COLUMN_REG_NUM);
    }

    /**
     * Set and change the registration number for the company
     * @param regNum    String value that contains the new registration number of the company
     */
    public void setRegNum(String regNum) {
        put(COLUMN_REG_NUM, regNum);
    }

    /**
     * Retrieve the description of the company
     * @return  String value that contains the description of the company
     */
    public String getDescription() {
        return getString(COLUMN_DESC);
    }

    /**
     * Set and change the description for the company
     * @param description   String value that contains the new description for the company
     */
    public void setDescription(String description) {
        put(COLUMN_DESC, description);
    }

    /**
     * Retrieve the cuisine type the company serves
     * @return  CuisineType object that contains the name of the cuisine
     */
    public CuisineType getCuisineType() {
        CuisineType cuisineType = (CuisineType) get(COLUMN_CUISINE_TYPE);

        if (!cuisineType.isDataAvailable()) {
            try {
                cuisineType.fetchIfNeeded();
            } catch (ParseException e) {
                Log.e(TABLE_NAME, e.getMessage());
            }
        }
        return cuisineType;
    }

    /**
     * Set and change the type of cuisine the company serves
     * @param cuisineType   CuisineType object that the company serves
     */
    public void setCuisineType(CuisineType cuisineType) {
        put(COLUMN_CUISINE_TYPE, cuisineType);
    }

    /**
     * Retrieve the company's email
     * @return  String value that contains company's email
     */
    public String getEmail() {
        return getString(COLUMN_EMAIL);
    }

    /**
     * Set and change the email for the company
     * @param email String value that contains a new email for the company
     */
    public void setEmail(String email) {
        put(COLUMN_EMAIL, email);
    }

    /**
     * Retrieve the website url of the company
     * @return  String value that contains the website url of the company
     */
    public String getWebsiteUrl() {
        return getString(COLUMN_WEBSITE_URL);
    }

    /**
     * Set and change the website url of the company
     * @param websiteUrl    String value that contains a new website url for the company
     */
    public void setWebsiteUrl(String websiteUrl) {
        put(COLUMN_WEBSITE_URL, websiteUrl);
    }

    /**
     * Retrieve the logo image of the company
     * @return  Bitmap that contains the logo image of the company
     * @throws ParseException
     */
    public Bitmap getLogoImage() throws ParseException {
        ParseFile logoImage = getParseFile(COLUMN_LOGO);
        if (logoImage != null) return ImageUtils.convertBytesToImage(logoImage.getData());
        else return null;
    }

    /**
     * Set and change the logo of the company
     * @param image Byte array that contains the new image of the company
     */
    public void setLogoImage(byte[] image){
        if (image != null) {
            ParseFile logoImage = new ParseFile(image);
            logoImage.saveInBackground(LogUtils.saveCallback(Company.class.getName()));
            put(COLUMN_LOGO, logoImage);
        } else {
            Log.e(TABLE_NAME, "Image cannot be null to set the logo image");
        }
    }

    /**
     * Retrieve the cover image for the company
     * @return  Bitmap that contains the cover image of the company
     * @throws ParseException
     */
    public Bitmap getCoverImage() throws ParseException {
        ParseFile coverImage = getParseFile(COLUMN_COVER_IMAGE);
        if (coverImage != null) return ImageUtils.convertBytesToImage(coverImage.getData());
        else return null;
    }

    /**
     * Set and change the cover image for the company
     * @param image    Byte array that contains the cover image for the company
     */
    public void setCoverImage(byte[] image) {
        if (image != null) {
            ParseFile coverImage = new ParseFile(image);
            coverImage.saveInBackground(LogUtils.saveCallback(Company.class.getName()));
            put(COLUMN_COVER_IMAGE, coverImage);
        } else {
            Log.e(TABLE_NAME, "Image cannot be null to set the cover image");
        }
    }

    /**
     * Retrieve the number of likes the company receives
     * @return  Integer value that contains the number of likes the company receives
     */
    public int getNumOfLikes() {
        return getInt(COLUMN_NUM_OF_LIKES);
    }

    /**
     * Set and change the number of likes the company receives
     * @param numOfLikes    Integer value that contains the number of likes the company receives
     */
    public void setNumOfLikes(int numOfLikes) {
        put(COLUMN_NUM_OF_LIKES, numOfLikes);
    }
}
