import java.util.Random;
import java.util.LinkedList;
import java.util.Iterator;
/**
 * Write a description of class TerrainGen here.
 * 
 * @author Gavin Yancey
 */
public class TerrainGen {
    private AdventureWorld world;
    
    private static final int HILL_MAX_H = 12;
    private static final int VALLEY_MIN_H = -12;
    private static final byte UNSEEDED=0;
    private static final byte BELOW_GROUND=1;
    private static final byte ABOVE_GROUND=2;
    private static final byte INVERSE_SQUARE=3;
    private Random r = new Random();
    private LinkedList<int[][]> lengthsToGen = new LinkedList<int[][]>();
    
    /**
     * 
     */
    public TerrainGen(AdventureWorld wrld) {
        world = wrld;
    }
    
    private long localYToGlobalY(int localY, long globalYCenterRefrence){
        return (localY+globalYCenterRefrence-AdventureWorld.GEN_RADIUS_H);
    }
    
    private int globalYToLocalY(long globalY, long globalYCenterRefrence){
        return (int)(globalY-globalYCenterRefrence+AdventureWorld.GEN_RADIUS_H);
    }
    
    /**
     * generate a square of the world
     */
    public void genWorld(WorldStrip[] strips, long cy) throws IllegalWorldException{
        Block genSpace[][]=new Block[strips.length][(AdventureWorld.GEN_RADIUS_H*2)+1];
        long top = cy+AdventureWorld.GEN_RADIUS_H,bottom = cy-AdventureWorld.GEN_RADIUS_H;
        byte gen_config=UNSEEDED;
        for(int i=0;i<genSpace.length;i++){
            for(int j=0;j<genSpace[i].length;j++){
                Block curBlock=strips[i].getBlock(localYToGlobalY(j,cy));
                genSpace[i][j]=curBlock;
                if(curBlock!=null){
                    switch(gen_config){
                        case UNSEEDED:
                            if(curBlock.isSolid()&&j==genSpace.length) gen_config=BELOW_GROUND;
                            else if(j==0) gen_config=ABOVE_GROUND;
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
        if(gen_config==UNSEEDED&&top<(VALLEY_MIN_H)) gen_config=BELOW_GROUND;
        if(gen_config==UNSEEDED&&bottom>HILL_MAX_H) gen_config=ABOVE_GROUND;
        switch(gen_config){
           case BELOW_GROUND:
                for(int i=0;i<genSpace.length;i++){
                    for(int j=0;j<genSpace[i].length;j++){
                        if(genSpace[i][j]==null){
                            genSpace[i][j]=new Ground(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(!genSpace[i][j].isSolid())throw new IllegalWorldException("underground block not solid!");//should never be reached
                    }
                }
                break;
             case ABOVE_GROUND:
                for(int i=0;i<genSpace.length;i++){
                    for(int j=0;j<genSpace[i].length;j++){
                        if(genSpace[i][j]==null){
                            genSpace[i][j]=new Air(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(genSpace[i][j].isSolid())throw new IllegalWorldException("aboveground block solid!");//should never be reached
                    }
                }
                break;
            case UNSEEDED:
                int heights[][]=new int[2][2];//{{x1,y1},{x2,y2}}
                heights[0][0]=0;
                heights[1][0]=genSpace.length;
                heights[0][1] = centeredRandom(VALLEY_MIN_H,HILL_MAX_H);
                heights[1][1] = centeredRandom(VALLEY_MIN_H,HILL_MAX_H);
                heights[0][1] = globalYToLocalY(heights[0][1],cy);
                heights[1][1] = globalYToLocalY(heights[1][1],cy);
                lengthsToGen.add(heights);
                gen_config=INVERSE_SQUARE;
            case INVERSE_SQUARE:
                int curx=0,curh=0;
                for(int i=0;i<genSpace.length;i++){
                    int highestGround=-1, lowestAir=-1;
                    for(int j=0;j<genSpace[i].length;j++){
                        if(genSpace[i][j]==null) continue;
                        if(genSpace[i][j].isSolid()) highestGround=j;
                        else lowestAir=((lowestAir==-1)? j: lowestAir);
                    }
                    if(highestGround==-1&&lowestAir==-1&&i!=0) continue;//column is empty
                    if(lowestAir==-1){
                        int g=((highestGround>=VALLEY_MIN_H)&&(highestGround!=-1))? highestGround: VALLEY_MIN_H-1;
                        for(lowestAir=g+1;localYToGlobalY(lowestAir,cy)<=HILL_MAX_H;lowestAir++){
                            if(genSpace[i][lowestAir]==null||genSpace[i][lowestAir].isSolid()) continue;
                            break;
                        }
                    }
                    if(highestGround==-1){
                        int a=lowestAir>=HILL_MAX_H? lowestAir: HILL_MAX_H+1;
                        for(highestGround=a-1;localYToGlobalY(highestGround,cy)>=VALLEY_MIN_H;highestGround--){
                            if(genSpace[i][highestGround]==null||genSpace[i][highestGround].isSolid()) continue;
                            break;
                        }
                    }
                    int curHeights[][]=new int[2][2];
                    curHeights[0][0]=curx;
                    curHeights[0][1]=curh;
                    curHeights[1][0]=i;
                    curHeights[1][1]=centeredRandom(highestGround, lowestAir);
                    lengthsToGen.add(curHeights);
                    curx=i;
                    curh=curHeights[1][1];
                }
        }
        //if(true) throw new RuntimeException("Code to generate world not created yet!");
        ///**generate world here*/
        Iterator<int[][]> lenghtsToGenIterator = lengthsToGen.iterator();
        int curLen[][];
        while(lenghtsToGenIterator.hasNext()){
            curLen=lenghtsToGenIterator.next();
            double curVar = curLen[1][0]-curLen[0][0]*1.1;
            int[] heights = applyInverseSquareRecursively(curLen,curVar);
            for(int i=0;i<heights.length;i++){
                if(heights[i]>genSpace[i].length){
                    for(int j=0;j<genSpace[i].length;j++){
                        if(genSpace[i][j]==null){
                            genSpace[i][j]=new Ground(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(!genSpace[i][j].isSolid())throw new IllegalWorldException("underground block not solid!");//should never be reached
                    }  
                }else if(heights[i]<0){
                    for(int j=0;j<genSpace[i].length;j++){
                        if(genSpace[i][j]==null){
                            genSpace[i][j]=new Air(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(genSpace[i][j].isSolid())throw new IllegalWorldException("aboveground block solid!");//should never be reached
                    }
                }else{
                    for(int j=heights[i];j>0;j--){
                        if(genSpace[i][j]==null){
                            genSpace[i][j]=new Ground(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(!genSpace[i][j].isSolid())throw new IllegalWorldException("underground block not solid!");//should never be reached
                    }
                    for(int j=heights[i]+1;j<genSpace[i].length;j++){
                        if(genSpace[i][j]==null){
                            genSpace[i][j]=new Air(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(genSpace[i][j].isSolid())throw new IllegalWorldException("aboveground block solid!");//should never be reached
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
    
    private int[] applyInverseSquareRecursively(int curLen[][], double curVar){
        int len=curLen[1][0]-curLen[0][0];
        int returnArray[]=new int[len+1];
        returnArray[0]=curLen[0][1];
        returnArray[len]=curLen[1][1];
        int center = (len)/2;
        int centerH = (returnArray[0]+returnArray[1])/2;
        centerH += (int)(r.nextInt((int)curVar)-curVar/2);
        returnArray[center]=centerH;
        if(curLen[0][0]-center>1){
            int newCurLen[][]=new int[2][2];
            newCurLen[0][0]=curLen[0][0];
            newCurLen[1][0]=center;
            newCurLen[0][1]=curLen[0][1];
            newCurLen[1][1]=centerH;
            int temp[] = applyInverseSquareRecursively(newCurLen,curVar/2);
            for(int i=0;i<temp.length;i++){
                returnArray[i]=temp[i];
            }
        }
        if(center-curLen[0][1]>1){
            int newCurLen[][]=new int[2][2];
            newCurLen[0][0]=center;
            newCurLen[1][0]=curLen[1][0];
            newCurLen[0][1]=centerH;
            newCurLen[1][1]=curLen[1][1];
            int temp[] = applyInverseSquareRecursively(newCurLen,curVar/2);
            for(int i=0;i<temp.length;i++){
                returnArray[i+center]=temp[i];
            }
        }
        return returnArray;
    }
        
    private int centeredRandom(int min, int max){
        return (r.nextInt((max-min)/2)+r.nextInt((max-min)/2))+min;
    }
    
}
