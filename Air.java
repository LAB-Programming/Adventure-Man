
/**
 * Write a description of class Air here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Air extends Block
{
    public Air(int x, int y) {
        super(x,y);
    }
    
    public boolean isSolid() {
        return true;
    }
}
