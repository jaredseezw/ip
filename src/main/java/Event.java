/**
 * Represents a task that occurs between specified start and end times.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an incomplete event with the given description and time range.
     *
     * @param description description of the event
     * @param from date or time at which the event starts
     * @param to date or time at which the event ends
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns this event in the data-file format.
     *
     * @return serialized event data
     */
    @Override
    public String toFileString() {
        return "E | " + getFileStatus() + " | " + description + " | " + from + " | " + to;
    }

    /**
     * Returns the event with its type, completion status, and time range.
     *
     * @return formatted event
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
