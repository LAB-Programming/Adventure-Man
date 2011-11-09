
/**
 * Write a description of class Tree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tree extends Block implements Interactable, Important
{
    private int height;
    
    private AdventureWorld world;

    public void interact(int i) {
        world.setBlock(loc, new Ground(loc));
        System.out.println("You cut down the tree and got " + (height*5) + " wood");
        world.player.addToInventory(new Wood(5));
    }

    public int whereInteractCmd(String response) {
        return response.equalsIgnoreCase("chop tree") ? 0 : -1;
    }

    public Tree(int x, int h, AdventureWorld wrld) {
        super(x);
        height = h;
        world = wrld;
    }

    public String getName() {
        return "Tree";
    }
}
