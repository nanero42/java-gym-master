package ru.yandex.practicum.gym;

import java.util.Objects;
import java.util.UUID;

public class TrainingSession {

    private final UUID id;
    private Group group;
    private Coach coach;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.id = UUID.randomUUID();
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        TrainingSession ts = (TrainingSession) o;
        return id.equals(ts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TrainingSession [group=" + group + ", coach=" + coach + ", dayOfWeek=" + dayOfWeek + ", timeOfDay="
                + timeOfDay + "]";
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }
}
