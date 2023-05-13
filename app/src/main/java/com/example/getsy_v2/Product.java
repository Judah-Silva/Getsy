package com.example.getsy_v2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.getsy_v2.DB.AppDatabase;

@Entity(tableName = AppDatabase.PRODUCT_TABLE)
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int mProductId;

    private int mQuantity;
    private double mPrice;

    private String mName;
    private String mDescription;

    public Product(int quantity, double price, String name, String description) {
        mQuantity = quantity;
        mPrice = price;
        mName = name;
        mDescription = description;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mProductId=" + mProductId +
                ", mQuantity=" + mQuantity +
                ", mPrice=" + mPrice +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
