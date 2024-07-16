package model;

public class shoppingItem {
    private String name;
    private boolean purchased;
    private double price;
    private int amount;


    public shoppingItem(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        purchased = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String settedName) {
        this.name = settedName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double settedPrice) {
        this.price = settedPrice;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int settedAmount) {
        this.amount = settedAmount;
    }

    public boolean getPurchased() {
        return this.purchased;
    }

    public void setPurchased(boolean settedPurchased) {
        this.purchased = settedPurchased;
    }


}

