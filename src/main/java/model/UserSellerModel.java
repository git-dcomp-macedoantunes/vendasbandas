package model;
import java.util.ArrayList;


public class UserSellerModel extends UserModel{
    //e guardado todos os itens dessa conta que estão sendo vendidos aqui separadamente
    //é final porq não é pra mudar o endereço dele
    private final ArrayList<ProductModel> sellingItems = new ArrayList<>();

    public UserSellerModel (String username, String password) throws IllegalArgumentException {
        super(username, password);
    }
    //adiciona produto à lista de produtos, e faz o produto criado ter esse UserSeller como dono. Só deve ser chamada logo após
    //UserClientModel.buyCart.
    @Override
    public void addProduct(ProductModel product) throws IllegalArgumentException, NullPointerException{
        mediator.addProductToList(this, product);
        mediator.setOwner(this, product);
    }
    
    @Override
    public ArrayList<ProductModel> getProductList(){
        return this.sellingItems;
    }

}
