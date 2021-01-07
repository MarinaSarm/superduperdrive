package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "login-form")
    private WebElement loginForm;

    private final JavascriptExecutor js;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.js = (JavascriptExecutor) webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
        this.driver = webDriver;
    }

    public void login(String username, String password) {
        js.executeScript("arguments[0].value='"+ username +"';", this.usernameField);
        js.executeScript("arguments[0].value='"+ password +"';", this.passwordField);
        js.executeScript("arguments[0].click();", this.submitButton);
    }

    public String getMessage() {
        return this.loginForm.findElement(By.className("alert")).getText();
    }
}
