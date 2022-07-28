package actions;

import person.Player;

import java.io.IOException;

/**Логика битвы*/
public class Battle {

    private boolean endFight = false;
    private boolean winPlayer = false;
    private Runnable runnableFight;
    private Thread threadFight;

    public void fight(Performance monster, Performance player, IWinFight winFight){
      //  В рамках боя игрок и монстр будут поочередно наносить удары.
        runnableFight = () ->{
            int turn = 1;

            while (!endFight) {
                System.out.println("Ход " + turn);
                if (turn % 2 == 0) {
                    try {
                        endFight = makeHit(monster, player, winFight);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        endFight = makeHit(player, monster, winFight);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                turn++;
            }
        };
        if(threadFight == null){
            start();
        } else {
            runnableFight.run();
        }
    }

    private boolean makeHit(Performance defender, Performance attacker, IWinFight winFight) throws IOException {
        int hit = attacker.attack();
        int health = defender.getHealth() - hit;
        if (hit != 0) {
            System.out.println(String.format("%s Нанес удар в %d единиц!", attacker.getName(), hit));
            System.out.println(String.format("У %s осталось %d единиц здоровья...", defender.getName(), health));
        } else {
            System.out.println(String.format("%s промахнулся!", attacker.getName()));
        }

        if (health <= 0 && defender instanceof Player) {
            System.out.println(String.format("%s пал в бою...", defender.getName()));
            winFight.winMonstr();
            return true;
        } else if(health <= 0) {
            //Если здоровья больше нет и защищающийся – это монстр, то мы забираем от монстра его опыт и золото
            System.out.println(String.format("Враг повержен! Вы получаете %d опыт и %d золота", defender.getExperience(), defender.getGold()));
            attacker.setExperience(attacker.getExperience() + defender.getExperience());
            attacker.setGold(attacker.getGold() + defender.getGold());
            winFight.winPlayer();
            return true;
        } else {
            //если защищающийся не повержен, то мы устанавливаем ему новый уровень здоровья
            defender.setHealth(health);
            return false;
        }
    }

    public void start(){
        threadFight = new Thread(runnableFight);
        threadFight.start();
    }

}
