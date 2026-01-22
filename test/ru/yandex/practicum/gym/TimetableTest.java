package ru.yandex.practicum.gym;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за вторник не вернулось занятий
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeSet<TrainingSession> thursdaySessions = new TreeSet<>(
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY)
        );
        assertEquals(2, thursdaySessions.size());

        TrainingSession first = thursdaySessions.getFirst();
        int firstHours = first.getTimeOfDay().getHours();
        int firstMinutes = first.getTimeOfDay().getMinutes();

        TrainingSession second = thursdaySessions.getLast();
        int secondHours = second.getTimeOfDay().getHours();
        int secondMinutes = second.getTimeOfDay().getMinutes();

        assertEquals(13,firstHours + firstMinutes);
        assertEquals(20,secondHours + secondMinutes);

        // Проверить, что за вторник не вернулось занятий
        Set<TrainingSession> tuesdaySessions = new TreeSet<>(
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY)
        );
        assertEquals(0,tuesdaySessions.size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник в 13:00 вернулось одно занятие
        Set<TrainingSession> session13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );
        assertEquals(1,session13.size());

        // Проверить, что за понедельник в 14:00 не вернулось занятий
        Set<TrainingSession> session14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)
        );
        assertNull(session14);
    }

}
