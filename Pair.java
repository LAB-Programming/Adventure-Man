
/**
 * Write a description of class Pair here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pair<E>
{
    // instance variables - replace the example below with your own
    private E x;
    private E y;

    /**
     * Constructor for objects of class Pair
     */
    public Pair(E nx,E ny){
        x = nx;
        y = ny;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public E getX(){
        return x;
    }
    
    public E getY(){
        return y;
    }
}
