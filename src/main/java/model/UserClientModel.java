package model;
import java.util.ArrayList;

import strategy.ClientStrategy;
public class UserClientModel extends UserModel {

    private ArrayList<ProductModel> shoppingCart = new ArrayList<>();
    public UserClientModel (String username, String password) throws IllegalArgumentException {
        super(username, password);
        this.strategy = new ClientStrategy();
    }

    //Compra os itens do carrinho: remove eles do carrinho e retorna o preço.
    public double buyCart(){
        double price = 0;
        for (int i = 0; i < shoppingCart.size(); i++){
            price += shoppingCart.get(i).getPrice();
        }
        shoppingCart = new ArrayList<>();
        return price;
    }

    @Override
    public ArrayList<ProductModel> getProductList(){
        return shoppingCart;
    }
}
