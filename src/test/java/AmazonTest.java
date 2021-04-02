import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BooksPage;
import pages.AuthorPage;
import pages.HomePage;
import java.util.List;

public class AmazonTest {
    private WebDriver driver;
    private String authorFullName = "george r. r. martin";

    @BeforeMethod
    public void testSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
    }

    @Test
    public void deliveryCountryTest() {
        HomePage homePage = new HomePage(driver);
        homePage.waitUntilPageLoads();
        String expectedDeliveryCountry = "deliver to armenia";
        String actualDeliveryCountry = driver.findElement(By.id("nav-global-location-slot")).
                getText().toLowerCase().replaceAll("\\s+", " ");
        Assert.assertEquals(actualDeliveryCountry, expectedDeliveryCountry,
                "The searched text and actual text are not the same");
    }

    @Test
    public void allBooksAreWrittenBySameAuthor() {
        HomePage homePage = new HomePage(driver);
        homePage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        Assert.assertTrue(checkAllBooksBySameAuthor(authorFullName),
                "Not all books are written by the searched author " + authorFullName);
    }

    @Test
    public void confirmSearchedTextExistence() {
        findAndClickOnAuthorBooks();
        String textToSearch = ("Books by " + authorFullName).toLowerCase();
        String actualText = driver.findElement(By.id("formatSelectorHeader")).getText().toLowerCase();
        Assert.assertTrue(actualText.contains(textToSearch),
                "No results found for " + textToSearch);
    }

    @Test
    public void sortAndCheckTest() {
        findAndClickOnAuthorBooks();
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.clickToSort();
        Assert.assertTrue(authorPage.sortByPrice(),
                "Prices are not sorted from low to high");
    }

    public boolean checkAllBooksBySameAuthor(String authorFullName) {
        List<WebElement> authors = driver.findElements
                (By.xpath("//div[@class='a-section a-spacing-none']//div[@class='a-row a-size-base a-color-secondary']"));
        for (WebElement author : authors) {
            if (!author.getText().toLowerCase().contains(authorFullName)) {
                return false;
            }
        }
        return true;
    }

    public void findAndClickOnAuthorBooks() {
        HomePage searchPage = new HomePage(driver);
        searchPage.waitUntilPageLoads();
        searchPage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        booksPage.clickOnSearchResult();
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.waitUntilPageLoads();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}