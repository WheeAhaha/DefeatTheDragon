import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
public class HowToKillDragon_Pages_Test {

    WebDriver horse;
    Logger logger;
    String weapon;
    int MAX_TIMEOUT = 15;
    String heroName = "Бенедікт Камбербетч";
    Tools tools;

    @BeforeTest
    public void prepare(){
        // Кличемо герольдів
        logger = LogManager.getLogger(HowToKillDragon_Pages_Test.class);
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
        //заходимо в лігво
        DragonLairPage lair = new DragonLairPage(horse);
        // Отже треба покликати дракона
        lair.callDragon(MAX_TIMEOUT);
        List<WebElement> availableDragons = lair.getAllDragons();
        if (availableDragons.size() == 0 ) // беремо всі елементи - і превіряемо що знайдено більше нуля
            logger.warn("Дракона не знайшли");
        else
            logger.info("Дракон тут");
        //вкажемо ім'я
        lair.setHeroName(heroName);
        //виберемо зброю
        lair.selectWeapon("Мечем");
        logger.info(lair.getSelectedWeapon());
        //вибераємо тип удару
        lair.selectPunchType("Буцнув");
        //перевіримо чи потрібний елемент був вибраний
        String punchTypes = lair.getSelectedPunchTypes();
        logger.info(punchTypes);
        //відмітимо чекбокс
        lair.setPunchTypeCheckbox();
        //вдаримо по дракону
        lair.clickOnPunchButton();
        //перевіримо як там Дракон
        logger.info(lair.getLastFightLogMesssage());
        //обробляємо алерти
        lair.checkForMessages(MAX_TIMEOUT);
        //перевіримо чи вмер
        assert lair.checkDragonIsDead(MAX_TIMEOUT);
    }
    @AfterTest
    public void celebrate(){
        logger.info("Пригоду завершено");
        horse.quit();
    }
}