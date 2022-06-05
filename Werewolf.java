public class Werewolf extends parent_Enemy {
    private static int instances;
    
    public Werewolf(int atk, int hp) {
        E_NAME = "Wolf";
        E_ATTACK = atk;
        E_HP = hp;
        E_HPMAX = hp;
    }
    
    public int getInstance() {
        return instances;
    }
    public void setInstance(int instanceCount) {
        instances = instanceCount;
    }
}