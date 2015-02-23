package com.example.leon.dingoapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.leon.dingoapplication.Entity.Address;
import com.example.leon.dingoapplication.Entity.Merchant;
import com.example.leon.dingoapplication.Entity.PercentageDiscount;

/**
 * Created by Leon on 23/2/2015.
 */
public class Bootstrap {
    Context mContext;

    public Bootstrap(Context context) {
        this.mContext = context;
        set();
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
        Address address_peach_garden = new Address("301", "Upper Thomson Rd", "#01-88 Thomson Plaza", 574408);
        Merchant peach_garden = new Merchant(merchantId_peach_garden, image_peach_garden,
                company_peach_garden, merchantName_peach_garden, address_peach_garden);

        // Deals available for Peach Garden
        PercentageDiscount deal1_peach_garden = new PercentageDiscount("PG1", true, 50);
        peach_garden.addDeal(deal1_peach_garden);

        Constants.merchantManager.addMerchant(peach_garden);

        // Merchant details for Pho Street
        String merchantId_pho_street = "merchant02";
        Bitmap image_pho_street = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.phostreet);
        String company_pho_street = "Pho Street";
        String merchantName_pho_street = "Unknown Owner";
        Address address_pho_street = new Address("311", "New Upper Changi Road", "#B1-40", 467360);
        Merchant pho_street = new Merchant(merchantId_pho_street, image_pho_street,
                company_pho_street, merchantName_pho_street, address_pho_street);

        // Deals available for Pho Street Restaurant
        PercentageDiscount deal1_pho_street = new PercentageDiscount("PS1", true, 50);
        pho_street.addDeal(deal1_pho_street);

        Constants.merchantManager.addMerchant(pho_street);


        Bitmap image_griddy_waffles = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.griddywaffles);
        Bitmap image_lerk_thai = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.lerkthai);
        Bitmap image_texas_chicken = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.texaschicken);
    }
}
