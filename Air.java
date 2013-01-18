
/**
 * Write a description of class Air here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Air extends Block
{
    public Air(long x, long y) {
        super(x,y);
    }
    
    public boolean isSolid() {
        return false;
    }
    
    public String toString() {
        return "Air";
    }
    
    public char getPic() {
        return ' ';
    }
}
