package no.hvl.dat250.assignment.dummydata.questions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tricky {

    private static final String list = "Would you betray a friend for a million dollars?\n" +
            "Would you sacrifice a loved one to save a dozen strangers?\n" +
            "If given the opportunity, would you live your entire life over?\n" +
            "Do you believe in an afterlife?\n" +
            "If you could know what other people really thought of you, would you want to know?\n" +
            "If you could find out every person who ever had a crush on you, would you?\n" +
            "If a stranger told you they knew a dark secret about your parents, would you want to find out what it was?\n" +
            "Would you risk your life to save a stranger?\n" +
            "Would you take the blame for a major crime to save a best friend’s life?\n" +
            "Do you think justice really exists?\n" +
            "If you were adopted, would you want to meet your biological parents?\n" +
            "Would you fake your own death to save your family?\n" +
            "Would you want to be a millionaire if it meant you would never find true love?\n" +
            "If you could find out how you were going to die, would you want to know?\n" +
            "If you could have certain memories erased, would you do it?\n" +
            "If a stranger offered to give your family 1 million dollars if you go to jail for 10 years, would you do it?\n" +
            "Would you ever start a romantic relationship if you knew for sure you were bound to part ways in three years?\n" +
            "If an authority figure accused one of your loved ones of doing something terrible, would you believe them outright?\n" +
            "Would you accept your dream job if one of the conditions was not communicating with your family and friends for one year?\n" +
            "If you were appointed to a committee that decided who to grant organ transplants, would you have a hard time deciding who to help?\n" +
            "If you could bring a loved one back to life but they would have no memory of you, would you do it?\n" +
            "If you found out the world was ending in 30 minutes, do you know what you would do in that half hour?\n" +
            "If everything were to end tomorrow, would you be happy with how your life turned out?\n" +
            "Do you think you will have any regrets when you’re 90?\n" +
            "If you could gain magical powers but lose one of your six senses, would you do it?";

    public static List<String> list() {
        return Arrays.stream(list.split("\n")).collect(Collectors.toList());
    }
}
