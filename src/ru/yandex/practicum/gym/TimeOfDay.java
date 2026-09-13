package ru.yandex.practicum.gym;

import java.util.Objects;
import java.util.UUID;

public class TimeOfDay implements Comparable<TimeOfDay> {

    private final UUID id;
    private int hours;
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.id = UUID.randomUUID();
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public int compareTo(TimeOfDay o) {
        if (hours != o.hours) return hours - o.hours;
        return minutes - o.minutes;
    }

    @Override
    public String toString() {
        return "TimeOfDay [id=" + id + ", hours=" + hours + ", minutes=" + minutes + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        TimeOfDay t = (TimeOfDay) o;
        return t.id.equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
}
