import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LogReaderTest {

    @Test
    public void test__readLogFile_exampleData() {
        String filePath = "resource/programming-task-example-data.log";

        try {
            LogAggregator logAggregator = LogReader.readLogFile(filePath); // evil no mock here

            assertNotNull(logAggregator);
            assertTrue(logAggregator.uniqueIpAddresses() == 11);
            assertNotNull(logAggregator.topMostActiveIpAddresses(3).contains("168.41.191.40"));
            assertNotNull(logAggregator.topMostVisitedUrls(3).contains("/docs/manage-websites/"));
        } catch (IOException e) {
            fail();
        }
    }
}
