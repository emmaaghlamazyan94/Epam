import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class GreetzTest {
    private WebDriver driver;
    String email = "register@gmail.com";
    String password = "asdfghjkl";

    @BeforeMethod
    public void login() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.greetz.nl/auth/login");
        Thread.sleep(5000);
        WebElement loginFormElem = driver.findElement(By.id("loginForm"));
        loginFormElem.findElement(By.name("email")).sendKeys(email);
        loginFormElem.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("login-cta")).click();
        Thread.sleep(5000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = -1)
    public void loggedInTest() {
        Assert.assertTrue(isLoggedIn("Harry"), "Failed to log in user " + email);
    }

    @Test(priority = 0)
    public void addFavoriteGiftTest() throws InterruptedException {
        driver.get("https://www.greetz.nl/ballonnen/denken-aan");
        Thread.sleep(3000);
        String nameOfElement = driver.findElement
                (By.xpath("//div[@class='b-products-grid__item-title']")).getText();
        String priceOfElement = driver.findElement(By.className("price-normal")).getText();
        WebElement giftElem = driver.findElement(By.xpath("//a[@class='b-products-grid__item-action']"));
        giftElem.click();
        Thread.sleep(3000);
        WebElement sideBarElem = driver.findElement(By.xpath("//i[@data-qa-ref='profile-icon']"));
        sideBarElem.click();
        Thread.sleep(3000);
        WebElement favorites = driver.findElement(By.xpath("//a[@class='b-list--item-link']//span[text()='Favorieten']"));
        favorites.click();
        Thread.sleep(3000);
        WebElement elemInFavorites = driver.findElement(By.xpath("//div[@class='gift-medium']"));
        elemInFavorites.click();
        Thread.sleep(3000);
        String actualName = driver.findElement
                (By.xpath("//h1[@class='label-large giftdetails--title productdetails--summary--title']")).getText();
        String actualPrice = driver.findElement(By.xpath("//span[@class='price-normal']")).getText();
        Thread.sleep(2000);
        Assert.assertEquals(actualName, nameOfElement);
        Assert.assertTrue(actualPrice.contains(priceOfElement));
        Thread.sleep(2000);
        WebElement deleteFavorite = driver.findElement(By.xpath("//div[@class ='productdetails-favorite favorite_active']"));
        deleteFavorite.click();
    }

    @Test(priority = 1)
    public void giftCardPriceEditGreetzTest() throws InterruptedException {
        driver.get("https://www.greetz.nl/kaarten/denken-aan");
        Thread.sleep(5000);
        WebElement cardElem = driver.findElement(By.xpath("//div[@class='b-card-preview__container']"));
        cardElem.click();
        Thread.sleep(3000);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("11");
        double price = Double.parseDouble(driver.findElement
                (By.xpath("//div[@class='page-detail__price']//span[@class='price-normal']"))
                .getText().substring(2).replace(",", "."));
        int count = 11;
        double priceMultiply = price * count;
        Thread.sleep(2000);
        double actualPrice = Double.parseDouble(driver.findElement
                (By.className("price-total")).getText().substring(9, 15).replace(",", "."));
        Assert.assertEquals(actualPrice, priceMultiply, 0.01);
        driver.quit();
    }

    public boolean isLoggedIn(String firstName) {
        try {
            driver.findElement(By.xpath("//div[@class='header-message']//span[text()='Welkom " + firstName + "']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}