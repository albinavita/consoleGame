package gameWorld;

import java.io.IOException;

public class GameWorld {

    public static void main(String[] args) {
        GameNavigation navigation = new GameNavigation();
        try {
            navigation.game();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
