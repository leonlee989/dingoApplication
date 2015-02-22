package com.example.leon.dingoapplication.Entity;

import android.media.Image;

import java.util.ArrayList;

/**
 * Restaurant object to contain necessary information
 * Created by Leon on 21/2/2015.
 */
public class Restaurant {
    /**
     * Logo for restaurant
     */
    Image image;
    /**
     * Name of the restaurant
     */
    String restaurantName;
    /**
     * Owner name of the restaurant
     */
    String ownerName;
    /**
     * Address of the restaurant
     */
    Address address;
    /**
     * Deals associated with the restaurant
     */
    ArrayList<Deal> dealList;

    /**
     * Constructor to initialize Restaurant Object
     * @param image
     * @param restaurantName
     * @param ownerName
     * @param address
     */
    public Restaurant(Image image, String restaurantName, String ownerName, Address address) {
        this.image = image;
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
        this.address = address;

        this.dealList = new ArrayList<Deal>();
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     *
     * @return
     */
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAddress(String houseNumber, String streetName, String unitNumber, int postalCode) {
        this.address = new Address(houseNumber, streetName, unitNumber, postalCode);
    }

    public ArrayList<Deal> getDealList() {
        return this.dealList;
    }

    public void setDealList(ArrayList<Deal> dealList) {
        this.dealList = dealList;
    }

    public void addDeal(Deal deal) {
        dealList.add(deal);
    }

    public void removeDeal(Deal removeDeal) {
        for (int i=0; i < dealList.size(); i++) {
            Deal deal = dealList.get(i);
            if (deal.getReferenceCode().equalsIgnoreCase(removeDeal.getReferenceCode())) {
                dealList.remove(i);
            }
        }
    }
}
