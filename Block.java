
/**
 * Write a description of interface Block here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Block extends WorldItem
{
    public Block(int x){
        loc = x;
    }

    public boolean equals(Object obj) {
        if((obj instanceof Block) && (this.getLoc() == ((Block) obj).getLoc())) return true;
        return false;
    }
}
