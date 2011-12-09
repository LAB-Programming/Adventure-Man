/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class Player extends Entity
{
    public void interact(String cmd){
        cmd = cmd.toLowerCase();
        if(cmd.equals("move left")) this.move(-1);
        else if(cmd.equals("move right")) this.move(1);
        else if(cmd.equals("inspect")) {
            WorldSpace curSpace = world.getStrip(getLocx()).getSpace(getLocy());
            Iterator<Entity> curEntitiesIterator = curSpace.getEntities().iterator();
            Entity curEntity=null;
            boolean locationIsImportant=false;
            if(curSpace.getBlock() instanceof Important && !((Important) curSpace.getBlock()).isHidden()) {
                System.out.println("You are standing near a " + ((Important) curSpace.getBlock()).getName());
                locationIsImportant = true;
            }
            while(curEntitiesIterator.hasNext()){
                curEntity=curEntitiesIterator.next();
                if(!curEntity.isHidden()) {
                    System.out.println("You are standing near a " + ((Important) curEntity).getName());
                    locationIsImportant = true;
                }
            }
            if(!locationIsImportant) System.out.println("There is nothing of interest around you");
        }
        else if(cmd.equals("inventory")){
            System.out.println("you have:\n"+Arrays.toString(inventory));
        }
    }

    public InventoryItem[] inventory = new InventoryItem[15];

    public int amntGold=0;

    public boolean addToInventory(InventoryItem newItem){
        int firstEmptySlot=-1;
        for(int i=0;i<inventory.length;i++){
            InventoryItem curItem=inventory[i];
            if(curItem==null){
                firstEmptySlot=i;
                continue;
            }
            if(curItem.name()==newItem.name()){
                curItem.setHowMany(curItem.getHowMany()+newItem.getHowMany());
                return true;
            }
        }
        if(firstEmptySlot>=0){
            inventory[firstEmptySlot]=newItem;
            return true;
        }
        return false;
    }

    public boolean removeFromInventory(InventoryItem item){
        for(int i=0;i<inventory.length;i++){
            InventoryItem curItem=inventory[i];
            if(curItem==null) continue;
            if(curItem.name()==item.name()){
                if(curItem.getHowMany()==item.getHowMany()){
                    inventory[i]=null;
                    return true;
                }
                if(curItem.getHowMany()>item.getHowMany()){
                    curItem.setHowMany(curItem.getHowMany()-item.getHowMany());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInteractCmd(String cmd) {
        cmd = cmd.toLowerCase();
        if (cmd.equals("move left") || cmd.equals("move right") || cmd.equals("inspect") || cmd.equals("inventory")) return true;
        return false;
    }

    public String getName(){
        return "Player";
    }

    public Player(AdventureWorld wrld, int x, int y) {
        super(wrld,x, y);
    }

    public boolean move(int i) {
        world.genWorld(getLocx()+i);
        if(super.move(i)) {
            System.out.println("You moved " + (i < 0 ? "Left" : "Right"));
            return true;
        }
        else {
            System.out.println("You are at the edge of the World");
            return false;
        }
    }

    public boolean isHidden() {
        return true;
    }
}
