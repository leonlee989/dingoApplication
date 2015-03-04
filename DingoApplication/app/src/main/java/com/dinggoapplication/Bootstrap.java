package com.dinggoapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dinggoapplication.Entity.Address;
import com.dinggoapplication.Entity.Merchant;
import com.dinggoapplication.Entity.PercentageDiscount;
import com.dinggoapplication.Entity.TierDiscount;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Leon on 23/2/2015.
 */
public class Bootstrap {
    static boolean completed = false;
    Context mContext;

    public Bootstrap(Context context) {
        this.mContext = context;

        if (!completed) {
            set();
            completed = true;
        }
    }

    public void set() {
        initializeEntity();
    }

    public void initializeEntity() {

        // TODO: Merchant details for Peach Garden
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

        // Deals available for Peach Garden
        PercentageDiscount deal1_peach_garden = new PercentageDiscount("OG1", oriental_garden, true, 50);
        Constants.dealManager.addDeal(deal1_peach_garden);

        // TODO: Merchant details for Pho Street
        String merchantId_pho_street = "merchant02";

        String password_pho_street = "123";

        Bitmap image_pho_street = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.phopho);

        String company_pho_street = "Pho Pho";

        String merchantName_pho_street = "Unknown Owner";

        String merchantDescription_pho_street = "Pho Street determines to bringing you delicious " +
                "and authentic Vietnamese cuisine. Freshest ingredients are carefully selected to " +
                "deliver unique flavors that complement their aromatic dishes";

        String merchantType_pho_street = "Vietnamese Food";

        Address address_pho_street = new Address("82", "Tanah Merah Kechil Ave", "#XX-XX", "465515");

        int contactNumber_pho_street = 61234567;

        String website_pho_street = "http://phopho.com.sg/";
        LatLng latLng_pho_street = new LatLng(1.3285, 103.9445);
        //LatLng latLng_pho_street = ApplicationFactory.getLocationFromAddress(address_pho_street.toString(), mContext);
        if (latLng_pho_street == null) {
            latLng_pho_street = new LatLng(1.3000, 103.8000);
        }

        Merchant pho_street = new Merchant(merchantId_pho_street, password_pho_street,
                image_pho_street, company_pho_street, merchantName_pho_street,
                merchantDescription_pho_street, merchantType_pho_street, address_pho_street,
                contactNumber_pho_street, website_pho_street, latLng_pho_street);

        Constants.merchantManager.addMerchant(pho_street);

        // Deals available for Pho Street Restaurant
        PercentageDiscount deal1_pho_street = new PercentageDiscount("PP1", pho_street, true, 50);
        Constants.dealManager.addDeal(deal1_pho_street);

        // TODO: Merchant details for Lerk Thai
        String merchantId_lerk_thai = "merchant03";

        String password_lerk_thai = "123";

        Bitmap image_lerk_thai = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.goldthai);

        String company_lerk_thai = "Gold Thai";

        String merchantName_lerk_thai = "Select Group Limited";

        String merchantDescription_lerk_thai = "Gold Thai @ Senoko Crescent offers a bountiful mix " +
                "of authentic Thai cuisine, with the plethora of dips and pastes used in our cuisine " +
                "and level of spiciness adjusted to local palates";

        String merchantType_lerk_thai = "Thai Food";

        Address address_lerk_thai = new Address("1", "Senoko Crescent", "", "758282");

        int contactNumber_lerk_thai = 61234567;

        String website_lerk_thai = "http://www.goldthai.com.sg/";
        LatLng latLng_lerk_thai = new LatLng(1.4649, 103.8066);
        //LatLng latLng_lerk_thai = ApplicationFactory.getLocationFromAddress(address_lerk_thai.toString(), mContext);
        if (latLng_lerk_thai == null) {
            latLng_lerk_thai = new LatLng(1.3000, 103.8000);
        }

        Merchant lerk_thai = new Merchant(merchantId_lerk_thai, password_lerk_thai, image_lerk_thai,
                company_lerk_thai, merchantName_lerk_thai, merchantDescription_lerk_thai,
                merchantType_lerk_thai, address_lerk_thai, contactNumber_lerk_thai,
                website_lerk_thai, latLng_lerk_thai);

        Constants.merchantManager.addMerchant(lerk_thai);

        // Deals available for Lerk Thai Restaurant
        TierDiscount deal1_lerk_thai = new TierDiscount("GT1", lerk_thai, true, 20, 10);
        Constants.dealManager.addDeal(deal1_lerk_thai);

        PercentageDiscount deal2_lerk_thai = new PercentageDiscount("GT2", lerk_thai, true, 40);
        Constants.dealManager.addDeal(deal2_lerk_thai);

        // TODO: Merchant details for Griddy Waffles
        String merchantId_griddy_waffles = "merchant04";

        String password_griddy_waffles = "123";

        Bitmap image_griddy_waffles = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.wafflessouffleslogo);

        String company_griddy_waffles = "Waffles & Souffles";

        String merchantName_griddy_waffles = "Ong Mei Kan";

        String merchantDescription_griddy_waffles = "Waffles & Souffles offers sweet and savoury " +
                "waffles artfully crafted to be visual and gastronomic delights";

        String merchantType_griddy_waffles = "Western Food";

        Address address_griddy_waffles = new Address("10", "Orchard Rd", "#XX-XX Orchard Rd", "238841");

        int contactNumber_griddy_waffles = 61234567;

        String website_griddy_waffles = "http://griddysouffles.com.sg/";
        //LatLng latLng_griddy_waffles = new LatLng(43.1, -87.9);
        LatLng latLng_griddy_waffles = ApplicationFactory.getLocationFromAddress(address_griddy_waffles.toString(), mContext);

        if (latLng_griddy_waffles == null) {
            latLng_griddy_waffles = new LatLng(1.3000, 103.8000);
        }

        Merchant griddy_waffles = new Merchant(merchantId_griddy_waffles, password_griddy_waffles,
                image_griddy_waffles, company_griddy_waffles, merchantName_griddy_waffles,
                merchantDescription_griddy_waffles, merchantType_griddy_waffles,
                address_griddy_waffles, contactNumber_griddy_waffles, website_griddy_waffles, latLng_griddy_waffles);

        Constants.merchantManager.addMerchant(griddy_waffles);

        // Deals available for Griddy Waffles
        TierDiscount deal1_griddy_waffles = new TierDiscount("GW1", griddy_waffles, true, 10, 50);
        Constants.dealManager.addDeal(deal1_griddy_waffles);

        // TODO: Merchant details for Texas Chicken
        String merchantId_texas_chicken = "merchant05";

        String password_texas_chicken = "123";

        Bitmap image_texas_chicken = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.fourseasonschicken);

        String company_texas_chicken = "Four Seasons Chicken";

        String merchantName_texas_chicken = "Hyden Sim.";

        String merchantDescription_texas_chicken = "Four Seasons Chicken offered freshly " +
                "prepared, high quality, delicious chicken and tenders with all your favorite " +
                "classic sides at a great value";

        String merchantType_texas_chicken = "Western Food";

        Address address_texas_chicken = new Address("23", "Serangoon Central", "#XX-XX, " +
                "Nex", "556083");

        int contactNumber_texas_chicken = 66342130;

        String website_texas_chicken = "http://www.fourchicken.com.sg/";
        //LatLng latLng_texas_chicken = new LatLng(1.3506, 103.8718);
        LatLng latLng_texas_chicken = ApplicationFactory.getLocationFromAddress(address_texas_chicken.toString(), mContext);
        if (latLng_texas_chicken == null) {
            latLng_texas_chicken = new LatLng(1.3000, 103.8000);
        }

        Merchant merchant_texas_chicken = new Merchant(merchantId_texas_chicken,
                password_texas_chicken, image_texas_chicken, company_texas_chicken,
                merchantName_texas_chicken, merchantDescription_texas_chicken,
                merchantType_texas_chicken, address_texas_chicken, contactNumber_texas_chicken,
                website_texas_chicken, latLng_texas_chicken);

        Constants.merchantManager.addMerchant(merchant_texas_chicken);

        // Deals available for Texas Chicken
        PercentageDiscount deal1_texas_chicken = new PercentageDiscount("tc1", merchant_texas_chicken, true, 20);
        Constants.dealManager.addDeal(deal1_texas_chicken);
        TierDiscount deal2_texas_chicken = new TierDiscount("tc2", merchant_texas_chicken, true, 10, 30);
        Constants.dealManager.addDeal(deal2_texas_chicken);

    }
}