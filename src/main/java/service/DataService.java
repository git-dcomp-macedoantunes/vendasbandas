/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package service;

import java.util.ArrayList;
import java.util.HashMap;
import model.ProductModel;
import model.UserModel;
//classe responsavel por salvar e carregar os dados do arquivo
//tambem é responsavel por guardar os objetos em collecttions enquanto o programa
//é executado
public final class DataService {
    private static final HashMap<String, UserModel> users = new HashMap<>();
    private static final ArrayList<ProductModel> products = new ArrayList<>();
    
    //public void readFromFiles(file produto, file usuario)
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
    
    //getters
    public HashMap<String, UserModel> getUsers(){
        return users;
    }
    public ArrayList<ProductModel> getProducts(){
        return products;
    }
}
