package itmo.se.lab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.WebDriverWait

class HomePage(private val driver: WebDriver) {
    @FindBy(xpath = "//div[@class=\"header\"]//a[.=\"Вход\"]")
    lateinit var openLoginPopupButton: WebElement

    @FindBy(xpath = "//div[@class=\"modal login-modal\"]")
    lateinit var loginPopup: WebElement

    @FindBy(xpath = "//div[@class=\"modal login-modal\"]//input[@id=\"login-form-login\"]")
    lateinit var loginPopupEmailField: WebElement

    @FindBy(xpath = "//div[@class=\"modal login-modal\"]//input[@id=\"login-form-pwd\"]")
    lateinit var loginPopupPasswordField: WebElement

    @FindBy(xpath = "//div[@class=\"modal login-modal\"]//a[@id=\"login_ok\"]")
    lateinit var loginPopupSubmitButton: WebElement

    @FindBy(xpath = "//a[@title=\"профиль пользователя\"]/following::a[1]")
    lateinit var userProfileLink: WebElement

    init {
        driver["https://advego.com/"]
        PageFactory.initElements(driver, this)
    }

    val invalidCredentialsError: WebElement
        get() {
            val locator = By.xpath("//*[text()=\"Неверный e-mail или пароль\"]")
            WebDriverWait(driver, 180).until(presenceOfElementLocated(locator))
            return driver.findElement(locator)
        }

    fun logIn(email: String, password: String) {
        openLoginPopupButton.click()
        loginPopupEmailField.sendKeys(email)
        loginPopupPasswordField.sendKeys(password)
        loginPopupSubmitButton.click()
        WebDriverWait(driver, 180).until(visibilityOf(userProfileLink))
    }
}