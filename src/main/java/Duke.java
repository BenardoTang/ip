public class Duke {

    private static final String MESSAGE_BOUNDARY = "\n____________________________________________________________\n";

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        System.out.println(MESSAGE_BOUNDARY + "Hello! I'm Duke\n" + "What can I do for you?" + MESSAGE_BOUNDARY);
        System.out.println("Bye. Hope to hear from you soon!"+ MESSAGE_BOUNDARY);
    }
}
