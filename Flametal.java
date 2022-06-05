import java.util.Random;
public class Flametal extends parent_Enemy {
    private static int instances;
    static Random rand = new Random();
    
    public Flametal(int atk, int hp) {
        E_NAME = "Flametal";
        E_ATTACK = atk;
        E_HP = hp;
        E_HPMAX = hp;
    }
    
    public void attackPlayer(Player player, parent_Enemy[] enemies, int enemyCount) {
        switch(rand.nextInt(4)) {
            case 0: // Regular attack
                player.setHP(player.getHP() - E_ATTACK);
                System.out.println(E_NAME+" deals "+E_ATTACK+" damage to hero!");  
                break;
            case 1: // Fire blast
                System.out.println(E_NAME+" spews hot flames! // Hero was burnt for 3 turns!");
                player.burned += 3;
                break;
            case 2: // Burn up
                if(E_HP > 50) {
                    System.out.println(E_NAME+" turned up the heat! // "+E_NAME+" took 50 damage but became stronger!");
                    E_ATTACK += 12;
                    E_HP -= 50;
                } else {
                    System.out.println(E_NAME+" tried to heat itself up, but couldn't!");
                }
                break;
            case 3: // Soulfire
                System.out.println(E_NAME+" expelled a blue wisp! // "+E_NAME+" stole 20 mana from hero!");
                player.setMana(player.getMana() - 20);
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