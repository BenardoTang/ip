import java.util.Scanner;

public class Duke {

    private static final String MESSAGE_BOUNDARY = "\n____________________________________________________________\n";
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    private String print_response(String input, boolean repeat) {
        if (!repeat) {
            return "Bye, hope to see you soon!";
        } else
            return input;
    }

    private Boolean continuechat(String input) {
        return !input.equals("bye"); // returns FALSE if user inputs "bye"

    }

    public void bot_respond() {
        Boolean repeat = true;
        Scanner myscanner = new Scanner(System.in);
        System.out.println(LOGO);
        System.out.println(MESSAGE_BOUNDARY + "Hello! I'm Duke\n" + "What can I do for you?" + MESSAGE_BOUNDARY);
        //System.out.println("Bye. Hope to hear from you soon!\n"+ MESSAGE_BOUNDARY);

        while (repeat) {
            //Gather input from user
            String userQuery = myscanner.nextLine();

            //Collect response and echos/exits when appropriate
            repeat = continuechat(userQuery);
            String dukerespond = print_response(userQuery, repeat);
            System.out.println(dukerespond + MESSAGE_BOUNDARY);

        }
        myscanner.close();
    }


    public static void main(String[] args) {

        Duke myobj = new Duke();
        myobj.bot_respond();

    }
}