package GRAMPOWER;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

public class App {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLogin() {
        driver.get("https://data.grampower.com/hes/");
        WebElement usernameField = driver.findElement(By.cssSelector("input[placeholder='Username']"));
        usernameField.sendKeys("automationuser");
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        passwordField.sendKeys("grampower");
        WebElement loginButton = driver.findElement(By.cssSelector("input[value='LOG IN']"));
        loginButton.click();

        // Check if the user has been redirected to the correct page after successful login
         Assert.assertEquals(driver.getTitle(), "Gram Power");
    }
  @Test
      public void testInvalidLogin() {
        driver.get("https://data.grampower.com/hes/");
        WebElement usernameField = driver.findElement(By.cssSelector("input[placeholder='Username']"));
        usernameField.sendKeys("user");
        WebElement passwordField = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        passwordField.sendKeys("abc");
        WebElement loginButton = driver.findElement(By.cssSelector("input[value='LOG IN']"));
        loginButton.click();

        // Check if the user has been redirected to the correct page after successful login
        wait = new WebDriverWait(driver, 10);
        try {
            WebElement formElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[contains(., 'invalid login')]")));
            String formText = formElement.getText().trim(); // Trim the retrieved text
    
            // Assert after trimming the strings
            Assert.assertTrue(formText.contains("invalid login"), "The 'invalid login' message was not displayed.");
        } catch (TimeoutException e) {
            Assert.fail("The 'invalid login' message was not displayed within the specified time.");
        }
    }
  @Test
  public void siteCount() {
    testLogin();
    driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
   // WebElement sitecount = driver.findElement(By.cssSelector("h4#div_site_count"));
       WebElement sitecount = driver.findElement(By.xpath("//h4[@id='div_site_count']"));
    WebElement livesitecount = driver.findElement(By.xpath("//h4[@id='div_live_site_count']"));
    WebElement totalmeters = driver.findElement(By.xpath("//ul[@id='ul_site_initilized']/li/span[contains(text(),'meters')]"));
    WebElement rooms = driver.findElement(By.xpath("//div[text()='114']"));
    // Validating the text content
    Assert.assertEquals(sitecount.getText(), "SITES: 1");
    Assert.assertEquals(livesitecount.getText(), "LIVE SITES: 1");
    Assert.assertEquals(totalmeters.getText(), " 117 meters ");
    Assert.assertEquals(rooms.getText(), "114");
  }
  
   @Test
  public void googleMap() throws InterruptedException {
    testLogin();
    Thread.sleep(2000);
   try {
    WebElement arrow = driver.findElement(By.xpath("//div[@class='arrow-next']"));
    arrow.click();
} catch (NoSuchElementException e) {
    
}
    Thread.sleep(1000);
    WebElement close = driver.findElement(By.xpath("//label[@onclick='suggestion_close();']"));
    close.click();
     WebElement google = driver.findElement(By.xpath("//div[@title='ROFANANDA A']/img"));
     google.click();
     // Wait for the Google Map element to be present
     WebDriverWait wait = new WebDriverWait(driver, 10);
By mapElementLocator = By.xpath("//div[@title='5.0.176.60 : A_1306']");
wait.until(ExpectedConditions.presenceOfElementLocated(mapElementLocator));

// Assert the presence of the Google Map element
Assert.assertTrue(driver.findElement(mapElementLocator).isDisplayed(), "Google Map did not load.");
  }
 @Test
  public void compareValues() throws InterruptedException {
    testLogin();
    Thread.sleep(2000);
    WebElement arrow = driver.findElement(By.xpath("//div[@class='arrow-next']"));
    arrow.click();
    Thread.sleep(1000);
    WebElement close = driver.findElement(By.xpath("//label[@onclick='suggestion_close();']"));
    close.click();
     WebElement google = driver.findElement(By.xpath("//div[@title='ROFANANDA A']/img"));
     google.click();
     WebDriverWait wait = new WebDriverWait(driver, 10);
By mapElementLocator = By.xpath("//div[@title='5.0.176.60 : A_1306']");
wait.until(ExpectedConditions.presenceOfElementLocated(mapElementLocator));
     WebElement realtime = driver.findElement(By.xpath("//a[text()='Realtime']"));
    realtime.click();
    WebElement graphBar = driver.findElement(By.xpath("//div[@aria-label='A chart.']/div/table"));
Actions builder = new Actions(driver);
builder.moveToElement(graphBar).perform();
Thread.sleep(2000); 
WebElement tooltip = driver.findElement(By.xpath("//your-xpath-for-tooltip"));
String graphValue = tooltip.getText();
// Retrieve the value from the table column
WebElement consumedCell = driver.findElement(By.xpath("//table//th[text()='Consumed']/parent::tr/td[7]"));
String tableValue = consumedCell.getText();

// Compare the values
Assert.assertEquals(graphValue, tableValue, "The values from the graph and the table do not match!");

  }


  @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
