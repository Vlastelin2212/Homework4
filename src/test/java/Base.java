import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.util.concurrent.TimeUnit;





public class Base {
    EventFiringWebDriver driver;
    Actions action;
    WebDriverWait wait;

    public Base(String browser){
        WebDriver initedDriver;
        if (browser.equals("firefox")) {
            initedDriver = initFirefoxDriver();
        } else {
            initedDriver = initChromeDriver();
        }
        this.driver = new EventFiringWebDriver(initedDriver);
        this.driver.register(new EventHandler());
        this.driver.manage().window().maximize();
        this.action = new Actions(this.driver);
        this.wait = new WebDriverWait(this.driver, 30);
        this.driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }
    private static WebDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver",
                Base.class.getResource("chromedriver.exe").getPath());
        return new ChromeDriver();
    }
    private static WebDriver initFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver",
                Base.class.getResource("geckodriver.exe").getPath());
        return new FirefoxDriver();
    }
    public void login() {
        this.driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0");
        this.driver.findElement(By.id("email")).sendKeys("webinar.test@gmail.com");
        this.driver.findElement(By.id("passwd")).sendKeys("Xcg7299bnSmMuRLp9ITw");
        WebElement button = this.driver.findElement(By.name("submitLogin"));
        button.click();
    }
    public void close() {
        this.driver.quit();
    }
}
