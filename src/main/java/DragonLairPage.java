import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class DragonLairPage {
    WebDriver driver;
    By dragonLocator =  By.id("dragon");
    By callDragonButton =  By.name("call_dragon");
    By heroNameInput = By.id("name");
    By weaponSelectLocator = By.id("weapon");
    By punchInput = By.name("punch");
    By punchCheckbox = By.xpath("//*[contains(text(),'Чи сильно')]/input");
    By actButton = By.id("act");
    By statusTableFirstRow = By.xpath("//table[@id='log']//tr[1]");
    By dragonImage = By.cssSelector("img#dragon");
    DragonLairPage(WebDriver driver){
        this.driver = driver;
    }
    public void callDragon(int MAX_TIMEOUT) {
        Tools.waitForElement(driver, callDragonButton, MAX_TIMEOUT);
        driver.findElement(callDragonButton).click();
    }
    public List<WebElement> getAllDragons() {
        return driver.findElements(dragonLocator);
    }
    public void setHeroName(String heroName) {
        driver.findElement(heroNameInput).sendKeys(heroName);
    }
    public void selectWeapon(String weaponType) {
        Select weaponSelect = new Select(driver.findElement(weaponSelectLocator));
        weaponSelect.selectByVisibleText(weaponType);
    }
    public String getSelectedWeapon() {
        Select weaponSelect = new Select(driver.findElement(weaponSelectLocator));
        return weaponSelect.getFirstSelectedOption().getText();
    }
    public void selectPunchType(String type) {
        List<WebElement> punchRadio = driver.findElements(punchInput); // виберемо елементи радіо
        for(WebElement current: punchRadio)
            if (current.getAttribute("value").equals(type))
                current.click(); // якщо елемент має потрібне value - клік
    }
    public String getSelectedPunchTypes() {
        List<WebElement> punchRadio = driver.findElements(punchInput);
        String punchTypes = "";
        for(WebElement current: punchRadio)
            punchTypes = punchTypes + "Елемент " + current.getAttribute("value") + " - вибраний: " + current.isSelected() + ";";
        return punchTypes;
    }
    public void setPunchTypeCheckbox() {
        driver.findElement(punchCheckbox).click();
    }
    public String getLastFightLogMesssage() {
        return driver.findElement(statusTableFirstRow).getText();
    }
    public void clickOnPunchButton() {
        driver.findElement(actButton).click();
    }
    public void checkForMessages(int MAX_TIMEOUT){
        Alert alert=driver.switchTo().alert();  // чекаемо prompt
        alert.accept();
        Tools.waitForAlert(driver, MAX_TIMEOUT);
        alert=driver.switchTo().alert(); // чекаемо notice
        alert.accept();
    }
    public boolean checkDragonIsDead(int MAX_TIMEOUT) {
        Tools.waitForElement(driver, By.cssSelector("img#dragon"), MAX_TIMEOUT);
        return driver.findElement(dragonImage).getAttribute("src").contains("dead_dragon");
    }
}