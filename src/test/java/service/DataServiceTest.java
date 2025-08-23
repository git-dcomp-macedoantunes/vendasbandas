/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import model.ProductModel;
import model.*;
import mediator.LogMediator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Pedro
 */
public class DataServiceTest {
    
    public static LogMediator mediatortest;
    public static DataService instance;
    public DataServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws IOException {
    mediatortest = new LogMediator();
    instance = mediatortest.getDataService();
    instance.readFromFiles();
    System.out.println(instance);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        
    }
    
    @AfterEach
    public void tearDown() throws IOException {
        instance.getUsers().clear();
        instance.getProducts().clear();
        instance.saveToFile();
    }

    /**
     * Test of readFromFiles method, of class DataService.
     */
    @Test
    public void testReadFromFiles() throws IOException {
        System.out.println("readFromFiles");
        instance.logUser("seller", "username", "password");
        assertEquals( "username" ,instance.getUsers().get("username").getUsername());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of logUser method, of class DataService.
     */
    @Test
    public void testLogUser() {
        System.out.println("logUser");
        String type = "seller";
        String username = "username02";
        String password = "password1";
        try{    
            instance.logUser(type, username, password);
        }
        catch(Exception e){
            fail(e.toString());
        }
    }

    /**
     * Test of logProduct method, of class DataService.
     */
    @Test
    public void testLogProduct() {   
        System.out.println("logProduct");
        String name = "Test Product5";
        double price = 5.0;
        UserModel owner = new UserSellerModel("Test Username", "Test Password");
        owner.setMediator(mediatortest);
        String description = "Test Description";
        int stock = 0;
        try{
            instance.logProduct(name, price, owner, description, stock);
        }
        catch(Exception e){
            fail(e.toString());
        }
    }
    
    @Test
    public void testProductList() throws IOException{
        System.out.println("setOwner (via DataService)");
        try{
            instance.logUser("seller", "userseller", "userseller");
            instance.logUser("client", "userclient", "userclient");
            UserModel userseller = instance.getUsers().get("userseller");
            UserModel userclient = instance.getUsers().get("userclient");
            
            instance.logProduct("producttest", 5, userseller, "description", 5); // logProduct ja tem addProductToList ent n vou testar com userseller
            instance.logProduct("producttest2", 5, userseller, "description", 5);
            
            mediatortest.addProductToList(userclient, "producttest");
            mediatortest.addProductToList(userclient, "producttest2");
            
        } catch (IOException e){
            fail(e.toString());
        }   
        //limpar a memoria atual
        instance.getUsers().clear();
        instance.getProducts().clear();
        //carregar os arquivos dnv
        instance.readFromFiles();
        assertEquals("producttest", instance.getUsers().get("userclient").getProductList().get(0).getName());
    }
    
}
