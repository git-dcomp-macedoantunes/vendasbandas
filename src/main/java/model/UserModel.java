package model;
import java.util.ArrayList;

import mediator.LogMediator;

public abstract class UserModel {
    protected String username;
    protected String password;
    protected LogMediator mediator;
    //Tenta criar um usuario com nome de usuário e senha de acordo com os métodos, e dá uma exceção caso os argumentos
    //sejam inválidos, pra o controller lidar com isso e poder informar ao usuário o problema.
    public UserModel(String username, String password) throws IllegalArgumentException {
        try {
            this.setUsername(username);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        try {
            this.setPassword(password);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
    
    //Verifica se o nome de usuário é valido, caso não seja, lança uma exceção
    private void setUsername(String username) throws IllegalArgumentException {
        if (username == null){
            throw new IllegalArgumentException("Erro ao receber nome de usuário.");
        }
        else if((username.trim().isEmpty())){
            throw new IllegalArgumentException("Nome de usuário não pode ser vazio.");
        }
        else if ((username.length()) > 30){
            throw new IllegalArgumentException("Nome de usuário não pode ser maior que 30 caracteres.");
        }
        else if((username.length()) < 8){
            throw new IllegalArgumentException("Nome de usuário não pode ser menor que 8 caracteres.");
        }
        else{
            this.username = username;
        }
    }
    
    //seta o mediator
    public void setMediator(LogMediator mediator){
        this.mediator = mediator;
    }

    //Verifica se a senha é válida, caso não seja, lança uma exceção
    private void setPassword(String password) throws IllegalArgumentException {
        if (username == null){
            throw new IllegalArgumentException("Erro ao receber senha.");
        }
        else if((username.trim().isEmpty())){
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }
        else if ((username.length()) > 30){
            throw new IllegalArgumentException("Senha não pode ser maior que 30 caracteres.");
        }
        else if((username.length()) < 8){
            throw new IllegalArgumentException("Senha não pode ser menor que 8 caracteres.");
        }
        else{
            this.password = password;
        }
    }
    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
    
    //Obriga as classes filhas a ter esses metodos
    public abstract void addProduct(ProductModel product) throws IllegalArgumentException, NullPointerException;
    public abstract ArrayList<ProductModel> getProductList();
}
