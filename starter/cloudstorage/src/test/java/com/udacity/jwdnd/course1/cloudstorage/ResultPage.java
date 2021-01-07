package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "result")
    private WebElement result;

    @FindBy(css = "#result message")
    private WebElement message;

    @FindBy(css = "#result .home-link")
    private WebElement homeLink;

    private final JavascriptExecutor js;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.js = (JavascriptExecutor) webDriver;
        this.wait = new WebDriverWait(webDriver, 5);
        this.driver = webDriver;
    }

    public void homeClick() {
        js.executeScript("arguments[0].click();", homeLink);
    }
}
