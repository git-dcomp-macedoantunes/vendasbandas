package model;
import java.util.ArrayList;

public class UserClientModel extends UserModel {

    private ArrayList<ProductModel> shoppingCart = new ArrayList<>();
    public UserClientModel (String username, String password) throws IllegalArgumentException {
        super(username, password);
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
    
    //Adiciona produto ao carrinho
    @Override
    public void addProduct(ProductModel product) throws NullPointerException, IllegalArgumentException {
        try {
            if (product.getStock() == 0){
                throw new IllegalArgumentException("Produto deve ter estoque maior que 0.");
            }
            mediator.addProductToList(this, product);
        }
        catch(NullPointerException e) {
            throw new NullPointerException("Erro ao inserir produto.");
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    @Override
    public ArrayList<ProductModel> getProductList(){
        return shoppingCart;
    }
}
