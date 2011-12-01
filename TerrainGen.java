
/**
 * Write a description of class TerrainGen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TerrainGen
{
    private AdventureWorld world;

    public TerrainGen(AdventureWorld wrld) {
        world = wrld;
    }
    
    public void genWorld(int cx, int cy) {
        WorldStrip[] strips = world.getStrips((cx - world.GEN_RADIUS_W),(cx + world.GEN_RADIUS_W + 1));
    }
}
