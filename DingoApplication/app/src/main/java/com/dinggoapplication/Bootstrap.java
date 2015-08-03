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
import com.dinggoapplication.Utils.Config;
import com.dinggoapplication.Utils.ImageUtils;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.CuisineType;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.entities.DealType;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

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

    /** Trigger method for bootstrappings */
    public void execute(boolean isExecute) {
        if (isExecute) {
            initializeData();
        }

        if (!completed) {
            initializeEntity();
            completed = true;
        }
    }

    public void initializeData() {
        // Initialization of cuisine types
        CuisineType western = new CuisineType("Western");
        CuisineType chinese = new CuisineType("Chinese");
        CuisineType vietnam = new CuisineType("Vietnamese");
        CuisineType thai = new CuisineType("Thai");
        CuisineType japanese = new CuisineType("Japanese");
        CuisineType indian = new CuisineType("Indian");
        CuisineType malay = new CuisineType("Malay");
        CuisineType crossCultural = new CuisineType("Cross-cultural");
        CuisineType seafood = new CuisineType("Seafood");

        // Initialization of deal types
        DealType percentage = new DealType("Percentage Discount",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.percentageicon),
                "Provide a certain percentage discount for meals");
        DealType setMeal = new DealType("Set Meal",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.setmealicon),
                "Certain food combination will be offered at a lower price");
        DealType happyHour = new DealType("Happy Hour",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.happyhouricon),
                "You may enjoy food or drink at lower price in certain time");
        DealType oneForOne = new DealType("1 for 1",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.oneforone),
                "You may get 1 additional item for free when you get one already");
        DealType tierDiscount = new DealType("Tier Discount",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.tiericon),
                "Spent a certain amount to receive for a certain percentage of discount off your bill!");

        // Initialization of companies
        /* ***************************** Oriental Garden **************************************** */
        Company orientalGarden = new Company("Oriental Garden",
                "G23456789",
                "Oriental Garden @ Chinatown point is renown for their quality culinary offerings " +
                        "and distinguished fine dining experience",
                chinese,
                "enquires@orientalgarden.com",
                "www.oriental-garden.com",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.orientalgarden),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.coverorientalgarden));

        // LatLng latLng_peachgarden = ApplicationFactory.getLocationFromAddress(address_peach_garden.toString(), mContext);
        Branch orientalGardenOutlet = new Branch(orientalGarden, "22 New Bridge Road Rd, #XX-XX Chinatown point",
                "", "Singapore", "Singapore", "059413", "Singapore", 61234567, 1.2852, 103.8449);

        /* ==== Deals ==== */
        new Deal(orientalGardenOutlet,
                "Every Sunday Discount",
                "50% discount for all Breakfast on Sunday",
                percentage,
                "Discount only applies to Ala Carts",
                100,
                new Date());

        /* *********************** Merchant details for Pho Pho ********************************* */
        Company phoPho = new Company("Pho Pho",
                "F50439850",
                "Pho Street determines to bringing you delicious and authentic Vietnamese cuisine. " +
                        "Freshest ingredients are carefully selected to deliver unique flavors that " +
                        "complement their aromatic dishes",
                vietnam,
                "enquires@phopho.com.sg",
                "www.pho-pho.com.sg",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.phopho),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.coverpho));

        //LatLng latLng_pho_pho = ApplicationFactory.getLocationFromAddress(address_pho_pho.toString(), mContext);
        Branch phoPhoOutlet = new Branch(phoPho, "82 Tanah Merah Kechil Ave, #XX-XX", "", "Singapore",
                "Singapore", "465515", "Singapore", 67654321, 1.3285, 103.9445);

        /* ==== Deals ==== */
        new Deal(phoPhoOutlet,
                "One for one",
                "Get a main course free for the purchase of a beef pho",
                oneForOne,
                "One for one does not include set meals",
                50,
                new Date());

        /* ***************** Merchant details for Four Seasons Chicken *************************** */
        Company fourSeasonChicken = new Company("Four Seasons Chicken",
                "GR9084234",
                "Four Seasons Chicken offered freshly prepared, high quality, delicious chicken and " +
                        "tenders with all your favorite classic sides at a great value",
                western,
                "enquires@fourchicken.com",
                "www.four-chicken.com",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.fourseasonschicken),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.coverfourseasonschicken));

        // LatLng latLng_fourseason_chicken = ApplicationFactory.getLocationFromAddress(address_four-season_chicken.toString(), mContext);
        Branch fourChickenOutlet_1 = new Branch(fourSeasonChicken, "23 Serangoon Central, Nex #XX-XX", "",
                "Singapore", "Singapore", "556083", "Singapore", 66342130, 1.3506, 103.8718);

        Branch fourChickenOutlet_2 = new Branch(fourSeasonChicken, "68 Orchard Rd, Plaze Singapura #XX-XX",
                "", "Singapore", "Singapore", "238839", "Singapore", 68998432, 1.3006139, 103.8451045);

        /* ==== Deals ==== */
        new Deal(fourChickenOutlet_1,
                "Lunch Discount for all set meal",
                "Get a 50% discount for all set meal for Monday to Friday",
                percentage,
                "Only application from Monday to Friday from 12pm to 2pm",
                100,
                new Date());

        new Deal(fourChickenOutlet_2,
                "Dinner Promotion",
                "Spent more than $100 for dinner to get a 20% discount off your bill",
                tierDiscount,
                "",
                50,
                new Date());

        new Deal(fourChickenOutlet_2,
                "Lunch Set Meal",
                "Purchase a set meal with any combination at a cheaper offer",
                setMeal,
                "Combination for the set meal is only inclusive to a specific list of food",
                100,
                new Date());

        /* ***************** Merchant details for Gold Thai *************************** */
        Company goldThai = new Company("Gold Thai",
                "G980429389",
                "Gold Thai offers a bountiful mix of authentic Thai cuisine, " +
                        "with the plethora of dips and pastes used in our cuisine and level of " +
                        "spiciness adjusted to local palates",
                thai,
                "enquires@goldthai.com",
                "www.goldthai.com.sg",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.goldthai),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.covergoldthai));

        Branch goldThaiOutlet_1 = new Branch(goldThai,"91 Bencoolen Street, #XX-XX, Sunshine Plaza", "",
                "Singapore", "Singapore", "189652", "Singapore", 61234567, 1.3007441, 103.8520604);

        Branch goldThaiOutlet_2 = new Branch(goldThai, "231 Bain Street, #XX-XX, Bras Basah Complex", "",
                "Singapore", "Singapore", "180231", "Singapore", 61234567, 1.297105, 103.853681);

        /* ==== Deals ==== */
        new Deal(goldThaiOutlet_1,
                "Lunch Promotion",
                "Purchase more than $50 as a group to get any desert free",
                tierDiscount,
                "",
                100,
                new Date());

        new Deal(goldThaiOutlet_2,
                "One for one meal",
                "Get a drink for a drink at any time of the day",
                oneForOne,
                "Only application to ice lemon tea, ice milo, ice lemon grass and ice earl grey",
                50,
                new Date());

        /* ***************** Merchant details for Lerk Thai *************************** */
        Company lerkThai = new Company("Lerk Thai",
                "F9809842",
                "Lerk Thai offers a bountiful mix of authentic Thai cuisine, " +
                        "with the plethora of dips and pastes used in our cuisine and level of " +
                        "spiciness adjusted to local palates",
                thai,
                "enquires@lerkthai.com",
                "www.lerk-thai.com.sg",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.lerkthai),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.covergoldthai));

        Branch lerkThaiOutlet = new Branch(lerkThai, "3 New Bugis St", "", "Singapore", "Singapore",
                "188867", "Singapore", 0, 1.300599, 103.854893);

        /* ==== Deals ==== */
        new Deal(lerkThaiOutlet,
                "Dinner Promotion",
                "Get 20% off for all ala cart every sunday",
                percentage,
                "Discount only valid on sundays",
                100,
                new Date());

        new Deal(lerkThaiOutlet,
                "Lunch Promotion",
                "Get 20% off your bill for any purchase before 12pm",
                percentage,
                "Only applicable from Monday to Friday from 9am to 12pm",
                100,
                new Date());

        /* ***************** Merchant details for Waffles & Souffles *************************** */
        Company wafflesSouffles = new Company("Waffles & Souffles",
                "G83094283",
                "Waffles & Souffles offers sweet and savoury waffles artfully crafted to be visual and gastronomic delights",
                western,
                "enquires@waffles.com",
                "www.griddy-souffles.com.sg",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.wafflessouffleslogo),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.coverwafflessouffles));

        Branch wafflesSoufflesOutlet = new Branch(wafflesSouffles, "10 Orchard Rd, #XX-XX Orchard Plaza",
                "", "Singapore", "Singapore", "238841", "Singapore", 61234567, 1.301284, 103.841309);

        /* ==== Deals ==== */
        new Deal(wafflesSoufflesOutlet,
                "Breakfast Offer",
                "Get 50% off for all breakfast set",
                percentage,
                "Deal is only application from 8am to 12pm every day",
                100,
                new Date());

        /* ***************** Merchant details for Ice Cold Beer *************************** */
        Company icb = new Company("Ice Cold Beer",
                "G390840298",
                "Ice cold beer is a cozy home for a mostly-undergrad crowd. Indeed, Ice-Cold B's, " +
                        "affectionately known as ICB, is a spin-off from the ever-popular watering hole. " +
                        "Just like its predecessor, it is the only bar in the area that pays loving homage to beer.",
                western,
                "enquires@icb.com",
                "www.icb.com.sg",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.icblogo),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.covericb));

        Branch icbOutlet = new Branch(icb, "50 Stamford Rd, #XX-XX Lee Kong Chian School Of Business",
                "", "Singapore", "Singapore", "178899", "Singapore", 61234567, 1.294866, 103.850259);

        /* ==== Deals ==== */
        new Deal(icbOutlet,
                "Student Discount",
                "SMU student get 20% icount for all food items",
                percentage,
                "Deal is only applicable to SMU students",
                200,
                new Date());

        new Deal(icbOutlet,
                "Happy Hour for all",
                "Get 2 bottle of beer for $15 and 3 bottle of beers for $25",
                happyHour,
                "Deal is only application from 6pm to 9pm every day",
                200,
                new Date());

        /* ***************** Merchant details for Sasuke Japan *************************** */
        Company sasukeJapan = new Company("Sasuke Japan",
                "F980493244",
                "Susake Japan offers the freshest sashimi and sushi, a sumptuous teppanyaki set menu " +
                        "as well as an a la carte menu.",
                japanese,
                "enquires@susake.com",
                "www.sasuke.com.sg",
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.sasukelogo),
                ImageUtils.convertImageToBytes(mContext.getResources(), R.drawable.coversasuke));

        Branch sasukeOutlet_1 = new Branch(sasukeJapan, "252 North Bridge Road, #XX-XX Raffles City", "",
                "Singapore", "Singapore", "179103", "Singapore", 61234567, 1.2941178, 103.8528128);

        Branch sasukeOutlet_2 = new Branch(sasukeJapan, "930 Yishun Ave 2, #XX-XX Northpoint Shopping Centre",
                "", "Singapore", "Singapore", "769098", "Singapore", 61234567, 1.4298358, 103.8355685);

        /* ==== Deals ==== */
        new Deal(sasukeOutlet_1,
                "Dinner Special Promotion",
                "Get 20% off for your bill for any meal that cost more than $100",
                tierDiscount,
                "Deal is only application from 7pm to 9pm",
                50,
                new Date());

        new Deal(sasukeOutlet_2,
                "Dinner Special Promotion",
                "Get 20% off for your bill for any meal that cost more than $100",
                tierDiscount,
                "Deal is only application from 7pm to 9pm",
                50,
                new Date());
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

        Config.merchantManager.addMerchant(oriental_garden);

        // Deals available for Oriental Garden
        Bitmap coverImage_oriental_garden = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverorientalgarden);

        PercentageDiscount deal1_peach_garden = new PercentageDiscount("OG1", coverImage_oriental_garden,
                oriental_garden, true, 50);
        Config.dealManager.addDeal(deal1_peach_garden);

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

        Config.merchantManager.addMerchant(pho_pho);

        // Deals available for Pho Pro Restaurant
        Bitmap coverImage_pho_pho = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverpho);

        PercentageDiscount deal1_pho_pho = new PercentageDiscount("PP1", coverImage_pho_pho,
                pho_pho, true, 50);
        Config.dealManager.addDeal(deal1_pho_pho);


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

        Config.merchantManager.addMerchant(merchant_fourseason_chicken);

        // Deals available for Texas Chicken
        Bitmap coverImage_four_chicken = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverfourseasonschicken);
        PercentageDiscount deal1_fourseason_chicken = new PercentageDiscount("tc1", coverImage_four_chicken,
                merchant_fourseason_chicken, true, 20);
        Config.dealManager.addDeal(deal1_fourseason_chicken);
        TierDiscount deal2_fourseason_chicken = new TierDiscount("tc2", coverImage_four_chicken,
                merchant_fourseason_chicken, true, 10, 30);
        Config.dealManager.addDeal(deal2_fourseason_chicken);

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

        Config.merchantManager.addMerchant(gold_thai);

        // Deals available for Gold Thai Restaurant
        Bitmap coverImage_gold_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.covergoldthai);
        TierDiscount deal1_gold_thai = new TierDiscount("GT1", coverImage_gold_thai,
                gold_thai, true, 20, 10);
        Config.dealManager.addDeal(deal1_gold_thai);

        PercentageDiscount deal2_gold_thai = new PercentageDiscount("GT2", coverImage_gold_thai,
                gold_thai, true, 40);
        Config.dealManager.addDeal(deal2_gold_thai);

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

        Config.merchantManager.addMerchant(lerk_thai);

        // Deals available for Gold Thai Restaurant
        Bitmap coverImage_lerk_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.covergoldthai);
        TierDiscount deal1_lerk_thai = new TierDiscount("LT1", coverImage_lerk_thai,
                lerk_thai, true, 100, 50);
        Config.dealManager.addDeal(deal1_lerk_thai);

        PercentageDiscount deal2_lerk_thai = new PercentageDiscount("LT2", coverImage_lerk_thai,
                lerk_thai, true, 20);
        Config.dealManager.addDeal(deal2_lerk_thai);

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

        Config.merchantManager.addMerchant(griddy_souffles);

        // Deals available for Griddy & Souffles
        Bitmap coverImage_waffles_souffles = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.coverwafflessouffles);
        TierDiscount deal1_griddy_souffles = new TierDiscount("GW1", coverImage_waffles_souffles,
                griddy_souffles, true, 10, 50);
        Config.dealManager.addDeal(deal1_griddy_souffles);

        PreferencesManager.initializeInstance(mContext);
    }
}