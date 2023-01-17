public class Game {
    private Room currentRoom;

    public Game(){

    }

    public static void main(String[]args){
        Game game = new Game();
        game.createRooms();
        game.play();

    }

    private void createRooms(){
        Room restaurant = new Room("");
        Room kitchen = new Room("");
        Room cellar = new Room("");
        Room pantry = new Room("");
        Room spices = new Room("");

    }

    public void play(){
        printWelcome();

        boolean finished = false;

        while(!finished){

        }
     System.out.println("Thanks for playing");

    }

    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("");
    }

}
