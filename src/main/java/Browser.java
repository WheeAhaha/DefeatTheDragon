import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class Browser {
    private static String FIREFOX_PATH = "src"+ File.separator+"main"+File.separator+"resources"+File.separator+"geckodriver_v18"+File.separator+"geckodriver.exe";  // шлях до гекодрайвера
    private static String CHROME_PATH = "src"+ File.separator+"main"+File.separator+"resources"+File.separator+"chromedriver_v2.33"+File.separator+"chromedriver.exe"; // шлях до громдрайвера

    static public FirefoxDriver getFirefox() {  // static функція дозволяє викликати код без створення об'екту
        System.setProperty("webdriver.gecko.driver", FIREFOX_PATH); //встановлюемо проперті - Selenium буде знати де знаходяться драйвра
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        return new FirefoxDriver(); // створюемо новий об'єкт для роботи з драйвером
    }
    static public ChromeDriver getChrome(){
        System.setProperty("webdriver.chrome.driver", CHROME_PATH);
        return new ChromeDriver();
    }
}
