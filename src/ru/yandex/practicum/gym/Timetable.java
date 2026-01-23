package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, TreeSet<TrainingSession>>> weekAndDayTable = new HashMap<>() {{
        put(DayOfWeek.MONDAY, new TreeMap<>());
        put(DayOfWeek.TUESDAY, new TreeMap<>());
        put(DayOfWeek.WEDNESDAY, new TreeMap<>());
        put(DayOfWeek.THURSDAY, new TreeMap<>());
        put(DayOfWeek.FRIDAY, new TreeMap<>());
        put(DayOfWeek.SATURDAY, new TreeMap<>());
        put(DayOfWeek.SUNDAY, new TreeMap<>());
    }};
    private final TreeMap<DayOfWeek, TreeSet<TrainingSession>> weekTable = new TreeMap<>();

    private void addSessionToWeekAndDayTable(TrainingSession s) {
        var map = weekAndDayTable.get(s.getDayOfWeek());
        var list = map.get(s.getTimeOfDay());
    
        if (list == null) {
            map.put(s.getTimeOfDay(), new TreeSet<>(){{ add(s); }});
        } else {
            list.add(s);
        }
    }

    private void addSessionToWeekTable(TrainingSession s) {
        var timeList = weekTable.putIfAbsent(
            s.getDayOfWeek(),
            new TreeSet<>(){{ add(s); }}
        );
        if (timeList != null) timeList.add(s);
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        this.addSessionToWeekAndDayTable(trainingSession);
        this.addSessionToWeekTable(trainingSession);
    }

    public TreeSet<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        var set = weekTable.get(dayOfWeek);
        return set == null ? new TreeSet<>() : set;
    }

    public TreeSet<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        var set = weekAndDayTable.get(dayOfWeek).get(timeOfDay);
        return set == null ? new TreeSet<>() : set;
    }
}
