import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;


public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private String longDescription;
    private HashMap<String, Items>inventory;

    public Room (String description, String longDescription){
        this.description = description;
        this.longDescription = longDescription;
        exits = new HashMap<String, Room>();
        inventory = new HashMap<>();
    }

    private String getExitString(){
        String returnString = description;
        Set<String> keys = exits.keySet();
        for(String exit: keys){
            returnString += "" + exit;
        }
        return returnString;
    }



    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction){
         return exits.get(direction);
    }

    public String getShortDescription(){
        return description;
    }

    public String getLongDescription(){
        return longDescription + "\n" + getExitString() + "\n" + getItemString();
    }

    public void setItem(String name, Items item){
        inventory.put(name, item);
    }

    public Items getItems(String name){
        return inventory.remove(name);
    }

    public String getItemString(){
        String returnString = "Room Inv: ";
        Set<String> keys = inventory.keySet();
        for(String item: keys){
            returnString += "" + item;
        }
        return returnString;
    }


}
