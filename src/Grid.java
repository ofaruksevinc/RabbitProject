import java.util.Random;

public class Grid {
    private final int size;
    private final char[][] board;
    private final Position holePosition;
    private static final Random random = new Random();

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
        updateCell(holePosition, HOLE);
    }

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


            if (getCell(pos) == EMPTY && !pos.equals(holePosition) && !(x==0 && y==size-1)) {
                 updateCell(pos, obstacleType);
                 count++;
            }
            
        }
    }

    public char getCell(Position pos) {
        if (isValidPosition(pos)) {
            return board[pos.y()][pos.x()];
        }
        return ' ';
    }

    public void updateCell(Position pos, char content) {
         if (isValidPosition(pos)) {
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

    public void display(Position rabbitPos) {
        System.out.println("--- Orman --- C: Sütun, R: Satır (0'dan başlar) ---");
        System.out.print("   ");
        for(int x = 0; x < size; x++) {
             System.out.print((char)('A' + x) + " ");
        }
        System.out.println();

        for (int y = size - 1; y >= 0; y--) {
             System.out.printf("%2d ", y + 1); 
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