import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BooksPage;
import pages.AuthorPage;
import pages.HomePage;

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
        Assert.assertEquals(actualDeliveryCountry, expectedDeliveryCountry);
    }

    @Test
    public void allBooksAreWrittenBySameAuthor() {
        HomePage homePage = new HomePage(driver);
        homePage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        Assert.assertTrue(booksPage.checkAllBooksBySameAuthor(authorFullName));
    }

    @Test
    public void confirmSearchedTextExistence() {
        findAndClickOnAuthorBooks();
        String textToSearch = ("Books by " + authorFullName).toLowerCase();
        String actualText = driver.findElement(By.id("formatSelectorHeader")).getText().toLowerCase();
        Assert.assertTrue(actualText.contains(textToSearch));
    }

    @Test
    public void sortAndCheckTest() {
        findAndClickOnAuthorBooks();
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.clickToSort();
        Assert.assertTrue(authorPage.sortByPrice());
    }

    public void findAndClickOnAuthorBooks() {
        HomePage searchPage = new HomePage(driver);
        searchPage.waitUntilPageLoads();
        searchPage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.clickOnSearchResult();
        authorPage.waitUntilPageLoads();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}