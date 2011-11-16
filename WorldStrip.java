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
    private Block block;

    private HashSet<Entity> entities;

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

    /*//Doesnt work with current entity equals code
    public containsEntity(Entity entity) {
    return entities.contains(entitiy);
    }

    public getEntity(Entity entity) {

    }
     */

    public HashSet<Entity> getEntities() {
        return entities;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
