package configuration;

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

public class AppConfig {
    private WebDriver driver;
    private static YamlReader yamlReader = new YamlReader();
    private static Logger log = LoggerFactory.getLogger(testbase.TestBase.class);
    private static Map<String, Object> properties = yamlReader.readYamlFile("src/test/resources/config.yaml");


    public static AppConfig getInstance() {
        initAppConfig();
        return AppConfig.AppConfigSingleton.INSTANCE;
    }


    public static void initAppConfig() {
        setActiveEnvironment(properties);
        setActiveBrowser(properties);
    }


    public static void setActiveEnvironment(Map<String, Object> data) {

        Map<String, Object> environments = (Map<String, Object>) data.get("environment");

        for (Map.Entry<String, Object> entry : environments.entrySet()) {
            Map<String, Object> envProperties = (Map<String, Object>) entry.getValue();
            Boolean isActive = (Boolean) envProperties.get("active");
            if (isActive != null && isActive) {
                envProperties.forEach((key, value) -> {
                    if (!key.equals("active")) {
                        System.setProperty(key, value.toString());
                    }
                });

                System.out.println("Active Environment: " + System.getProperty("envName"));
                log.debug("Active Environment: " + System.getProperty("envName"));
                break;
            }
        }

    }

    public static void setActiveBrowser(Map<String, Object> data) {
        String browserValue = (String) data.get("browser");
        System.setProperty("browser", browserValue);
        System.out.println("Active Browser: " + System.getProperty("browser"));
        log.debug("Active Browser: " + System.getProperty("browser"));

    }

    public WebDriver getDriver() {

        if (System.getProperty("browser") == null) {
            System.out.println("Active browser information is missing. Switching to default.");

            ChromeOptions chromeOptions = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            chromeOptions.addArguments("start-maximized");
            driver = new ChromeDriver(chromeOptions);
            driver.get(System.getProperty("appUrl"));

        } else {

            switch (System.getProperty("browser").toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    driver.get(System.getProperty("appUrl"));
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    WebDriverManager.edgedriver().setup();
                    edgeOptions.addArguments("start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    driver.get(System.getProperty("appUrl"));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    WebDriverManager.firefoxdriver().setup();
                    firefoxOptions.addArguments("start-maximized");
                    driver = new FirefoxDriver(firefoxOptions);
                    driver.get(System.getProperty("appUrl"));
            }
        }
        return this.driver;
    }

    private static class AppConfigSingleton {
        private static final AppConfig INSTANCE = new AppConfig();

    }


}



