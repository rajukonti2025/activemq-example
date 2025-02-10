package com.mq.logger;
import java.io.FileWriter;
import java.io.IOException;

public class GroupLogger {
    private static final String LOG_FILE = "group_operations.log";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
