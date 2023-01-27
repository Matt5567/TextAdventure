import java.util.HashMap;

import java.util.Set;
import java.util.Iterator;

public class Player {
    private HashMap<String, Items>inventory;

    Player(){
        inventory = new HashMap<>();
    }

    public void setItem(String name, Items item){
        inventory.put(name, item);
    }

    public Items getItems(String name){
        return inventory.remove(name);
    }

    public String getItemString(){
        String returnString = "Player Inv: ";
        Set<String> keys = inventory.keySet();
        for(String item: keys){
            returnString += "" + item;
        }
        return returnString;
    }



}
