/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediator;

import java.io.IOException;
import java.util.ArrayList;

import model.ProductModel;
import model.UserModel;
import model.UserSellerModel;
import service.DataService;
import org.springframework.stereotype.Component;

//classe usada para "Logar", logar produtos e contas.
//ou seja, qunado se cria uma conta ou produto, ela cria e fala pra outra salvar num arquivo
//media interações entre Controller e Model para tornar elas mais simples

@Component
public class LogMediator implements Mediator{
    //é final porq não é pra mudar o endereço dele.
    private final DataService data;
    
    public LogMediator() {
        this.data = new DataService(this);

    }

    //loga o usuário, dependendo de "mode" ele registra um tipo de usuário diferente
    //esse "mode" deve ser dado pelo controller.
    @Override
    public void logUser(String type, String username, String password) throws IllegalArgumentException, IOException{
        data.logUser(type, username, password);
    }

    //cria um produto, e cadastra ele no arquivo
    @Override
    public void logProduct(String name, double price, UserModel owner, String description, int stock) throws IllegalArgumentException, NullPointerException, IOException{
        data.logProduct(name, price, owner, description, stock);
    }
    
    //Verifica se owner é valido e da set no owner
    @Override
    public void setOwner(UserModel owner, ProductModel product) throws NullPointerException{
        product.setOwner(owner);
        if (owner == null || !(owner instanceof UserSellerModel)){
            throw new NullPointerException("Erro ao adquirir vendedor.");
        }
        else{
            product.setOwner(owner);
        }
    }
    
    //pega a lista do usuario especificado
    @Override
    public ArrayList<ProductModel> getProductList(UserModel user){
        return user.getProductList();
    }
    
    @Override
    public void addProductToList(UserModel user, String product) throws IOException{
        ProductModel productFound = findProductByName(product);
        user.addProduct(productFound);
        data.saveToFile();
    }

    @Override 
    public ProductModel findProductByName(String product){
        ArrayList<ProductModel> list = data.getProducts();
        for(int i = 0; i < list.size() ; i++){
            if(list.get(i).getName().equals(product))
            return list.get(i);
        }
        return null;
    }
    @Override
    public String getUserPassword(UserModel user){
        return user.getPassword();
    }
    @Override
    public DataService getDataService(){
        return data;
    }
}
