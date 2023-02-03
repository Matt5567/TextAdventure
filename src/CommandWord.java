public enum CommandWord {

    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?" ), LOOK("look" ), GRAB("grab"), DROP("drop"), TALK("talk"), READ("read"), ADD("add"), SERVE("serve");

    private String commandString;

    CommandWord(String commandString){
        this.commandString = commandString;
    }

    public String toString (){
        return commandString;
    }


}
