package model;
import java.util.ArrayList;

public class UserSellerModel extends UserModel{
    //é estatico e é daqui que vai ser lido os itens disponiveis para compra.
    private static ArrayList<ProductModel> sellingItems;
    public UserSellerModel (String username, String password) throws IllegalArgumentException {
        super(username, password);
    }

    //define a lista de itens que será usada
    public static void setListaProdutos(ArrayList<ProductModel> newSellingItems){
        sellingItems = newSellingItems;
    }
    //adiciona produto à lista de produtos, e faz o produto criado ter esse UserSeller como dono. Só deve ser chamada logo após
    //UserClientModel.buyCart.
    @Override
    public void addProduct(ProductModel product){
        product.setStock(product.getStock() - 1);
        sellingItems.add(product);
    }
}
