import java.util.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleProgram {
    static Random rand = new Random();
    static Scanner scan = new Scanner(System.in);
    static Player hero = new Player(-1,-1,-1);
    static Magicbox magicBox = new Magicbox();
    
    static parent_Enemy[] enemies = new parent_Enemy[5]; // 5 enemies.
    static int userCommand = 0;
    static int enemyCount = 0;
    
    public static void endBattle(int winner) {
        if(winner == 0) { // PLAYER WIN
            System.out.println("Hero has killed its foe! You win!");
        } else {
            System.out.println("The hero has died! You lose!");
        }
        
        // RESET GAME
        System.out.print("\nPlay Again?\n[1] Yes\n[2] No\nINPUT: ");
        int userInput = scan.nextInt();
        while(!(userInput < 3 && userInput > 0)) {
            clrscr();
            System.out.print("INVALID INPUT.");
            System.out.print("\nPlay Again?\n[1] Yes\n[2] No\nINPUT: ");
            userInput = scan.nextInt();
        }
        if(userInput == 1) {
            clrscr();
            main(new String[0]);
        } else {
            System.exit(0);
        }
    }
    
    public static void setStats() {
        System.out.println("ENTER HERO MELEE ATTACK POWER (Enter 0 for default stats): ");
        int heroStrength = scan.nextInt();
        while(heroStrength < 0) {
            clrscr();
            System.out.println("INVALID VALUE.\nENTER HERO MELEE ATTACK POWER: ");
            heroStrength = scan.nextInt();
        }
        if(heroStrength == 0) {
            heroStrength = 120;
        }
        
        System.out.println("ENTER HERO HP: ");
        int heroHealth = scan.nextInt();
        while(heroHealth < 0) {
            clrscr();
            System.out.println("HERO ATTACK: "+heroStrength);
            System.out.println("INVALID VALUE.\nENTER HERO HP: ");
            heroHealth = scan.nextInt();
        }
        if(heroHealth == 0) {
            heroHealth = 1500;
        }
        
        System.out.println("ENTER HERO MANA: ");
        int heroMana = scan.nextInt();
        while(heroMana < 0) {
            clrscr();
            System.out.println("HERO ATTACK: "+heroStrength);
            System.out.println("HERO HEALTH: "+heroHealth);
            System.out.println("INVALID VALUE.\nENTER HERO MANA: ");
            heroStrength = scan.nextInt();
        }
        if(heroMana == 0) {
            heroMana = 400;
        }
        
        hero = new Player(heroStrength, heroHealth, heroMana);
    }

    public static int displayOptions() {
        if(hero.stunned) { // Skip turn.
            System.out.println("CHOOSE ACTION:");
            System.out.println("[X] Do nothing!");
            System.out.print("INPUT: ");
            scan.next();
            return 0;
        }
        System.out.println("CHOOSE ACTION:");
        System.out.println("[1] Attack for " + hero.getAttack() + " damage.");
        System.out.println("[2] Heal for 150 HP.");
        System.out.println("[3] Fireball (50 Mana)");
        System.out.println("[4] Last Resort (150 Mana)");
        System.out.println("[5] Magic box!");
        System.out.println("[6] Run away!");
        System.out.print("INPUT: ");
        int command = scan.nextInt();
        while(!(command < 7 && command > 0)) {
                System.out.print("INVALID COMMAND. INPUT: ");
                command = scan.nextInt();
        }
        return command;
    }
    
    public static void playerAction(int playerCommand) {
        int selection = 0;
        switch(playerCommand) { // PLAYER TURN
                case 0: // SKIP TURN
                    clrscr();
                    System.out.println("Hero missed their turn!");
                    hero.stunned = false;
                    break;
                case 1: // MELEE ATTACK
                    selection = chooseEnemy();
                    clrscr();
                    hero.attackEnemy(enemies[selection]);
                    break;
                case 2: // HEAL
                    clrscr();
                    hero.drinkHPPotion();
                    System.out.println("Hero heals for 150 HP! // Hero's HP is now "+hero.getHP()+" HP!");
                    break;
                case 3: // FIREBALL
                    selection = chooseEnemy();
                    if(hero.getMana() >= 50) {
                        clrscr();
                        hero.fireball(enemies[selection]);
                        System.out.println("Hero shoots a fireball! // "+enemies[selection].getName()+" took 100 damage and now has "+enemies[selection].getHP()+" HP left!");
                    } else {
                        clrscr();
                        System.out.println("Hero tried to cast a fireball, but didn't have enough mana!");
                    }
                    break;
                case 4: // LAST RESORT
                    selection = chooseEnemy();
                    if(hero.getMana() >= 150) {
                        int spellDamage = hero.lastResort(enemies[selection]);
                        clrscr();
                        System.out.println("Hero used their last resort! // "+enemies[selection].getName()+" took "+spellDamage+" damage and now has has "+enemies[selection].getHP()+" HP left!");
                    } else {
                        clrscr();
                        System.out.println("Hero tried to use their last resort, but couldn't!");
                    }
                    break;
                case 5: // MAGIC BOX
                    clrscr();
                    magicBox.useMagicbox(enemies, hero, enemyCount);
                    break;
                case 6: // GIVE UP
                    clrscr();
                    System.out.println("Hero ran away!");
                    System.exit(0);
        }
        hero.statusEffect();
    }
    
    public static void enemyAction(parent_Enemy[] enemies) {
        int pastHP = hero.getHP();
        for(int i=0; i < enemyCount; i++) {
            if(enemies[i].getHP() > 0) {
                enemies[i].attackPlayer(hero, enemies, enemyCount);
            }
        }
        int damageTaken = pastHP - hero.getHP();
        if(damageTaken > 0) {
            System.out.println("Hero took "+damageTaken+" overall damage!\n");
        }
    }
    
    public static void printStats() {
        System.out.println("[PLAYER HP: " + hero.getHP()+"/"+hero.getMaxHP()+"]");
        System.out.println("[PLAYER MANA: " + hero.getMana()+"/"+hero.getMaxMana()+"]\n");
        
        for(int i=0; i < enemyCount; i++) {
            System.out.println("{"+enemies[i].getName()+" HP: " + enemies[i].getHP()+"/"+enemies[i].getMaxHP()+"}");
        }
    }
    
    public static void winnerCheck(Player p, parent_Enemy[] enemies) {
        boolean enemiesDead = true;
        for(int i=0; i < enemyCount; i++) {
            if(enemies[i].getHP() > 0) {
                enemiesDead = false;
            } 
        }
        if(enemiesDead) {
            endBattle(0); // Player wins
        }
        if(hero.getHP() <= 0) {
            endBattle(1); // Player loses
        }
    }
    
    public static int chooseEnemy() {
        if(enemyCount == 1) {
            return 0; // If only one enemy, no need to choose.
        }
        int enemyChoice;
        while(true) {
            try {
                System.out.print("CHOOSE ENEMY (1-"+enemyCount+"): ");
                enemyChoice = scan.nextInt() - 1;
                while(enemies[enemyChoice].getHP() <= 0) {
                    System.out.println("Theres no enemy to strike there!");
                    System.out.print("CHOOSE ENEMY (1-"+enemyCount+"): ");
                    enemyChoice = scan.nextInt() - 1;
                }
                return enemyChoice; // Escape infinite while loop.
            } catch(Exception e) {
                System.out.println("Invalid choice to strike!");
            }
        }
    }
    
    public static void main(String[] args) {
        if(hero.getHP() <= 0) { // Set player stats if dead.
            setStats();
        }
        enemies = new parent_Enemy[5]; // Reset enemy array.
        magicBox.used = false; // Reset magic box.
        System.out.println("WHO DO YOU WISH TO CHALLENGE?");
        System.out.println("[0] RANDOM HORDE");
        System.out.println("[1] A single werewolf");
        System.out.println("[2] A pack of 5 werewolves");
        System.out.println("[3] A skeleton");
        System.out.println("[4] A vampire and its followers");
        System.out.println("[5] A pair of loaded and heavily armed dice");
        System.out.println("[6] A shocking pair of skeletons");
        System.out.print("YOUR CHOICE: ");
        int battleChoice = scan.nextInt();
        int randEnemy = 0;
        
        switch(battleChoice) {
            case 0:
                for(int i=0; i < rand.nextInt(6)+1; i++) {
                    randEnemy = rand.nextInt(6);
                    switch(randEnemy) {
                        case 0: // WEREWOLF
                            enemies[i] = new Werewolf(rand.nextInt(10)+30, rand.nextInt(300)+100);
                            break;
                        case 1:
                            enemies[i] = new Skeleton(rand.nextInt(60)+30, rand.nextInt(500)+200);
                            break;
                        case 2:
                            enemies[i] = new Vampire(rand.nextInt(65)+65, rand.nextInt(600)+400);
                            break;
                        case 3:
                            enemies[i] = new Flametal(rand.nextInt(45)+10, rand.nextInt(480)+100);
                            break;
                        case 4:
                            enemies[i] = new Mortar(rand.nextInt(100)+100, rand.nextInt(400)+200);
                            break;
                        case 5:
                            enemies[i] = new Razzler(rand.nextInt(10)+40, rand.nextInt(300)+300);
                            break;
                        case 6:
                            enemies[i] = new Electronan(rand.nextInt(50)+50, rand.nextInt(200)+120);
                            break;
                    }
                }
                break;
            case 1:
                enemies[0] = new Werewolf(80,1000);
                break;
            case 2:
                enemies[0] = new Werewolf(25,350);
                enemies[1] = new Werewolf(30,300);
                enemies[2] = new Werewolf(45,500);
                enemies[3] = new Werewolf(12,600);
                enemies[4] = new Werewolf(66,90);
                break;
            case 3:
                enemies[0] = new Skeleton(105,1111);
                break;
            case 4:
                enemies[0] = new Skeleton(30,400);
                enemies[1] = new Vampire(77, 777);
                enemies[2] = new Werewolf(20,300);
                break;
            case 5:
                enemies[0] = new Mortar(70,700);
                enemies[1] = new Razzler(60, 600);
                enemies[2] = new Mortar(85, 750);
                enemies[3] = new Razzler(50, 560);
                break;
            case 6:
                enemies[0] = new Skeleton(45,500);
                enemies[1] = new Electronan(80, 770);
                enemies[2] = new Skeleton(42,700);
                break;
            default:
                enemies[0] = new Mortar(300,3000); // Invalid input punishment.
        }
        
        battleNamer(); // Give each enemy unique ID
        clrscr();
        while(true) {
            playGame(hero, enemies);
        }
    }
    
    public static void playGame(Player p, parent_Enemy[] enemies) {
        printStats();
        userCommand = displayOptions();
        playerAction(userCommand);
        enemyAction(enemies);
        winnerCheck(p, enemies);
    }
    
    public static void battleNamer() {
        enemyCount = 0;
        for(int i=0; i < enemies.length; i++) {
            if(enemies[i] != null) {
                enemies[i].setInstance(0);
                enemyCount++;
            }
        }
        String[] enemyAlphabet = new String[] {" A"," B"," C"," D"," E"};
        int pointer = 0;
        for(int i=0; i < enemyCount; i++) {
            enemies[i].setName(enemies[i].getName() + enemyAlphabet[enemies[i].getInstance()]);
            enemies[i].setInstance(enemies[i].getInstance() + 1);
        }
    }
    
    public static void clrscr() { // WIPES CONSOLE, QUALITY OF LIFE
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {}
    }
}