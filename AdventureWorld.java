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
    public Player player=null;
    HashMap<Integer,WorldStrip> world = new HashMap<Integer,WorldStrip>();
    public AdventureWorld() {
        for(int x = -10; x < 11; x++) {
            WorldStrip block = new WorldStrip(getBlock(x));
            world.put(new Integer(x),block);
        }
    }
    
    private Block getBlock(int x) {
        Random rand = new Random();
        int r = rand.nextInt(5);
        if(r==0) {
            int h = (rand.nextInt(3)+1);
            return new Tree(x,h,this);
        }else{
            return new Ground(x);
        }
    }
    
    public void setBlock(int x, Block newBlock) {
        WorldStrip curStrip = world.get(new Integer(x));
        curStrip.setBlock(newBlock);
    }
    
    public void addEntity(Entity newEntity, int x) {
        world.get(new Integer(x)).addEntity(newEntity);
    }
}
