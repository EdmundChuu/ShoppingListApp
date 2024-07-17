package model;

import java.util.ArrayList;

public class ShoppingItemList {
    private String name;
    private boolean completion;
    private double totPrice;
    private ArrayList<shoppingItem> theList; 

    public ShoppingItemList(String name) {
        this.name = name;
        this.completion = false;
        this.totPrice = 0;
        this.theList = new ArrayList<>();
    }

    public void setListName(String settedName) {
        this.name = settedName;
    }

    public String getListName() {
        return this.name;
    }

    public void setListPrice(double settedPrice) {
        this.totPrice = settedPrice;
    }

    public double getTotPrice() {
        for(shoppingItem si : this.theList) {
            si.getPrice();
            this.totPrice = 0;
        }
        return this.totPrice;
    }

    public void setCompletion(boolean settedCompletion) {
        this.completion = settedCompletion;
    }

    public boolean getCompletion() {
        return this.completion;
    }

    public ArrayList<shoppingItem> getList() {
        return this.theList;
    }









}

