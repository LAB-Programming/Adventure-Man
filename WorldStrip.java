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
    //private WorldSpace[] strip = new WorldSpace[1024];
    //private HashMap<Integer,WorldSpace> strip = new HashMap<Integer,WorldSpace>();
    private LinkedList<WorldSpace> stripAboveWater = new LinkedList<WorldSpace>();
    private LinkedList<WorldSpace> stripBelowWater = new LinkedList<WorldSpace>();
    
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
    
    public void addToTop(Block newBlock){
        stripAboveWater.addLast((new WorldSpace()).setBlock(newBlock));
    }
    
    public void addToBottom(Block newBlock){
        stripBelowWater.addLast((new WorldSpace()).setBlock(newBlock));
    }

    public Block getBlock(long yAboveWater) {
        if(yAboveWater>=0){
            return stripAboveWater.get(yAboveWater).getBlock();
        }else{
            return stripBelowWater.get((yAboveWater-1)*(-1)).getBlock();
        }
    }

    public void setBlock(Block newBlock, long yAboveWater) {
        if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).setBlock(newBlock);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).setBlock(newBlock);
        }
    }

    public void addEntity(Entity newEntity, long yAboveWater) {
        if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).addEntity(newEntity);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).addEntity(newEntity);
        }
    }

    public void addEntities(Collection<Entity> long yAboveWater) {
        if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).addEntities(newEntities);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).addEntities(newEntities);
        }
    }
    
    public HashSet<Entity> getEntities(long yAboveWater) {
        if(yAboveWater>=0){
            return stripAboveWater.get(yAboveWater).getEntities();
        }else{
            return stripBelowWater.get((yAboveWater-1)*(-1)).getEntities();
        }
    }

    public void removeEntity(Entity entity, long yAboveWater) {
        if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).removeEntity(entity);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).removeEntity(entity);
        }
    }
}
