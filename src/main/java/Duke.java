import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    private static final String MESSAGE_BOUNDARY = "\n____________________________________________________________\n";
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";


    private String print_response(String input , boolean repeat){
        if(!repeat){
            return "Bye, hope to see you soon!";
        }
        else
            return input;
    }
    private Boolean continuechat(String input){
        return !input.equals("bye"); // returns FALSE if user inputs "bye"

    }

    public void bot_respond(){
        Boolean repeat = true;
        Scanner myscanner = new Scanner(System.in);
        System.out.println(LOGO);
        System.out.println(MESSAGE_BOUNDARY+ "Hello! I'm Duke\n" + "What can I do for you?" + MESSAGE_BOUNDARY);
        //System.out.println("Bye. Hope to hear from you soon!\n"+ MESSAGE_BOUNDARY);
        List<String> myList;
        myList = new ArrayList<String>();
        while(repeat){
            //Gather input from user
            String userQuery = myscanner.nextLine();

            //Collect response and add to list/exits when appropriate
            repeat = continuechat(userQuery);
            if(userQuery.equals("list")){

                for(int i=0;i<myList.size();i++){
                    int index = i+1;
                    String task = myList.get(i);
                    System.out.println(index + ". " + task);

                }
                System.out.print(MESSAGE_BOUNDARY);
            }
            else if(repeat){
                myList.add(userQuery);
                System.out.print(MESSAGE_BOUNDARY +"added: " + userQuery + MESSAGE_BOUNDARY);
            }
            else {
                System.out.print(MESSAGE_BOUNDARY  + print_response(userQuery, repeat) + MESSAGE_BOUNDARY);
            }


        }
        myscanner.close();
    }

    public static void main(String[] args) {

        Duke myobj = new Duke();
        myobj.bot_respond();;

    }
}
