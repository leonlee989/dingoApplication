package com.example.leon.dingoapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.leon.dingoapplication.Entity.Address;
import com.example.leon.dingoapplication.Entity.Merchant;
import com.example.leon.dingoapplication.Entity.PercentageDiscount;
import com.example.leon.dingoapplication.Entity.TierDiscount;

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
        // Merchant details for Peach Garden
        String merchantId_peach_garden = "merchant01";
        Bitmap image_peach_garden = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.peachgarden);
        String company_peach_garden = "Peach Garden";
        String merchantName_peach_garden = "Veronica Tan";
        String merchantType_peach_garden = "Chinese Food";
        Address address_peach_garden = new Address("301", "Upper Thomson Rd", "#01-88 Thomson Plaza", 574408);
        Merchant peach_garden = new Merchant(merchantId_peach_garden, image_peach_garden,
                company_peach_garden, merchantName_peach_garden, merchantType_peach_garden,
                address_peach_garden);

        Constants.merchantManager.addMerchant(peach_garden);

        // Deals available for Peach Garden
        PercentageDiscount deal1_peach_garden = new PercentageDiscount("PG1", peach_garden, true, 50);
        Constants.dealManager.addDeal(deal1_peach_garden);

        // Merchant details for Pho Street
        String merchantId_pho_street = "merchant02";
        Bitmap image_pho_street = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.phostreet);
        String company_pho_street = "Pho Street";
        String merchantName_pho_street = "Unknown Owner";
        String merchantType_pho_street = "Vietnamese Food";
        Address address_pho_street = new Address("311", "New Upper Changi Road", "#B1-40", 467360);
        Merchant pho_street = new Merchant(merchantId_pho_street, image_pho_street,
                company_pho_street, merchantName_pho_street, merchantType_pho_street,
                address_pho_street);

        Constants.merchantManager.addMerchant(pho_street);

        // Deals available for Pho Street Restaurant
        PercentageDiscount deal1_pho_street = new PercentageDiscount("PS1", pho_street, true, 50);
        Constants.dealManager.addDeal(deal1_pho_street);

        // Merchant details for Lerk Thai
        String merchantId_lerk_thai = "merchant03";
        Bitmap image_lerk_thai = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.lerkthai);
        String company_lerk_thai = "Lerk Thai";
        String merchantName_lerk_thai = "Unknown Owner";
        String merchantType_lerk_thai = "Thai Food";
        Address address_lerk_thai = new Address("36", "Senoko Crescent", "", 758282 );
        Merchant lerk_thai = new Merchant(merchantId_lerk_thai, image_lerk_thai, company_lerk_thai,
                merchantName_lerk_thai, merchantType_lerk_thai, address_lerk_thai);

        Constants.merchantManager.addMerchant(lerk_thai);

        // Deals available for Lerk Thai Restaurant
        TierDiscount deal1_lerk_thai = new TierDiscount("lt1", lerk_thai, true, 20, 10);
        Constants.dealManager.addDeal(deal1_lerk_thai);

        PercentageDiscount deal2_lerk_thai = new PercentageDiscount("t2", lerk_thai, true, 40);
        Constants.dealManager.addDeal(deal2_lerk_thai);

        // Merchant details for Griddy Waffles
        String merchantId_griddy_waffles = "merchant04";
        Bitmap image_griddy_waffles = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.griddywaffles);
        String company_griddy_waffles = "Griddy Waffles";
        String merchantName_griddy_waffles = "SM Ong";
        String merchantType_griddy_waffles = "Western Food";
        Address address_griddy_waffles = new Address("3", "Gateway Drive", "#B2-K12 Westgate", 608532);
        Merchant griddy_waffles = new Merchant(merchantId_griddy_waffles, image_griddy_waffles,
                company_griddy_waffles, merchantName_griddy_waffles, merchantType_griddy_waffles,
                address_griddy_waffles);

        Constants.merchantManager.addMerchant(griddy_waffles);

        // Deals available for Griddy Waffles
        TierDiscount deal1_griddy_waffles = new TierDiscount("gw1", griddy_waffles, true, 10, 50);
        Constants.dealManager.addDeal(deal1_griddy_waffles);

        // Merchant details for Texas Chicken
        String merchantId_texas_chicken = "merchant05";
        Bitmap image_texas_chicken = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.texaschicken);
        String company_texas_chicken = "Texas Chicken";
        String merchantName_texas_chicken = "George W. Church Sr.";
        String merchantType_texas_chicken = "Western Food";
        Address address_texas_chicken = new Address("33", "Sengkang Ave West", "#B1-06/07, The Seletar Mall", 797653);
        Merchant merchant_texas_chicken = new Merchant(merchantId_texas_chicken, image_texas_chicken,
                company_texas_chicken, merchantName_texas_chicken, merchantType_texas_chicken,
                address_texas_chicken);

        Constants.merchantManager.addMerchant(merchant_texas_chicken);

        // Deals available for Texas Chicken
        PercentageDiscount deal1_texas_chicken = new PercentageDiscount("tc1", merchant_texas_chicken, true, 20);
        Constants.dealManager.addDeal(deal1_texas_chicken);
        TierDiscount deal2_texas_chicken = new TierDiscount("tc2", merchant_texas_chicken, true, 10, 30);
        Constants.dealManager.addDeal(deal2_texas_chicken);
    }
}
