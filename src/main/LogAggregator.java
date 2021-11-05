package main;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LogAggregator {

    // 177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] "GET /intranet-analytics/ HTTP/1.1" 200 3574 "-" "Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7"
    private Map<String, AtomicInteger> urlCounter;
    private Map<String, AtomicInteger> ipCounter;

    public LogAggregator() {
        ipCounter = new ConcurrentHashMap<>();
        urlCounter = new ConcurrentHashMap<>();
    }

    public Integer uniqueIpAddresses() {
        return ipCounter.size();
    }

    public String topMostActiveIpAddresses(int top) {
        List<String> topIps = ipCounter.entrySet().stream().sorted(Collections.reverseOrder(Comparator.comparingInt(e -> e.getValue().get())))
                .limit(top).map(e -> e.getKey()).collect(Collectors.toList());
        return Arrays.toString(topIps.toArray());
    }

    public String topMostVisitedUrls(int top) {
        List<String> topIps = urlCounter.entrySet().stream().sorted(Collections.reverseOrder(Comparator.comparingInt(e -> e.getValue().get())))
                .limit(top).map(e -> e.getKey()).collect(Collectors.toList());
        return Arrays.toString(topIps.toArray());
    }

    public void consumeLog(LogEntry entry) {
        if (entry == null) return;

        // consume ip address
        if (entry.getIpAddress() != null) {
            ipCounter.putIfAbsent(entry.getIpAddress(), new AtomicInteger(0));
            ipCounter.get(entry.getIpAddress()).incrementAndGet();
        }

        // consume url
        if (entry.getRequestedUrl() != null) {
            urlCounter.putIfAbsent(entry.getRequestedUrl(), new AtomicInteger(0));
            urlCounter.get(entry.getRequestedUrl()).incrementAndGet();
        }
    }

    public LogEntry parseLog(String log) {
        if (log == null) return null;

        // parse ip address
        String ipAddress = null;
        if (!log.startsWith("-")) {
            ipAddress = log.substring(0, log.indexOf(" "));
        }

        // parse url
        String url = null;
        String[] parts = log.split("\"");
        if (parts.length > 1) {
            // second part is the request detail
            String urlPart = parts[1];
            String[] requestPart = urlPart.split(" ");
            if (requestPart.length == 3) {
                url = requestPart[1];
            }
        }

        return (ipAddress != null || url != null) ?
                new LogEntry.LogEntryBuilder()
                    .ipAddress(ipAddress)
                    .requestedUrl(url)
                    .build()
                : null;
    }
}
