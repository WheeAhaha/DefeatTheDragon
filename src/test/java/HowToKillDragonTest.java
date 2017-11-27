import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
public class HowToKillDragonTest {
    WebDriver horse;
    Logger logger;
    String weapon;
    int MAX_TIMEOUT = 15;
    String heroName = "Бенедікт Камбербетч";
    Tools tools;
    @BeforeTest
    public void prepare(){
        // Кличемо герольдів
        logger = LogManager.getLogger(HowToKillDragonTest.class);
        logger.info("Пригоди кличуть!!");
        // вдягаемось в дорогу
        weapon = Armory.getItemFromProperties("sword");
        logger.info("Герой взяв зброю: " + weapon);
        // створюємо "коника
        if (System.getProperty("horse").equals("ff"))
            horse = Browser.getFirefox();
        else
            horse = Browser.getChrome();
    }
    @Test
    public void journey(){
        logger.info("Пригода...");

        // створюємо "коника"
        horse.get("https://killthedragon.weebly.com");
        // Отже треба покликати дракона
        tools.waitForElement(horse, By.name("call_dragon"), MAX_TIMEOUT);
        horse.findElement(By.name("call_dragon")).click();
        List<WebElement> availableDragons = horse.findElements(By.id("dragon"));
        if (availableDragons.size() == 0 ) // беремо всі елементи - і превіряемо що знайдено більше нуля
            System.out.println("Дракона не знайшли");
        else
            System.out.println("Дракон тут");
        //вкажемо ім'я
        horse.findElement(By.id("name")).sendKeys(heroName);
        //виберемо зброю
        Select weaponSelect = new Select(horse.findElement(By.id("weapon")));
        weaponSelect.selectByValue("sword");
        weaponSelect.selectByIndex(1);
        weaponSelect.selectByVisibleText("Мечем");
        logger.info(weaponSelect.getFirstSelectedOption().getText());
        //вибераємо тип удару
        String radioValue = "Буцнув";
        List<WebElement> punchRadio = horse.findElements(By.name("punch")); // виберемо елементи радіо
        for(WebElement current: punchRadio)
            if (current.getAttribute("value").equals(radioValue))
                current.click(); // якщо елемент має потрібне value - клік
        //перевіримо чи потрібний елемент був вибраний
        for(WebElement current: punchRadio)
            logger.info("Елемент " + current.getAttribute("value") + " - вибраний: " + current.isSelected());
        //відмітимо чекбокс
        horse.findElement(By.xpath("//*[contains(text(),'Чи сильно')]/input")).click();
        //вдаримо по дракону
        horse.findElement(By.id("act")).click();
        //перевіримо як там Дракон
        logger.info(horse.findElement(By.xpath("//table[@id='log']//tr[1]")).getText());
        //перевіримо чи вмер дракон
        Alert alert=horse.switchTo().alert();  // чекаємо prompt
        alert.sendKeys(heroName);
        alert.accept();
        tools.waitForAlert(horse, MAX_TIMEOUT);
        alert=horse.switchTo().alert(); // чекаємо notice
        alert.accept();
        //перевіримо чи вмер дракон
        tools.waitForElement(horse, By.cssSelector("img#dragon"), MAX_TIMEOUT);
        horse.findElement(By.cssSelector("img#dragon")).getAttribute("src").contains("dead_dragon");
    }
    @AfterTest
    public void celebrate(){
        logger.info("Пригоду завершено");
        horse.quit();
    }
}