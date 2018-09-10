import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Random;

public class NewProductTest {
    private NewProduct product;
    @BeforeMethod
    public void setUp() {
        this.product = new NewProduct("chrome");
    }
    @AfterMethod
    public void tearDown() {
        this.product.close();
    }

    static int generateQty(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    static double generatePrice(float min, float max) {
        double price = min + (max - min) * Math.random();
        return (double)Math.round(price * 100d) / 100d;
    }

    static String generateName(String subString, int length){
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int)(Math.random()*subString.length());
            builder.append(subString.charAt(character));
        }
        return builder.toString();
    }

    @Test
    public void testProductCreate() {
        String name = generateName("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 8);
        String qty = String.valueOf(generateQty(1, 100));
        String price = String.valueOf(generatePrice(0.1f, 100));
        System.out.println("Params are " + name + ", " + price + ", " + qty);
        this.product.login();
        this.product.enterProducts();
        this.product.clickNewProduct();
        this.product.fillProductAttributes(name, qty, price);
        this.product.activateProduct();
        this.product.saveProduct();
        this.product.openMainPage();
        this.product.openAllProductsList();
        this.product.findProduct(name);
        this.product.verifyProduct(name, qty, price);
    }
}