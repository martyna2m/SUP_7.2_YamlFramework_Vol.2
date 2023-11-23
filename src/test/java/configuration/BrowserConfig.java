package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BrowserConfig {

    private static Browser activeBrowser;
    private WebDriver driver;
    private int browserImplicitTimeOut = 10;
    static YamlReader yamlReader = new YamlReader();
    static Map<String, Object> properties = yamlReader.readYamlFile();
    static Logger log = LoggerFactory.getLogger(testbase.TestBase.class);


    public BrowserConfig() {
        setActiveBrowser(properties);
        initBrowserSettings();
    }




    public static Browser setActiveBrowser(Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<String, Object> browsers = (Map<String, Object>) data.get("browser");
        activeBrowser = null;

        for (Map.Entry<String, Object> entry : browsers.entrySet()) {
            Map<String, Object> browserProperties = (Map<String, Object>) entry.getValue();
            Boolean isActive = (Boolean) browserProperties.get("active");
            if (isActive != null && isActive) {
                activeBrowser = mapper.convertValue(browserProperties, Browser.class);
                System.out.println("Active Browser: " + activeBrowser.getBrowserName());
                log.debug("Active Browser: " + activeBrowser.getBrowserName());
                break;
            }
        }
        return activeBrowser;
    }

    public WebDriver getDriver() {

        if (activeBrowser == null || activeBrowser.getBrowserName() == null) {
            System.out.println("Active browser information is missing. Switching to default.");

            ChromeOptions chromeOptions = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            chromeOptions.addArguments("start-maximized");
            driver = new ChromeDriver(chromeOptions);
            driver.get(System.getProperty("appUrl"));

        } else {

            switch (activeBrowser.getBrowserName()) {
                case "CHROME":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    driver.get(System.getProperty("appUrl"));
                    break;

                case "EDGE":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    WebDriverManager.edgedriver().setup();
                    edgeOptions.addArguments("start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    driver.get(System.getProperty("appUrl"));
                    break;

                case "FIREFOX":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    WebDriverManager.firefoxdriver().setup();
                    firefoxOptions.addArguments("start-maximized");
                    driver = new FirefoxDriver(firefoxOptions);
                    driver.get(System.getProperty("appUrl"));
            }
        }
        return this.driver;
    }


    private void initBrowserSettings() {
        this.browserImplicitTimeOut = System.getProperty("browserImplicitTimeOut") != null ? Integer.parseInt(System.getProperty("browserImplicitTimeOut")) : this.browserImplicitTimeOut;
    }

}
