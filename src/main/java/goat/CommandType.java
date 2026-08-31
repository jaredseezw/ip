package goat;

/**
 * Identifies the supported command words entered by the user.
 */
public enum CommandType {
    BYE("bye"),
    DELETE("delete"),
    DEADLINE("deadline"),
    EVENT("event"),
    LIST("list"),
    MARK("mark"),
    TODO("todo"),
    UNMARK("unmark"),
    UNKNOWN("");

    private final String commandWord;

    CommandType(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Finds the command type represented by a command word.
     *
     * @param commandWord first word of the user's input
     * @return matching command type, or {@link #UNKNOWN} if unsupported
     */
    public static CommandType from(String commandWord) {
        for (CommandType type : values()) {
            if (type.commandWord.equals(commandWord)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
