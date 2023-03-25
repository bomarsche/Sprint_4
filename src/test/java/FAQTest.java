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
public class FAQTest {

    private WebDriver driver;

    private final String faqItemTitle;
    private final String faqItemText;


    public FAQTest(String faqItemTitle, String faqItemText) {
        this.faqItemTitle = faqItemTitle;
        this.faqItemText = faqItemText;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой." },
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим." },
                {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}, //На сайте в этом пункте опечатка в тексте ¯\_(ツ)_/¯ . Поэтому будет падать тест
        };
    }

    @Before
    public void setUp(){
        //Проверяем соответствие версий вебдрайвера и браузера в системе
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();

        // Chromedriver init
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*", "--disable-extensions");
        driver = new ChromeDriver(options);

        // Firefoxdriver init
        /*FirefoxOptions options = new FirefoxOptions();
        options.addArguments();
        driver = new FirefoxDriver(options);*/
    }

    @Test
    // Проверка отображения блока "Вопросы о важном", а также корректного функционирования аккордеона и соответствия пары вопрос-ответы
    public void checkFAQAccordion () throws InterruptedException {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.openURL();
        objMainPage.setAcceptCookieButton();
        objMainPage.isFAQBlockDisplayed();
        objMainPage.getFAQItemText(faqItemTitle, faqItemText);
    }

    @After
    public void tearDown() {
        // Закрыть браузер
        driver.quit();
    }

}
