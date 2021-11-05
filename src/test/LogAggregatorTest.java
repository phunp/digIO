package test;

import main.LogAggregator;
import main.LogReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class LogAggregatorTest {

    @Test
    public void testLogFile_1() {
        String filePath = "resource/programming-task-example-data.log";

        try {
            LogAggregator logAggregator = LogReader.readLogFile(filePath); // evil no mock here

            Assert.assertNotNull(logAggregator);
            Assert.assertTrue(logAggregator.uniqueIpAddresses() == 11);
            Assert.assertNotNull(logAggregator.topMostActiveIpAddresses(3).contains("168.41.191.40"));
            Assert.assertNotNull(logAggregator.topMostVisitedUrls(3).contains("/docs/manage-websites/"));
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
