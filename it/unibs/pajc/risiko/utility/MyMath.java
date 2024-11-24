package it.unibs.pajc.risiko.utility;

import java.util.Random;

import it.unibs.pajc.risiko.Achievement;
import it.unibs.pajc.risiko.Card;

public class MyMath {
    private static Random random = new Random();

    public static int diceRoll() {
        return random.nextInt(6) + 1;
    }

    public static Card generatedCard() {
        return Card.getCard()[random.nextInt(3)];

    }

    public static Achievement generAchievement() {
        return null; // TODO costruire achivement
    }
}
