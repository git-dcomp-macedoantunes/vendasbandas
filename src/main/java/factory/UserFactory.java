/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package factory;
import model.UserClientModel;
import model.UserModel;
import model.UserSellerModel;

//Cria um "UserModel" de tipo especifico que é especificado em tempo de execução, por meio de "type" em LogService.
public class UserFactory {
    public static UserModel getUser(String username, String password, String type) throws IllegalArgumentException {
        try {
            if (type.equals("seller")){
                return new UserSellerModel(username, password);
            } 
            else if (type.equals("client")) {
                return new UserClientModel(username, password);
            } else{
                //erro apenas para quem esta desenvolvendo a aplicação
                throw new IllegalArgumentException("Tipo de usuário inválido (o usuario nao deve ver esse erro)");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }
        
    }
}
