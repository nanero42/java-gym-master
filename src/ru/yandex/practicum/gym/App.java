package ru.yandex.practicum.gym;

public class App {
    public static void main(String[] args) {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Федосов", "Александр", "Юрьевич");
        Coach coach2 = new Coach("Макаров", "Николай", "Евгеньевич");
        Coach coach3 = new Coach("Якушев", "Егор", "Артемович");

        Group g1 = new Group("Йога", Age.ADULT, 60);
        Group g2 = new Group("Бокс", Age.ADULT, 60);
        Group g3 = new Group("ФЛК", Age.CHILD, 30);

        TimeOfDay time1 = new TimeOfDay(12, 30);
        TimeOfDay time2 = new TimeOfDay(13, 00);
        TimeOfDay time3 = new TimeOfDay(18, 30);

        TrainingSession train5 = new TrainingSession(g3, coach2, DayOfWeek.MONDAY, time2);
        TrainingSession train1 = new TrainingSession(g1, coach1, DayOfWeek.MONDAY, time1);
        TrainingSession train2 = new TrainingSession(g2, coach2, DayOfWeek.MONDAY, time3);
        TrainingSession train3 = new TrainingSession(g3, coach3, DayOfWeek.MONDAY, time3);
        TrainingSession train4 = new TrainingSession(g3, coach3, DayOfWeek.MONDAY, time1);

        timetable.addNewTrainingSession(train2);
        timetable.addNewTrainingSession(train5);
        timetable.addNewTrainingSession(train3);
        timetable.addNewTrainingSession(train1);
        timetable.addNewTrainingSession(train4);

        System.out.println(timetable.getCountByCoaches());
    }
}
