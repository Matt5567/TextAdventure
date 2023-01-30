public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    Items pasta = new Items();
    Items chicken = new Items();
    Room restaurant = new Room("restaurant\n", "To the south is the entrance to your kitchen, where all the food is prepared. All around you are circular tables covered with elegant silverware and white tablecloths,\nall filled with people talking, laughing, and enjoying their delicious meals. Sitting alone in the corner is a man dressed in all black, who you recognize to be a renowned yet cynical food critic,\nfamous for his scathing reviews and unfavorable attitude. His review will make or break the success of your restaurant, and he is staring at you waiting for you to talk to him.");
    Room kitchen = new Room("kitchen\n", "To the south is a staircase that leads down into the cellar. To the east and west are two doors that lead to two separate rooms.\nOn the table is a cookbook, conveniently opened to the desired recipe and ready for you to read. Next to the cookbook is a kitchen knife, which could be used to chop ingredients.\nOn top of the stove is a large pot already filled with water, heated to the perfect temperature for whatever you wish to make.\n");
    Room cellar = new Room("cellar\n", "long Description");
    Room pantry = new Room("pantry\n", "long Description");
    Room spices = new Room("spice room\n", "long Description");

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
        }
        return wantToQuit;
    }

    private void talk(Command command){
        if(!currentRoom.equals(restaurant) ){
            System.out.println("You can't talk here");
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
        System.out.println("Type look to explore the room you are in.");
    }

}
