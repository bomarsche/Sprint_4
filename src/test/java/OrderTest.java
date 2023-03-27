import SamokatPOMs.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    private final String fname;
    private final String sname;
    private final String address;
    private final String phone;
    private final String date;
    private final String comment;


    public OrderTest(String fname, String sname, String address, String phone, String date, String comment) {
        this.fname = fname;
        this.sname = sname;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Константин", "Константинопольский", "Зорге 15", "89000001234", "27.03.2023", "Курьеру печеньки" },
                {"Изабелла", "Пупкинская", "Тверская 155", "84950009876", "01.04.2023", "Курьеру водочки" },

        };
    }

    @Before
    public void setUp(){
        //Проверяем соответствие версий вебдрайвера и браузера в системе
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();

        // Chromedriver init
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        // Firefoxdriver init
        /*FirefoxOptions options = new FirefoxOptions();
        options.addArguments();
        driver = new FirefoxDriver(options);*/


    }

    @Test
    //Проверка хэппикейса заказа самоката через кнопку в хэдере
    public void checkoutOrderFromHeader() {
        OrderPage objOrderPage = new OrderPage(driver);
        objOrderPage.openURL();
        objOrderPage.enterOrderFromHeader();
        objOrderPage.setFirstStepOrder(fname, sname, address, phone);
        objOrderPage.setSecondStepOrder(date, comment);
        objOrderPage.confirmationOrder();
        objOrderPage.acceptedOrder();
        objOrderPage.isOrderStatusPageDisplayed();
    }

    @Test
    //Проверка хэппикейса заказа самоката через кнопку в теле страницы
    public void checkoutOrderFromBody() {
        OrderPage objOrderPage = new OrderPage(driver);
        objOrderPage.openURL();
        objOrderPage.enterOrderFromBody();
        objOrderPage.setFirstStepOrder(fname, sname, address, phone);
        objOrderPage.setSecondStepOrder(date, comment);
        objOrderPage.confirmationOrder();
        objOrderPage.acceptedOrder();
        objOrderPage.isOrderStatusPageDisplayed();
    }

    @After
    public void tearDown() {
        // Закрыть браузер
        driver.quit();
    }

}
