/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mediator;

import java.util.ArrayList;

import model.ProductModel;
import model.UserModel;
public interface Mediator {
    //logue usuarios
    public void logUser(String type, String username, String password) throws IllegalArgumentException;
    //logue produtos
    public void logProduct(String name, double price, UserModel owner, String description, int stock) throws IllegalArgumentException, NullPointerException;
    //faça usuario user ser dono de produto product
    public void setOwner(UserModel owner, ProductModel product);
    //informe quais são os itens na lista do usuario (a venda ou no carrinho)
    public ArrayList<ProductModel> getProductList(UserModel user);
    //adicione produto a esse usuário
    public void addProductToList(UserModel user, String product);
    //encontre o produto com esse nome
    public ProductModel findProductByName(String product);
}
