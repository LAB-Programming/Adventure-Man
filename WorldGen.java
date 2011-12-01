
/**
 * Write a description of class WorldGen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldGen
{
    private AdventureWorld world;
    
    private TerrainGen tgen = new TerrainGen(world);

    public WorldGen(AdventureWorld wrld) {
        world = wrld;
    }
    
    public void genWorld(int cx, int cy) {
        
    }
}