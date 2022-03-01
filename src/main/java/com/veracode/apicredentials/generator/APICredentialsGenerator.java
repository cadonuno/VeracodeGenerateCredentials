package com.veracode.apicredentials.generator;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeoutException;

public class APICredentialsGenerator {
    private static final String BASE_URL = "https://web.analysiscenter.veracode.com/login/";
    private static final String USERNAME_FIELD_ID = "okta-signin-username";
    private static final String PASSWORD_FIELD_ID = "okta-signin-password";
    private static final String LOGIN_BUTTON_ID = "okta-signin-submit";
    private static final String USER_NAME_ICON_ID = "icon_user";
    private static final String API_CREDENTIALS_BUTTON_ID = "api_credentials";
    private static final String GENERATE_CREDENTIALS_BUTTON_ID = "generateCredentialsButton";
    private static final String COPY_ID_TO_CLIPBOARD_BUTTON_ID = "copyIdToClipboardButton";

    public static ApiCredentials generate(String username, String password, String driverName) throws TimeoutException {
        ApiCredentials credentials;
        final WebDriver webDriver = WebDriverProvider.getDriver(driverName);
        try {
            webDriver.get(BASE_URL);
            loginToPlatform(username, password, webDriver);
            openCredentialsPage(webDriver);
            credentials = generateCredentials(webDriver);
        } finally {
            webDriver.quit();
        }
        return credentials;
    }

    private static void loginToPlatform(String username, String password, WebDriver webDriver) throws TimeoutException {
        webDriver.manage().window().setSize(new Dimension(800, 600));
        SeleniumHelper.waitForElementPresent(webDriver, By.id(LOGIN_BUTTON_ID));
        webDriver.findElement(By.id(USERNAME_FIELD_ID)).sendKeys(username);
        webDriver.findElement(By.id(PASSWORD_FIELD_ID)).sendKeys(password);
        SeleniumHelper.clickElement(webDriver, By.id(LOGIN_BUTTON_ID));
        SeleniumHelper.waitForElementPresent(webDriver, By.id(USER_NAME_ICON_ID));
    }

    private static void openCredentialsPage(WebDriver webDriver) throws TimeoutException {
        SeleniumHelper.clickElement(webDriver, By.id(USER_NAME_ICON_ID));
        SeleniumHelper.clickElement(webDriver, By.id(API_CREDENTIALS_BUTTON_ID));
        SeleniumHelper.waitForElementPresent(webDriver, By.id(GENERATE_CREDENTIALS_BUTTON_ID));
    }

    private static ApiCredentials generateCredentials(WebDriver webDriver) throws TimeoutException {
        SeleniumHelper.clickElement(webDriver, By.id(GENERATE_CREDENTIALS_BUTTON_ID));
        SeleniumHelper.waitForElementPresent(webDriver, By.id(COPY_ID_TO_CLIPBOARD_BUTTON_ID));
        SeleniumHelper.clickElement(webDriver, By.id(COPY_ID_TO_CLIPBOARD_BUTTON_ID));
        return new ApiCredentials(
                webDriver.findElement(By.id("apiIdText")).getText(),
                webDriver.findElement(By.id("secretKeyText")).getText());
    }
}
