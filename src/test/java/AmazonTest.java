import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BooksPage;
import pages.AuthorPage;
import pages.HomePage;
import util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmazonTest {
    private WebDriver driver;

    @DataProvider(name = "excelData")
    public Object[][] readExcel() throws IOException {
        return ExcelReader.readExcel(Constant.FILE_PATH, Constant.SHEET_NAME);
    }

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

    @Test(dataProvider = "excelData")
    public void allBooksAreWrittenBySameAuthorTest(String authorFullName) {
        HomePage homePage = new HomePage(driver);
        homePage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        Assert.assertTrue(checkAllBooksBySameAuthor(authorFullName, booksPage.authorsList()),
                "Not all books are written by the searched author " + authorFullName);
    }

    @Test(dataProvider = "excelData")
    public void confirmSearchedTextExistenceTest(String authorFullName) {
        findAndClickOnAuthorBooks(authorFullName);
        String textToSearch = ("titles by " + authorFullName);
        BooksPage booksPage = new BooksPage(driver);
        String actualText = booksPage.getActualText();
        Assert.assertTrue(actualText.contains(textToSearch),
                "No results found for " + textToSearch);
    }

    @Test(dataProvider = "excelData")
    public void sortAndCheckTest(String authorFullName) {
        findAndClickOnAuthorBooks(authorFullName);
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.clickToSort();
        Assert.assertTrue(isListSorted(authorPage.sortedByPrice()),
                "Prices are not sorted from low to high");
    }

    public void findAndClickOnAuthorBooks(String authorFullName) {
        HomePage searchPage = new HomePage(driver);
        searchPage.waitUntilPageLoads();
        searchPage.searchAuthorName(authorFullName, "Books");
        BooksPage booksPage = new BooksPage(driver);
        booksPage.waitUntilPageLoads();
        booksPage.clickOnSearchResult();
        AuthorPage authorPage = new AuthorPage(driver);
        authorPage.waitUntilPageLoads();
    }

    public boolean isListSorted(ArrayList<Float> data) {
        for (int i = 0; i < data.size() - 1; i++) {
            if (data.get(i) > data.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAllBooksBySameAuthor(String authorFullName, List<String> authorsList) {
        for (String author : authorsList) {
            if (!author.contains(authorFullName)) {
                return false;
            }
        }
        return true;
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}