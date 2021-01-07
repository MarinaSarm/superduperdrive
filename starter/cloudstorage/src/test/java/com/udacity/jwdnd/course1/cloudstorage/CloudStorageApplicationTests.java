package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
	String username = "marina";
	String password = "badpass";
	String firstname = "marina";
	String lastname = "me";

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 60);
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testHomeWithoutLogin() {
		driver.get(baseURL + "/home");

		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void testUserSignupLoginHomeLogoutHome() {
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.submitSignup(firstname, lastname, username, password);
		LoginPage loginPage = new LoginPage(driver);

		assertEquals("Login", driver.getTitle());
		assertEquals("You successfully signed up!", loginPage.getMessage());

		HomePage homePage = new HomePage(driver);
		loginPage.login(username, password);
		// wait.until(ExpectedConditions.titleContains("Home"));
		assertEquals("Home", driver.getTitle());

		homePage.logout();

		assertEquals("Login",  driver.getTitle());
		assertEquals("You have been logged out", loginPage.getMessage());

		driver.get(baseURL + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void testCreateAndDisplayNote() {
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		HomePage homePage = new HomePage(driver);

		assertEquals("Home", driver.getTitle());

		homePage.navNotesTabClick();
		homePage.createNote("note", "description");
		WebElement result = driver.findElement(By.id("result"));

		assertEquals("Result", driver.getTitle());
		assertEquals("Note was successfully added", result.findElement(By.className("message")).getText());

		ResultPage resultPage = new ResultPage(driver);
		resultPage.homeClick();

		assertEquals("Home", driver.getTitle());

		homePage.navNotesTabClick();
		WebElement notesTable = driver.findElement(By.id("noteTable"));

		assertEquals("note", driver.findElement(By.xpath("//table[@id='noteTable']/tbody/tr/th[1]")).getAttribute("innerHTML"));
		assertEquals("description", driver.findElement(By.xpath("//table[@id='noteTable']/tbody/tr/td[2]")).getAttribute("innerHTML"));
	}
}
