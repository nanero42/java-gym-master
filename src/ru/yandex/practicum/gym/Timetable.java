package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    public HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        var sessions = timetable.get(dayOfWeek);

        if (sessions == null) {
            var newSessions = new TreeMap<TimeOfDay, List<TrainingSession>>();
            newSessions.put(timeOfDay, new ArrayList<>(List.of(trainingSession)));
            timetable.put(dayOfWeek, newSessions);
            trainingSession.getCoach().increaseTotalSessionsCount();
            return;
        }
        
        List<TrainingSession> sessionList = sessions.get(timeOfDay);
        if (sessionList == null) {
            sessionList = new ArrayList<>(List.of(trainingSession));
            sessions.put(timeOfDay, sessionList);
            trainingSession.getCoach().increaseTotalSessionsCount();
            return;
        }

        for (TrainingSession oldSession : sessionList) {
            if (
                oldSession.getCoach().equals(trainingSession.getCoach())
                && oldSession.getGroup().equals(trainingSession.getGroup())
                && oldSession.getDayOfWeek().equals(trainingSession.getDayOfWeek())
                && oldSession.getTimeOfDay().equals(trainingSession.getTimeOfDay())
            ) {
                throw new IllegalArgumentException("Такая сессия уже есть");
            }
        }
        
        sessionList.add(trainingSession);
        trainingSession.getCoach().increaseTotalSessionsCount();
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessions = timetable.get(dayOfWeek);

        if (sessions == null) {
            return new ArrayList<>();
        }

        Collection<List<TrainingSession>> sessionCollection = sessions.values();
        List<TrainingSession> sessionList = new ArrayList<>();

        for (List<TrainingSession> s : sessionCollection) {
        sessionList.addAll(s);
        }

        return sessionList;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        var sessions = timetable.get(dayOfWeek);

        if (sessions == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessionList = sessions.get(timeOfDay);

        if (sessionList == null) {
            return new ArrayList<>();
        }

        return sessionList;
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Comparator<Coach> totalSessionCountComparator = new Comparator<>() {
            @Override
            public int compare(Coach c1, Coach c2) {
                int res = Integer.compare(c1.getTotalSessionCount(), c2.getTotalSessionCount());
                if (res != 0) {
                    return res;
                }
                String n1 = String.format("%s %s %s", c1.getSurname(), c1.getName(), c1.getMiddleName());
                String n2 = String.format("%s %s %s", c2.getSurname(), c2.getName(), c2.getMiddleName());

                return String.CASE_INSENSITIVE_ORDER.compare(n1, n2);
            }
        };

        Map<Coach, Integer> statistics = new TreeMap<>(totalSessionCountComparator.reversed());

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySessions : timetable.values()) {
            for (List<TrainingSession> sessionList : daySessions.values()) {
                for (TrainingSession ts : sessionList) {
                    Coach coach = ts.getCoach();

                    if (!statistics.containsKey(coach)) {
                        statistics.put(coach, coach.getTotalSessionCount());
                    }
                }
            }
        }

        return statistics;
    }
}
