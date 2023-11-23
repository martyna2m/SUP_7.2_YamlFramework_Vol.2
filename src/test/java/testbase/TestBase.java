package testbase;

import configuration.BrowserConfig;
import configuration.EnvironmentConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {
    protected static EnvironmentConfig appConfig;
    protected static BrowserConfig browserConfig;
    protected static WebDriver driver;
    public static Logger log = LoggerFactory.getLogger(testbase.TestBase.class);

    @BeforeAll
    static void setUpDriver() {
        appConfig = EnvironmentConfig.getInstance();
        browserConfig = new BrowserConfig();
        driver = browserConfig.getDriver();
        log.debug("Driver initialized");
    }


    @AfterAll
    static void tearDown() {
        driver.quit();
        log.debug("Driver closed");
    }

}


