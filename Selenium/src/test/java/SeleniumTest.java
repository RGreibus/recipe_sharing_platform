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
}
