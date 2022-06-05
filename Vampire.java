import java.util.Random;
public class Vampire extends parent_Enemy {
    private static int instances;
    static Random rand = new Random();
    
    public Vampire(int atk, int hp) {
        E_NAME = "Vampire";
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
        int mesmeriseChance = rand.nextInt(100);
        player.setHP(player.getHP() - E_ATTACK);
        System.out.println(E_NAME+" deals "+E_ATTACK+" damage to hero!");
                
       if(mesmeriseChance <= 13) {
           mesmerise(player);
       }
    }
    
    public void mesmerise(Player p) {
        System.out.println(E_NAME+" mesmerised the player! // They will miss their next turn!");
        p.stunned = true;
    }
    
    public int getInstance() {
        return instances;
    }
    public void setInstance(int instanceCount) {
        instances = instanceCount;
    }
}