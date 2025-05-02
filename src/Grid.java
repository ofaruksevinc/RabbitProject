import java.util.Random;

public class Grid {
    private final int size;
    private final char[][] board;
    private final Position holePosition;
    private static final Random random = new Random();

    // Define characters for items on the board
    public static final char EMPTY = '.';
    public static final char RABBIT = 'T';
    public static final char HOLE = 'H';
    public static final char WOLF = 'K'; // Kurt
    public static final char FOX = 'X';  // Tilki
    public static final char WIRE = 'W'; // Dikenli Tel (Duck)
    public static final char FENCE = 'F'; // Çit (Jump)

    public Grid(int size) {
        this.size = size;
        this.board = new char[size][size];
        // Hole position: Bottom-right (H1 equivalent -> x=size-1, y=0)
        this.holePosition = new Position(size - 1, 0);
        initializeBoard();
        placeObstacles();
    }

    private void initializeBoard() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                board[y][x] = EMPTY;
            }
        }
        // Place the hole
        updateCell(holePosition, HOLE);
    }

    // Place obstacles randomly, ensuring no overlaps and respecting max counts
    private void placeObstacles() {
        placeObstacleType(WOLF, 4); // Max 4 Kurt
        placeObstacleType(FOX, 4);  // Max 4 Tilki
        placeObstacleType(WIRE, 4); // Max 4 Dikenli Tel
        placeObstacleType(FENCE, 4);// Max 4 Çit
    }

    private void placeObstacleType(char obstacleType, int maxCount) {
        int count = 0;
        while (count < maxCount) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            Position pos = new Position(x, y);

            // Ensure the cell is empty and not the hole or potential rabbit start
            if (getCell(pos) == EMPTY && !pos.equals(holePosition) && !(x==0 && y==size-1)) {
                 updateCell(pos, obstacleType);
                 count++;
            }
            // Basic protection against infinite loops if the board is too full,
            // though unlikely with current constraints.
            // A more robust solution might track placement attempts.
        }
    }

    public char getCell(Position pos) {
        if (isValidPosition(pos)) {
            // IMPORTANT: Array access is board[y][x], but Position is (x, y)
            return board[pos.y()][pos.x()];
        }
        return ' ';// Indicate out of bounds
    }

    public void updateCell(Position pos, char content) {
         if (isValidPosition(pos)) {
            // IMPORTANT: Array access is board[y][x], but Position is (x, y)
            board[pos.y()][pos.x()] = content;
        }
    }

    public boolean isValidPosition(Position pos) {
        return pos.x() >= 0 && pos.x() < size && pos.y() >= 0 && pos.y() < size;
    }

    public Position getHolePosition() {
        return holePosition;
    }

    public int getSize() {
        return size;
    }

    // Method to display the grid (optional, but useful for debugging/visualization)
    public void display(Position rabbitPos) {
        System.out.println("--- Orman --- C: Sütun, R: Satır (0'dan başlar) ---");
        // Print column headers (A, B, C...)
        System.out.print("   ");
        for(int x = 0; x < size; x++) {
             System.out.print((char)('A' + x) + " ");
        }
        System.out.println();

        // Print rows (8, 7, 6...) with content
        for (int y = size - 1; y >= 0; y--) {
             System.out.printf("%2d ", y + 1); // Row number (1-indexed)
            for (int x = 0; x < size; x++) {
                Position currentPos = new Position(x, y);
                if (currentPos.equals(rabbitPos)) {
                    System.out.print(RABBIT + " ");
                } else {
                    System.out.print(board[y][x] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("-------------");
    }
} 