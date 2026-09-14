package ru.yandex.practicum.gym;

import java.util.Objects;
import java.util.UUID;

public class Group {

    private final UUID id;
    private String title;
    private Age age;
    private int duration;

    public Group(String title, Age age, int duration) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Group g = (Group) o;
        return g.id.equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Group [title=" + title + ", age=" + age + ", duration=" + duration + "]";
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }
}
