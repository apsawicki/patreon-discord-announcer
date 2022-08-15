package PDA.selenium;

import ch.qos.logback.classic.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

@Component
@Scope("prototype")
public class PatreonScraper {

    Wait<WebDriver> wait;
    Logger log;
    WebDriver driver;


    public PatreonScraper () {
        this.log = (Logger) LoggerFactory.getLogger(this.getClass().getSimpleName());

        driver = createBrowser();
        if (driver == null) {
            this.log.error("The Firefox driver failed to initialize.  Stopping PDA.");
            System.exit(1);
        }

        this.log.info("Initializing waiting interface...");
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(250));
        this.log.info("Initialized the waiting interface!");
    }

    // Instantiates the driver that we use for loading up patreon pages through FireFox
    private WebDriver createBrowser() {
        try {
            this.log.info("Initializing Firefox driver...");

            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            options.setLogLevel(FirefoxDriverLogLevel.FATAL);


            // Marionette is required to redirect Firefox's logging
            options.setCapability(FirefoxDriver.Capability.MARIONETTE, "true");

            // Set redirect location.  If OS is Windows based then "NUL:", otherwise "/dev/null"
            String logLocation = SystemUtils.OS_NAME.startsWith("Windows") ? "NUL:" : "/dev/null";

            // TODO: something in this commented code makes the firefox driver filepath null when we get the driver with WebDriverManager
			// System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, logLocation);

            // Create the driver
            this.log.info("Initialized the Firefox driver!");
            return WebDriverManager.firefoxdriver().capabilities(options).create();
        } catch (SessionNotCreatedException e) {
            this.log.error("A Firefox session could not be created. If you do not have Firefox installed, please install it.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.log.error("Could not initialize Firefox driver!");
        return null;
    }

    // Sleeps while handling {@link InterruptedException}
    public void sleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Returns a number between [a, b)
    public int randNum(int min, int max) {
        if (max <= min)
            return 0;

        return new Random().nextInt(max - min) + min;
    }

     // Will format the time printed to the console, so it is more readable
     public String formatTime(int timeInSeconds) {
        int seconds = timeInSeconds % 3600 % 60;
        int minutes = (int) Math.floor(timeInSeconds % 3600 / 60);

        return (minutes == 0 ? minutes + "m:" : "") + seconds + "s";
    }

    // Checks for the visibility of an element.  The element must be visible regardless of being loaded.
    public boolean visibleElementFound(By by) {
        try {
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Attempts to load the patreon page, handles events where it asks you to login or has a captcha to solve
    public void goToPatreonPage(String patreonUrl, By postCardSelector) {
        // Load the login page to pass GeeTest, ensuring we're allowed to see post
        driver.get(patreonUrl);

        this.log.info("Waiting for post cards to be found...");
        // Time has passed and we haven't seen any post cards...
        if (!this.visibleElementFound(postCardSelector)) {
            this.log.info("No postcards found after waiting for 5 second.  Checking for GeeTest.");

            int loadCount = 0;

            // Keep reload the login page to get the GeeTest.  Sometimes it doesn't appear on the first load.
            while (loadCount++ < 5) {
                driver.get("https://www.patreon.com/login");

                /*
                 * Check for the login page on the 2nd or greater successful page reload
                 *
                 * This is required as sometimes loading the page for the first time
                 * will not show the bot check
                 */
                if (/* loadCount > 1 && */ !driver.getPageSource().contains("New to Patreon?"))
                    break;
            }

            if (!driver.getPageSource().contains("New to Patreon?")) {
                this.log.info("Attempting to solve GeeTest CAPTCHA...");

                driver.manage().deleteAllCookies();
                geeTest(driver);
                driver.get(patreonUrl);
            }
        }
    }

    // Setting up the browser before solving the puzzle
    public void geeTest(WebDriver driver) {

        // Wait until the GeeTest iframe is loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("iframe")));

        // Store the GeeTest iframe
        WebElement iFrame = driver.findElement(By.tagName("iframe"));

        // Switch the web driver context to the iframe
        driver.switchTo().frame(iFrame);

        // Wait until the GeeTest clickable verification button is loaded
        this.visibleElementFound(By.className("geetest_radar_btn"));

        // Check to see if Patreon has blocked our IP entirely
        if (this.visibleElementFound(By.className("captcha__human__title")))
            if (driver.findElement(By.className("captcha__human__title")).getText().contains("You have been blocked")) {
                this.log.warn("The current IP has been blocked by Patreon.  Stopping.");
                System.exit(1);
            }

        // For some reason the page isn't loading.  Simply return from the GeeTest function so the program can navigate elsewhere.
        if (!this.visibleElementFound(By.className("geetest_radar_btn")))
            return;

        // Store the GeeTest verification button
        WebElement geeTestVerify = driver.findElement(By.className("geetest_radar_btn"));

        // Click the GeeTest verification button
        geeTestVerify.click();

        int loopAttempts = 0;

        // While the puzzle is visible, attempt to solve it repeatedly
        while (loopAttempts < 4 && this.visibleElementFound(By.className("geetest_canvas_bg"))) {
            loopAttempts++;
            this.sleep(1000);

            moveSlider(solvePuzzle());
        }

        // If we've failed the CAPTCHA 5 times, reset the page and cookies
        if (loopAttempts >= 5) {
            driver.get("https://www.example.com");
            sleep(1500);
            driver.manage().deleteAllCookies();
        }
    }

    // Solving the puzzle
    private int solvePuzzle() {
        // Wait until both the original and the puzzle image are present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("geetest_canvas_fullbg")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("geetest_canvas_bg")));

        // Save both the original and the puzzle image elements
        WebElement originalImageElement = driver.findElement(By.className("geetest_canvas_fullbg"));
        WebElement puzzleImageElement = driver.findElement(By.className("geetest_canvas_bg"));

        // Convert both images to base64 image strings
        String originalImageString = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].toDataURL('image/png').substring(22);", originalImageElement);
        String puzzleImageString = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].toDataURL('image/png').substring(22);", puzzleImageElement);

        byte[] originalImageBytes = DatatypeConverter.parseBase64Binary(originalImageString);
        byte[] puzzleImageBytes = DatatypeConverter.parseBase64Binary(puzzleImageString);

        // Convert the base64 image to an actual image
        BufferedImage originalImage = null, puzzleImage = null;

        // Attempt to convert the base64 encoded image to an image in memory
        try {
            originalImage = ImageIO.read(new ByteArrayInputStream(originalImageBytes));
            puzzleImage = ImageIO.read(new ByteArrayInputStream(puzzleImageBytes));
        } catch (IOException ex) {
            this.log.error("An issue occurred while reading the GeeTest puzzle image!\n");
            ex.printStackTrace();
            driver.quit();
            System.exit(1);
        }

        // Ensure the images were converted properly
        if (originalImage == null || puzzleImage == null) {
            this.log.error("The original image or the puzzle image were null after being saved.");
            driver.quit();
            System.exit(1);
        }

        // Store WxH of each image
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        int[][] differenceMatrix = new int[originalWidth][originalHeight];

        // Calculate the differences between the original image and the puzzle image
        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                // Get current RGB values
                int rgbA = originalImage.getRGB(x, y);
                int rgbB = puzzleImage.getRGB(x, y);

                // Something from geeksforgeeks...
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;

                // Store the difference values
                differenceMatrix[x][y] += Math.abs(redA - redB);
                differenceMatrix[x][y] += Math.abs(greenA - greenB);
                differenceMatrix[x][y] += Math.abs(blueA - blueB);

                // If the number is less than 130 (a threshold I chose), set it to 0 to signify no change in current pixel.
                if (differenceMatrix[x][y] < 130)
                    differenceMatrix[x][y] = 0;

            }
        }

        int dragAmount = 0;

        // Find the first change in the difference matrix by going from top left to bottom right, clearing lines vertically
        for (int x = 0; x < originalWidth && dragAmount == 0; x++)
            for (int y = 0; y < originalHeight; y++)
                if (differenceMatrix[x][y] != 0) {
                    dragAmount = x - 6;
                    break;
                }

        return dragAmount;
    }

    // Moves the slider to solve the captcha puzzle
    private void moveSlider(int dragAmount) {
        // Let the page load
        if (this.visibleElementFound(By.className("geetest_slider_button")))
            this.sleep(randNum(500, 1000));

        WebElement dragButton = driver.findElement(By.className("geetest_slider_button"));
        Actions move = new Actions(driver);

        this.log.info("Solving...");

        // Move on top of the button with a seemingly random offset
        move.moveToElement(dragButton, 20 + new Random().nextInt(10), 20 + new Random().nextInt(10)).perform();

        // Left mouse button down
        move.clickAndHold().perform();

        // Wait between 1-2 seconds
        this.sleep(randNum(500, 1000));

        // Move the cursor with slight variation
        move.moveByOffset(dragAmount + (dragAmount % 2 == 0 ? 2 : -2), 0).perform();

        // Wait some time before letting go to increase the CAPTCHA click timer
        this.sleep(500);

        if (dragAmount > 90)
            this.sleep(randNum(500, 900));

        // Release LMB
        move.release().perform();

        // Click the "reset" button if it exists
        if (this.visibleElementFound(By.className("geetest_reset_tip_content"))) {
            this.sleep(randNum(500, 1000));

            WebElement resetButton = driver.findElement(By.className("geetest_reset_tip_content"));
            move.moveToElement(resetButton);
            this.sleep(randNum(500, 1000));

            resetButton.click();
        }
    }
}
