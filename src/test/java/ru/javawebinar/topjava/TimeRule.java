package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

public class TimeRule implements TestRule {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private StringBuilder report = new StringBuilder();

    @Override
    public Statement apply(final Statement base, Description description) {
        if (description.isSuite()) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    base.evaluate();
                    log.info(report.toString());
                }
            };
        }
        if (description.isTest()) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    Instant start = Instant.now();
                    base.evaluate();
                    String info = description.getMethodName() + " - "
                            + Duration.between(start, Instant.now()).toMillis() + "ms";
                    log.info(info);
                    if (report.length() != 0) {
                        report.append("\n");
                    }
                    report.append(info);
                }
            };
        }
        return base;
    }
}