import java.util.Random;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Arrays;
/**
 * Write a description of class TerrainGen here.
 * 
 * @author Gavin Yancey
 */
public class TerrainGen {
    private AdventureWorld world;
    
    private static final int HILL_MAX_H = 12;
    private static final int VALLEY_MIN_H = -12;
    private enum genType{
        UNSEEDED,
        BELOW_GROUND,
        ABOVE_GROUND,
        INVERSE_SQUARE,
    }
    private enum OoB{
        DO,
        DONT_DO,
        THROW_X,
        THROW_Y,
        PRINT_OUT,
        PRINT_ERR,
    }
    private enum setNotNull{
        DO,
        IGNORE,
        PRINT_OUT_AND_DO,
        PRINT_OUT_AND_IGNORE,
        PRINT_ERR_AND_DO,
        PRINT_ERR_AND_IGNORE,
        THROW_IF_DIFFERENT,
        THROW,
    }
    private Random r = new Random();
    private LinkedList<int[][]> lengthsToGen = new LinkedList<int[][]>();
    private Block[][] genSpace;
    private WorldStrip[] strips;
    private long cy;
    
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
    
    private Block getBlock(int x, int y, OoB outOfBounds) throws BadBlockException {
        if(x>=0&&x<genSpace.length&&y>=0&&y<genSpace[x].length) return genSpace[x][y];
        switch(outOfBounds){
            case THROW_X: throw new BadBlockCordinatesException("("+x+", "+y+") is out of bounds");
            case THROW_Y:
            case PRINT_ERR: System.err.println("("+x+", "+y+") is out of bounds"); break;
            case PRINT_OUT: System.out.println("("+x+", "+y+") is out of bounds"); break;
            case DO: break;
        }
        if(x>=0&&x<strips.length) return strips[x].getBlock(localYToGlobalY(y,cy));
        switch(outOfBounds){
            case THROW_Y: throw new BadBlockCordinatesException("("+x+", "+y+") is out of bounds");
            case PRINT_ERR: System.err.println("("+x+", "+y+") is out of bounds"); break;
            case PRINT_OUT: System.out.println("("+x+", "+y+") is out of bounds"); break;
            case DO: break;
        }
        throw new BadBlockCordinatesException("("+x+", "+y+") is out of bounds");
    }
    
    private void setBlock(Block b, int x, int y, OoB outOfBounds, setNotNull snn) throws BadBlockException {
        Block curBlock;
        if(x>=0&&x<genSpace.length&&y>=0&&y<genSpace[x].length){
            curBlock=genSpace[x][y];
            if(curBlock!=null){
                if(curBlock.getClass()==b.getClass()) return; //if we're trying to set something to what it is, we can just do nothing
                switch(snn){
                    case DO: break;
                    case IGNORE: return;
                    case PRINT_OUT_AND_DO: System.out.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); break;
                    case PRINT_OUT_AND_IGNORE: System.out.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); return;
                    case PRINT_ERR_AND_DO: System.err.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); break;
                    case PRINT_ERR_AND_IGNORE: System.err.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); return;
                    case THROW_IF_DIFFERENT: throw new IllegalWorldException("block ("+x+", "+y+") not good; is"+curBlock+", trying to set to "+b);
                    case THROW: throw new BlockNotNullException("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock, curBlock);
                }
            }
            genSpace[x][y]=b;
            return;
        }
        switch(outOfBounds){
            case THROW_X: throw new BadBlockCordinatesException("("+x+", "+y+") is out of bounds");
            case THROW_Y:
            case PRINT_ERR: System.err.println("("+x+", "+y+") is out of bounds"); break;
            case PRINT_OUT: System.out.println("("+x+", "+y+") is out of bounds"); break;
            case DONT_DO: return;
            case DO: break;
        }
        if(x>=0&&x<strips.length){
            curBlock=strips[x].getBlock(localYToGlobalY(y,cy));
            if(curBlock!=null){
                switch(snn){
                    case DO: break;
                    case IGNORE: return;
                    case PRINT_OUT_AND_DO: System.out.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); break;
                    case PRINT_OUT_AND_IGNORE: System.out.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); return;
                    case PRINT_ERR_AND_DO: System.err.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); break;
                    case PRINT_ERR_AND_IGNORE: System.err.println("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock); return;
                    case THROW_IF_DIFFERENT: throw new IllegalWorldException("block ("+x+", "+y+") not good; is"+curBlock+", trying to set to "+b);
                    case THROW: throw new BlockNotNullException("attemting to set ("+x+", "+y+") to "+b+"; it is "+curBlock, curBlock);
                }
            }
            strips[x].setBlock(b, localYToGlobalY(y,cy));
            return;
        }
        switch(outOfBounds){
            case THROW_Y: throw new BadBlockCordinatesException("("+x+", "+y+") is out of bounds");
            case PRINT_ERR: System.err.println("("+x+", "+y+") is out of bounds"); break;
            case PRINT_OUT: System.out.println("("+x+", "+y+") is out of bounds"); break;
            case DO: break;
        }
        throw new BadBlockCordinatesException("("+x+", "+y+") is out of bounds");
    }
    
    
    private void setupWorldForGen(){
        genSpace=new Block[strips.length][(AdventureWorld.GEN_RADIUS_H*2)+1];
        long top = cy+AdventureWorld.GEN_RADIUS_H,bottom = cy-AdventureWorld.GEN_RADIUS_H;
        genType gen_config=genType.UNSEEDED;
        for(int i=0;i<genSpace.length;i++){
            for(int j=0;j<genSpace[i].length;j++){
                Block curBlock=strips[i].getBlock(localYToGlobalY(j,cy));
                setBlock(curBlock,i,j,OoB.THROW_X,setNotNull.DO);//genSpace[i][j]=curBlock;
                if(curBlock!=null){
                    switch(gen_config){
                        case UNSEEDED:
                            if(curBlock.isSolid()&&j==genSpace.length) gen_config=genType.BELOW_GROUND;
                            else if(j==0) gen_config=genType.ABOVE_GROUND;
                            break;
                        case BELOW_GROUND:
                            if(!curBlock.isSolid()) gen_config=genType.INVERSE_SQUARE;
                            break;
                        case ABOVE_GROUND:
                            if(curBlock.isSolid()) gen_config=genType.INVERSE_SQUARE;
                            break;
                    }
                }
            }
        }
        if(gen_config==genType.UNSEEDED&&top<(VALLEY_MIN_H)) gen_config=genType.BELOW_GROUND;
        if(gen_config==genType.UNSEEDED&&bottom>HILL_MAX_H) gen_config=genType.ABOVE_GROUND;
        switch(gen_config){
           case BELOW_GROUND:
                for(int i=0;i<genSpace.length;i++){
                    for(int j=0;j<genSpace[i].length;j++){
                        setBlock(/*genSpace[i][j]=*/new Ground(strips[i].getX(),localYToGlobalY(j,cy))/* */,i,j,OoB.THROW_X,setNotNull.THROW);
                    }
                }
                break;
             case ABOVE_GROUND:
                for(int i=0;i<genSpace.length;i++){
                    for(int j=0;j<genSpace[i].length;j++){
                        setBlock(/*genSpace[i][j]=*/new Air(strips[i].getX(),localYToGlobalY(j,cy))/* */,i,j,OoB.THROW_X,setNotNull.THROW);
                    }
                }
                break;
            case UNSEEDED:
                int heights[][]=new int[2][2];//{{x1,y1},{x2,y2}}
                heights[0][0] = 0;
                heights[1][0] = genSpace.length;
                heights[0][1] = centeredRandom(VALLEY_MIN_H,HILL_MAX_H);
                heights[1][1] = centeredRandom(VALLEY_MIN_H,HILL_MAX_H);
                heights[0][1] = globalYToLocalY(heights[0][1],cy);
                heights[1][1] = globalYToLocalY(heights[1][1],cy);
                lengthsToGen.add(heights);
                gen_config=genType.INVERSE_SQUARE;
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
                            //if(genSpace[i][lowestAir]==null||genSpace[i][lowestAir].isSolid()) continue;
                            if(getBlock(i,lowestAir,OoB.DO)==null||getBlock(i,lowestAir,OoB.DO).isSolid()) continue;
                            break;
                        }
                    }
                    if(highestGround==-1){
                        int a=lowestAir>=HILL_MAX_H? lowestAir: HILL_MAX_H+1;
                        for(highestGround=a-1;localYToGlobalY(highestGround,cy)>=VALLEY_MIN_H;highestGround--){
                            //if(genSpace[i][highestGround]==null||genSpace[i][highestGround].isSolid()) continue;
                            if(getBlock(i,highestGround,OoB.DO)==null||getBlock(i,highestGround,OoB.DO).isSolid()) continue;
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
    }
    
    
    /**
     * generate a square of the world
     */
    public void genWorld(WorldStrip[] strips, long cy) throws IllegalWorldException{
        this.strips=strips;
        this.cy=cy;
        setupWorldForGen();
        
        Iterator<int[][]> lenghtsToGenIterator = lengthsToGen.iterator();
        int curLen[][];
        while(lenghtsToGenIterator.hasNext()){
            curLen=lenghtsToGenIterator.next();
            double curVar = curLen[1][0]-curLen[0][0]*1.1;
            int[] heights = applyInverseSquareRecursively(curLen,curVar);
            for(int i=0;i<heights.length;i++){
                System.out.println(heights[i]);
                if(i<0||i>=genSpace.length) continue;
                if(heights[i]>genSpace[i].length){
                    for(int j=0;j<genSpace[i].length;j++){
                        /*if(genSpace[i][j]==null){
                            genSpace[i][j]=new Ground(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(!genSpace[i][j].isSolid())throw new IllegalWorldException("underground block not solid!");//should never be reached*/
                        setBlock(new Ground(strips[0].getX()+i/*strips[i].getX()*/,localYToGlobalY(j,cy)),i,j,OoB.DO,setNotNull.PRINT_ERR_AND_IGNORE/*THROW_IF_NOT_SOLID*/);
                    }  
                }else if(heights[i]<0){
                    for(int j=0;j<genSpace[i].length;j++){
                        /*if(genSpace[i][j]==null){
                            genSpace[i][j]=new Air(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(genSpace[i][j].isSolid())throw new IllegalWorldException("aboveground block solid!");//should never be reached*/
                        setBlock(new Air(strips[0].getX()+i/*strips[i].getX()*/,localYToGlobalY(j,cy)),i,j,OoB.DO,setNotNull.PRINT_ERR_AND_IGNORE/*THROW_IF_SOLID*/);
                    }
                }else{
                    for(int j=heights[i];j>0;j--){
                        if(j==0) break;
                        /*if(genSpace[i][j]==null){
                            genSpace[i][j]=new Ground(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(!genSpace[i][j].isSolid())throw new IllegalWorldException("underground block not solid!");//should never be reached*/
                        setBlock(new Ground(strips[0].getX()+i/*strips[i].getX()*/,localYToGlobalY(j,cy)),i,j,OoB.DO,setNotNull.PRINT_ERR_AND_IGNORE/*THROW_IF_NOT_SOLID*/);
                    }
                    for(int j=heights[i]+1;j<genSpace[i].length;j++){
                        /*if(genSpace[i][j]==null){
                            genSpace[i][j]=new Air(strips[i].getX(),localYToGlobalY(j,cy));
                        }else if(genSpace[i][j].isSolid())throw new IllegalWorldException("aboveground block solid!");//should never be reached*/
                        setBlock(new Air(strips[0].getX()+i/*strips[i].getX()*/,localYToGlobalY(j,cy)),i,j,OoB.DO,setNotNull.PRINT_ERR_AND_IGNORE/*THROW_IF_SOLID*/);
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
        if(curLen[0][0]==curLen[1][0]) return new int[]{curLen[0][1]};
        int len = Math.abs(curLen[1][0]-curLen[0][0]);
        int returnArray[] = new int[len+1];
        returnArray[0] = curLen[0][1];
        returnArray[len] = curLen[1][1];
        int center = (len)/2;
        int centerH = (returnArray[0]+returnArray[1])/2;
        try {
            centerH += (int)(r.nextInt((int)curVar)-curVar/2);
        }catch(IllegalArgumentException iae) {
            StringBuilder errString = new StringBuilder("curVar is BAD: ");
            errString.append(curVar);
            errString.append(", (int) ");
            errString.append((int)curVar);
            errString.append("\n badly derived from curLen: {");
            for(int[] i: curLen) errString.append(Arrays.toString(i));
            errString.append("}");
            throw new IllegalArgumentException(errString.toString(),iae);
        }
        returnArray[center] = centerH;
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
