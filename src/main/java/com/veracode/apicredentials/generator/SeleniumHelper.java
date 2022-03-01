package com.veracode.apicredentials.generator;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class SeleniumHelper {
    private static final int POLLING_TIMEOUT = 30;

    private SeleniumHelper() {
        throw new IllegalStateException("This class should not be instantiated");
    }

    public static void waitForElementPresent(WebDriver webDriver, By elementToCheck) throws TimeoutException {
        boolean hasFound = false;
        Instant start = Instant.now();
        while (!hasFound) {
            try {
                webDriver.findElement(elementToCheck);
                hasFound = true;
            } catch (NoSuchElementException notFound) {
                checkTimeout(start);
            }
        }
    }

    private static void checkTimeout(Instant start) throws TimeoutException {
        if (getTimeElapsed(start) > POLLING_TIMEOUT) {
            timeout();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            //nothing to do here
        }
    }

    public static void clickElement(WebDriver webDriver, By elementToClick) throws TimeoutException {
        boolean hasFound = false;
        Instant start = Instant.now();
        while (!hasFound) {
            try {
                webDriver.findElement(elementToClick).click();
                hasFound = true;
            } catch (ElementNotInteractableException notFound) {
                checkTimeout(start);
            }
        }
    }

    private static void timeout() throws TimeoutException {
        throw new TimeoutException("Timed out when running command");
    }

    private static long getTimeElapsed(Instant start) {
        return Duration.between(start, Instant.now()).getSeconds();
    }
}
