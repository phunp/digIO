import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogAggregatorTest {

    private static final String IP_ADDRESS = "127.0.0.1";
    private static final String URL = "/home";

    @Test
    public void test__consumeLog__fullData() {
        LogEntry entry = new LogEntry(IP_ADDRESS, URL);

        LogAggregator aggregator = new LogAggregator();
        aggregator.consumeLog(entry);

        assertEquals(aggregator.getIpCounter().size(), 1);
        assertTrue(aggregator.getIpCounter().containsKey(IP_ADDRESS));
        assertEquals(aggregator.getUrlCounter().size(), 1);
        assertTrue(aggregator.getUrlCounter().containsKey(URL));
        assertTrue(aggregator.topMostActiveIpAddresses(1).contains(IP_ADDRESS));
        assertTrue(aggregator.topMostVisitedUrls(1).contains(URL));
        assertTrue(aggregator.uniqueIpAddresses() == 1);
    }

    @Test
    public void test__consumeLog__nullIp() {
        LogEntry entry = new LogEntry(null, URL);

        LogAggregator aggregator = new LogAggregator();
        aggregator.consumeLog(entry);

        assertEquals(aggregator.getIpCounter().size(), 0);
        assertEquals(aggregator.getUrlCounter().size(), 1);
        assertTrue(aggregator.getUrlCounter().containsKey(URL));
    }

    @Test
    public void test__consumeLog__nullUrl() {
        LogEntry entry = new LogEntry(IP_ADDRESS, null);

        LogAggregator aggregator = new LogAggregator();
        aggregator.consumeLog(entry);

        assertEquals(aggregator.getIpCounter().size(), 1);
        assertTrue(aggregator.getIpCounter().containsKey(IP_ADDRESS));
        assertEquals(aggregator.getUrlCounter().size(), 0);
    }

    @Test
    public void test__consumeLog__nullEntry() {
        LogAggregator aggregator = new LogAggregator();
        aggregator.consumeLog(null);

        assertEquals(aggregator.getIpCounter().size(), 0);
        assertEquals(aggregator.getUrlCounter().size(), 0);
    }

    @Test
    public void test__parseLog__fullLog() {
        String log = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";

        LogAggregator aggregator = new LogAggregator();
        LogEntry logEntry = aggregator.parseLog(log);

        assertEquals(logEntry.getIpAddress(), "177.71.128.21");
        assertEquals(logEntry.getRequestedUrl(), "/intranet-analytics/");
    }

    @Test
    public void test__parseLog__missingIp() {
        String log = "- - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";

        LogAggregator aggregator = new LogAggregator();
        LogEntry logEntry = aggregator.parseLog(log);

        assertNull(logEntry.getIpAddress());
        assertEquals(logEntry.getRequestedUrl(), "/intranet-analytics/");
    }

    @Test
    public void test__parseLog__missingUrl() {
        String log = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";

        LogAggregator aggregator = new LogAggregator();
        LogEntry logEntry = aggregator.parseLog(log);

        assertEquals(logEntry.getIpAddress(), "177.71.128.21");
        assertNull(logEntry.getRequestedUrl());
    }

    @Test
    public void test__parseLog__missingIpANdUrl() {
        String log = "- - - [10/Jul/2018:22:21:28 +0200] \"GET HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"";

        LogAggregator aggregator = new LogAggregator();
        LogEntry logEntry = aggregator.parseLog(log);

        assertNull(logEntry);
    }

    @Test
    public void test__parseLog__nullLog() {
        LogAggregator aggregator = new LogAggregator();
        LogEntry logEntry = aggregator.parseLog(null);

        assertNull(logEntry);
    }

    @Test
    public void test__parseLog__invalidLog() {
        LogAggregator aggregator = new LogAggregator();
        LogEntry logEntry = aggregator.parseLog("N/A");

        assertNull(logEntry);
    }
}