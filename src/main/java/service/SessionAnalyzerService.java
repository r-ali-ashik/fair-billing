package service;

import exception.AppException;
import model.UsageSummary;

import java.io.BufferedReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import static constant.AppConstants.START_INDICATOR;
import static util.AppUtils.isEmpty;
import static util.AppUtils.isNull;
import static util.ValidationUtils.isValidSession;

public class SessionAnalyzerService {
    public Map<String, UsageSummary> analyzeSession(BufferedReader bufferedReader) {
        try {
            LocalTime soonestTime = null;
            LocalTime latestTime = null;

            Map<String, Stack<LocalTime>> userSessionMap = new HashMap<>();
            Map<String, UsageSummary> usageSummaryMap = new TreeMap<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (isEmpty(line) || !isValidSession(line)) {
                    continue;
                }
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) {
                    continue;
                }
                String timeString = parts[0];
                String username = parts[1];
                String indicator = parts[2];

                LocalTime sessionTime = LocalTime.parse(timeString);
                soonestTime = isNull(soonestTime) ? sessionTime : soonestTime;
                latestTime = sessionTime;

                Stack<LocalTime> sessionStack = userSessionMap.getOrDefault(username, new Stack<>());
                if (!userSessionMap.containsKey(username)) {
                    userSessionMap.put(username, sessionStack);
                }

                if (indicator.equals(START_INDICATOR)) {
                    //session has started for a user, putting it into user-specific stack
                    sessionStack.push(sessionTime);
                } else {
                    //session has ended for a user, determining the session start time either from the stack or the soonest time
                    LocalTime sessionStartTime = !isEmpty(sessionStack) ? sessionStack.pop() : soonestTime;
                    if (isEmpty(sessionStack)) {
                        //removing empty stack will decrease later iteration
                        userSessionMap.remove(username);
                    }
                    //getting exising or a new usage summary for the particular user
                    UsageSummary usageSummary = usageSummaryMap.getOrDefault(username, new UsageSummary());
                    //putting the updated usage summary back to the map
                    usageSummaryMap.put(username, this.processSessionSummary(usageSummary, sessionStartTime, sessionTime));
                }
            }
            //processing sessions which started but never ended
            this.processUnfinishedSessions(userSessionMap, usageSummaryMap, latestTime);

            return usageSummaryMap;
        } catch (Exception ex) {
            throw new AppException(ex.getMessage(), ex);
        }
    }

    private UsageSummary processSessionSummary(UsageSummary usageSummary, LocalTime sessionStartTime, LocalTime sessionEndTime) {
        //calculating the duration of the session, and updates the usage summary
        long seconds = getSessionDuration(sessionStartTime, sessionEndTime);
        usageSummary.setTotalSession(usageSummary.getTotalSession() + 1);
        usageSummary.setTotalTime(usageSummary.getTotalTime() + seconds);
        return usageSummary;
    }


    private void processUnfinishedSessions(Map<String, Stack<LocalTime>> userSessionMap,
                                           Map<String, UsageSummary> usageSummaryMap, LocalTime sessionEndTime) {
        //processing sessions which started but never ended
        if(isEmpty(userSessionMap)) {
            return;
        }
        for (Map.Entry<String, Stack<LocalTime>> entry : userSessionMap.entrySet()) {
            String username = entry.getKey();
            Stack<LocalTime> ongoingSessionStack = entry.getValue();
            for (LocalTime sessionStartTime : ongoingSessionStack) {
                UsageSummary usageSummary = usageSummaryMap.getOrDefault(username, new UsageSummary());
                usageSummaryMap.put(username, this.processSessionSummary(usageSummary,  sessionStartTime, sessionEndTime));
            }
        }
    }
    private long getSessionDuration(LocalTime sessionStartTime, LocalTime sessionEndTime) {
        Duration duration = Duration.between(sessionStartTime, sessionEndTime);
        return duration.getSeconds();
    }
}
