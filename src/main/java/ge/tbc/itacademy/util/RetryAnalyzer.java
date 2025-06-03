package ge.tbc.itacademy.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;

    @Override
    public boolean retry(ITestResult result) {
        RetryCount annotation = result.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(RetryCount.class);

        if (count < annotation.count()) {
            count++;
            System.out.println("Retrying...");
            return true;
        }
        return false;
    }
}