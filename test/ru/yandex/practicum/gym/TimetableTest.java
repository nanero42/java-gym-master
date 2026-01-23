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
        assertEquals(0, session14.size());
    }

    @Test
    public void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        Group group2 = new Group("Кунфу для чайников", Age.ADULT, 60);
        TrainingSession trainingSession2 = new TrainingSession(
                group2,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)
        );

        Group group3 = new Group("Жим штанги до 100кг", Age.CHILD, 60);
        TrainingSession trainingSession3 = new TrainingSession(
                group3,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(15, 0)
        );

        Coach coach2 = new Coach("Ли", "Брюс", "");
        Group group4 = new Group("Карате без правил", Age.ADULT, 60);
        TrainingSession trainingSession4 = new TrainingSession(
                group4,
                coach2,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        Group group5 = new Group("Карате без правил", Age.ADULT, 60);
        TrainingSession trainingSession5 = new TrainingSession(
                group5,
                coach2,
                DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)
        );

        Coach coach3 = new Coach("Чан", "Джеки", "");
        Group group6 = new Group("Карате без правил", Age.ADULT, 60);
        TrainingSession trainingSession6 = new TrainingSession(
                group6,
                coach3,
                DayOfWeek.MONDAY,
                new TimeOfDay(12, 0)
        );

        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);

        timetable.addNewTrainingSession(trainingSession);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);

        timetable.addNewTrainingSession(trainingSession6);

        TreeMap<Coach, Integer> map = timetable.getCountByCoachestCountByCoaches();

        assertEquals(1, (int) map.get(coach3));
        assertEquals(3, (int) map.get(coach));
        assertEquals(2, (int) map.get(coach2));
    }

}
