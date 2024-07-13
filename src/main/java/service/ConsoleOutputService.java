package service;

import model.UsageSummary;

import java.util.Map;

import static util.AppUtils.isEmpty;

public class ConsoleOutputService {
    public void printUsageSummary(Map<String, UsageSummary> usageSummaryMap) {
        if(isEmpty(usageSummaryMap)) {
            return;
        }
        for (Map.Entry<String, UsageSummary> entry : usageSummaryMap.entrySet()) {
            UsageSummary usageSummary = entry.getValue();
            System.out.printf("%s %d %d%n", entry.getKey(), usageSummary.getTotalSession(), usageSummary.getTotalTime());
        }
    }
}
