package com.veracode.apicredentials.generator;

import java.util.Locale;
import java.util.concurrent.TimeoutException;

public final class ApiCredentialsGeneratorRunner {
    private String username;
    private String password;
    private String driverName;
    private String driverLocation;

    private ApiCredentialsGeneratorRunner(String[] arguments) {
        parseArguments(arguments);
        validateParameters();
        System.setProperty(driverName, driverLocation);
    }

    private void validateParameters() {
        StringBuilder messageBuilder = new StringBuilder();
        if (username == null) {
            messageBuilder.append("\t --username or -u\n");
        }
        if (password == null) {
            messageBuilder.append("\t --password or -p\n");
        }
        if (driverName == null) {
            messageBuilder.append("\t --driverName or -dn\n");
        }
        if (driverLocation == null) {
            messageBuilder.append("\t --driverLocation or -dl\n");
        }
        String errorMessage = messageBuilder.toString();
        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException("Missing parameters: \n" + errorMessage);
        }
    }

    private void parseArguments(String[] arguments) {
        String currentParameter = "";
        for (String argument : arguments) {
            if (argument.charAt(0) == '-') {
                currentParameter = argument.toLowerCase(Locale.ROOT);
            } else {
                trySetValue(currentParameter, argument);
            }
        }
    }

    private void trySetValue(String currentParameter, String argument) {
        switch (currentParameter) {
            case "--username":
            case "-u":
                username = argument;
                break;
            case "--password":
            case "-p":
                password = argument;
                break;
            case "--drivername":
            case "-dn":
                driverName = argument;
                break;
            case "--driverlocation":
            case "-dl":
                driverLocation = argument;
                break;
            case "":
                break;
            default:
                throw new IllegalArgumentException("Unrecognized parameter: " + currentParameter);
        }
    }

    private void generateCredentials() throws TimeoutException {
        ApiCredentials newCredentials = APICredentialsGenerator.generate(username, password, driverName);
        if (newCredentials != null) {
            System.out.println("API ID: " + newCredentials.getApiID());
            System.out.println("API Key: " + newCredentials.getApiKey());
        } else {
            System.out.println("Error generating credentials");
        }
    }

    public static void run(String[] arguments) throws TimeoutException {
        new ApiCredentialsGeneratorRunner(arguments).generateCredentials();
    }
}
