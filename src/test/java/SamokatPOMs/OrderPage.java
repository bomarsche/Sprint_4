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
import java.util.Random;


public class OrderPage {

    private WebDriver driver;

    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    //Кнопка заказать в хэдере
    private By headerOrderButton = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']");

    //Кнопка заказать на странице
    private By pageOrderBlock = By.className("Home_ThirdPart__LSTEE");
    private By pageOrderButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[contains(text(), 'Заказать')]");

    // Форма заказа
    private By orderForm = By.className("Order_Content__bmtHS");

    //1 шаг заказа
    //Поле Имя
    private By orderFormFirstName = By.xpath(".//input[@placeholder='* Имя']");

    //Поле Фамилия
    private By orderFormSecondName = By.xpath(".//input[@placeholder='* Фамилия']");

    //Поле Адрес
    private By orderFormAddress = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");

    //Поле Метро
    private By orderFormMetro = By.xpath(".//input[@placeholder='* Станция метро']");
    private By orderFormMetroStationList = By.className("select-search__select");

    //Поле Телефон
    private By orderFormPhone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    //Кнопка Далее
    private By orderNextButton = By.xpath(".//div[@class='Order_NextButton__1_rCA']/button");


    // 2 шаг формы заказа
    private By orderSeconPage = By.className("Order_Form__17u6u");

    //Поле "Когда привезти самокат"
    private By orderStartDay = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private By orderStartDayDataPicker = By.className("react-datepicker");
    private By orderStartDayDataPickerChoosedDay = By.xpath(".//div[contains(@class, 'react-datepicker__day--selected')]");

    //Поле "Cрок аренды"
    private By orderPeriod = By.xpath(".//div[text()='* Срок аренды']");


    //Поле "Цвет"
    private By orderColorBlack = By.id("black");


    //Поле "Комментарий"
    private By orderComment= By.xpath(".//input[@placeholder='Комментарий для курьера']");

    //Кнопка заказать
    private By orderSubmitButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    //Модалка подтверждения заказа
    private By orderConfirmationModal = By.className("Order_Modal__YZ-d3");
    private By orderConfirmationModalButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and contains(text(), 'Да')]");


    // Модалка успешного создания заказа
    private By orderAcceptedModal = By.className("Order_Modal__YZ-d3");
    private By orderAcceptedModalTitle = By.xpath("//*[contains(text(), 'Заказ оформлен')]");
    private By orderStatusButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and contains(text(), 'Посмотреть статус')]");


    //Конструктор класса
    public OrderPage(WebDriver driver){
        this.driver = driver;
    }


    //Метод перехода по урлу
    public void openURL() {
        driver.get(PAGE_URL);
    }

    // Метод проверки отображения кнопки в хедере
    public boolean isHeaderOrderButtonDisplayed(){
        WebElement orderButtonInHeader = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(headerOrderButton));
        return orderButtonInHeader.isDisplayed();
    }

    // Метод проверки отображения кнопки в теле страницы
    public boolean isPageOrderButtonDisplayed(){
        WebElement orderBlockInPage = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(pageOrderBlock));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", orderBlockInPage);
        return orderBlockInPage.isDisplayed();

    }

    //Переход к форме заказа из хэдера
    public void openOrderFormFromHeader() {
        driver.findElement(headerOrderButton).click();
    }

    //Переход к форме заказа из тела страницы
    public void openOrderFormFromBody() {
        driver.findElement(pageOrderButton).click();
    }

    //Метод захода на оформление заказа через хэдер
    public void enterOrderFromHeader() {
        isHeaderOrderButtonDisplayed();
        openOrderFormFromHeader();
    }

    //Метод захода на оформление заказа через кнопку в теле страницы
    public void enterOrderFromBody() {
        isPageOrderButtonDisplayed();
        openOrderFormFromBody();
    }

    //Отображение формы заказа
    public boolean isOrderFormDisplayed(){
        WebElement openedOrderForm = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderForm));
        return openedOrderForm.isDisplayed();
    }

    // 1 шаг заказа
    // метод заполняет поле «Имя»
    public void setFname(String fname) {
        driver.findElement(orderFormFirstName).sendKeys(fname);
    }

    // метод заполняет поле «Фамилия»
    public void setSname(String sname) {
        driver.findElement(orderFormSecondName).sendKeys(sname);
    }

    // метод заполняет поле «Адрес»
    public void setAddress(String address) {
        driver.findElement(orderFormAddress).sendKeys(address);
    }

    // Метод рандомно выбирает станцию «Метро»
    public void setMetro() {
        driver.findElement(orderFormMetro).click();
        WebElement openedMetroStationList = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderFormMetroStationList));
       if (openedMetroStationList.isDisplayed()) {
           List<WebElement> items = driver.findElements(By.className("select-search__row"));
           int countOfStation = items.size();
           Random r = new Random();
           int result = r.nextInt(countOfStation - 1);
           ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(".//li[@data-value='" + result + "']")));
           driver.findElement(By.xpath(".//li[@data-value='" + result + "']")).click();
       }

    }

    // метод заполняет поле «Телефон»
    public void setPhone(String phone) {
        driver.findElement(orderFormPhone).sendKeys(phone);
    }

    // метод кликает по кнопке «Далее»
    public void clickNextButton() {
        driver.findElement(orderNextButton).click();
    }


    // метод заполнения 1 шага формы заказа
    public void setFirstStepOrder(String fname, String sname, String address, String phone){
        isOrderFormDisplayed();
        setFname(fname);
        setSname(sname);
        setAddress(address);
        setMetro();
        setPhone(phone);
        clickNextButton();
    }

    // 2 шаг заказа
    //Отображение 2 шага формы заказа
    public boolean isOrderFormSecondPageDisplayed(){
        WebElement openedOrderFormSecondPage = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderSeconPage));
        return openedOrderFormSecondPage.isDisplayed();
    }
    // метод заполняет поле «Когда привезти самокат»
    public void setDate(String date) {
        driver.findElement(orderStartDay).sendKeys(date);
        WebElement openedDatapicker = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderStartDayDataPicker));
        if (openedDatapicker.isDisplayed()) {
            driver.findElement(orderStartDayDataPickerChoosedDay).click();
        }
    }

    // Метод рандомно выбирает «Срок аренды»
    public void setPeriod() {
        driver.findElement(orderPeriod).click();
        if(driver.findElement(By.className("Dropdown-menu")).isDisplayed()) {
            List<WebElement> dayList = driver.findElements(By.className("Dropdown-option"));
            int countOfDays = dayList.size();
            Random rnd = new Random();
            int res = rnd.nextInt(countOfDays - 1);
            driver.findElement(By.xpath(".//div[@class='Dropdown-option'][" + res + "]")).click();
        }
    }

    // метод выбирает «Цвет самоката»
    public void setColorBlack() {
        driver.findElement(orderColorBlack).click();
    }


    // метод заполняет поле «Комментарий для курьера»
    public void setComment(String comment) {
        driver.findElement(orderComment).sendKeys(comment);
    }

    // метод кликает по кнопке «Заказать»
    public void orderSecondStepSubmit() {
        driver.findElement(orderSubmitButton).click();
    }

    // метод заполнения 2 шага формы заказа
    public void setSecondStepOrder(String date, String comment) {
        isOrderFormSecondPageDisplayed();
        setDate(date);
        setPeriod();
        setColorBlack();
        setComment(comment);
        orderSecondStepSubmit();
    }

    // Модалка подтверждения заказа
    //Отображение модалки подтверждения заказа
    public boolean isOrderConfirmationFormDisplayed(){
        WebElement openedOrderConfirmationForm = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationModal));
        return openedOrderConfirmationForm.isDisplayed();
    }

    //Нажатие кнопки "Да"
    public void setOrderConfirmationModalButton() {
        driver.findElement(orderConfirmationModalButton).click();
    }

    //Метод прохождения модалки подтверждения заказа
    public void confirmationOrder() {
        isOrderConfirmationFormDisplayed();
        setOrderConfirmationModalButton();
    }

    // Модалка успешного создания заказа
    //Отображение модалки подтверждения заказа
    public boolean isOrderAcceptedModalDisplayed(){
        WebElement openedOrderAcceptedModal = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderAcceptedModal));
        return openedOrderAcceptedModal.isDisplayed();
    }

    //Отображение заголовка модалки "Заказ оформлен"
    public boolean isOrderAcceptedModalTitleDisplayed(){
        WebElement openedOrderAcceptedModalTitle = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(orderAcceptedModalTitle));
        return openedOrderAcceptedModalTitle.isDisplayed();
    }

    //Клик по кнопке "Посмотреть статус"
    public void orderStatusButtonClick(){
        driver.findElement(orderStatusButton).click();
    }

    //Метод проверки отображения модалки успешно созданного заказа
    public void acceptedOrder() {
        isOrderAcceptedModalDisplayed();
        isOrderAcceptedModalTitleDisplayed();
        orderStatusButtonClick();
    }

    //Метод проверки открытия страницы статуса заказа
    public void isOrderStatusPageDisplayed() {
        String currentUrl = driver.getCurrentUrl();
        boolean correctUrl = false;
        if (currentUrl.contains("/track?t=") ) {
            correctUrl = true;
        }
        Assert.assertTrue("Incorrect Page URL", correctUrl);
    }

}
