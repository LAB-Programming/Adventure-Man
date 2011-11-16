
/**
 * Abstract class Entity - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Entity extends WorldItem implements Interactable
{
    protected AdventureWorld world;
    
    public Entity(AdventureWorld wrld, int x) {
        world = wrld;
        loc = x;
    }

    public boolean move(int i) {
        if(world.world.containsKey(new Integer(loc + i))) {
            world.removeEntity(this,loc);
            loc += i;
            world.addEntity(this,loc);
            return true;
        }
        else return false;
    }
    
}
