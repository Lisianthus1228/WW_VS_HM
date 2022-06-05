import java.util.Random;
public class Skeleton extends parent_Enemy {
    private static int instances;
    static Random rand = new Random();
    
    public Skeleton(int atk, int hp) {
        E_NAME = "Skeleton";
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
        int attackChoice = rand.nextInt(2);
        switch(attackChoice) {
            case 0: // Regular attack
                player.setHP(player.getHP() - E_ATTACK);
                System.out.println(E_NAME+" deals "+E_ATTACK+" damage to hero!");
                break;
            case 1: // Bone blast
                int attackDMG = (int) (E_ATTACK*2) + 25;
                player.setHP(player.getHP() - attackDMG);
                System.out.println("RATTLE ME BONES! // "+E_NAME+" deals "+attackDMG+" damage to hero with a giant bone!");
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