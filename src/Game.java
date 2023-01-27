public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;

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
        Room restaurant = new Room("restaurant", "long Description");
        Room kitchen = new Room("kitchen", "long Description");
        Room cellar = new Room("cellar", "long Description");
        Room pantry = new Room("pantry", "long Description");
        Room spices = new Room("spices", "long Description");

        //restaurant exits
        restaurant.setExit("south", kitchen);

        //kitchen exits
        kitchen.setExit("south", cellar);
        kitchen.setExit("east", spices);
        kitchen.setExit("west", pantry);
        kitchen.setExit("north", restaurant);

        // cellar exits
        cellar.setExit("north", kitchen);

        //pantry exits
        pantry.setExit("east", kitchen);

        // spices exits
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
                break;

            case GRAB:
                break;
        }
        return wantToQuit;
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
        System.out.println(" You stand in the center of your new and growing restaurant, crowded and bustling with noise and energy, \n but one solemn man captures your full attention. \n");
    }

}
