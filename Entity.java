
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
        if(!(moveToTop(false).equals(MoveResult.SUCCESS) && moveToGround().equals(MoveResult.SUCCESS))) {
            System.err.println("error fixing posistion");//should put them above ground
            System.err.println(world);
        } else {
            world.addEntity(this,locx,locy);
        }
    }

    public MoveResult move(int i) {
        long storeX = locx;
        long storeY = locy;
        locx += i;
        if(world.getStrip(locx).getBlock(locy) == null) {
            locx = storeX;
            locy = storeY; // yes I know I haven't modified locy yet but just being safe in case I add something before this and forget to add this
            return MoveResult.EDGE_OF_WORLD;
        }
        MoveResult moveToTop = moveToTop(true);
        switch(moveToTop) {
            case SUCCESS:
                break;
            default:
                locx = storeX;
                locy = storeY;
                return moveToTop;
        }
        MoveResult moveToGround = moveToGround();
        switch(moveToGround) {
            case SUCCESS:
                break;
            default:
                locx = storeX;
                locy = storeY;
                return moveToTop;
        }
        world.removeEntity(this,storeX,storeY);
        world.addEntity(this,locx,locy);
        return MoveResult.SUCCESS;
    }
    
    /**
     * If the entity is in a solid block, moves entity up to the nearest nonsolid block
     */
    public MoveResult moveToTop(boolean useMaxMoveHeight){
        WorldStrip curStrip = world.getStrip(locx);
        Block curBlock = curStrip.getBlock(locy);
        if(curBlock == null) {
            return MoveResult.FAILURE;
        }
        if(!curBlock.isSolid()) {
            return MoveResult.SUCCESS;
        }
        long yCheck = locy + 1;
        Block checkBlock = curStrip.getBlock(yCheck);
        while(checkBlock != null && checkBlock.isSolid() && (yCheck - locy < getMaxMoveHeight() || !useMaxMoveHeight)) {
            yCheck += 1;
            checkBlock = curStrip.getBlock(yCheck);
        }
        if(checkBlock == null) return MoveResult.EDGE_OF_WORLD;
        if(checkBlock.isSolid()) return MoveResult.TOO_HIGH;
        world.removeEntity(this,locx,locy);
        locy = yCheck;
        world.addEntity(this,locx,locy);
        return MoveResult.SUCCESS;
    }
    
    /**
     * If the entity is suspended in midair (not on top of a solid block), moves entity down
     */
    public MoveResult moveToGround() {
        WorldStrip curStrip = world.getStrip(locx);
        Block curBlock = curStrip.getBlock(locy);
        if(curBlock == null || curBlock.isSolid()) { //should fail if the curBlock entity is in is solid, moving up gets the highest priority
            return MoveResult.FAILURE;
        }
        long yCheck = locy - 1;
        Block checkBlock = curStrip.getBlock(yCheck);
        while(checkBlock != null && !checkBlock.isSolid()) {
            yCheck -= 1;
            checkBlock = curStrip.getBlock(yCheck);
        }
        if(checkBlock == null) return MoveResult.EDGE_OF_WORLD;
        world.removeEntity(this,locx,locy);
        locy = yCheck+1;
        world.addEntity(this,locx,locy);
        return MoveResult.SUCCESS;
    }
    
    public abstract int getMaxMoveHeight();
    
    protected enum MoveResult {
        SUCCESS, EDGE_OF_WORLD, TOO_HIGH, FAILURE;
    }
}
