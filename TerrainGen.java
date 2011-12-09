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

    //private static final int HILL_MAX_H = 12;
    //private static final int VALLEY_MIN_H = 12;
    private static final byte UNSEEDED=0;
    private static final byte BELOW_GROUND=1;
    private static final byte ABOVE_GROUND=2;
    private static final byte INVERSE_SQUARE=3;
    
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
        WorldSpace genSpace[][]=new WorldSpace[strips.length][(AdventureWorld.GEN_RADIUS_H*2)+1]();
        byte gen_config=UNSEEDED;
        for(int i=0;i<genSpace.length;i++){
            for(int j=0;j<genSpace[0].length;j++){
                WorldSpace curSpace=strips[i].getBlock(j+cy-AdventureWorld.GEN_RADIUS_H);
                genSpace[i][j]=curSpace;
                if(curSpace!=null){
                    switch(gen_config){
                        case UNSEEDED:
                        if(curSpace.isSolid()) 
                    }
                }
            }
        }
        
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
