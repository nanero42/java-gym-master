package ru.yandex.practicum.gym;

import java.util.Objects;
import java.util.UUID;

public class Coach {

    private final UUID id;
    private final String surname;
    private final String name;
    private final String middleName;
    private int totalSessionsCount;

    public Coach(String surname, String name, String middleName) {
        this.id = UUID.randomUUID();
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.totalSessionsCount = 0;
    }

    @Override
    public String toString() {
        return "Coach [id=" + id + ", surname=" + surname + ", name=" + name + ", middleName=" + middleName
                + ", totalSessionsCount=" + totalSessionsCount + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return id.equals(coach.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getTotalSessionCount() {
        return totalSessionsCount;
    }

    public void increaseTotalSessionsCount() {
        totalSessionsCount++;
    }
}
