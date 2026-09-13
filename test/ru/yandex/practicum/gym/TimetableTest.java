package ru.yandex.practicum.gym;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TimetableTest {

    @Test
    public void shouldAddSessionAndIncreaseCoachSessionCount() {
        Timetable timetable = new Timetable();
        Coach coach = createCoach("Васильев");
        TimeOfDay time = new TimeOfDay(13, 0);
        TrainingSession session = createSession(
                createGroup("Акробатика для детей"), coach, DayOfWeek.MONDAY, time
        );

        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, time
        );
        assertEquals(1, sessions.size());
        assertSame(session, sessions.get(0));
        assertEquals(1, coach.getTotalSessionCount());
    }

    @Test
    public void shouldAddSessionsAtDifferentTimesAndReturnThemInChronologicalOrder() {
        Timetable timetable = new Timetable();
        Coach coach = createCoach("Васильев");
        TrainingSession eveningSession = createSession(
                createGroup("Акробатика для взрослых"), coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0)
        );
        TrainingSession afternoonSession = createSession(
                createGroup("Акробатика для детей"), coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(eveningSession);
        timetable.addNewTrainingSession(afternoonSession);

        assertEquals(
                Arrays.asList(afternoonSession, eveningSession),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)
        );
        assertEquals(2, coach.getTotalSessionCount());
    }

    @Test
    public void shouldAddDifferentSessionsAtTheSameTime() {
        Timetable timetable = new Timetable();
        TimeOfDay time = new TimeOfDay(18, 30);
        TrainingSession firstSession = createSession(
                createGroup("Бокс"), createCoach("Макаров"), DayOfWeek.MONDAY, time
        );
        TrainingSession secondSession = createSession(
                createGroup("ФЛК"), createCoach("Якушев"), DayOfWeek.MONDAY, time
        );

        timetable.addNewTrainingSession(firstSession);
        timetable.addNewTrainingSession(secondSession);

        assertEquals(
                Arrays.asList(firstSession, secondSession),
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time)
        );
    }

    @Test
    public void shouldRejectDuplicateSessionWithoutIncreasingCoachSessionCount() {
        Timetable timetable = new Timetable();
        Group group = createGroup("Йога");
        Coach coach = createCoach("Федосов");
        TimeOfDay time = new TimeOfDay(12, 30);
        TrainingSession firstSession = createSession(group, coach, DayOfWeek.MONDAY, time);
        TrainingSession duplicateSession = createSession(group, coach, DayOfWeek.MONDAY, time);
        timetable.addNewTrainingSession(firstSession);

        try {
            timetable.addNewTrainingSession(duplicateSession);
            fail("Добавление дубликата должно завершаться исключением");
        } catch (IllegalArgumentException exception) {
            assertEquals("Такая сессия уже есть", exception.getMessage());
        }

        assertEquals(
                Arrays.asList(firstSession),
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time)
        );
        assertEquals(1, coach.getTotalSessionCount());
    }

    @Test
    public void shouldReturnEmptyListForDayWithoutSessions() {
        Timetable timetable = new Timetable();
        timetable.addNewTrainingSession(createSession(
                createGroup("Акробатика"), createCoach("Васильев"),
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        ));

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertTrue(sessions.isEmpty());
    }

    @Test
    public void shouldReturnSessionsForDayAndEquivalentTime() {
        Timetable timetable = new Timetable();
        TrainingSession session = createSession(
                createGroup("Акробатика"), createCoach("Васильев"),
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        );
        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        );

        assertEquals(1, sessions.size());
        assertSame(session, sessions.get(0));
    }

    @Test
    public void shouldReturnEmptyListForTimeWithoutSessions() {
        Timetable timetable = new Timetable();
        timetable.addNewTrainingSession(createSession(
                createGroup("Акробатика"), createCoach("Васильев"),
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)
        ));

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0)
        );

        assertTrue(sessions.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForDayAndTimeWithoutSessions() {
        Timetable timetable = new Timetable();

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.SUNDAY, new TimeOfDay(10, 0)
        );

        assertTrue(sessions.isEmpty());
    }

    @Test
    public void shouldReturnEmptyCoachStatisticsForEmptyTimetable() {
        Map<Coach, Integer> statistics = new Timetable().getCountByCoaches();

        assertTrue(statistics.isEmpty());
    }

    @Test
    public void shouldCountSessionsByCoachAndSortByCountThenNameDescending() {
        Timetable timetable = new Timetable();
        Coach mostActiveCoach = createCoach("Alpha");
        Coach zuluCoach = createCoach("Zulu");
        Coach betaCoach = createCoach("Beta");

        timetable.addNewTrainingSession(createSession(
                createGroup("Йога"), mostActiveCoach, DayOfWeek.MONDAY, new TimeOfDay(10, 0)
        ));
        timetable.addNewTrainingSession(createSession(
                createGroup("Бокс"), mostActiveCoach, DayOfWeek.TUESDAY, new TimeOfDay(11, 0)
        ));
        timetable.addNewTrainingSession(createSession(
                createGroup("ФЛК"), zuluCoach, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)
        ));
        timetable.addNewTrainingSession(createSession(
                createGroup("Стретчинг"), betaCoach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)
        ));

        Map<Coach, Integer> statistics = timetable.getCountByCoaches();

        assertEquals(Arrays.asList(mostActiveCoach, zuluCoach, betaCoach),
                new ArrayList<>(statistics.keySet()));
        assertEquals(Arrays.asList(2, 1, 1), new ArrayList<>(statistics.values()));
    }

    private Coach createCoach(String surname) {
        return new Coach(surname, "Имя", "Отчество");
    }

    private Group createGroup(String title) {
        return new Group(title, Age.ADULT, 60);
    }

    private TrainingSession createSession(
        Group group,
        Coach coach,
        DayOfWeek dayOfWeek, TimeOfDay timeOfDay
    ) {
        return new TrainingSession(group, coach, dayOfWeek, timeOfDay);
    }
}
