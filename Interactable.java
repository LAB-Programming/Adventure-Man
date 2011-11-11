
/**
 * Write a description of interface Interactable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Interactable extends Important
{
    public void interact(String cmd);
    
    public boolean isInteractCmd(String cmd);
}
