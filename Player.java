
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class Player extends Entity
{
    public void interact(int i){}
    
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
    
    public int whereInteractCmd(String cmd) {return -1;}
    
    public String getName(){
        return "Player";
    }

    public Player(AdventureWorld wrld, int x) {
        super(wrld,x);
    }

    public boolean move(int i) {
        if(super.move(i)) {
            System.out.println("You moved " + (i < 0 ? "Left" : "Right"));
            return true;
        }
        else {
            System.out.println("You are at the edge of the World");
            return false;
        }
    }
}
