/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductModelTest {
    
    private ProductModel instance;
    
    @BeforeEach
    public void setUp() {
        instance = new ProductModel("namenamee", 50.5, "descricao");
    }

    /**
     * Test of getStock method, of class ProductModel.
     */
    @Test
    public void testGetStock() {
        System.out.println("getStock");
        int expResult = 0;
        int result = instance.getStock();
        assertEquals(expResult, result, "Estoque inicial = 0");
        
    }

    /**
     * Test of getDescription method, of class ProductModel.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        String expResult = "descricao";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOwner method, of class ProductModel.
     */
    @Test
    public void testGetOwner() {
        System.out.println("getOwner");
        assertEquals("12345678", instance.getOwner());
    }
    
}
