package main;

public class LogEntry {
    private String ipAddress;
    private String requestedUrl;

    public LogEntry(LogEntryBuilder builder) {
        this.ipAddress = builder.ipAddress;
        this.requestedUrl = builder.requestedUrl;
    }

    public LogEntry(String ipAddress, String requestedUrl) {
        this.ipAddress = ipAddress;
        this.requestedUrl = requestedUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRequestedUrl() {
        return requestedUrl;
    }

    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }

    public static class LogEntryBuilder {
        private String ipAddress;
        private String requestedUrl;

        public LogEntryBuilder(){}

        public LogEntryBuilder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public LogEntryBuilder requestedUrl(String requestedUrl) {
            this.requestedUrl = requestedUrl;
            return this;
        }

        public LogEntry build() {
            return new LogEntry(this);
        }

    }
}
