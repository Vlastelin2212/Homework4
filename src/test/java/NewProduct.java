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

import static java.lang.Thread.sleep;

class NewProduct extends Base {
    NewProduct(String browser){
        super(browser);
    }
    void enterProducts() {
        this.action.moveToElement(this.driver.findElement(By.id("subtab-AdminCatalog")))
                .build().perform();
        this.action.moveToElement(this.driver.findElement(By.id("subtab-AdminProducts")))
                .click().build().perform();
    }
    void clickNewProduct() {
        this.action.moveToElement(this.driver.findElement(By.id("page-header-desc-configuration-add")))
                .click().build().perform();
    }
    private static void scrollElementIntoMiddle(WebDriver driver, WebElement element) {
        String command = "var viewPortHeight = "
                + "Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) driver).executeScript(command, element);
    }
    void fillProductAttributes(String name, String qty, String price) {
        this.action.moveToElement(this.driver.findElement(By.id("form_step1_name_1")))
                .click().sendKeys(name).build().perform();
        this.action.moveToElement(this.driver.findElement(By.id("form_step1_qty_0_shortcut")))
                .click().sendKeys(Keys.BACK_SPACE)
                .sendKeys(qty).build().perform();
        WebElement priceInput = this.driver.findElement(By.id("form_step1_price_shortcut"));
        scrollElementIntoMiddle(this.driver, priceInput);
        priceInput.clear();
        this.action.moveToElement(priceInput).click().sendKeys(Keys.BACK_SPACE)
                .sendKeys(price).build().perform();
    }
    void activateProduct() {
        WebElement activateSwitch = driver.findElement(
                By.cssSelector("#form > div.product-footer > div.col-lg-5 > div"));
        this.action.moveToElement(activateSwitch)
                .click().build().perform();
        System.out.println("Pop-up text after activating product is: " + this.checkPopUp());
    }
    void saveProduct() {
        this.action.moveToElement(this.driver.findElement(By.id("submit")))
                .click().build().perform();
        System.out.println("Pop-up text after saving product is: " + this.checkPopUp());
    }
    private String checkPopUp() {
        By locator = By.cssSelector("#growls div.growl-message");
        WebElement popUpMessage = this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        String message = popUpMessage.getText();
        this.action.moveToElement(this.driver.findElement(By.cssSelector("#growls div.growl-close")))
                .click().build().perform();
        return message;
    }
    void openMainPage() {
        this.driver.get("http://prestashop-automation.qatestlab.com.ua");
    }
    void openAllProductsList() {
        this.action.moveToElement(this.driver.findElement(By.cssSelector("a.all-product-link")))
                .click().build().perform();
    }
    void findProduct(String name) {
        WebElement search = this.driver.findElement(By.cssSelector(
                "#search_widget > form > input.ui-autocomplete-input"));
        this.action.moveToElement(search).click().sendKeys(name).sendKeys(Keys.ENTER).build().perform();
        WebElement product = this.driver.findElement(
                By.xpath("//*[@id=\"js-product-list\"]/div[1]/article[1]/div/a"));
        action.moveToElement(product).click().build().perform();
    }
    void verifyProduct(String name, String qty, String price) {
        WebElement elem = this.driver.findElement(By.cssSelector("#main h1[itemprop=\"name\"]"));
        System.out.println(elem.getText());
        assert elem.getText().equals(name);
        elem = this.driver.findElement(By.cssSelector("#main span[itemprop=\"price\"]"));
        System.out.println(elem.getText());
        assert elem.getAttribute("content").equals(price);
        elem = this.driver.findElement(By.cssSelector("#main div.product-quantities > span"));
        System.out.println(elem.getText());
        assert elem.getText().equals(qty + " Товары");
    }
}
