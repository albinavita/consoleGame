package actions;

public class Performance {

    private String name;
    /**здоровье*/
    private int health;
    private int gold;
    /**ловкость*/
    private int skill;
    /**опыт*/
    private int experience;
    /**сила*/
    private int strength;


    public Performance(String name, int health, int gold, int skill, int experience, int strength) {
        this.name = name;
        this.health = health;
        this.gold = gold;
        this.skill = skill;
        this.experience = experience;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getSkill() {
        return skill;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**атака*/
    public int attack(){
        int random = (int) (Math.random() * 100);
        if( (skill * 3) > random ) {
            return strength;
        }
        return 0;
    }


}
