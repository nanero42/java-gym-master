package ru.yandex.practicum.gym;

import java.util.Objects;

public class SessionKey implements  Comparable<SessionKey> {
    private final DayOfWeek day;
    private final TimeOfDay time;

    public SessionKey(DayOfWeek day, TimeOfDay time) {
        this.day = Objects.requireNonNull(day, "day can\'t be null");
        this.time = Objects.requireNonNull(time, "time can\'t be null");
    }

    public DayOfWeek getDay() {
        return day;
    }

    public TimeOfDay getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SessionKey that = (SessionKey) o;
        return day == that.day && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time);
    }

    @Override
    public String toString() {
        return "SessionKey{" +
                "day=" + day +
                ", time=" + time +
                '}';
    }

    @Override
    public int compareTo(SessionKey o) {
        if (day != o.getDay()) return day.compareTo(o.getDay());
        return time.compareTo(o.getTime());
    }
}
