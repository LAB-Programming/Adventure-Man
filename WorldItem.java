
/**
 * Write a description of interface WorldItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class WorldItem
{
    protected long locx;
    protected long locy;
    public long getLocx() {
        return locx;
    }
    
    public long getLocy() {
        return locy;
    }
    
    public abstract char getPic();
}
