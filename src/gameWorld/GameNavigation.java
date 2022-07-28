package gameWorld;

import actions.Battle;
import actions.IWinFight;
import actions.Performance;
import person.Dealer;
import person.Goblin;
import person.Player;
import person.Skeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameNavigation {

    private BufferedReader reader = null;
    private Battle battle = null;
    private Performance player;
    private Performance monster;
    private Dealer dealer;
    private int countLevel = 0;

    public void printNavigation(){
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    public void resultChoice(String choice) throws IOException {
        switch (choice) {
            case "1": {
                dealer.potionSale(player, this, reader);
            }
            break;
            case "2":
                System.out.println("Бой");
                playWinner();
                break;
            case "3":
               goOutGame();
                break;
            case "да":
               resultChoice("2");
                break;
            case "нет":
                printNavigation();
                resultChoice(reader.readLine());
                break;
            default:
                System.out.println("Введены непонятные символы.\nПовторите ответ.");
                resultChoice(reader.readLine());
                break;
        }
    }
    /** Если герой убит или выбрана категория выход,
     *  то игра заканчивается
     *  */
    private void goOutGame()  {
        System.out.println("Игра окончена!");
        System.exit(1);
    }

    private static Performance createMonster() {

        int random = (int) (Math.random() * 10);

        if (random % 2 == 0) return new Goblin(
                "Гоблин",
                50,
                50,
                10,
                100,
                10
        );
        else return new Skeleton(
                "Скелет",
                25,
                20,
                20,
                100,
                10
        );
    }

    /**если герой победил, предлагаем продолжить путь*/
    private void playWinner() throws IOException {
        battle.fight(monster, player, new IWinFight() {
            @Override
            public void winPlayer() throws IOException {
                newLevel();
                System.out.println(String.format("%s победил! Теперь у вас %d опыта, %d ловкости, %d золота, %d сила, а также осталось %d едениц здоровья.",
                        player.getName(), player.getExperience(), player.getSkill(), player.getGold(), player.getStrength(), player.getHealth()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                resultChoice(reader.readLine());
            }

            @Override
            public void winMonstr() {
                        goOutGame();
                    }
        });
    }

    /**После победы над монстром добавляется уровень*/
    private void newLevel(){
        System.out.println( String.format("Поздравляю! Вы перешли на %d уровень.\nВы дополнительно получаете: 10 здоровья", ++countLevel));
        player.setHealth(player.getHealth() + 10);
    }

    public void game() throws IOException {
        battle = new Battle();
        System.out.println("Введите имя персонажа:");
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            player = new Player(reader.readLine(), 50, 0, 20, 0, 20);
            dealer = new Dealer();
            monster = createMonster();
            printNavigation();
            resultChoice(reader.readLine());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
