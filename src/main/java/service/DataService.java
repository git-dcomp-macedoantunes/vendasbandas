/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import factory.UserFactory;
import mediator.LogMediator;
import model.ProductModel;
import model.UserClientModel;
import model.UserModel;
import model.UserSellerModel;
import org.springframework.stereotype.Service;

//classe responsavel por salvar e carregar os dados do arquivo
//tambem é responsavel por guardar os objetos em collecttions enquanto o programa
//é executado
@Service
public final class DataService {
    private final Path FILEPATHPRODUCTS = Path.of("products.json");
    private final Path FILEPATHUSERS = Path.of("users.json");
    private static final HashMap<String, UserModel> users = new HashMap<>();
    private static final ArrayList<ProductModel> products = new ArrayList<>();

    private LogMediator mediator;

    public DataService(LogMediator mediator){
        this.mediator = mediator;
    }

    public void readFromFiles() throws IOException {
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
    private String fileToString(Path fileName) throws IOException {
        return Files.readString(fileName);
    }

  //salva os dados do produto e usuário em arquivos JSON
    public void saveToFile() throws IOException {
        JSONArray jsonArrayProducts = new JSONArray(); //criação da lista
        for (int i = 0; i < products.size(); i++) {
            ProductModel product = products.get(i);
            JSONObject obj = new JSONObject();
            obj.put("name", product.getName());
            obj.put("price", product.getPrice());
            obj.put("description", product.getDescription());
            obj.put("stock", product.getStock());
            //Adição dos atributos referentes ao produto

            jsonArrayProducts.put(obj);
        }
        JSONArray jsonArrayUsers = new JSONArray();
        List<UserModel> usersList = new ArrayList<>(users.values()); //criação da ArrayList para o recebimento dos índices
        for (int j = 0; j < usersList.size(); j++ ) {
            UserModel user = usersList.get(j);
            JSONObject obj = new JSONObject();
            obj.put("username", user.getUsername());
            String password = mediator.getUserPassword (user);
            obj.put("password", password);
            String type;
            if (user instanceof UserClientModel) {
                type = "client";
            }
            else if (user instanceof UserSellerModel) {
                type = "seller";
            }
            else {
                type = "unknown";
            }
            obj.put("type", type);

        JSONArray userProducts = new JSONArray();
        List<ProductModel> list = mediator.getProductList(user); //chama o método que retorna a lista de produtos de um usuário
        for (int k = 0; k < list.size(); k++) {
            ProductModel product = list.get(k);
            JSONObject pObj = new JSONObject();
            pObj.put("name", product.getName());
            userProducts.put(pObj);
        }
        obj.put("products", userProducts);
        jsonArrayUsers.put(obj);
        }

        Files.write(FILEPATHPRODUCTS, jsonArrayProducts.toString(4).getBytes()); //salva os arquivos
        Files.write(FILEPATHUSERS, jsonArrayUsers.toString(4).getBytes());
    }
    
    //cria usuario, coloca no hash e salva no arquivo
    public void logUser(String type, String username, String password) throws IllegalArgumentException, IOException {
        UserModel user;
        try {
            if (getUsers().get(username) != null){ //caso haja colisão
                throw new IllegalArgumentException("Nome de usuário já existe");
            }else{
                user = UserFactory.getUser(username,password,type);
                user.setMediator(mediator);
                getUsers().put(username, user);
                saveToFile();
            }
        } 
        catch (IllegalArgumentException | IOException e) {
            throw e;
        }
    }

    //cria produto, adiciona a lista e salva no arquivo
    public void logProduct(String name, double price, UserModel owner, String description, int stock) throws IllegalArgumentException, NullPointerException, IOException{
        try {
            ProductModel product = new ProductModel(name, price, description, stock);
            getProducts().add(product);
            owner.addProduct(product);
            saveToFile();
        } catch (IllegalArgumentException | NullPointerException | IOException e) {
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
