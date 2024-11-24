package it.unibs.pajc.risiko;

import java.util.Random;

public class MyMath {
    private static Random random = new Random();
    
    public static int diceRoll(){
            return random.nextInt(6) + 1;
    }

    public static Card generatedCard(){
        return Card.getCard()[random.nextInt(3)];

    }

    public static Achievement generAchievement(){
        return ; //TODO costruire achivement
    }
}
