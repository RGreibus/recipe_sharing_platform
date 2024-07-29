import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    public void testSuccessfulRegistrationWithAllValidCredentials() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("http://localhost:5173/"));

        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "User should be redirected to the homepage");
    }
//    @Test
//    public void testSuccessfulRegistrationMessage() {
//        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
//        driver.findElement(By.id("first-name")).sendKeys("Rasa");
//        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
//        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
//        driver.findElement(By.id("email")).sendKeys("rassa@gmail.com");
//        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
//        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
//        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
//        driver.findElement(By.id("other")).click();
//
//        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
//
//        Actions actions = new Actions(driver);
//        actions.moveToElement(submitButton).perform();
//
//        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));
//
//        driver.findElement(By.id("country")).sendKeys("Lithuania");
//        driver.findElement(By.id("privacy-policy")).click();
//
//        actions.moveToElement(submitButton).click().perform();
//
//        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("http://localhost:5173/"));
//
//        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "User should be redirected to the homepage");
//
//        WebElement successMessage = driver.findElement(By.cssSelector("#root > main > div > h2"));
//        assertEquals("You have registered successfully. You can now log in", successMessage.getText(), "Success message should match");
//    }
    @Test
    public void testRegistrationWithNumberInFirstName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithFirstNameEmptyField() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("This field is required", errorMessage.getText(), "Error message should match");
    }

    @Test
    public void testRegistrationWithTooShortFirstName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongFirstName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

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
        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithLastNameEmptyField() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("This field is required", errorMessage.getText(), "Error message should match");
    }

    @Test
    public void testRegistrationWithTooShortLastName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("You can only enter English letters. First letter must be capital. At least 2 characters long", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongLastName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("Maximum length is 100 characters", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTwoConsecutiveSpacesInDisplayName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithDisplayNameEmptyField() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("http://localhost:5173/"));

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

        actions.moveToElement(submitButton).perform();
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("Already exists", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithNonUniqueDisplayEmail() throws InterruptedException {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("http://localhost:5173/"));

        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasosLasasLasas"); // Different display name
        driver.findElement(By.id("email")).sendKeys("rasoslasas@gmail.com"); // Same email
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

        actions.moveToElement(submitButton).perform();
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("Already exists", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongDisplayName() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("Maximum length is 255 characters", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooShortEmail() {
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

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("Minimum length 5 characters", errorMessage.getText(), "Error message should match");
    }
    @Test
    public void testRegistrationWithTooLongEmail() {
        driver.findElement(By.cssSelector("#navbarSupportedContent > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.id("first-name")).sendKeys("Rasa");
        driver.findElement(By.id("last-name")).sendKeys("Rasiene");
        driver.findElement(By.id("displayName")).sendKeys("rasossLasas");
        driver.findElement(By.id("email")).sendKeys("averylongemailaddressconsistingofmanylettersandnumbers1234567890abcdefghijklmnopqrstuvwx@exampledomainwithaverylongnamethatexceedstheusualcharacterlimitforanemailaddress1234567890abcdefghijklmnopqrstuvwxy.com");
        driver.findElement(By.id("password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("repeat-password")).sendKeys("RasaRasiene123!");
        driver.findElement(By.id("dateOfBirth")).sendKeys("001980-09-25");
        driver.findElement(By.id("other")).click();

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));

        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).perform();

        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(submitButton));

        driver.findElement(By.id("country")).sendKeys("Lithuania");
        driver.findElement(By.id("privacy-policy")).click();

        actions.moveToElement(submitButton).click().perform();

        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-danger")));

        assertEquals("Maximum length 200 characters", errorMessage.getText(), "Error message should match");
    }
}
