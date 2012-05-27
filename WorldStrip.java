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
    //private BlockGenerator dBlockGen;
    private HashMap<Long,WorldSpace> strip = new HashMap<Long,WorldSpace>();
    private long x;
    //private LinkedList<WorldSpace> stripAboveWater = new LinkedList<WorldSpace>();
    //private LinkedList<WorldSpace> stripBelowWater = new LinkedList<WorldSpace>();
    
    public WorldStrip(long locx){
        x = locx;
    }
    
    public long getX(){
        return x;
    }
    
    /*
    public void setDefaultBlock(Block dBlock){
        dBlockGet=dbj;
    }
    
    public Block getDefaultBlock(){
        return dBlockGen;
    }*
    
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
    
    /*public void addToTop(Block newBlock){
        stripAboveWater.addLast(new WorldSpace(newBlock));
    }
    
    public void addToBottom(Block newBlock){
        stripBelowWater.addLast(new WorldSpace(newBlock));
    }*/
    
    public boolean didGenBlock(long y){
        return (getBlock(y) != null && getSpace(y).isGenned());
    }
    

    public Block getBlock(long y) {
        /*if(yAboveWater>=0){
            return stripAboveWater.get(yAboveWater).getBlock();
        }else{
            return stripBelowWater.get((yAboveWater-1)*(-1)).getBlock();
        }*/
        WorldSpace curSpace = strip.get(new Long(y));
        return curSpace == null? null: curSpace.getBlock();
    }

    public void setBlock(Block newBlock, long y) {
        /*if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).setBlock(newBlock);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).setBlock(newBlock);
        }*/
        WorldSpace curSpace = strip.get(new Long(y));
        if(curSpace == null) strip.put(new Long(y), new WorldSpace(newBlock));
        else curSpace.setBlock(newBlock);
    }

    public boolean addEntity(Entity newEntity, long y) {
        /*if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).addEntity(newEntity);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).addEntity(newEntity);
        }*/
        WorldSpace curSpace = strip.get(new Long(y));
        if(curSpace == null) return false;/*{
            curSpace = new WorldSpace(defaultBlock);
            curSpace.addEntity(newEntity);
            strip.put(new Long(y), curStrip);
        }*/
        else curSpace.addEntity(newEntity);
        return true;
    }

    public boolean addEntities(Collection<Entity> newEntities, long y) {
        /*if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).addEntities(newEntities);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).addEntities(newEntities);
        }*/
        WorldSpace curSpace = strip.get(new Long(y));
        if(curSpace == null) return false;/*{
            curSpace = new WorldSpace(defaultBlock);
            curSpace.addEntities(newEntities);
            strip.put(new Long(y), curStrip);
        }*/
        else curSpace.addEntities(newEntities);
        return true;
    }
    
    public HashSet<Entity> getEntities(long y) {
        /*if(yAboveWater>=0){
            return stripAboveWater.get(yAboveWater).getEntities();
        }else{
            return stripBelowWater.get((yAboveWater-1)*(-1)).getEntities();
        }*/
        WorldSpace curSpace = strip.get(new Long(y));
        return curSpace == null? null: curSpace.getEntities();
    }

    public boolean removeEntity(Entity entity, long y) {
        /*if(yAboveWater>=0){
            stripAboveWater.get(yAboveWater).removeEntity(entity);
        }else{
            stripBelowWater.get((yAboveWater-1)*(-1)).removeEntity(entity);
        }*/
        WorldSpace curSpace = strip.get(new Long(y));
        if(curSpace != null) return curSpace.removeEntity(entity);
        else return false;
    }
    
    public WorldSpace getSpace(long y) {
        return strip.get(new Long(y));
    }
    
    
    public String toString() {
        return strip.toString();
    }
}
