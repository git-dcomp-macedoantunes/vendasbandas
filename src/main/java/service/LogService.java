/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package service;
import java.util.ArrayList;
import java.util.HashMap;

import model.ProductModel;
import model.UserClientModel;
import model.UserModel;
import model.UserSellerModel;

//classe usada para "Logar", logar produtos e contas.
//ou seja, qunado se cria uma conta ou produto, ela cria e salva em um arquivo
public abstract class LogService {

    private static HashMap<String, UserModel> users = new HashMap<>();
    private static ArrayList<ProductModel> products = new ArrayList<>();

    //loga o usuário, dependendo de "mode" ele registra um tipo de usuário diferente
    //esse "mode" deve ser dado pelo controller.
    public static void logUser(String mode, String username, String password) throws IllegalArgumentException {
        UserModel user;
        try {
            if (users.get(username) != null){
                throw new IllegalArgumentException("Nome de usuário já existe");
            }
            if (mode.equals("seller")){
                user = new UserSellerModel(username,password);
                users.put(username, user);
                //TODO: saveToFile(file, user)
            }
            else if(mode.equals("client")){
                user = new UserClientModel(username,password);
                users.put(username, user);
                //TODO: saveToFile(file, user)
            }
            else{
                throw new IllegalArgumentException("Modo inválido (o usuario não deve ver isso)");
            }
        } 
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    //cria um produto, e cadastra ele no arquivo
    public static void logProduct(String name, double price, UserSellerModel owner, String description) throws IllegalArgumentException, NullPointerException{
        try {
            ProductModel product = new ProductModel(name, price, owner, description);
            products.add(product);
            //TODO: saveToFile(file, product)
        } catch (IllegalArgumentException | NullPointerException e) {
            throw e;
        }
    }

    public static ArrayList<ProductModel> getProductList(){
        return products;
    }
}
