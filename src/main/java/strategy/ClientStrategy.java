/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package strategy;

import model.ProductModel;
import model.UserModel;

public class ClientStrategy implements UserStrategy{
    //Adiciona produto ao carrinho
    @Override
    public void addProduct(UserModel user, ProductModel product) throws NullPointerException, IllegalArgumentException {
        try {
            if (product.getStock() == 0){
                throw new IllegalArgumentException("Produto deve ter estoque maior que 0.");
            }
            user.getProductList().add(product);
        }
        catch(NullPointerException e) {
            throw new NullPointerException("Erro ao inserir produto.");
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }
    
}
