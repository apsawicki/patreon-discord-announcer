package PDA.selenium;

import ch.qos.logback.classic.Logger;
import net.dv8tion.jda.api.entities.Guild;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Selenium web scraping implementation.
 * <p>
 * Responsibilities:
 * <p>
 * 1) Web scrape a single patreon link to add the posts to the database without being announced to discord
 */

@Component
@Scope("prototype")
public class PatreonSingleLink {

    @Autowired
    PatreonScraper patreonScraper;

    private By postCardSelector;
    private Logger log;

    public void PatreonSingleLink() {
        this.postCardSelector = By.cssSelector("[data-tag='post-card']");
        this.log = (Logger) LoggerFactory.getLogger(this.getClass().getSimpleName());
    }

    public void readPosts(Guild guild, String url) {

        log.info("Going to patreon page");
        patreonScraper.goToPatreonPage(url, postCardSelector);

        if (patreonScraper.visibleElementFound(postCardSelector)) {
            patreonScraper.sleep(4000);
        }

        log.info("Scanning all post cards");
        List<WebElement> foundPostElements = patreonScraper.driver.findElements(postCardSelector);



    }


}
