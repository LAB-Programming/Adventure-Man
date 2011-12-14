/**
 * Write a description of class Tree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tree extends Block implements Interactable
{
    private int height;
    
    private AdventureWorld world;

    public void interact(String cmd) {
        world.setBlock(new Ground(locx,locy), locx, locy);
        System.out.println("You cut down the tree and got " + (height*5) + " wood");
        world.getPlayer().addToInventory(new Wood(height*5));
    }

    public boolean isInteractCmd(String cmd) {
        return cmd.equalsIgnoreCase("chop tree")||cmd.equalsIgnoreCase("chop");
    }

    public Tree(int x, int y, int h, AdventureWorld wrld) {
        super(x,y);
        height = h;
        world = wrld;
    }

    public String getName() {
        return "Tree";
    }
    
    public boolean isHidden() {
        return false;
    }
    
    public boolean isSolid() {
        return false;
    }
}
