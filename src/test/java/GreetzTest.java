import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class GreetzTest {
    private WebDriver driver;

    @BeforeMethod
    public void login() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.greetz.nl/auth/login");
        Thread.sleep(5000);
        WebElement loginFormElem = driver.findElement(By.id("loginForm"));
        String email = "register@gmail.com";
        String password = "asdfghjkl";
        loginFormElem.findElement(By.name("email")).sendKeys(email);
        loginFormElem.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("login-cta")).click();
        Thread.sleep(5000);
        Assert.assertTrue(isLoggedIn("Harry"), "Failed to log in user " + email);
    }

    @Test
    public void addFavoriteGift() throws InterruptedException {
        driver.get("https://www.greetz.nl/ballonnen/denken-aan");
        Thread.sleep(3000);
        WebElement giftElem = driver.findElement(By.xpath("//a[@class='b-products-grid__item-action']"));
        giftElem.click();
        Thread.sleep(3000);
        WebElement sideBarElem = driver.findElement
                (By.xpath("//i[@class='page-header__navigation-item-icon b-icon b-icon-hamburger']"));
        sideBarElem.click();
        Thread.sleep(3000);
        WebElement favorites = driver.findElement(By.xpath("//a[@class='b-list--item-link']//span[text()='Favorieten']"));
        favorites.click();
        Thread.sleep(3000);
        WebElement elemInFavorites = driver.findElement(By.xpath("//div[@class='gift-medium']"));
        elemInFavorites.click();
        Thread.sleep(3000);
        Assert.assertTrue(elemAdded(), "No element in favorites");
        Thread.sleep(2000);
        WebElement deleteFavorite = driver.findElement(By.xpath("//div[@class ='productdetails-favorite favorite_active']"));
        deleteFavorite.click();
        driver.quit();
    }

    @Test
    public void giftCardPriceEditGreetzTest() throws InterruptedException {
        driver.get("https://www.greetz.nl/kaarten/denken-aan");
        Thread.sleep(5000);
        WebElement cardElem = driver.findElement(By.xpath("//div[@class='b-card-preview__container']"));
        cardElem.click();
        Thread.sleep(3000);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.click();
        amount.clear();
        amount.sendKeys("4");
        Double price = Double.parseDouble(driver.findElement
                (By.xpath("//div[@class='page-detail__price']//span[@class='price-normal']"))
                .getText().substring(2).replace(",", "."));
        Double priceMultiply = 4 * price;
        Thread.sleep(2000);
        Double actualPrice = Double.parseDouble(driver.findElement
                (By.className("price-total")).getText().substring(9, 15).replace(",", "."));
        Assert.assertEquals(actualPrice, priceMultiply);
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

    public boolean elemAdded() {
        boolean ifAdded;
        try {
            driver.findElement(By.xpath("//h1[@class='label-large giftdetails--title productdetails--summary--title']"));
            driver.findElement(By.xpath("//span[@class='price-normal']"));
            ifAdded = true;
            return ifAdded;
        } catch (NoSuchElementException e) {
            ifAdded = false;
            return ifAdded;
        }
    }
}