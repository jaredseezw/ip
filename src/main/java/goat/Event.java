package goat;

import java.time.LocalDate;

/**
 * Represents a task that occurs between specified start and end dates.
 */
public class Event extends Task {
    /** First date of this event. */
    protected LocalDate from;
    /** Last date of this event. */
    protected LocalDate to;

    /**
     * Creates an incomplete event with the given description and date range.
     *
     * @param description description of the event
     * @param from date on which the event starts
     * @param to date on which the event ends
     */
    public Event(String description, LocalDate from, LocalDate to) {
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
        return formatFileData("E", from.toString(), to.toString());
    }

    /**
     * Returns the event with its type, completion status, and date range.
     *
     * @return formatted event
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + TaskDate.format(from)
                + " to: " + TaskDate.format(to) + ")";
    }
}
