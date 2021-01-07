package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotes;

    @FindBy(id = "nav-files-tab")
    private WebElement navFiles;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentials;

    @FindBy(css = "#nav-notes button")
    private WebElement noteCreate;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "submit-note")
    private WebElement noteSubmit;

    private final JavascriptExecutor js;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.js = (JavascriptExecutor) webDriver;
        this.wait = new WebDriverWait(webDriver, 60);
        this.driver = webDriver;
    }

    public void logout() {
        js.executeScript("arguments[0].click();", this.logout);
    }

    public void navNotesTabClick() {
        wait.until(ExpectedConditions.elementToBeClickable(this.navNotes));
        js.executeScript("arguments[0].click();", this.navNotes);
    }

    public void createNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(this.noteCreate));
        js.executeScript("arguments[0].click();", this.noteCreate);
        js.executeScript("arguments[0].value='"+ title +"';", this.noteTitle);
        js.executeScript("arguments[0].value='"+ description +"';", this.noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(this.noteSubmit));
        js.executeScript("arguments[0].click();", this.noteSubmit);
    }
}
