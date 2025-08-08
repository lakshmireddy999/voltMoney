package com.example.volt.Helper;

import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LoggerHelper {

	private static boolean root = false;

	public static Logger getLogger(Class<?> cls) {
		if (root) {
			return Logger.getLogger(cls);
		}
		PropertyConfigurator.configure("Log4j.properties");
		DesiredCapabilities caps = new DesiredCapabilities();
		LoggingPreferences logPreferences = new LoggingPreferences();
		logPreferences.enable(LogType.BROWSER, Level.ALL);
		caps.setCapability("goog:loggingPrefs", logPreferences);
		root = true;
		return Logger.getLogger(cls);
	}
}