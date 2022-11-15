package no.hvl.dat250.assignment.dummydata.questions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Kids {
    private static final String list = "Do you have a best friend?\n" +
            "Do you have any siblings?\n" +
            "Are your brothers and sisters annoying?\n" +
            "Can you count to 1000?\n" +
            "Are you afraid of the dark?\n" +
            "Do you have a favorite superhero?\n" +
            "Do you know your mom’s and dad’s first names?\n" +
            "Do your parents ever embarrass you?\n" +
            "Do you have a favorite dinosaur?\n" +
            "Do you know why the sky is blue?\n" +
            "Can you spell onomatopoeia?\n" +
            "Have you ever lied to your parents?\n" +
            "Do you like ice cream?\n" +
            "Do you like broccoli?\n" +
            "Have you ever won a contest?\n" +
            "Have you ever played on a sports team?\n" +
            "Do you help your parents with chores?\n" +
            "Have you ever been grounded?\n" +
            "Do you have a favorite aunt or uncle?\n" +
            "Have you ever broken a bone?\n" +
            "Have you ever had stitches?\n" +
            "Do you know how to cook?\n" +
            "Do you know how to ride a bike?\n" +
            "Do you know how to swim?\n" +
            "Do you like spiders?\n" +
            "Have you ever been to summer camp?\n" +
            "Do you have an imaginary friend?\n" +
            "Have you ever caught a fish?\n" +
            "Have you ever been to a sleepover?\n" +
            "Have you ever seen a lion up close?\n" +
            "Can you make a dolphin noise?\n" +
            "Have you ever moved to a new city?\n" +
            "Have you ever read a 100 page book?";

    public static List<String> list() {
        return Arrays.stream(list.split("\n")).collect(Collectors.toList());
    }
}
