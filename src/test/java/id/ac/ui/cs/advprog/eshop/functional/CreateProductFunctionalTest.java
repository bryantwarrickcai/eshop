package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testIfProductIsInProductList(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/list");

        // Click the "Create Product" button
        WebElement createProductButton = driver.findElement(By.id("create-product-button"));
        createProductButton.click();
        Thread.sleep(2000);

        // Enter the data into form
        WebElement nameInputField = driver.findElement(By.id("nameInput"));
        nameInputField.sendKeys("Hello World");
        WebElement quantityInputField = driver.findElement(By.id("quantityInput"));
        quantityInputField.clear();
        quantityInputField.sendKeys("40");

        // Submit the form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        Thread.sleep(2000);
        
        WebElement table = driver.findElement(By.tagName("table"));
        WebElement secondRow = table.findElements(By.tagName("tr")).get(1);
        List<WebElement> columnsForSecondRow = secondRow.findElements(By.tagName("td"));
        WebElement firstColumn = columnsForSecondRow.get(0);
        WebElement secondColumn = columnsForSecondRow.get(1);

        assertEquals("Hello World", firstColumn.getText());
        assertEquals("40", secondColumn.getText());
    }
}
