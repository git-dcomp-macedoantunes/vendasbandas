package model;

public class ProductModel{
    private String name;
    private double price;
    private int stock;
    private String description;
    private UserModel owner;
 
    //cria um produto, coloca stock = 0, e deixa o owner para o mediator lidar
    public ProductModel(String name, double price, String description, int stock) throws IllegalArgumentException, NullPointerException{
        try {
        setName(name);
        setPrice(price);
        setDescription(description);
        setStock(stock);
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    //tenta colocar a descrição, caso seja invalida, da uma exceção
    private void setDescription(String description) throws IllegalArgumentException{
        if((name.length()) > 200){
            throw new IllegalArgumentException("A descrição não deve conter mais de 200 caracteres.");
        }
        else{
            this.description = description;
        }
    }

    //tenta colocar o dono, caso seja inválido, da uma exceção
    public void setOwner(UserModel owner) throws NullPointerException{
        this.owner = owner;
    }
    //Tenta colocar o preço, caso seja inválido, da uma exceção
    private void setPrice(double price) throws IllegalArgumentException{
        if (price <= 0){
            throw new IllegalArgumentException("Preço não deve ser menor ou igual a 0.");
        } 
        else {
            this.price = price;
        }
    }

    //Tenta colocar o nome, caso seja inválido, da uma exceção
    private void setName(String name) throws IllegalArgumentException {
        if (name == null){
            throw new IllegalArgumentException("Erro ao receber nome do produto.");
        }
        else if((name.trim().isEmpty())){
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        else if ((name.length()) > 30){
            throw new IllegalArgumentException("Nome do produto não pode ser maior que 30 caracteres.");
        }
        else if((name.length()) < 8){
            throw new IllegalArgumentException("Nome do produto não pode ser menor que 8 caracteres.");
        }
        else{
            this.name = name;
        }
    }

    //getters e setters
    public int getStock(){
        return this.stock;
    }

    public double getPrice(){
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public final void setStock(int stock) throws IllegalArgumentException{
        if (stock < 0){
            throw new IllegalArgumentException("Estoque não deve ser menor que 0.");
        } else{
            this.stock = stock;
        }
    }

    public String getOwner(){
        return owner.getUsername();
    }

}
