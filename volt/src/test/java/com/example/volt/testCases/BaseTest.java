package com.example.volt.testCases;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import com.example.volt.Utilities.ReadConfig;
import com.example.volt.Utilities.enums.Environment;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    public Environment env;
    public WebDriver driver;

    ReadConfig readConfig = new ReadConfig();
    public String browser = readConfig.getBrowser();
    public boolean headlessMode = readConfig.getMode();

    @BeforeClass(alwaysRun = true)
    public void beforeClass(@Optional String browserParam, @Optional Environment envParam,
            @Optional Boolean headlessModeParam) {
        env = envParam == null ? readConfig.getEnvironment() : envParam;
        headlessMode = headlessModeParam == null ? readConfig.getMode() : headlessModeParam;
        driver = browserParam == null ? initializeDriver(browser) : initializeDriver(browserParam);
    }

    public WebDriver initializeDriver(String browser) {
        DesiredCapabilities caps = new DesiredCapabilities();

        switch (browser) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxProfile profile = new FirefoxProfile();
                FirefoxOptions options = new FirefoxOptions();
                profile.setPreference("media.navigator.streams.fake", true);
                profile.setPreference("media.navigator.permission.disabled", true);
                profile.setPreference("browser.tabs.remote.autostart", false);
                profile.setPreference("browser.shell.checkDefaultBrowser", false);
                profile.setPreference("browser.startup.page", 1);
                profile.setPreference("browser.newtabpage.enabled", false);
                profile.setPreference("browser.download.manager.showWhenStarting", false);
                profile.setPreference("browser.download.manager.useWindow", false);
                profile.setPreference("browser.download.manager.showAlertOnComplete", false);
                profile.setPreference("browser.download.manager.closeWhenDone", true);
                profile.setPreference("browser.download.folderList", 2);
                profile.setPreference("browser.download.forbid_open_with", true);
                System.out.println("HeadlessMode: " + headlessMode);
                if (headlessMode) {
                    options.addArguments("--headless");
                }
                options.setProfile(profile);
                WebDriver driver = new FirefoxDriver(options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                driver.manage().window().maximize();
                return driver;
            }

            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                WebDriverManager.chromiumdriver().setup();
                options.addArguments("use-fake-device-for-media-stream");
                options.addArguments("use-fake-ui-for-media-stream");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--test-type");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-first-run");
                options.addArguments("--no-default-browser-check");
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("--disable-extensions");
                options.addArguments("--start-maximized");
                // options.addArguments("--guest");
                options.addArguments("--window-size=1920,1080");

                System.out.println("HeadlessMode: " + headlessMode);
                if (headlessMode) {
                    options.addArguments("--headless");
                    options.addArguments("--window-size=1920,1080");
                }
                caps.setCapability(ChromeOptions.CAPABILITY, options);
                WebDriver driver = new ChromeDriver(options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                if (!headlessMode)
                    driver.manage().window().maximize();
                return driver;
            }

            default:
                break;
        }
        return null;
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
