package person;

import actions.Performance;
import gameWorld.GameNavigation;

import java.io.BufferedReader;
import java.io.IOException;

public class Dealer {

    public void potionSale(Performance player, GameNavigation navigation, BufferedReader reader) throws IOException {
        if (player.getGold() >= 10){
            System.out.println("Сколько зелья вы готовы приобрести?");
            System.out.println(String.format("Вы можете приобрести %d зелья.", player.getGold() / 10));
            int count = Integer.parseInt (reader.readLine());
            if (count > 0) {
                player.setGold(player.getGold() - (count * 10));
                player.setHealth(player.getHealth() + (count * 10));
                System.out.println( String.format("Теперь у вас %d золота и %d здоровья", player.getGold(), player.getHealth()));
            }
        } else {
            System.out.println("У вас совсем нет золота. Золото можно добыть в темном лесу.");
        }

        navigation.printNavigation();
        navigation.resultChoice(reader.readLine());
    }

}
