/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dinggoapplication.Entity.Address;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.Entity.PercentageDiscount;
import com.dinggoapplication.Entity.TierDiscount;
import com.dinggoapplication.Manager.PreferencesManager;
import com.google.android.gms.maps.model.LatLng;

/**
 * Bootstrap class to initialize all entity and manager class
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 23/2/2015.
 */
public class Bootstrap {
    /** Status of bootstrapping */
    static boolean completed = false;
    /** Application context */
    private Context mContext;

    /**
     * Constructor for Bootstrap class with the following parameters
     * @param context   Application context
     */
    public Bootstrap(Context context) {
        this.mContext = context;
    }

    /**
     * Trigger method for bootstrapping
     */
    public void set() {
        if (!completed) {
            initializeEntity();
            completed = true;
        }
    }

    /**
     * Method initialize all entity and manager classes with dummy data for testing purpose
     */
    public void initializeEntity() {

        // TODO: Merchant details for Oriental Garden
        String merchantId_oriental_garden = "merchant01";

        String password_oriental_garden = "123";

        Bitmap image_oriental_garden = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.orientalgarden);

        String company_oriental_garden = "Oriental Garden";

        String merchantName_oriental_garden = "Veronica Tan";

        String merchantDescription_oriental_garden = "Oriental Garden @ Chinatown Point is renown for " +
                "their quality culinary offerings and distinguished fine dining experience";

        String merchantType_oriental_garden = "Chinese Food";

        Address address_oriental_garden = new Address("22", "New Bridge Road Rd",
                "#XX-XX Chinatown point", "059413");

        int contactNumber_oriental_garden = 61234567;

        String website_oriental_garden = "orientalgarden.com.sg";
        LatLng latLng_orientalgarden = new LatLng(1.2852, 103.8449);
        //LatLng latLng_peachgarden = ApplicationFactory.getLocationFromAddress(address_peach_garden.toString(), mContext);
        if (latLng_orientalgarden == null) {
            latLng_orientalgarden = new LatLng(1.3000, 103.8000);
        }

        Merchant oriental_garden = new Merchant(merchantId_oriental_garden, password_oriental_garden,
                image_oriental_garden, company_oriental_garden, merchantName_oriental_garden,
                merchantDescription_oriental_garden, merchantType_oriental_garden, address_oriental_garden,
                contactNumber_oriental_garden, website_oriental_garden, latLng_orientalgarden);

        Constants.merchantManager.addMerchant(oriental_garden);

        // Deals available for Oriental Garden
        Bitmap coverImage_oriental_garden = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverorientalgarden);

        PercentageDiscount deal1_peach_garden = new PercentageDiscount("OG1", coverImage_oriental_garden,
                oriental_garden, true, 50);
        Constants.dealManager.addDeal(deal1_peach_garden);

        // TODO: Merchant details for Pho Pho
        String merchantId_pho_pho = "merchant02";

        String password_pho_pho = "123";

        Bitmap image_pho_pho = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.phopho);

        String company_pho_pho = "Pho Pho";

        String merchantName_pho_pho = "Unknown Owner";

        String merchantDescription_pho_pho = "Pho Street determines to bringing you delicious " +
                "and authentic Vietnamese cuisine. Freshest ingredients are carefully selected to " +
                "deliver unique flavors that complement their aromatic dishes";

        String merchantType_pho_pho = "Vietnamese Food";

        Address address_pho_pho = new Address("82", "Tanah Merah Kechil Ave", "#XX-XX", "465515");

        int contactNumber_pho_pho = 61234567;

        String website_pho_pho = "phopho.com.sg";
        LatLng latLng_pho_pho = new LatLng(1.3285, 103.9445);
        //LatLng latLng_pho_pho = ApplicationFactory.getLocationFromAddress(address_pho_pho.toString(), mContext);
        if (latLng_pho_pho == null) {
            latLng_pho_pho = new LatLng(1.3000, 103.8000);
        }

        Merchant pho_pho = new Merchant(merchantId_pho_pho, password_pho_pho,
                image_pho_pho, company_pho_pho, merchantName_pho_pho,
                merchantDescription_pho_pho, merchantType_pho_pho, address_pho_pho,
                contactNumber_pho_pho, website_pho_pho, latLng_pho_pho);

        Constants.merchantManager.addMerchant(pho_pho);

        // Deals available for Pho Pro Restaurant
        Bitmap coverImage_pho_pho = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverpho);

        PercentageDiscount deal1_pho_pho = new PercentageDiscount("PP1", coverImage_pho_pho,
                pho_pho, true, 50);
        Constants.dealManager.addDeal(deal1_pho_pho);


        // TODO: Merchant details for Four Seasons Chicken
        String merchantId_fourseason_chicken = "merchant03";

        String password_fourseason_chicken = "123";

        Bitmap image_fourseason_chicken = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.fourseasonschicken);

        String company_fourseason_chicken = "Four Seasons Chicken";

        String merchantName_fourseason_chicken = "Hyden Sim.";

        String merchantDescription_fourseason_chicken = "Four Seasons Chicken offered freshly " +
                "prepared, high quality, delicious chicken and tenders with all your favorite " +
                "classic sides at a great value";

        String merchantType_fourseason_chicken = "Western Food";

        Address address_fourseason_chicken = new Address("23", "Serangoon Central", "#XX-XX, " +
                "Nex", "556083");

        int contactNumber_fourseason_chicken = 66342130;

        String website_fourseason_chicken = "www.fourchicken.com.sg";
        //LatLng latLng_fourseason_chicken = new LatLng(1.3506, 103.8718);
        LatLng latLng_fourseason_chicken = ApplicationFactory.getLocationFromAddress(address_fourseason_chicken.toString(), mContext);
        if (latLng_fourseason_chicken == null) {
            latLng_fourseason_chicken = new LatLng(1.3000, 103.8000);
        }

        Merchant merchant_fourseason_chicken = new Merchant(merchantId_fourseason_chicken,
                password_fourseason_chicken, image_fourseason_chicken, company_fourseason_chicken,
                merchantName_fourseason_chicken, merchantDescription_fourseason_chicken,
                merchantType_fourseason_chicken, address_fourseason_chicken, contactNumber_fourseason_chicken,
                website_fourseason_chicken, latLng_fourseason_chicken);

        Constants.merchantManager.addMerchant(merchant_fourseason_chicken);

        // Deals available for Texas Chicken
        Bitmap coverImage_four_chicken = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverfourseasonschicken);
        PercentageDiscount deal1_fourseason_chicken = new PercentageDiscount("tc1", coverImage_four_chicken,
                merchant_fourseason_chicken, true, 20);
        Constants.dealManager.addDeal(deal1_fourseason_chicken);
        TierDiscount deal2_fourseason_chicken = new TierDiscount("tc2", coverImage_four_chicken,
                merchant_fourseason_chicken, true, 10, 30);
        Constants.dealManager.addDeal(deal2_fourseason_chicken);

        // TODO: Merchant details for Gold Thai
        String merchantId_gold_thai = "merchant04";

        String password_gold_thai = "123";

        Bitmap image_gold_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.goldthai);

        String company_gold_thai = "Gold Thai";

        String merchantName_gold_thai = "Select Group Limited";

        String merchantDescription_gold_thai = "Gold Thai @ Thanon Witthayu offers a bountiful mix " +
                "of authentic Thai cuisine, with the plethora of dips and pastes used in our cuisine " +
                "and level of spiciness adjusted to local palates";

        String merchantType_gold_thai = "Thai Food";

        Address address_gold_thai = new Address("", "Thanon Witthayu", ", Khwaeng Lumphini, Khet Pathum Wan, Krung Thep Maha Nakhon", "10330");

        int contactNumber_gold_thai = 61234567;

        String website_gold_thai = "www.goldthai.com.sg";
        LatLng latLng_gold_thai = new LatLng(13.7475, 100.5472);
        //LatLng latLng_gold_thai = ApplicationFactory.getLocationFromAddress(address_gold_thai.toString(), mContext);
        if (latLng_gold_thai == null) {
            latLng_gold_thai = new LatLng(13.7475, 100.5472);
        }

        Merchant gold_thai = new Merchant(merchantId_gold_thai, password_gold_thai, image_gold_thai,
                company_gold_thai, merchantName_gold_thai, merchantDescription_gold_thai,
                merchantType_gold_thai, address_gold_thai, contactNumber_gold_thai,
                website_gold_thai, latLng_gold_thai);

        Constants.merchantManager.addMerchant(gold_thai);

        // Deals available for Gold Thai Restaurant
        Bitmap coverImage_gold_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.covergoldthai);
        TierDiscount deal1_gold_thai = new TierDiscount("GT1", coverImage_gold_thai,
                gold_thai, true, 20, 10);
        Constants.dealManager.addDeal(deal1_gold_thai);

        PercentageDiscount deal2_gold_thai = new PercentageDiscount("GT2", coverImage_gold_thai,
                gold_thai, true, 40);
        Constants.dealManager.addDeal(deal2_gold_thai);

        // TODO: Merchant details for Lerk Thai
        String merchantId_lerk_thai = "merchant05";

        String password_lerk_thai = "123";

        Bitmap image_lerk_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.lerkthai);

        String company_lerk_thai = "Lerk Thai";

        String merchantName_lerk_thai = "Select Group Limited";

        String merchantDescription_lerk_thai = "Gold Thai @ Witthayu Road offers a bountiful mix " +
                "of authentic Thai cuisine, with the plethora of dips and pastes used in our cuisine " +
                "and level of spiciness adjusted to local palates";

        String merchantType_lerk_thai = "Thai Food";

        Address address_lerk_thai = new Address("1", "Witthayu Road", " Khwaeng Lumphini Krung Thep Maha Nakhon", "758282");

        int contactNumber_lerk_thai = 61234567;

        String website_lerk_thai = "www.lerkthai.com.sg";
        LatLng latLng_lerk_thai = new LatLng(13.7480911, 100.54860510000003);
        //LatLng latLng_lerk_thai = ApplicationFactory.getLocationFromAddress(address_lerk_thai.toString(), mContext);
        if (latLng_lerk_thai == null) {
            latLng_lerk_thai = new LatLng(13.7475, 100.5472);
        }

        Merchant lerk_thai = new Merchant(merchantId_lerk_thai, password_lerk_thai, image_lerk_thai,
                company_lerk_thai, merchantName_lerk_thai, merchantDescription_lerk_thai,
                merchantType_lerk_thai, address_lerk_thai, contactNumber_lerk_thai,
                website_lerk_thai, latLng_lerk_thai);

        Constants.merchantManager.addMerchant(lerk_thai);

        // Deals available for Gold Thai Restaurant
        Bitmap coverImage_lerk_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.covergoldthai);
        TierDiscount deal1_lerk_thai = new TierDiscount("LT1", coverImage_lerk_thai,
                lerk_thai, true, 100, 50);
        Constants.dealManager.addDeal(deal1_lerk_thai);

        PercentageDiscount deal2_lerk_thai = new PercentageDiscount("LT2", coverImage_lerk_thai,
                lerk_thai, true, 20);
        Constants.dealManager.addDeal(deal2_lerk_thai);

        // TODO: Merchant details for Griddy & Souffles
        String merchantId_griddy_souffles = "merchant06";

        String password_griddy_souffles = "123";

        Bitmap image_griddy_souffles = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.wafflessouffleslogo);

        String company_griddy_souffles = "Waffles & Souffles";

        String merchantName_griddy_souffles = "Ong Mei Kan";

        String merchantDescription_griddy_souffles = "Waffles & Souffles offers sweet and savoury " +
                "waffles artfully crafted to be visual and gastronomic delights";

        String merchantType_griddy_souffles = "Western Food";

        Address address_griddy_souffles = new Address("10", "Orchard Rd", "#XX-XX Orchard Plaza", "238841");

        int contactNumber_griddy_souffles = 61234567;

        String website_griddy_souffles = "griddysouffles.com.sg";
        //LatLng latLng_griddy_souffles = new LatLng(43.1, -87.9);
        LatLng latLng_griddy_souffles = ApplicationFactory.getLocationFromAddress(address_griddy_souffles.toString(), mContext);

        if (latLng_griddy_souffles == null) {
            latLng_griddy_souffles = new LatLng(1.3000, 103.8000);
        }

        Merchant griddy_souffles = new Merchant(merchantId_griddy_souffles, password_griddy_souffles,
                image_griddy_souffles, company_griddy_souffles, merchantName_griddy_souffles,
                merchantDescription_griddy_souffles, merchantType_griddy_souffles,
                address_griddy_souffles, contactNumber_griddy_souffles, website_griddy_souffles, latLng_griddy_souffles);

        Constants.merchantManager.addMerchant(griddy_souffles);

        // Deals available for Griddy & Souffles
        Bitmap coverImage_waffles_souffles = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverwafflessouffles);
        TierDiscount deal1_griddy_souffles = new TierDiscount("GW1", coverImage_waffles_souffles,
                griddy_souffles, true, 10, 50);
        Constants.dealManager.addDeal(deal1_griddy_souffles);

        PreferencesManager.initializeInstance(mContext);
    }
}