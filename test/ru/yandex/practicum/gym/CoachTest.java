package ru.yandex.practicum.gym;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoachTest {

    @Test
    public void shouldIncreaseTotalSessionsCountOnEveryCall() {
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        coach.increaseTotalSessionsCount();
        coach.increaseTotalSessionsCount();

        assertEquals(2, coach.getTotalSessionCount());
    }
}
