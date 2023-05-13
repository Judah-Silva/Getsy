package com.example.getsy_v2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.getsy_v2.Product;
import com.example.getsy_v2.ShoppingCart;
import com.example.getsy_v2.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    User getUserById(int userId);

    @Query(("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername = :userName"))
    User getUserByUsername(String userName);

    @Insert
    void insert(ShoppingCart...carts);

    @Update
    void update(ShoppingCart...carts);

    @Delete
    void delete(ShoppingCart cart);

    @Query(" SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE)
    List<ShoppingCart> getAllCarts();

    @Query(" SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE + " WHERE mCartId = :cartId")
    ShoppingCart getCartByCartId(int cartId);

    @Query(" SELECT * FROM " + AppDatabase.SHOPPING_CART_TABLE + " WHERE mUserId = :userId")
    ShoppingCart getCartByUserId(int userId);

    @Insert
    void insert(Product...products);

    @Update
    void update(Product...products);

    @Delete
    void delete(Product product);

    @Query(" SELECT * FROM " + AppDatabase.PRODUCT_TABLE)
    List<Product> getAllProducts();

    @Query(" SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductId = :productId")
    Product getProductById(int productId);

}
