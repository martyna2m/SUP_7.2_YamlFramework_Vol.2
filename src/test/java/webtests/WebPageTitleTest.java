package webtests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testbase.TestBase;

public class WebPageTitleTest extends TestBase {
    @Test
    @DisplayName("env test webTitle")
    @Tag("regression")
    void checkWebsiteTitle() {
        log.info(">>>>Start test>>>>");

        String actualTitle = driver.getTitle();
        String expectedTitle = System.getProperty("webTitle");
        String appUrl = System.getProperty("appUrl");

        log.info("appUrl{}", appUrl);
        log.info("Actual title: {}", actualTitle);
        log.info("Expected title: {}", expectedTitle);

        Assertions.assertThat(actualTitle).isEqualTo(expectedTitle);


    }
}
