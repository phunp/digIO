package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LogReader {
    public static void main(String[] args) throws IOException {
        String logFile = "resource/programming-task-example-data.log";
        LogAggregator logAggregator = readLogFile(logFile);

        System.out.println("Finish reading log file.\n");
        System.out.println("Number of unique ip address: " + logAggregator.uniqueIpAddresses());
        System.out.println("Top 3 most active IPs: " + logAggregator.topMostActiveIpAddresses(3));
        System.out.println("Top 3 most visited URLs: " + logAggregator.topMostVisitedUrls(3));
    }

    public static LogAggregator readLogFile(String logFile) throws IOException{
        LogAggregator logAggregator = new LogAggregator();

        System.out.println("Start reading log file: " + logFile);
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String logLine = br.readLine();
            while (logLine != null) {
                logAggregator.consumeLog(logAggregator.parseLog(logLine));
                logLine = br.readLine();
            }
        }
        return logAggregator;
    }
}
