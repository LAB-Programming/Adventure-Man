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
        Block genSpace[][]=new Block[strips.length][(AdventureWorld.GEN_RADIUS_H*2)+1];
        byte gen_config=UNSEEDED;
        for(int i=0;i<genSpace.length;i++){
            for(int j=0;j<genSpace[i].length;j++){
                Block curBlock=strips[i].getBlock(j+cy-AdventureWorld.GEN_RADIUS_H);
                genSpace[i][j]=curBlock;
                if(curBlock!=null){
                    switch(gen_config){
                        case UNSEEDED:
                            if(curBlock.isSolid()) gen_config=BELOW_GROUND;
                            else gen_config=ABOVE_GROUND;
                            break;
                        case BELOW_GROUND:
                            if(!curBlock.isSolid()) gen_config=INVERSE_SQUARE;
                            break;
                        case ABOVE_GROUND:
                            if(curBlock.isSolid()) gen_config=INVERSE_SQUARE;
                            break;
                    }
                }
            }
        }
        if(gen_config==BELOW_GROUND||(gen_config==UNSEEDED&&cy<(-12))){
            for(int i=0;i<genSpace.length;i++){
                for(int j=0;j<genSpace[i].length;j++){
                    if(genSpace[i][j]==null) genSpace[i][j]=new Ground(strips[i].getX(),j+cy-AdventureWorld.GEN_RADIUS_H);
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
