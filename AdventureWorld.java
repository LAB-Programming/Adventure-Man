import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
/**
 * Write a description of class AdventureWorld here.
 * 
 * @author gavin yancey
 * @author louis hyde
 * @version -1
 */
public class AdventureWorld
{
    public static final int GEN_RADIUS_H = 15;
    public static final int GEN_RADIUS_W = 10;

    private HashMap<Long,WorldStrip> world = new HashMap<Long,WorldStrip>();

    private AdventureMan game;

    private WorldGen generator = null;

    public AdventureWorld(AdventureMan am) {
        game = am;
    }

    //needs fixing
    /*
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
     */

    public void genWorld(long cx, long cy) {
        if(generator == null) {
            if(this == null) System.out.println("AdventureWorld: WORLD IS NUULLLLLLLL!!!!!!");
            generator = new WorldGen(this);
        }
        generator.genWorld(cx,cy);
    }
    
    public WorldStrip getStrip(long x) {
        return world.get(new Long(x));
    }

    public void setBlock(Block newBlock, long x, long y) {
        WorldStrip curStrip = world.get(new Long(x));
        curStrip.setBlock(newBlock,y);
    }

    public void addEntity(Entity newEntity, long x, long y) {
        world.get(new Long(x)).addEntity(newEntity,y);
    }

    public boolean removeEntity(Entity entity, long x, long y) {
        return world.get(new Long(x)).removeEntity(entity,y);
    }

    /**
     * Gets WorldStrips from the world and puts them into an array
     * NB: USE ONLY FOR WORLDGEN!
     * 
     * @param  start   the x of the first WorldStrip in the array
     * @param  end   the x of the first WorldStrip after the array
     * @return     An array of WorldStrips
     */
    public WorldStrip[] getStrips(long start, long end) {
        WorldStrip strips[] = new WorldStrip[(int)(((start - end)>0)? (start - end): (end - start))];
        int i;
        long x;
        if(start < end) {
            for(x = start, i = 0; x < end; x++, i++) {
                if(!world.containsKey(new Long(x))) {
                    strips[i] = new WorldStrip(x);
                    world.put(new Long(x), strips[i]);
                }else strips[i] = world.get(new Long(x));
            }
        }else{
            for(x = start, i = 0; x > end; x--, i++) {
                if(!world.containsKey(new Long(x))) {
                    strips[i] = new WorldStrip(x);
                    world.put(new Long(x), strips[i]);
                }else strips[i] = world.get(new Long(x));
            }
        }
        return strips;
    }

    public Player getPlayer() {
        return game.getPlayer();
    }
    
    
    public String toString() {
        return world.toString();
    }
}
