import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://testpages.herokuapp.com/styled/calculator");
    }
    @Test
    public void testCalculation(){
        driver.findElement(By.id("number1")).sendKeys("3");
        driver.findElement(By.cssSelector("#function > option:nth-child(3)")).click();
        driver.findElement(By.id("number2")).sendKeys("2");
        driver.findElement(By.id("calculate")).click();

        assertEquals("1" ,driver.findElement(By.id("answer")).getText(), "Answer should be the same " );
    }
}