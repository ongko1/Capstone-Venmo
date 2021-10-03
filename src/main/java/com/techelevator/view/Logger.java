package com.techelevator.view;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private String fileName;
    private File logFile;

    public Logger(String fileName) {
        this.fileName = fileName;
        try {
             logFile = new File(fileName);
             //boolean deleteFile = new File(fileName).delete();
             logFile.createNewFile();
        } catch (IOException e) {}
    }

    private String getCurrentTime() {
        String date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date());
        return date;
    }

    public String logEvent(String event, String balanceBeforeTransaction, String afterTransaction) {
        String logString = String.format("%-24s %-22s %-14s %-14s",
                getCurrentTime(),
                event,
                balanceBeforeTransaction,
                afterTransaction);

        try (Writer fileWriter = new FileWriter(logFile, true);
             BufferedWriter buffered = new BufferedWriter(fileWriter)) {
            buffered.write(logString + "\n");
        } catch (IOException e1) {
        }
        return logString;


    }
    public String logAudit(String event) {
        try (Writer fileWriter = new FileWriter(logFile, true);
             BufferedWriter buffered = new BufferedWriter(fileWriter)) {
            buffered.write(event + "\n");
        } catch (IOException e1) {
        }
        return event;


    }


}
