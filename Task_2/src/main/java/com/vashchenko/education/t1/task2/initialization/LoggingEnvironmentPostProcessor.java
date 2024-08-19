package com.vashchenko.education.t1.task2.initialization;

import com.vashchenko.education.t1.task2.exception.EnvinronmentStartupException;
import org.apache.logging.log4j.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Arrays;

public class LoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        validateBooleanProperty(environment, "vashchenko.logging.enabled");
        validateBooleanProperty(environment, "vashchenko.logging.includeIncomingHeaders");
        validateBooleanProperty(environment, "vashchenko.logging.includeOutgoingHeaders");
        validateBooleanProperty(environment, "vashchenko.logging.includeRequestBody");
        validateBooleanProperty(environment, "vashchenko.logging.includeResponseBody");
        validateLogLevel(environment,"vashchenko.logging.level");
    }

    private static void validateBooleanProperty(ConfigurableEnvironment environment, String propertyName) {
        String propertyValue = environment.getProperty(propertyName);
        if (propertyValue!=null && !propertyValue.isEmpty()) {
            if (!Boolean.TRUE.toString().equalsIgnoreCase(propertyValue) && !Boolean.FALSE.toString().equalsIgnoreCase(propertyValue)) {
                throw new EnvinronmentStartupException("Property '" + propertyName + "' must be a boolean value ('true' or 'false').");
            }
        }
    }

    private static void validateLogLevel(Environment environment, String propertyName){
        String level = environment.getProperty(propertyName);
        if(level!=null && !level.isEmpty()){
            try {
                Level logLevel = Level.valueOf(level.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new EnvinronmentStartupException("Property '" + propertyName + "' must be a level value: "+ Arrays.toString(Level.values()));

            }
        }
    }
}
