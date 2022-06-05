import java.util.Random;
public class Player {
    private static Random rand = new Random();
    private int P_MELEE_ATK;
    private int P_HP;
    private int P_HP_MAX;
    private int P_MANA;
    private int P_MANAMAX;
    boolean stunned;
    int burned = 0;
    
    public Player(int atk, int hp, int mana) {
        P_MELEE_ATK = atk;
        P_HP = hp;
        P_HP_MAX = hp;
        P_MANA = mana;
        P_MANAMAX = mana;
    }
    
    public void statusEffect() {
        if(burned > 0) {
            int burnDMG = rand.nextInt(30)+20; // 20-50 damage
            System.out.println("Hero is burning! // Hero took "+burnDMG+" damage!");
            P_HP -= burnDMG;
            burned--;
        }
    }
    
    public void attackEnemy(parent_Enemy enemy) {
        enemy.setHP(enemy.getHP() - P_MELEE_ATK);
        if(enemy.glowing) { // Reflect attack
            System.out.println(enemy.getName()+"'s glowing aura reflected the attack!");
            P_HP -= P_MELEE_ATK;
            System.out.println("Hero took "+P_MELEE_ATK+" reflected damage!");
        } else {
            System.out.println("Hero deals "+P_MELEE_ATK+" damage to "+enemy.getName()+"! // "+enemy.getName()+" now has "+enemy.getHP()+" HP left!");
        }
    }
    
    public void drinkHPPotion() {
        P_HP += 150;
        if(P_HP > P_HP_MAX) {
            P_HP = P_HP_MAX;
        }
    }
    
    public void fireball(parent_Enemy enemy) {
        if(P_MANA >= 50) {
            enemy.setHP(enemy.getHP() - 150);
            P_MANA -= 50;
            if(rand.nextInt(100) <= 20) {
                enemy.burned += 2;
            }
        }
    }
    
    public int lastResort(parent_Enemy enemy) {
        if(P_MANA >= 150) {
            int spellDMG = 600 - P_HP;
            if(spellDMG < 1) {
                spellDMG = 1;
            } else if(spellDMG > 500) {
                enemy.stunned = true;
            }
            enemy.setHP(enemy.getHP() - spellDMG); // More damage on low HP
            P_MANA -= 150;
            return spellDMG;
        }
        return 0;
    }
    
    public int getAttack() {
        return P_MELEE_ATK;
    } public void setAttack(int atk) {
        P_MELEE_ATK = atk;
    }
    
    public int getHP() {
        return P_HP;
    } public void setHP(int hp) {
        P_HP = hp;
        if(P_HP > P_HP_MAX) {
            P_HP = P_HP_MAX;
        }
    } public int getMaxHP() {
        return P_HP_MAX;
    } public void setMaxHP(int maxHP) {
        P_HP_MAX = maxHP;
    } 
    
    public int getMana() {
        return P_MANA;
    } public void setMana(int mana) {
        P_MANA = mana;
    }
    public int getMaxMana() {
        return P_MANAMAX;
    }
}