
/**
 * Abstract class Entity - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Entity extends WorldItem implements Interactable
{
    protected AdventureWorld world;
    
    public Entity(AdventureWorld wrld, int x, int y) {
        world = wrld;
        locx = x;
        locy = y;
        move(0);//should put them above ground
    }

    public boolean move(int i) {
        //if(world.getStrip(locx).get) {
            System.out.println("Hey Stupid! Fix Entity move!");
            if(true) throw new EntityMoveNeedsToBeFixedException();
            world.removeEntity(this,locx,locy);
            locx += i;
            world.addEntity(this,locx,locy);
            return true;
        //}
        //else return false;
    }
    
}
