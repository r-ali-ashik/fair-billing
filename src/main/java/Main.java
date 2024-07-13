import model.UsageSummary;
import service.ConsoleOutputService;
import service.SessionAnalyzerService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import static util.AppUtils.getFileName;
import static util.AppUtils.isEmpty;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("No arguments provided");
            return;
        }

        String fileName = getFileName(args);
        if (isEmpty(fileName)) {
            System.err.println("No .txt file provided in arguments");
            return;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File '" + file + "' does not exist!");
            return;
        }

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        ConsoleOutputService consoleOutputService = new ConsoleOutputService();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);
            consoleOutputService.printUsageSummary(usageSummaryMap);
        } catch (Exception ex) {
            System.err.println("Sorry! System failed to process the fair billing: " + ex.getMessage());
        }
    }
}