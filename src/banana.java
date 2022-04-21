import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class banana {
    static final String NAME = "Test";
    static final String BIRTHDAY_TIME = "17/04/2022 00:00:00";
    static final String NUMBER = "+919999999999";
    static WebDriver driver;

    public static void main(String[] args) throws Exception {
        // path to the gecko driver
        System.setProperty("webdriver.gecko.driver", "/home/shvmpc/Downloads/geckodriver");
        // path to the firefox profile
        FirefoxProfile firefoxProfile = new FirefoxProfile(
                new File("/home/shvmpc/.mozilla/firefox/j6vs2ebx.default"));
        // path to the firefox executable
        FirefoxBinary firefoxBinary = new FirefoxBinary(
                new File("/usr/bin/firefox-developer-edition"));
        FirefoxOptions options = new FirefoxOptions().setBinary(firefoxBinary).setProfile(firefoxProfile);
        driver = new FirefoxDriver(options);

        // driver code
        driver.get("https://web.whatsapp.com");
        Thread.sleep(TimeUnit.SECONDS.toMillis(7));

        Thread thread = new Thread(() -> {
            System.out.println("It's time bois.");
            if (!sendMessage()) {
                try {
                    Desktop.getDesktop().browse(new URI(
                            "https://web.whatsapp.com/send?phone=" + NUMBER + "&text=HAPPY%20BIRTHDAY!"));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "lmao ded");
            }
            System.exit(0);
        });

        // schedule the thread
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long delay = dateFormat.parse(BIRTHDAY_TIME).getTime() - new Date().getTime();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(thread, delay, TimeUnit.MILLISECONDS);
    }

    static boolean sendMessage() {
        try {
            // locating chat
            WebElement user = driver.findElement(By.xpath("//span[@title=\"" + NAME + "\"]"));
            user.click();
            // locating the chat's input field
            WebElement inputField = driver.findElement(By.xpath("//div[@title=\"Type a message\"]"));
            inputField.click();
            // typing
            inputField.sendKeys("Happy Birthday " + NAME + "!\uD83C\uDF82");
            // send message using Enter
            inputField.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something's wrong I can feel it.");
            return false;
        }
        return true;
    }

}
