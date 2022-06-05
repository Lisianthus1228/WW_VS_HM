import java.util.Random;
public class Mortar extends parent_Enemy {
    private static int instances;
    static Random rand = new Random();
    private int cooldown;
    private int prevHP;
    
    public Mortar(int atk, int hp) {
        E_NAME = "Mortar Shot";
        E_ATTACK = atk;
        E_HP = hp;
        prevHP = hp;
        E_HPMAX = hp;
        cooldown = 0;
    }
    
    public void attackPlayer(Player player, parent_Enemy[] enemies, int enemyCount) {
        statusEffect();
        if(E_HP != prevHP) {
            cooldown = -1; // Stunned
        }
        switch(cooldown) {
            case 0: // Charge
                System.out.println(E_NAME+" is reloading its ammo...");
                cooldown++;
                break;
            case 1: // Charge
                System.out.println(E_NAME+" is aiming its cannon...");
                cooldown++;
                break;
            case 2: // Fire!
                System.out.println(E_NAME+" blasted Hero to pieces and dealt "+E_ATTACK+" damage!");
                player.setHP(player.getHP() - E_ATTACK);
                cooldown = 0;
                break;
            default: // Stunned/Reset
                System.out.println(E_NAME+" was struck! // It has to recalibrate!");
                cooldown = 0;
        }
        prevHP = E_HP;
    }
    
    public int getInstance() {
        return instances;
    }
    public void setInstance(int instanceCount) {
        instances = instanceCount;
    }
}