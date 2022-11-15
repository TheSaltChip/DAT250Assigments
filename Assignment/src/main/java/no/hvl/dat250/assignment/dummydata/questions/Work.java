package no.hvl.dat250.assignment.dummydata.questions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Work {
    private static final String list = "Do you feel like you belong at work?\n" +
            "Is this your first job?\n" +
            "Is this the job you dreamed of when you were a child?\n" +
            "Do you enjoy your job?\n" +
            "Do you ever look up your coworkers on social media?\n" +
            "Have you ever stolen a coworker’s lunch from the break room fridge?\n" +
            "Do you like to work while listening to music?\n" +
            "Do you enjoy working on group projects?\n" +
            "If you won the lottery would you still continue working?\n" +
            "Have you ever had an awful interview?\n" +
            "Have you ever been a manager?\n" +
            "Would you work in a lower paying profession if it meant you would be happier?\n" +
            "Do you know how to send a fax?\n" +
            "Do you ever procrastinate during work?\n" +
            "Have you ever played a prank on your boss?\n" +
            "Would you hang out with your coworkers outside of work?\n" +
            "Do you like to work early in the morning?\n" +
            "Do you ever work past midnight?\n" +
            "Do you ever check emails off the clock?\n" +
            "Have you ever quit a job without giving two weeks notice?\n" +
            "Have you ever worked for a family member?\n" +
            "Do you like working with data?\n" +
            "Do you prefer creative projects?\n" +
            "Do you prefer to get guidelines rather than specific instructions?\n" +
            "Do you like working by yourself?\n" +
            "Do you keep your desk clean?\n" +
            "Are you still friends with past coworkers?\n" +
            "Do you regularly achieve inbox zero?\n" +
            "Are you in charge of any big projects?\n" +
            "Do you enjoy being a leader?\n" +
            "Do you work well under pressure?\n" +
            "Do you usually meet deadlines?\n" +
            "Have you ever worked in a different industry?\n" +
            "Do you enjoy working directly with clients?\n" +
            "Have you ever taken an unflattering employee ID picture?\n" +
            "Have you ever accidentally hit “reply all” on an email?\n" +
            "If there is a sheet cake up for grabs in the office kitchen, do you take a piece?\n" +
            "Do you like working from home?";

    public static List<String> list() {
        return Arrays.stream(list.split("\n")).collect(Collectors.toList());
    }
}
