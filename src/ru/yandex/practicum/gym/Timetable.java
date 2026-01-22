package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private final HashMap<DayOfWeek, HashSet<TrainingSession>> sessionsByDay = new HashMap<>() {{
        put(DayOfWeek.MONDAY, new HashSet<>());
        put(DayOfWeek.TUESDAY, new HashSet<>());
        put(DayOfWeek.WEDNESDAY, new HashSet<>());
        put(DayOfWeek.THURSDAY, new HashSet<>());
        put(DayOfWeek.FRIDAY, new HashSet<>());
        put(DayOfWeek.SATURDAY, new HashSet<>());
        put(DayOfWeek.SUNDAY, new HashSet<>());
    }};
    private final HashMap<SessionKey, HashSet<TrainingSession>> sessionsByDayAndTime = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        sessionsByDay.get(trainingSession.getDayOfWeek()).add(trainingSession);

        SessionKey key = new SessionKey(
            trainingSession.getDayOfWeek(),
            trainingSession.getTimeOfDay()
        );
        
        Set<TrainingSession> list = sessionsByDayAndTime.get(key);
        if (list == null) {
            sessionsByDayAndTime.put(key, new HashSet<>(){{ add(trainingSession); }});
        } else {
            list.add(trainingSession);
        }
    }

    // О(1)
    public Set<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return sessionsByDay.get(dayOfWeek);
    }

    // О(1)
    public Set<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay
    ) {
        return sessionsByDayAndTime.get(
                new SessionKey(
                        dayOfWeek,
                        timeOfDay
                )
            );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(sessionsByDay, timetable.sessionsByDay)
                && Objects.equals(sessionsByDayAndTime, timetable.sessionsByDayAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionsByDay, sessionsByDayAndTime);
    }
}
