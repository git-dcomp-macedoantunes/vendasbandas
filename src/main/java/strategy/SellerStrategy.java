/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package strategy;

import model.ProductModel;
import model.UserModel;

public class SellerStrategy implements UserStrategy {
    @Override
    public void addProduct(UserModel user, ProductModel product) throws IllegalArgumentException, NullPointerException{
        user.getProductList().add(product);
        product.setOwner(user);
    }
}
