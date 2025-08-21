/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediator;

import java.util.ArrayList;

import factory.UserFactory;
import model.ProductModel;
import model.UserModel;
import model.UserSellerModel;
import service.DataService;

//classe usada para "Logar", logar produtos e contas.
//ou seja, qunado se cria uma conta ou produto, ela cria e fala pra outra salvar num arquivo
//media interações entre Controller e Model para tornar elas mais simples

public class LogMediator implements Mediator{
    //é final porq não é pra mudar o endereço dele.
    private final DataService data = new DataService(this);

    //loga o usuário, dependendo de "mode" ele registra um tipo de usuário diferente
    //esse "mode" deve ser dado pelo controller.
    // !!!!!!! Util pra controller
    @Override
    public void logUser(String type, String username, String password) throws IllegalArgumentException {
        UserModel user;
        try {
            if (data.getUsers().get(username) != null){
                throw new IllegalArgumentException("Nome de usuário já existe");
            }else{
                user = UserFactory.getUser(username,password,type);
                user.setMediator(this);
                data.getUsers().put(username, user);
                //TODO: data.saveToFile(file, user)
            }
        } 
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    //cria um produto, e cadastra ele no arquivo
    // !!!!!!! Util pra controller
    @Override
    public void logProduct(String name, double price, UserModel owner, String description, int stock) throws IllegalArgumentException, NullPointerException{
        try {
            ProductModel product = new ProductModel(name, price, description, stock);
            data.getProducts().add(product);
            owner.addProduct(product);
            //TODO: data.saveToFile(file, product)
        } catch (IllegalArgumentException | NullPointerException e) {
            throw e;
        }
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
    public void addProductToList(UserModel user, String product){
        ProductModel productFound = findProductByName(product);
        user.addProduct(productFound);
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
}
