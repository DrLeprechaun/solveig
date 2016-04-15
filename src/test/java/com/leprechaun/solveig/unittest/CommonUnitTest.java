/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leprechaun.solveig.unittest;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author v_emelyanov
 */
public class CommonUnitTest {
    
    private Collection collection;
    
    public CommonUnitTest() {
    }
    
    @Ignore
    @BeforeClass
    public static void setUpClass() {
        System.out.println("@BeforeClass - oneTimeSetUp");
    }
    
    @Ignore
    @AfterClass
    public static void tearDownClass() {
        System.out.println("@AfterClass - oneTimeTearDown");
    }
    
    @Ignore
    @Before
    public void setUp() {
        collection = new ArrayList();
        System.out.println("@Before - setUp");
    }
    
    @Ignore
    @After
    public void tearDown() {
        collection.clear();
        System.out.println("@After - tearDown");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Ignore
    @Test
    public void testEmptyCollection() {
        assertTrue(collection.isEmpty());
        System.out.println("@Test - testEmptyCollection");
    }
    
    @Ignore
    @Test
    public void testOneItemCollection() {
        collection.add("itemA");
        assertEquals(1, collection.size());
        System.out.println("@Test - testOneItemCollection");
    }
    
}
