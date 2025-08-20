/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package service;

import java.util.ArrayList;
import java.util.HashMap;
import model.ProductModel;
import model.UserModel;
import java.io.*;
import model.UserClientModel;
import model.UserSellerModel;
//classe responsavel por salvar e carregar os dados do arquivo
//tambem é responsavel por guardar os objetos em collecttions enquanto o programa
//é executado
public final class DataService {
    private static final HashMap<String, UserModel> users = new HashMap<>();
    private static final ArrayList<ProductModel> products = new ArrayList<>();
    
    public void readFromFiles(File produto, File usuario){
     
      try {
          int i=0, m=0;
          String linhaP, linhaU;
          
          
          //Leitores
          FileReader produtoR = new FileReader (produto);
          FileReader usuarioR = new FileReader (usuario);
          BufferedReader produtoBR = new BufferedReader (produtoR); 
          BufferedReader usuarioBR = new BufferedReader (usuarioR);
          
          
          // Todos referentes a Produtos
          ArrayList<String> name = new ArrayList<>();
          ArrayList<String> price = new ArrayList<>();
          ArrayList<String> stock = new ArrayList<>();
          ArrayList<String> description = new ArrayList<>();
          ArrayList<String> keyOwner = new ArrayList<>();
          
          
          //Todos referentes a Users C para Clients e S para Sellers
          ArrayList<String> usernameC = new ArrayList<>();
          ArrayList<String> passwordC = new ArrayList<>();
          ArrayList<String> usernameS = new ArrayList<>();
          ArrayList<String> passwordS = new ArrayList<>();
          
          
          //Colocando cada linha como um valor específico, já que será necessario separar por \n, apenas dos Users
          //VALORES IMPORTAM: Nao pode ter x usernames e y passowords, erro
          while  ((linhaU = usuarioBR.readLine()) != null){

              if (linhaU.replaceAll(" ", "").startsWith("clientusername")){
                usernameC.add(linhaU.replaceAll(" ", "").substring("clientusername".length()));
                i++;
                }
             
             if (linhaU.replaceAll(" ", "").startsWith("clientpassword")){
                passwordC.add(linhaU.replaceAll(" ", "").substring( "clientpassword".length()));
                }
             
             if (linhaU.replaceAll(" ", "").startsWith("sellerusername")){
                usernameS.add(linhaU.replaceAll(" ", "").substring("sellerusername".length()));
                m++;
                }
             
             if (linhaU.replaceAll(" ", "").startsWith("sellerpassword")){
                passwordS.add(linhaU.replaceAll(" ", "").substring("sellerpassword".length()));
                }
            }

             
          //colocação dos valores nos seus respectivos HashMap, com a KEY sendo se ele é cliente ou vendedor e o valor dele
           for (int j=0; j <i; j++){
               users.put("client" +j, (new UserClientModel (usernameC.get(j), passwordC.get(j))));
               } 

           for (int j=0; j <m; j++){
               users.put("seller" +j, (new UserSellerModel (usernameS.get(j), passwordS.get(j))));
               } 
               
          
          //Mesma coisa dos Users, só que para os Produtos
          i =0;
            while ((linhaP = produtoBR.readLine()) != null) {
                if (linhaP.startsWith("name")){ //ve se tem a palavra nome nessa linha
                    name.add(linhaP.replaceAll(" ", "").substring("name".length()));//ele vai ignorar a palavra nome "name DiscoA"
                i++;
                }
                
                if (linhaP.startsWith("price")) {
                    price.add(linhaP.replaceAll(" ", "").substring("price".length()));
                } 
                
                 if (linhaP.startsWith("stock")) {
                    stock.add(linhaP.replaceAll(" ", "").substring("stock".length()));
                } 
                 
                 if (linhaP.startsWith("description")) {
                    description.add(linhaP.replaceAll(" ", "").substring("description".length()));
                }
                
                if (linhaP.startsWith("keyOwner")) { //Esse keyOwner será o valor da KEY do User, mostrado mais acima
                    keyOwner.add(linhaP.replaceAll(" ", "").substring("keyOwner".length()));
                } 
            }
            
             for (int j=0; j <i; j++){
               products.add (new ProductModel(name.get(j), Double.parseDouble(price.get(j)), (description.get(j))));
               products.get(j).setStock(Integer.parseInt(stock.get(j)));
               products.get(j).setOwner(users.get(keyOwner.get(j)));
               }  
              
            
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
            }
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
    
    //getters
    public HashMap<String, UserModel> getUsers(){
        return users;
    }
    public ArrayList<ProductModel> getProducts(){
        return products;
    }
}

