import java.util.Random;
/**
 * Write a description of class TerrainGen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TerrainGen
{
    private final AdventureWorld world;

    public TerrainGen(AdventureWorld theWorld) {
        world = theWorld;
    }
    
    public void genWorld(WorldStrip[] strips, long cy) {
        for(WorldStrip x : strips) {
            for(long y = cy - AdventureWorld.GEN_RADIUS_H; y < cy + AdventureWorld.GEN_RADIUS_H; y++) {
                if(!x.didGenBlock(y)) {
                    Block newBlock;
                    if(y < 0) {
                        newBlock = new Ground(x.getX(), y);
                    } else if(y == 0) {
                        Random rand = new Random();
                        if(rand.nextInt(WorldStrip.TREE_AVERAGE_INTERVAL) == 0) {
                            newBlock = new Tree(x.getX(), y, rand.nextInt(WorldStrip.TREE_HEIGHT_RANGE) + WorldStrip.TREE_HEIGHT_RANGE, world);
                        } else {
                            newBlock = new Air(x.getX(), y);
                        }
                    } else {
                        newBlock = new Air(x.getX(), y);
                    }
                    x.setBlock(newBlock, y);
                }
            }
        }
    }
}
