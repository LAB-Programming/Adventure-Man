import java.util.Scanner;
import java.io.File;
import java.util.*;
/**
 * Write a description of class AdventureMan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AdventureMan
{
    // instance variables - replace the example below with your own
    private Scanner in = new Scanner(System.in);
    private AdventureWorld world;
    private Player player;

    /**
     * Constructor for objects of class AdventureMan
     */
    public AdventureMan()
    {
        System.out.println("Hi World!");
        String response = in.nextLine();
        while(!response.equalsIgnoreCase("start")) {
            if(response.equalsIgnoreCase("path")){
                System.out.println(new File(".").getAbsolutePath());
                response = in.nextLine();
                continue;
            }
            if(response.equalsIgnoreCase("quit")){
                System.exit(0);
                response = in.nextLine();
                continue;
            }
            System.out.println(response + " to you, too");
            response = in.nextLine();
        }
        world = new AdventureWorld(this);
        System.out.println("Welcome to Adventure Man!");
        player = new Player(world,0);
        world.addEntity(player,0);
        
        while(!response.equalsIgnoreCase("quit")) {
            /*Set<WorldItem> curItems = world.world.get(new Integer(player.getLoc()));
            WorldItem curItem=null;
            Iterator<WorldItem> curItemsIterator = curItems.iterator();
            boolean locationIsImportant=false;
            if(response.equalsIgnoreCase("move left")) player.move(-1);
            else if(response.equalsIgnoreCase("move right")) player.move(1);
            else if(response.equalsIgnoreCase("inspect")) {
                while(curItemsIterator.hasNext()){
                    curItem=curItemsIterator.next();
                    if (curItem instanceof Important){
                        System.out.println("You are standing near a " + ((Important) curItem).getName());
                        locationIsImportant=true;
                    }
                }
                if(!locationIsImportant) System.out.println("There is nothing of interest around you");
            }
            else{
                curItem=null;
                curItemsIterator = curItems.iterator();
                while(curItemsIterator.hasNext()){
                    curItem=curItemsIterator.next();
                    if((curItem instanceof Interactable) && response.equalsIgnoreCase(((Interactable) curItem).getInteractCmd())) ((Interactable) curItem).interact();
                }
            }*/
            WorldStrip curStrip = world.world.get(new Integer(player.getLoc()));
            HashSet<Interactable> interacters = new HashSet<Interactable>();
            Iterator<Entity> curEntitiesIterator = curStrip.getEntities().iterator();
            Entity curEntity=null;
            if(curStrip.getBlock() instanceof Interactable && (((Interactable) curStrip.getBlock()).isInteractCmd(response)))
                interacters.add((Interactable) curStrip.getBlock());
            while(curEntitiesIterator.hasNext()){
                    curEntity=curEntitiesIterator.next();
                    if(curEntity.isInteractCmd(response)) interacters.add(curEntity);
            }
            Interactable[] interacterArray = interacters.toArray(new Interactable[interacters.size()]);
            if(interacters.size() > 1) {
                String msg = "Which thing do you want to "+response+"?\nType its coresponding number or type anything else to cancel.";
                int i = 0;
                while(i <= interacterArray.length) {
                    Interactable cur = interacterArray[i];
                    msg += "/n("+(++i)+") "+ cur;
                }
                try {
                    int which = Integer.parseInt(specialRead(msg));
                    if(which <= interacterArray.length) interacterArray[--which].interact(response);
                    else System.out.println(response + " cancelled");
                }catch(NumberFormatException nfe){
                    System.out.println(response + " cancelled");
                }
            }else if(interacters.size() != 0) interacterArray[0].interact(response);
            response = in.nextLine();
        }
        System.out.println("See ya!");
        in.close();
    }

    public static void main(String args[]) {
        AdventureMan am = new AdventureMan();

    }
    
    public String specialRead(String msg) {
        System.out.println(msg);
        return in.nextLine();
    }
    
    public Player getPlayer() {
        return player;
    }
}
