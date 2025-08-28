package model;
import java.util.ArrayList;


public class UserSellerModel extends UserModel{
    //e guardado todos os itens dessa conta que estão sendo vendidos aqui separadamente
    //é final porq não é pra mudar o endereço dele
    private ArrayList<ProductModel> sellingItems;

    public UserSellerModel(String username, String password) throws IllegalArgumentException {
        super(username, password);
        this.sellingItems = new ArrayList<>();
    }
    //adiciona produto à lista de produtos, e faz o produto criado ter esse UserSeller como dono. Só deve ser chamada logo após
    //UserClientModel.buyCart.
    @Override
    public void addProduct(ProductModel product) throws IllegalArgumentException, NullPointerException{
        sellingItems.add(product);
        mediator.setOwner(this, product);
    }
    
    @Override
    public ArrayList<ProductModel> getProductList(){
        return this.sellingItems;
    }

}
