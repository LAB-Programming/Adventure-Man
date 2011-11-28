import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
/**
 * Write a description of class AdventureWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AdventureWorld
{
    HashMap<Integer,WorldStrip> world = new HashMap<Integer,WorldStrip>();
    
    private AdventureMan game;
    
    public AdventureWorld(AdventureMan am) {
        genWorld(0);
        game = am;
    }
    
    //needs fixing
    private WorldStrip getStrip(int x) {
        Random rand = new Random();
        int r = rand.nextInt(5);
        if(r==0) {
            int h = (rand.nextInt(3)+1);
            return new WorldStrip(new Tree(x,512,h,this));
        }else{
            return new Ground(x,512);
        }
    }
    
    public void genWorld(int center) {
        for(int x = -10; x < 11; x++) {
            if(!world.containsKey(new Integer(center + x))) {
                WorldStrip strip = getStrip(center + x);
                world.put(new Integer(center + x),block);
            }
        }
    }
    
    public void setBlock(Block newBlock, int x, int y) {
        WorldStrip curStrip = world.get(new Integer(x));
        curStrip.setBlock(newBlock,y);
    }
    
    public void addEntity(Entity newEntity, int x, int y) {
        world.get(new Integer(x)).addEntity(newEntity,y);
    }
    
    public void removeEntity(Entity entity, int x, int y) {
        world.get(new Integer(x)).removeEntity(entity,y);
    }
    
    public Player getPlayer() {
        return game.getPlayer();
    }
}
