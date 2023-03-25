package SamokatPOMs;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class MainPage {

    private WebDriver driver;

    //URL
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Вопросы о важном (FAQ)
    private By faqBlock = By.className("Home_FAQ__3uVm4");
    private By faqBlockItem = By.xpath(".//div[@data-accordion-component='AccordionItem']");
    private By faqBlockItemTitle = By.xpath(".//div[@data-accordion-component='AccordionItemButton']");
    private By faqBlockItemText = By.xpath(".//div[@data-accordion-component='AccordionItemPanel']/p");;

    //Плашка про куки
    private By cookieBlock = By.className("App_CookieConsent__1yUIN");
    private By acceptCookieButton = By.id("rcc-confirm-button");


    //Конструктор класса
    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    //Метод перехода по урлу
    public void openURL() {
        driver.get(PAGE_URL);
    }

    // Метод проверки отображения FAQ
    public boolean isFAQBlockDisplayed(){
        WebElement element = driver.findElement(faqBlock);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        WebElement FAQBlock = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(faqBlock));
        return FAQBlock.isDisplayed();
    }

    //Метод проверки наличия и соответствия вопросов и ответов
    public void getFAQItemText(String faqItemTitle, String faqItemText) throws InterruptedException {
        List<WebElement> items = driver.findElements(faqBlockItem);
        boolean isItemFound = false;
        for (WebElement element : items) {
            element.findElement(By.className("accordion__button")).click();
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
            Thread.sleep(500); //без слипа периодически падают тесты, т.к. не успевает отобразиться блок с ответом.
            if(element.findElement(faqBlockItemText).isDisplayed()) {
                String itemTitle = element.findElement(faqBlockItemTitle).getText();
                String itemText = element.findElement(faqBlockItemText).getText();
                if(itemTitle.equals(faqItemTitle) && itemText.equals(faqItemText)) {
                    isItemFound = true;
                    break;
                }
            }
        }
        Assert.assertTrue("Совпадения не найдены", isItemFound);
    }

    //Метод принятия кук
    public void setAcceptCookieButton() {
        WebElement clickAcceptCookieButton = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(cookieBlock));
        if (driver.findElement(cookieBlock).isDisplayed()) {
            driver.findElement(acceptCookieButton).click();
        }
    }

}
