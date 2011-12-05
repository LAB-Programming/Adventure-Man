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
    public static final int GEN_RADIUS_H = 15;
    public static final int GEN_RADIUS_W = 10;

    HashMap<Integer,WorldStrip> world = new HashMap<Integer,WorldStrip>();

    private AdventureMan game;

    private WorldGen generator = new WorldGen(this);

    public AdventureWorld(AdventureMan am) {
        genWorld(0,0);
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

    public void genWorld(int cx, int cy) {
        generator.genWorld(cx,cy);
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

    /**
     * Gets WorldStrips from the world and puts them into an array
     * NB: USE ONLY FOR WORLDGEN!
     * 
     * @param  start   the x of the first WorldStrip in the array
     * @param  end   the x of the first WorldStrip after the array
     * @return     An array of WorldStrips
     */
    public WorldStrip[] getStrips(int start, int end) {
        WorldStrip strips[] = new WorldStrip[Math.abs(start - end)];
        if(start < end) {
            for(int x = start, i = 0; x < end; x++, i++) {
                if(!world.containsKey(new Integer(x))) {
                    strips[i] = new WorldStrip(x);
                    world.put(new Integer(x), strips[i]);
                }else strips[i] = world.get(new Integer(x));
            }
        }else{
            for(int x = start, i = 0; x > end; x--, i++) {
                if(!world.containsKey(new Integer(x))) {
                    strips[i] = new WorldStrip(x);
                    world.put(new Integer(x), strips[i]);
                }else strips[i] = world.get(new Integer(x));
            }
        }
        return strips;
    }

    public Player getPlayer() {
        return game.getPlayer();
    }
}
