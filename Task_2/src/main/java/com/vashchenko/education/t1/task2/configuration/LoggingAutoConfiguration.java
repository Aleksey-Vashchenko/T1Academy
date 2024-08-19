package com.vashchenko.education.t1.task2.configuration;

import com.vashchenko.education.t1.task2.configuration.properties.LoggingProperties;
import com.vashchenko.education.t1.task2.filter.LoggingFilter;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;

@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "vashchenko.logging",value = "enabled",havingValue = "true", matchIfMissing = false)
public class LoggingAutoConfiguration {
    private final LoggingProperties properties;

    public LoggingAutoConfiguration(LoggingProperties properties) {
        this.properties = properties;
    }

    @Bean
    LoggingFilter loggingFilter(){
        return new LoggingFilter(properties);
    }

    @PostConstruct
    void initFilterLogger()
    {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        final Layout<String> layout = PatternLayout.newBuilder()
                .withPattern(properties.getPattern())
                .withConfiguration(config)
                .withCharset(StandardCharsets.UTF_8)
                .build();
        Appender appender = ConsoleAppender.newBuilder()
                .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
                .setName("VashchenkoLogging")
                .setLayout(layout)
                .build();
        appender.start();
        config.addAppender(appender);
        AppenderRef ref = AppenderRef.createAppenderRef("VashchenkoLogging", Level.INFO, null);
        AppenderRef[] refs = new AppenderRef[] {ref};
        LoggerConfig loggerConfig = LoggerConfig.newBuilder()
                .withConfig(config)
                .withLevel(Level.INFO)
                .withConfig(config)
                .withAdditivity(false)
                .withLoggerName(LoggingFilter.class.getName())
                .withRefs(refs)
                .build();
        loggerConfig.addAppender(appender, Level.INFO, null);
        config.addLogger(LoggingFilter.class.getName(), loggerConfig);
        ctx.updateLoggers();
    }

}
