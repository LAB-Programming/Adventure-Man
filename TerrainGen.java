import java.util.Random;
/**
 * Write a description of class TerrainGen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TerrainGen
{
    private AdventureWorld world;
    
    private static final int HILL_MAX_H = 12;
    private static final int VALLEY_MIN_H = 12;

    
    /**
     * 
     */
    public TerrainGen(AdventureWorld wrld) {
        world = wrld;
    }
    
    
    /**
     * generate a square of the world
     */
    public void genWorld(WorldStrip[] strips, long cy) {
        
        /*int[] heights = new int[strips.length];
        for(int i = 0; i < heights.length; i++)
            heights[i] = (new Random()).nextInt(HILL_MAX_H + VALLEY_MIN_H + 1);
        for(int x = 0; x < strips.length; x++) {
            
            //for(int y = cy - world.GEN_RADIUS_H; y < (cy + world.GEN_RADIUS_H + 1); y++) {
            //    if(strips[x].didGenBlock(y)) continue; //y loop
            //    
            //}
        }*/
    }
    
    
}
