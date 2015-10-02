/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import com.jayway.restassured.parsing.Parser;
        


/**
 *
 * @author Elev
 */
public class MyTest {
    
    public MyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
            baseURI = "http://localhost:8080";
            defaultParser = Parser.JSON;
            basePath = "/Rest_JAX_RS/api/quote";
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testGet(){
        when()
                .get("/1")
                .then()
                .body("quote",equalTo("Friends are kisses blown to us by angels"));
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
