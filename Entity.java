
/**
 * Abstract class Entity - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Entity extends WorldItem implements Interactable, Important
{
    protected AdventureWorld world;
    
    
    public Entity(AdventureWorld wrld, int x) {
        world = wrld;
        loc = x;
    }

    public boolean move(int i) {
        if(world.world.containsKey(new Integer(loc + i))) {
            loc += i;
            return true;
        }
        else return false;
    }
    
}
