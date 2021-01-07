package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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
		wait = new WebDriverWait(driver, 5);
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testHomeWithoutLogin() {
		driver.get(baseURL + "/home");

		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginHomeLogoutHome() {
		String username = "marina";
		String password = "badpass";
		String firstname = "marina";
		String lastname = "me";

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.submitSignup(firstname, lastname, username, password);
		LoginPage loginPage = new LoginPage(driver);

		assertEquals("Login", driver.getTitle());
		assertEquals("You successfully signed up!", loginPage.getMessage());

		HomePage homePage = new HomePage(driver);
		loginPage.login(username, password);
		wait.until(ExpectedConditions.titleContains("Home"));
		assertEquals("Home", driver.getTitle());

		homePage.logout();

		assertEquals("Login",  driver.getTitle());
		assertEquals("You have been logged out", loginPage.getMessage());

		driver.get(baseURL + "/home");
		assertEquals("Login", driver.getTitle());
	}
}
