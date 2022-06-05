import java.util.Random;
public class Razzler extends parent_Enemy {
    private static int instances;
    static Random rand = new Random();
    
    public Razzler(int atk, int hp) {
        E_NAME = "Razzler";
        E_ATTACK = atk;
        E_HP = hp;
        E_HPMAX = hp;
    }
    
    public void attackPlayer(Player player, parent_Enemy[] enemies, int enemyCount) {
        statusEffect();
        if(stunned) {
            stunned = false;
            return; // End turn early.
        }
        int diceRoll = rand.nextInt(6) + 1;
        System.out.print(E_NAME+" rolls a "+diceRoll+"! // ");
        switch(diceRoll) {
            case 1: // Dud
                System.out.println("It's a dud!");
                break;
            case 2: // Heal enemies
                System.out.println(E_NAME+" gave an encouraging speech! Enemies were healed by 30 HP!");
                for(int i=0; i < enemyCount; i++) {
                    if(enemies[i] != null && enemies[i].getHP() > 0) {
                        enemies[i].setHP(enemies[i].getHP() + 30);
                    }
                }
                break;
            case 3: // Basic attack
                player.setHP(player.getHP() - E_ATTACK);
                System.out.println(E_NAME+" threw a die at you! Took "+E_ATTACK+" damage!");
                break;
            case 4: // Fireball
                int fireballDMG = (int) (E_ATTACK*2.5);
                player.setHP(player.getHP() - fireballDMG);
                player.burned++;
                burned++;
                System.out.println(E_NAME+" conjured a fireball! The explosion dealt "+fireballDMG+" damage and burnt both of you!");
                break;
            case 5: // Self-Destruct
                player.setHP(player.getHP() - E_HP);
                System.out.println(E_NAME+" blew up! Player took "+E_HP+" damage!");
                E_HP = 0;
                break;
            case 6: // Holy Light
                for(int i=0; i < enemyCount; i++) {
                    if(enemies[i] != null && enemies[i].getHP() <= 0) {
                        enemies[i].setHP(10);
                    }
                }
                System.out.println("All fallen enemies were showered in a bright light and revived!");
                break;
        }
    }
    
    public int getInstance() {
        return instances;
    }
    public void setInstance(int instanceCount) {
        instances = instanceCount;
    }
}