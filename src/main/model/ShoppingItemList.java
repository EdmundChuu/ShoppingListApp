package model;

import java.util.ArrayList;

public class ShoppingItemList {
    private String name;
    private boolean completion;
    private ArrayList<shoppingItem> theList; 

    public ShoppingItemList(String name) {
        this.name = name;
        this.completion = false;
        this.theList = new ArrayList<>();
    }

    public void setListName(String settedName) {
        this.name = settedName;
    }

    public String getListName() {
        return this.name;
    }
/** 
 * Effects:  Returns the total price of all items in the shopping list.
 * The total price is calculated by summing the product of 
 * the price and amount for each item in the list.
*/
    public double getTotPrice() {
        double totPrice = 0;
        for (shoppingItem si : this.theList) {
            totPrice += si.getPrice() * si.getAmount();
        }
        return totPrice;
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

