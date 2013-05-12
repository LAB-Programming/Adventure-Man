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
        if(cmd.equals("move left")||cmd.equals("ml")) this.move(-1);
        else if(cmd.equals("move right")||cmd.equals("mr")) this.move(1);
        else if(cmd.startsWith("move "))this.move(Integer.parseInt(cmd.split("move ")[0]));
        else if(cmd.equals("inspect")) {
            String inspect = inspect();
            if(inspect.isEmpty()) inspect = "There is nothing of interest around you";
            System.out.print(inspect);
        }
        else if(cmd.equals("inventory")){
            System.out.println("you have:\n"+Arrays.toString(inventory));
        }
        else if(cmd.equals("survey")){
            StringBuilder sb = new StringBuilder();
            for(int i = 2; i >= -2; i--){
                for(int j = -3; j <= 3; j++){
                    sb.append(world.getStrip(locx+j).getSpace(locy+i).getFrontItem().getPic());
                }
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }

    private String inspect() {
        WorldSpace curSpace = world.getStrip(getLocx()).getSpace(getLocy());
        Iterator<Entity> curEntitiesIterator = curSpace.getEntities().iterator();
        Entity curEntity=null;
        StringBuilder response = new StringBuilder();
        if(curSpace.getBlock() instanceof Important && !((Important) curSpace.getBlock()).isHidden()) {
            response.append("You are standing near a ");
            response.append(((Important) curSpace.getBlock()).getName());
            response.append("\n");
        }
        while(curEntitiesIterator.hasNext()){
            curEntity=curEntitiesIterator.next();
            if(!curEntity.isHidden()) {
                 response.append("You are standing near a ");
                 response.append(((Important) curEntity).getName());
                 response.append("\n");
            }
        }
        return response.toString();
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
        if (cmd.equals("move left") || cmd.equals("move right") || cmd.equals("inspect") || cmd.equals("inventory") || cmd.equals("survey")) return true;
        return false;
    }

    public String getName(){
        return "Player";
    }

    public Player(AdventureWorld wrld, long x, long y) {
        super(wrld,x, y);
    }

    public MoveResult move(int i) {
        world.genWorld(getLocx()+i,getLocy());
        MoveResult result = super.move(i);
        switch(result) {
            case SUCCESS:
            System.out.println("You moved " + (i < 0 ? "Left" : "Right"));
            System.out.print(inspect());
            break;
            case EDGE_OF_WORLD:
            System.out.println("You are at the edge of the World");
            System.err.println("WTF! the world is supposed to be infinite!!!!");
            break;
            case TOO_HIGH:
            System.err.println("You try to reach the edge of the cliff you are facing but you fall just slightly short");
            break;
            default:
            System.err.println("Hmm... for some reason you can not move that way. Go smack the programmer");
            break;
        }
        return result;
    }

    public boolean isHidden() {
        return true;
    }

    public int getMaxMoveHeight() {
        return 1;
    }

    public char getPic() {
        return '@';
    }
}
