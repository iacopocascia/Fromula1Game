package it.unicam.formula1Game.cell;

/**
 * Enum representing the various types of cells that make up the track.
 * The cell types are used to represent walls,
 * the track itself, the start line, and finish line.
 */
public enum CellType {
    /**
     * Represents a wall cell.
     * It is denoted by the character {@code '*'}.
     */
    WALL('*'),

    /**
     * Represents an open track cell.
     * It is denoted by the character {@code ' '}.
     */
    TRACK(' '),

    /**
     * Represents the start line cell.
     * It is denoted by the character {@code '+'}.
     */
    START('+'),

    /**
     * Represents the finish line cell.
     * It is denoted by the character {@code '-'}.
     */
    FINISH('-');

    /**
     * The character value associated with the cell type.
     */
    private final char value;

    /**
     * Constructs a new {@code CellType} with the specified character value.
     *
     * @param c the character that represents the cell type
     */
    CellType(final char c) {
        this.value = c;
    }

    /**
     * Converts a character to the corresponding {@code CellType}.
     * Throws {@link IllegalArgumentException} if the character does not map to a valid {@code CellType}.
     *
     * @param symbol the character to convert
     * @return the corresponding {@code CellType}
     */
    public static CellType fromChar(char symbol) {
        return switch (symbol) {
            case '*' -> WALL;
            case '+' -> START;
            case '-' -> FINISH;
            case ' ' -> TRACK;
            default -> throw new IllegalArgumentException("Invalid track symbol: " + symbol);
        };
    }

    /**
     * Returns the character value associated with this cell type.
     *
     * @return the character representing the cell type
     */
    public char getValue() {
        return value;
    }
}
