
/**
 * Abstract class Entity - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Entity extends WorldItem implements Interactable
{
    protected AdventureWorld world;
    
    public Entity(AdventureWorld wrld, long x, long y) {
        world = wrld;
        locx = x;
        locy = y;
        if(!moveToTop()) System.err.println("error fixing posistion");//should put them above ground
    }

    public MoveResult move(int i) {
        
        world.removeEntity(this,locx,locy);
        locx += i;
        world.addEntity(this,locx,locy);
        return MoveResult.SUCCESS;
    }
    
    public boolean moveToTop(){
        WorldStrip curStrip = world.getStrip(locx);
        Block curBlock = curStrip.getBlock(locy);
        if(curBlock == null) {
            return false;
        }
        final int shift;
        if(curBlock.isSolid()) {
            shift = 1;
        } else {
            shift = -1;
        }
        long yCheck = locy + shift;
        Block checkBlock = curStrip.getBlock(yCheck);
        while(checkBlock != null && checkBlock.isSolid() != shift < 0) {
            yCheck += shift;
            checkBlock = curStrip.getBlock(yCheck);
        }
        if(checkBlock == null) return false;
        world.removeEntity(this,locx,locy);
        locy = yCheck;
        world.addEntity(this,locx,locy);
        return true;
    }
    
    public abstract int getMaxMoveHeight();
    
    protected enum MoveResult {
        SUCCESS, EDGE_OF_WORLD, TOO_HIGH, FAILURE;
    }
}
