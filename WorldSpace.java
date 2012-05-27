import java.util.HashSet;
import java.util.Collection;
/**
 * Write a description of class WorldSpace here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldSpace
{
    private Block block;

    private HashSet<Entity> entities;
    
    private boolean gen = false;

    public WorldSpace(Block startBlock) {
        block = startBlock;
        entities = new HashSet<Entity>();
    }

    public WorldSpace(Block startBlock, HashSet<Entity> startEntities) {
        block = startBlock;
        entities = startEntities;
    }

    public WorldSpace(Block startBlock, Entity startEntity) {
        block = startBlock;
        entities = new HashSet<Entity>();
        entities.add(startEntity);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block newBlock) {
        block = newBlock;
    }

    public void addEntity(Entity newEntity) {
        entities.add(newEntity);
    }

    public void addEntities(Collection<Entity> newEntities) {
        entities.addAll(newEntities);
    }

    public HashSet<Entity> getEntities() {
        return entities;
    }

    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    public boolean isGenned() {
        return gen;
    }
    
    
    public String toString() {
        return "Block "+block.getClass().getName()+"and " +entities.size()+ " Entities";
    }
}
