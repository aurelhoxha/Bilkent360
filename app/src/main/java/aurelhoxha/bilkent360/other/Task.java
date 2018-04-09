package aurelhoxha.bilkent360.other;

/**
 * Created by aurel on 6/24/17.
 */

public class Task {

    private String task;

    public Task() {}

    public Task(String task) {
        this.task = task;
    }

    public String getTitle() {
        return task;
    }

    public void setTitle(String task) {
        this.task = task;
    }
}