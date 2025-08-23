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
    public static void setUpClass() {
        mediatortest = new LogMediator();
        instance = mediatortest.getDataService();
        //try{
        //(new DataServiceTest()).testLogProduct();
        (new DataServiceTest()).testLogUser();
        //instance.readFromFiles();
        //} catch (IOException e){
            
        //}
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        instance.readFromFiles();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of readFromFiles method, of class DataService.
     */
    @Test
    public void testReadFromFiles() throws IOException {
        System.out.println("readFromFiles");
        instance.readFromFiles();
        assertEquals( "username4" ,instance.getUsers().get("username4").getUsername());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of logUser method, of class DataService.
     */
    @Test
    public void testLogUser() {
        System.out.println("logUser");
        String type = "seller";
        String username = "username3";
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
        String name = "TestProduct";
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
    
}
