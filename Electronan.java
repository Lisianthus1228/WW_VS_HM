import java.util.Random;
public class Electronan extends parent_Enemy {
    private static int instances;
    static Random rand = new Random();
    
    public Electronan(int atk, int hp) {
        E_NAME = "Electronan";
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
        
        if(!glowing) { // Charge
            System.out.println(E_NAME+" began glowing!");
            glowing = true;
        } else { // Discharge
            int chargeDMG = E_ATTACK + rand.nextInt(30);
            System.out.println(E_NAME+" released its energy! Dealt "+chargeDMG+" damage!");
            glowing = false;
        }
    }
    
    public int getInstance() {
        return instances;
    }
    public void setInstance(int instanceCount) {
        instances = instanceCount;
    }
}