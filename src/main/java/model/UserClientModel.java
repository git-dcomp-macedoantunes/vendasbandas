package model;
import java.util.ArrayList;

public class UserClientModel extends UserModel {

    private ArrayList<ProductModel> shoppingCart;
    public UserClientModel (String username, String password) throws IllegalArgumentException {
        super(username, password);
        this.shoppingCart = new ArrayList<>();
        this.shoppingCart = new ArrayList<>();
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
            shoppingCart.add(product);
            product.setStock(product.getStock() - 1);
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
        return this.shoppingCart;
    }
}
