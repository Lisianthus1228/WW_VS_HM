public class parent_Enemy {
    protected String E_NAME;
    protected int E_HP;
    protected int E_HPMAX;
    protected int E_ATTACK;
    boolean stunned;
    boolean glowing;
    int burned = 0;
    
    public String getName() {
        return E_NAME;
    }
    public void setName(String newName) {
        E_NAME = newName;
    }
    
    public void attackPlayer(Player player, parent_Enemy[] enemies, int enemyCount) {
        statusEffect();
        if(stunned) {
            stunned = false;
            return; // End turn early.
        }
        player.setHP(player.getHP() - E_ATTACK);
        System.out.println(E_NAME+" deals "+E_ATTACK+" damage to hero!");
    }
    
    public int getAttack() {
        return E_ATTACK;
    } public void setAttack(int atk) {
        E_ATTACK = atk;
    }
    
    public int getHP() {
        return E_HP;
    } public void setHP(int hp) {
        E_HP = hp;
        if(E_HP > E_HPMAX) {
            E_HP = E_HPMAX;
        }
    }
    public int getMaxHP() {
        return E_HPMAX;
    }
    
    public void statusEffect() {
        if(burned > 0) {
            int burnDMG = (int) (E_HPMAX * 0.1);
            System.out.println(E_NAME+" is burning! // "+E_NAME+" took "+burnDMG+" damage!");
            E_HP -= burnDMG;
            burned--;
        }
        if(stunned) {
            System.out.println(E_NAME+" is stunned! It can't attack!");
        }
    }
    
    public int getInstance() {
        return 0;
    }
    public void setInstance(int instance) {}
}