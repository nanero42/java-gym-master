package ru.yandex.practicum.gym;

import java.util.Objects;

public class Coach {

    private final String surname;
    private final String name;
    private final String middleName;
    private int totalSessionsCount;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.totalSessionsCount = 0;
    }

    @Override
    public String toString() {
        return "Coach [surname=" + surname + ", name=" + name + ", middleName=" + middleName
                + ", totalSessionsCount=" + totalSessionsCount + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(surname, coach.surname)
            && Objects.equals(name, coach.name)
            && Objects.equals(middleName, coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName);
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
