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
        String actualDeliveryCountryText = homePage.getActualText();
        Assert.assertEquals(actualDeliveryCountryText, expectedDeliveryCountry,
                "The searched text and actual text are not the same");
    }

    @Test
    public void allBooksAreWrittenBySameAuthorTest() {
        HomePage homePage = new HomePage(driver);
        homePage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        Assert.assertTrue(checkAllBooksBySameAuthor(authorFullName),
                "Not all books are written by the searched author " + authorFullName);
    }

    @Test
    public void confirmSearchedTextExistenceTest() {
        findAndClickOnAuthorBooks();
        String textToSearch = ("titles by " + authorFullName);
        BooksPage booksPage = new BooksPage(driver);
        String actualText = booksPage.getActualText();
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
        AuthorPage authorPage = new AuthorPage(driver);
        for (WebElement author : authorPage.authorsList()) {
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