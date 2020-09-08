package Duke;

public abstract class Task {

        protected String description;
        protected boolean isDone;
        protected final String TASK_DONE_NOTATION = ":)";
        protected final String TASK_NOT_DONE_NOTATION = ":(";

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }
        public String getDescription(){
        return this.description;
        }
        public String getStatusIcon() {
            return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
        }

        public void markAsDone() {
            this.isDone = true;
        }
        public String toString() {
            return String.format("[%s]%s", getStatusIcon(), this.description);
        }
        public abstract String[] getTaskData();

}
