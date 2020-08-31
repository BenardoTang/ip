public class DukeIllegalArgumentException extends Exception{
    public DukeIllegalArgumentException(String message) {
        super(message);
    }
}

/* use when user uses wrong args, example: done two instead of done 2, or done 5 when user only has 4 tasks or
when user inputs deadline but no /by
 */