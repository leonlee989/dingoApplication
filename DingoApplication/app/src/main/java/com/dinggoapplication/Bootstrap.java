/*
 * Copyright (C) Technify Pte Ltd - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Lee Quee Leong <leequeeleong@technify.com.sg> and
 * Seah Siu Ngee <seahsiungee@techinify.com.sg, May 2015
 */

package com.dinggoapplication;

import android.content.Context;

import com.dinggoapplication.managers.PreferencesManager;
import com.dinggoapplication.utilities.ImageUtils;
import com.dinggoapplication.entities.Branch;
import com.dinggoapplication.entities.Company;
import com.dinggoapplication.entities.CuisineType;
import com.dinggoapplication.entities.Deal;
import com.dinggoapplication.entities.DealType;

import java.util.Date;

/**
 * Bootstrap class to initialize all entity and manager class
 * @author Lee Quee Leong & Seah Siu Ngee
 * @version 2.1
 * Created by Leon on 23/2/2015.
 */
public class Bootstrap {
    /** Application context */
    private Context mContext;

    /**
     * Constructor for Bootstrap class with the following parameters
     * @param context   Application context
     */
    public Bootstrap(Context context) {
        this.mContext = context;
    }

    /** Trigger method for bootstrapping */
    public void execute(boolean isExecute) {
        if (isExecute) {
            initializeData();
        }
    }

    /**
     * Initialization of data and store into database
     */
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

        /* *********************** Company details for Pho Pho ********************************* */
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

        /* ***************** Company details for Four Seasons Chicken *************************** */
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

        /* ***************** Company details for Gold Thai *************************** */
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

        /* ***************** Company details for Lerk Thai *************************** */
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

        /* ***************** Company details for Waffles & Souffles *************************** */
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

        /* ***************** Company details for Ice Cold Beer *************************** */
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

        /* ***************** Company details for Sasuke Japan *************************** */
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

        PreferencesManager.initializeInstance(mContext);
    }
}