import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {
    WebDriver driver;

    @BeforeEach
    void cleanUpDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/recipe-sharing-platform/backend/rsp", "sa", "");
        String deleteQuery = "DELETE FROM users_roles; DELETE FROM users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:5173");
    }

    @Test
    public void testUserIsRedirectedFromHomePageToRegistrationFormAfterClickRegister() {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "URL should be the same");

    }

    @Test
    public void testSuccessfulRegistrationWithAllValidCredentials() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("1980-09-25");
        driver.findElement(By.id("other")).click();
        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "User should be redirected to the homepage");
    }
    @Test
    public void testSuccessfulRegistrationMessage() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("1980-09-25");
        driver.findElement(By.id("other")).click();
        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "User should be redirected to the homepage");

        WebElement successMessage = driver.findElement(By.cssSelector("#root > main > div > h2"));
        assertEquals("You have registered successfully. You can now log in", successMessage.getText(), "Success message should match");

    }
    @Test
    public void testRegistrationWithNumberInFirstName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa1");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasass");
        driver.findElement(By.id("email")).sendKeys("rasa0@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("1980-09-25");
        driver.findElement(By.id("other")).click();
        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("You can only enter letters. First letter must be capital. At least 2 characters long", errorMessage.getText(), "Error message should match");
    }
@Test
public void testRegistrationWithFirstNameEmptyField() throws InterruptedException {
    driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
    driver.findElement(By.id("first-name")).sendKeys("");
    driver.findElement(By.id("last-name")).sendKeys("Rasiene");
    driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
    driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
    driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
    driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
    driver.findElement(By.id("dateOfBirth")).sendKeys("1980-09-25");
    driver.findElement(By.id("other")).click();
    driver.findElement(By.id("country")).sendKeys("Lithuania");
    driver.findElement(By.id("privacy-policy")).click();

    WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
    sleep(2000);

    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

    sleep(5000);

    WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
    assertEquals("This field is required", errorMessage.getText(), "Error message should match");

    }
    @Test
    public void testRegistrationWithTooShortFirstName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("R");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("1980-09-25");
        driver.findElement(By.id("other")).click();
        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("Minimum symbols: 2", errorMessage.getText(), "Error message should match");

    }
}
