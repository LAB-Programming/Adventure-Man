import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;
/**
 * Write a description of class WorldStrip here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldStrip
{
    //Should air be a block or just make Block be null?
    //World Height is fixed maybe I want an ordinary Array?
    private WorldSpace[] strip2 = new WorldSpace[1024];
    private HashMap<Integer,WorldSpace> strip = new HashMap<Integer,WorldSpace>();
    /*
    public WorldStrip(Block startBlock) {
        block = startBlock;
        entities = new HashSet<Entity>();
    }

    public WorldStrip(Block startBlock, HashSet<Entity> startEntities) {
        block = startBlock;
        entities = startEntities;
    }

    public WorldStrip(Block startBlock, Entity startEntity) {
        block = startBlock;
        entities = new HashSet<Entity>();
        entities.add(startEntity);
    }
    */

    public Block getBlock(int y) {
        return strip.get(new Integer(y)).getBlock();
    }

    public void setBlock(Block newBlock, int y) {
        strip.get(new Integer(y)).setBlock(newBlock);
    }

    public void addEntity(Entity newEntity, int y) {
        strip.get(new Integer(y)).addEntity(newEntity);
    }

    public void addEntities(Collection<Entity> newEntities, int y) {
        strip.get(new Integer(y)).addEntities(newEntities);
    }
    
    public HashSet<Entity> getEntities(int y) {
        return strip.get(new Integer(y)).getEntities;
    }

    public void removeEntity(Entity entity, int y) {
        strip.get(new Integer(y)).removeEntity(entity);
    }
}
