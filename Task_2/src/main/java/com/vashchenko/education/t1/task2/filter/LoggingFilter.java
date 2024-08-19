package com.vashchenko.education.t1.task2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vashchenko.education.t1.task2.configuration.properties.LoggingProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

public class LoggingFilter extends OncePerRequestFilter {
    private final LoggingProperties properties;
    private final ObjectMapper mapper = new ObjectMapper();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private Logger log = LogManager.getLogger(this.getClass());

    public LoggingFilter(LoggingProperties properties) {
        this.properties = properties;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(shouldLog(request)) {
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            long startTime = System.currentTimeMillis();
            filterChain.doFilter(requestWrapper, responseWrapper);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            logResponse(requestWrapper,responseWrapper,duration);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }


    private boolean shouldLog(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        return properties.getIgnoredUrls() == null ||
                properties.getIgnoredUrls().stream().noneMatch(pattern -> pathMatcher.match(pattern, requestUrl));
    }

    private void logResponse(ContentCachingRequestWrapper requestWrapper,
                             ContentCachingResponseWrapper responseWrapper, long duration) throws IOException {
        StringBuilder logString = new StringBuilder();
        logString.append(logRequestDetails(requestWrapper));
        logString.append(logResponseDetails(responseWrapper));
        logString.append(String.format("\n\tDURATION : %s ms",duration));
        log.info(logString);
    }

    private String logRequestDetails(ContentCachingRequestWrapper requestWrapper) throws IOException {
        String method = requestWrapper.getMethod();
        String uri = requestWrapper.getRequestURI();
        StringBuilder logBuffer = new StringBuilder(String.format("URL : \"%s\", METHOD : \"%s\" ", uri, method));
        if(properties.isIncludeIncomingHeaders()){
            logBuffer.append(getRequestHeaders(requestWrapper));
        }
        if(properties.isIncludeRequestBody()){
            Object jsonObject = mapper.readValue(requestWrapper.getContentAsByteArray(), Object.class);
            String formattedObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            logBuffer.append("\n\tREQUEST BODY: \n").append(formattedObject);
        }
        return logBuffer.toString();
    }

    private String getRequestHeaders(HttpServletRequest httpRequest) {
        StringBuilder requestHeadersLogPart = new StringBuilder("\n\tREQUEST HEADERS: [");
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpRequest.getHeader(headerName);
            requestHeadersLogPart.append(String.format("%s:\"%s\", ",headerName,headerValue));
        }
        requestHeadersLogPart.deleteCharAt(requestHeadersLogPart.length()-1);
        requestHeadersLogPart.append("]");
        return requestHeadersLogPart.toString();
    }

    private String logResponseDetails(ContentCachingResponseWrapper responseWrapper) throws IOException {
        StringBuilder logBuffer = new StringBuilder(String.format("\n\tSTATUS: \"%s\"",responseWrapper.getStatus()));
        if (properties.isIncludeRequestBody()){
            Object jsonObject = mapper.readValue(responseWrapper.getContentAsByteArray(), Object.class);
            String formattedObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            logBuffer.append("\n\tRESPONSE BODY: \n").append(formattedObject);
        }
        responseWrapper.copyBodyToResponse();
        if(properties.isIncludeIncomingHeaders()){
            logBuffer.append(getResponseHeaders(responseWrapper));
        }
        responseWrapper.copyBodyToResponse();
        return logBuffer.toString();
    }
    private String getResponseHeaders(ContentCachingResponseWrapper httpResponseWrapper) {
        StringBuilder responseHeadersLogPart = new StringBuilder("\n\tRESPONSE HEADERS: [");
        Collection<String> headerNames = httpResponseWrapper.getHeaderNames();
        for (String headerName : headerNames) {
            String headerValue = httpResponseWrapper.getHeader(headerName);
            responseHeadersLogPart.append(String.format("%s:\"%s\", ",headerName,headerValue));
        }
        responseHeadersLogPart.deleteCharAt(responseHeadersLogPart.length()-1);
        responseHeadersLogPart.append("]");
        return responseHeadersLogPart.toString();
    }

}