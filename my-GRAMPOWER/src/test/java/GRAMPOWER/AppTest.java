package GRAMPOWER;

import org.testng.annotations.Test;

public class AppTest {

    @Test
    public void testValidLogin() {
      
          App app = new App();
          app.setUp();
          app.testLogin();
          app.tearDown();
    }

    @Test
    public void testInvalidLogin() {
       
          App app = new App();
          app.setUp();
          app.testInvalidLogin();
          app.tearDown();
    }

    // Add more test methods as needed
}