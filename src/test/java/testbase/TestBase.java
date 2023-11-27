package testbase;

import configuration.AppConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {
    private static AppConfig appConfig;
    protected static WebDriver driver;
    public static Logger log = LoggerFactory.getLogger(testbase.TestBase.class);

    @BeforeAll
    static void setUpDriver() {
        appConfig = AppConfig.getInstance();
        driver = appConfig.getDriver();
        log.debug("Driver initialized");
    }


    @AfterAll
    static void tearDown() {
        driver.quit();
        log.debug("Driver closed");
    }

}


