package com.veracode.apicredentials.generator;

import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] arguments) throws TimeoutException {
        ApiCredentialsGeneratorRunner.run(arguments);
    }
}
