/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package strategy;

import model.ProductModel;
import model.UserModel;
//Força os clientes a ter como "adicionar produto"
    //Seller -> Itens que ele tem a venda
    //Client -> Itens que ele quer comprar
public interface UserStrategy {
    void addProduct(UserModel user, ProductModel product) throws IllegalArgumentException, NullPointerException;

}
