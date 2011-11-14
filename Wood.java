/**
 * Write a description of class Wood here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wood implements InventoryItem
{
    // instance variables - replace the example below with your own
    private int howMany;
    
    public void setHowMany(int newHowMany){
        howMany=newHowMany;
    }
    public int getHowMany(){
        return howMany;
    }
    public String name(){
        return "Wood";
    }
    public String toString(){
        return howMany+" "+name();
    }
    /**
     * Constructor for objects of class Wood
     */
    public Wood(int hm)
    {
        howMany=hm;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    
}
