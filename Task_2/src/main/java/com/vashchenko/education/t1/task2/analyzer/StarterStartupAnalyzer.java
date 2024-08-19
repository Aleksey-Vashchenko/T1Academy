package com.vashchenko.education.t1.task2.analyzer;

import com.vashchenko.education.t1.task2.exception.EnvinronmentStartupException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class StarterStartupAnalyzer extends AbstractFailureAnalyzer<EnvinronmentStartupException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, EnvinronmentStartupException cause) {
        return new FailureAnalysis(cause.getMessage(), "Set valid values for configuration properties", cause);
    }
}
