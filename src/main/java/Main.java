import model.UsageSummary;
import service.ConsoleOutputService;
import service.SessionAnalyzerService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static util.AppUtils.isEmpty;
import static util.AppUtils.isNull;
import static util.AppUtils.getFileName;

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

        SessionAnalyzerService sessionAnalyzerService = new SessionAnalyzerService();
        ConsoleOutputService consoleOutputService = new ConsoleOutputService();

        try (InputStream inputStream = Main.class.getResourceAsStream(fileName)) {
            if (isNull(inputStream)) {
                System.err.println("Sorry! Something bad happened with the file data, aborting the program");
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Map<String, UsageSummary> usageSummaryMap = sessionAnalyzerService.analyzeSession(bufferedReader);
            consoleOutputService.printUsageSummary(usageSummaryMap);
        } catch (Exception ex) {
            System.err.println("Sorry! System failed to process the fair billing: " + ex.getMessage());
        }
    }
}