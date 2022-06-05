import java.util.Random;

public class Magicbox {
    static Random rand = new Random();
    static boolean used = false;
    
    public void useMagicbox(parent_Enemy[] enemies, Player p, int enemyCount) {
        int magicResult = rand.nextInt(3);
        if(used) {
            System.out.println("Magic box has already been used!");
        } else {
            switch(magicResult) {
                case 0: // Magic Staff
                    for(int i=0; i < enemyCount; i++) {
                        if(enemies[i].getHP() > 0) {
                            enemies[i].setHP(enemies[i].getHP() - 550);
                            System.out.println("Magic box was used! // "+enemies[i].getName()+" received 550 damage from a magic staff! "+enemies[i].getName()+" has "+enemies[i].getHP()+" HP left!");
                            break;
                        }
                    }
                    break;
                case 1: // Super Heal
                    p.setHP(p.getHP() + 600);
                    System.out.println("Magic box was used! // Hero was healed for 600 HP, they now have "+p.getHP()+" left!");
                    break;
                case 2: // Caltrops
                    for(int i=0; i < enemyCount; i++) {
                        if(enemies[i].getHP() > 0) {
                            enemies[i].setHP(enemies[i].getHP() - 100);
                        }
                    }
                    System.out.println("Magic box was used! // Caltrops were scattered everywhere! All enemies took 100 damage!");
                    break;
            }
            used = true;
        }
    }
}