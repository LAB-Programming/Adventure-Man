
/**
 * Write a description of class Ground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ground extends Block
{
    public Ground(int x, int y) {
        super(x,y);
    }
    
    public boolean isSolid() {
        return true;
    }
}
