
/**
 * Write a description of interface Block here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Block extends WorldItem
{
    public Block(int x, int y){
        locx = x;
        locy = y;
    }

    public boolean equals(Object obj) {
        if((obj instanceof Block) && (this.getLocx() == ((Block) obj).getLocx()) && (this.getLocy() == ((Block) obj).getLocy()))
            return true;
        return false;
    }
    
    public abstract boolean isSolid();
}
