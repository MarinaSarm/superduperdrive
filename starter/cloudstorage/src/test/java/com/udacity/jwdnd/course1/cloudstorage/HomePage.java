package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.*;
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

    @FindBy(css = "#nav-credentials button")
    private WebElement credCreate;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "submit-note")
    private WebElement noteSubmit;

    @FindBy(id = "submit-credential")
    private WebElement submitCredential;

    @FindBy(css = "#noteTable button")
    private WebElement editNote;

    @FindBy(css = "#credentialTable button")
    private WebElement editCred;

    @FindBy(css = "#noteTable a")
    private WebElement deleteNote;

    @FindBy(css = "#credentialTable a")
    private WebElement deleteCred;

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

    public void credTabClick() {
        wait.until(ExpectedConditions.elementToBeClickable(this.navCredentials));
        js.executeScript("arguments[0].click();", this.navCredentials);
    }

    public void createNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(this.noteCreate));
        js.executeScript("arguments[0].click();", this.noteCreate);
        js.executeScript("arguments[0].value='"+ title +"';", this.noteTitle);
        js.executeScript("arguments[0].value='"+ description +"';", this.noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(this.noteSubmit));
        js.executeScript("arguments[0].click();", this.noteSubmit);
    }

    public void createCred(String url, String login, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(this.credCreate));
        js.executeScript("arguments[0].click();", this.credCreate);
        js.executeScript("arguments[0].value='"+ url +"';", this.credentialUrl);
        js.executeScript("arguments[0].value='"+ login +"';", this.credentialUsername);
        js.executeScript("arguments[0].value='"+ password +"';", this.credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(this.submitCredential));
        js.executeScript("arguments[0].click();", this.submitCredential);
    }

    public void updateNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(this.editNote));
        js.executeScript("arguments[0].click();", this.editNote);
        js.executeScript("arguments[0].value='"+ title +"';", this.noteTitle);
        js.executeScript("arguments[0].value='"+ description +"';", this.noteDescription);
        wait.until(ExpectedConditions.elementToBeClickable(this.noteSubmit));
        js.executeScript("arguments[0].click();", this.noteSubmit);
    }

    public void updateNote(String url, String login, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(this.editCred));
        js.executeScript("arguments[0].click();", this.editCred);
        js.executeScript("arguments[0].value='"+ url +"';", this.credentialUrl);
        js.executeScript("arguments[0].value='"+ login +"';", this.credentialUsername);
        js.executeScript("arguments[0].value='"+ password +"';", this.credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(this.submitCredential));
        js.executeScript("arguments[0].click();", this.submitCredential);
    }

    public void deleteNote() {
        wait.until(ExpectedConditions.elementToBeClickable(this.deleteNote));
        js.executeScript("arguments[0].click();", this.deleteNote);
    }

    public void deleteCredential() {
        wait.until(ExpectedConditions.elementToBeClickable(this.deleteCred));
        js.executeScript("arguments[0].click();", this.deleteCred);
    }

    public boolean doesNoteExist() {
        try {
            driver.findElement(By.xpath("//table[@id='noteTable']/tbody/tr/td[2]"));
            driver.findElement(By.xpath("//table[@id='noteTable']/tbody/tr/th[1]"));
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }

    public boolean doesCredExist() {
        try {
            driver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr/td[2]"));
            driver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr/th[1]"));
            driver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr/td[3]"));
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }
}
