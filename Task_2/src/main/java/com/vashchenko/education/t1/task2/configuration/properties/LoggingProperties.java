package com.vashchenko.education.t1.task2.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "vashchenko.logging")
public class LoggingProperties {
    private boolean enabled = false;
    private String pattern = "%d{yyyy-MM-dd HH:mm:ss} - %msg%n";
    private List<String> ignoredUrls;
    private boolean includeIncomingHeaders = false;
    private boolean includeOutgoingHeaders = false;
    private boolean includeRequestBody = false;
    private boolean includeResponseBody = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getIgnoredUrls() {
        return ignoredUrls;
    }

    public void setIgnoredUrls(List<String> ignoredUrls) {
        this.ignoredUrls = ignoredUrls;
    }

    public boolean isIncludeIncomingHeaders() {
        return includeIncomingHeaders;
    }

    public void setIncludeIncomingHeaders(boolean includeIncomingHeaders) {
        this.includeIncomingHeaders = includeIncomingHeaders;
    }

    public boolean isIncludeOutgoingHeaders() {
        return includeOutgoingHeaders;
    }

    public void setIncludeOutgoingHeaders(boolean includeOutgoingHeaders) {
        this.includeOutgoingHeaders = includeOutgoingHeaders;
    }

    public boolean isIncludeRequestBody() {
        return includeRequestBody;
    }

    public void setIncludeRequestBody(boolean includeRequestBody) {
        this.includeRequestBody = includeRequestBody;
    }

    public boolean isIncludeResponseBody() {
        return includeResponseBody;
    }

    public void setIncludeResponseBody(boolean includeResponseBody) {
        this.includeResponseBody = includeResponseBody;
    }
}