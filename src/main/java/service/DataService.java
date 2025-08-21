/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package service;

import java.util.ArrayList;
import java.util.HashMap;

import mediator.LogMediator;
import model.ProductModel;
import model.UserModel;
import java.io.*;
import java.util.Scanner;

import model.UserClientModel;
import model.UserSellerModel;
import org.json.JSONArray;
import org.json.JSONObject;

//classe responsavel por salvar e carregar os dados do arquivo
//tambem é responsavel por guardar os objetos em collecttions enquanto o programa
//é executado
public final class DataService {
    private static final String filePathProducts = "src/savedfiles/products.json";
    private static final String filePathUsers = "src/savedfiles/users.json";
    private static final HashMap<String, UserModel> users = new HashMap<>();
    private static final ArrayList<ProductModel> products = new ArrayList<>();

    public void readFromFiles(File produto, File usuario){
        //Foram criadas quatro variáveis para que o arquivo pudesse ser colocado no formato "String" e implementado em um array no formato "JSON"
        String fileProducts = fileToString (filePathProducts);
        String fileUsers = fileToString (filePathUsers);
        JSONArray jsonArrayProducts = new JSONArray (fileProducts);
        JSONArray jsonArrayUsers = new JSONArray (fileUsers);

        JSONObject obj;

        //Essa implementação consiste em criar objetos, indicando suas chaves e valores, com o intuito de adicionar em ArraysLists
        for (int i = 0; i < jsonArrayProducts.length(); i++) {
            obj = jsonArrayProducts.getJSONObject(i);
            ProductModel product = new ProductModel(obj.getString("name"), obj.getDouble("price"),obj.getString("description"), obj.getInt("stock"));
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
                throw new IllegalArgumentException (obj.getString("type"));
            }
            //Cria objetos e coloca no hash para depois, por meio do array, relacionar os produtos com cada objeto
            users.put(user.getUsername(), user);
            JSONArray arrayProducts = obj.getJSONArray("products");

            for (int j = 0; j <  arrayProducts.length(); j++) {
                LogMediator.addProductToList(user,array.getString(j)); //adiciona produto à lista do usuário
            }
        }


    }

    private String fileToString(String fileName){
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }



    //lê produtos, *depois* lê usuários e então faz as ligações necessarias
    //de acordo com o usuario owner de produto[i]:
    //produto[i].owner = users.get(produto[i].owner)
    //então, checa a lista de (ids?) de ProductModel em cada UserModel
    //e cria a lista corretamente

    //public void saveToFile(file, usuario/produto)
    //formata ele
    //salva no arquivo(de produto ou usuario) como nova linha
    //modelo:
    //user: tipo usuario senha lista ids
    //produto: nome preco descricao estoque usuario id?



    public void saveToFiles(File produtoFile, File usuarioFile) {}


    //getters
    public HashMap<String, UserModel> getUsers(){
        return users;
    }
    public ArrayList<ProductModel> getProducts(){
        return products;
    }
}
