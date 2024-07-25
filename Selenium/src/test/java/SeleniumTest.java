import org.junit.jupiter.api.AfterEach;
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
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
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
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

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
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

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
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long.", errorMessage.getText(), "Error message should match");
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
    driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
    driver.findElement(By.id("other")).click();

    WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
    sleep(2000);

    driver.findElement(By.id("country")).sendKeys("Lithuania");
    driver.findElement(By.id("privacy-policy")).click();

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
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long.", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongFirstName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Aldrenizorilamantheusgrodinorastelvimundilcaranorbrastianopereidronimalkastorvelianorximandrathionelbriorbelindrospharomandarithoromantilonarcrestiphonaldomitharielzonophralindionzelkathrotharianorilcanthiorbelinorothianelphrothramondorivelintharimundronisthal");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("Maximum length is 135 characters", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithNumberInLastName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene1");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasass");
        driver.findElement(By.id("email")).sendKeys("rasa0@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long.", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithLastNameEmptyField() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("This field is required", errorMessage.getText(), "Error message should match");
    }

    @Test
    public void testRegistrationWithTooShortLastName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("R");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long.", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongLastName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Eldranorithanovelimustorianthropelmindoravianthrasilomandorinelviorbresthalianorimandrilostarventhionpharostilmorandivoltrenphoraxisthalen");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("Maximum length is 100 characters", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTwoConsecutiveSpacesInDisplayName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasos  Lasass");
        driver.findElement(By.id("email")).sendKeys("rasa0@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithDisplayNameEmptyField() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("This field is required", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithNonUniqueDisplayName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasas");
        driver.findElement(By.id("email")).sendKeys("rasoslasas@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasas"); // Same display name
        driver.findElement(By.id("email")).sendKeys("rasabasa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("Already exists", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithNonUniqueDisplayEmail() throws InterruptedException {
        // Register the first user with the display name "rasosLasas"
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasas");
        driver.findElement(By.id("email")).sendKeys("rasoslasas@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasasLasas"); // Same display name
        driver.findElement(By.id("email")).sendKeys("rasoslasas@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);
        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("Already exists", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongDisplayName() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("Aelthorin3xvelion4drenivalthor7inorbrastelimundorin8valdristaphonilkaranth3noren1drelvoraximandelbristopharnomelinthorvalian7ophrandorilmurathilon6droximarbelanoreth3drivelanthormion5tarvionelphrasothian7elkaristovrimandelthoran4imunovilthrandionphar3tomal8dralinthoravionel3dramirbelinthorvalpharostilornian5del");
        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("Maximum length is 255 characters", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooShortEmail() throws InterruptedException {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("r@gm");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        sleep(2000);

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        sleep(5000);

        WebElement errorMessage = driver.findElement(By.cssSelector("div.text-danger"));
        assertEquals("May only contain English letters, all lowercase. Can contain numbers, and these symbols ._-", errorMessage.getText(), "Error message should match");
    }
}
