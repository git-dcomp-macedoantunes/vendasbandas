/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import factory.UserFactory;
import mediator.LogMediator;
import model.ProductModel;
import model.UserClientModel;
import model.UserModel;
import model.UserSellerModel;

//classe responsavel por salvar e carregar os dados do arquivo
//tambem é responsavel por guardar os objetos em collecttions enquanto o programa
//é executado
public final class DataService {
    private static final String FILEPATHPRODUCTS = "src/savedfiles/products.json";
    private static final String FILEPATHUSERS = "src/savedfiles/users.json";
    private static final HashMap<String, UserModel> users = new HashMap<>();
    private static final ArrayList<ProductModel> products = new ArrayList<>();

    private LogMediator mediator;

    public DataService(LogMediator mediator){
        this.mediator = mediator;
    }

    public void readFromFiles(File produto, File usuario) throws IOException {
        //Foram criadas quatro variáveis para que o arquivo pudesse ser colocado no formato "String" e implementado em um array no formato "JSON"
        String fileProducts = fileToString (FILEPATHPRODUCTS);
        String fileUsers = fileToString (FILEPATHUSERS);
        JSONArray jsonArrayProducts = new JSONArray (fileProducts);
        JSONArray jsonArrayUsers = new JSONArray (fileUsers);

        JSONObject obj;
        //keys nos JSONObjects:
        //UserModel -> username, password, type, products (array)
        //ProductModel -> name, price, description, stock
        //Essa implementação consiste em criar objetos, indicando suas chaves e valores, com o intuito de adicionar em ArraysLists
        for (int i = 0; i < jsonArrayProducts.length(); i++) {
            obj = jsonArrayProducts.getJSONObject(i);
            ProductModel product = new ProductModel(obj.getString("name"), obj.getDouble("price"), 
                                                    obj.getString("description"), obj.getInt("stock"));
            products.add(product);
            //Criação de produto para cada JSONObject no array
        }

        for (int i = 0; i < jsonArrayUsers.length(); i++) {
            obj = jsonArrayUsers.getJSONObject(i);
            UserModel user;
            if (obj.getString("type").equals("client")){
                 user  = new UserClientModel(obj.getString("username"), obj.getString("password"));
            }
            else if (obj.getString("type").equals("seller")){
                user  = new UserSellerModel(obj.getString("username"), obj.getString("password"));
            }
            else {
                throw new IllegalArgumentException(obj.getString("type"));
            }
            //Cria objetos e coloca no hash para depois, por meio do array, relacionar os produtos com cada objeto
            users.put(user.getUsername(), user);
            JSONArray arrayProducts = obj.getJSONArray("products");

            for (int j = 0; j <  arrayProducts.length(); j++) {
                mediator.addProductToList(user, arrayProducts.getString(j)); //adiciona produto à lista do usuário
            }
        }


    }

    //transforma o arquivo fileName em apenas uma string, para que funcione no JSONArray
    private String fileToString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void saveToFiles(File produtoFile, File usuarioFile) {}

    //cria usuario, coloca no hash e salva no arquivo
    public void logUser(String type, String username, String password) throws IllegalArgumentException {
        UserModel user;
        try {
            if (getUsers().get(username) != null){ //caso haja colisão
                throw new IllegalArgumentException("Nome de usuário já existe");
            }else{
                user = UserFactory.getUser(username,password,type);
                user.setMediator(mediator);
                getUsers().put(username, user);
                //TODO: data.saveToFile(users, FILEPATHUSERS)
            }
        } 
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    //cria produto, adiciona a lista e salva no arquivo
    public void logProduct(String name, double price, UserModel owner, String description, int stock) throws IllegalArgumentException, NullPointerException{
        try {
            ProductModel product = new ProductModel(name, price, description, stock);
            getProducts().add(product);
            owner.addProduct(product);
            //TODO: data.saveToFile(product, FILEPATHPRODUCTS)
        } catch (IllegalArgumentException | NullPointerException e) {
            throw e;
        }
    }

    //getters
    public HashMap<String, UserModel> getUsers(){
        return users;
    }
    public ArrayList<ProductModel> getProducts(){
        return products;
    }
}
