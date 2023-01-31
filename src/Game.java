public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    private String book = "book";
    Items pasta = new Items();
    Items chicken = new Items();
    Room restaurant = new Room("You stand in the center of your new and growing restaurant, crowded and bustling with noise and energy,\nbut one solemn man captures your full attention.", "To the south is the entrance to your kitchen, where all the food is prepared. All around you are circular tables covered with elegant silverware and white tablecloths,\nall filled with people talking, laughing, and enjoying their delicious meals. Sitting alone in the corner is a man dressed in all black, who you recognize to be a renowned yet cynical food critic,\nfamous for his scathing reviews and unfavorable attitude. His review will make or break the success of your restaurant, and he is staring at you waiting for you to talk to him.");
    Room kitchen = new Room("You enter your beloved kitchen, equipped with a large wood table in the center and a stove in the back corner.", "To the south is a staircase that leads down into the cellar. To the east and west are two doors that lead to two separate rooms.\nOn the table is a cookbook, conveniently opened to the desired recipe and ready for you to read. On top of the stove is a large pot already filled with water,\nheated to the perfect temperature for whatever you wish to make.\n");
    Room cellar = new Room("You descend into the cellar, A dark, cold room, thick with the stench of raw meat.", "To the north is a staircase leading up to the kitchen. All around you carcasses of various animals are hung from the ceiling,\nwaiting to be turned into a delicious meal. In the center is a metal table on which lies a platter of chicken meat. This would be perfect to use in a soup…");
    Room pantry = new Room("Dimly lit by a lone hanging bulb, you stand in a small room lined with shelves from top to bottom.", "All around you are the various ingredients needed to make a wide range of recipes.\nOn the left are bins full of fruits and vegetables, and on the right the shelves are filled with countless bottles of wine that are served with dinner.\nOne box on the bottom shelf catches your eye; you notice it is labeled “Pasta.”");
    Room spices = new Room("spice room", "long Description");

    public Game(){
        parser = new Parser();
        player = new Player();
    }

    public static void main(String[]args){
        Game game = new Game();
        game.createRooms();
        game.play();

    }

    private void createRooms(){


        //restaurant exits
        restaurant.setExit("south", kitchen);

        //kitchen exits
        kitchen.setExit("south", cellar);
        kitchen.setExit("east", spices);
        kitchen.setExit("west", pantry);
        kitchen.setExit("north", restaurant);



        // cellar exits and inventory
        cellar.setExit("north", kitchen);
        cellar.setItem("chicken", chicken);

        //pantry exits and inventory
        pantry.setExit("east", kitchen);
        pantry.setItem("pasta", pasta);

        // spices exits and inventory
        spices.setExit("west", kitchen);

        Items obj = new Items();
        Items obj2 = new Items();

        currentRoom = restaurant;
    }

    public void play(){
        printWelcome();

        boolean finished = false;

        while(!finished){
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
     System.out.println("Thanks for playing");

    }

    private boolean processCommand(Command command){
        boolean wantToQuit = false;
        CommandWord commandWord = command.getCommandWord();

        switch(commandWord){
            case UNKNOWN:
                System.out.println("I don't know what you mean");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = true;
                break;

            case LOOK:
                look(command);
                break;

            case DROP:
                drop(command);
                break;

            case GRAB:
                grab(command);
                break;

            case TALK:
                talk(command);
                break;

            case READ:
                read(command);
                break;
        }
        return wantToQuit;
    }

    private void read(Command command){
        if(!currentRoom.equals(kitchen)){
            System.out.println("There is nothing to read here.");
        }
        else if(!command.hasSecondWord()){
            System.out.println("Read what?");
        }
        else if(!command.getSecondWord().equals(book)){
            System.out.println("You can't read " + command.getSecondWord());
        }
        else {
            System.out.println("Chicken Noodle Soup: Your Best Meal\nAdd chicken, pasta, and secret sauce into a pot\nthen grab soup and serve");
        }
    }

    private void talk(Command command){
        if(!currentRoom.equals(restaurant) ){
            System.out.println("You can't talk here");
        }
        else if(command.hasSecondWord()){
            System.out.println("Just type talk");
        }
        else{
            System.out.println("Cook me the best meal you have.");
        }

    }

    private void grab(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Grab what?");
            return;
        }

        String key = command.getSecondWord();
        Items grabItem = currentRoom.getItems(key);

        if (grabItem == null){
            System.out.println("You can't grab " + command.getSecondWord());
        }
        else{
            player.setItem(key, grabItem);
            System.out.println("Grabbed " + command.getSecondWord());
        }
    }

    private void drop(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            return;
        }

        String key = command.getSecondWord();
        Items dropItem = player.getItems(key);

        if(dropItem == null) {
            System.out.println("You can't drop " + command.getSecondWord());

        }
            else{
                currentRoom.setItem(key, dropItem);
                System.out.println("Dropped " + command.getSecondWord());
            }

    }

    private void printHelp(){

        System.out.println("You must find the right ingredients");
        System.out.println();
        System.out.println("Your command words are");
        parser.showCommands();
    }

    private void look (Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't look at " + command.getSecondWord());
            return;
        }
        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }

    private void goRoom(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null){
            System.out.println("There is no door!");
        }
        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private boolean quit(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't quit" + command.getSecondWord());
            return false;
        }
        else{
            return true;
        }
    }

    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will have to cook your best meal to save your new restaurant.");
        System.out.println("Type look to explore the room you are in. Type help for a list of possible commands.");

    }

}
