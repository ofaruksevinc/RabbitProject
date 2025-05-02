public class Rabbit {
    private Position position;
    private Direction direction;

    // Initial position: Top-left (A8 equivalent -> x=0, y=size-1)
    // Initial direction: South
    public Rabbit(int gridSize) {
        this.position = new Position(0, gridSize - 1);
        this.direction = Direction.SOUTH;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void turnRight() {
        switch (direction) {
            case NORTH: direction = Direction.EAST; break;
            case EAST:  direction = Direction.SOUTH; break;
            case SOUTH: direction = Direction.WEST; break;
            case WEST:  direction = Direction.NORTH; break;
        }
    }

    public void turnLeft() {
        switch (direction) {
            case NORTH: direction = Direction.WEST; break;
            case WEST:  direction = Direction.SOUTH; break;
            case SOUTH: direction = Direction.EAST; break;
            case EAST:  direction = Direction.NORTH; break;
        }
    }

    // Calculates the position one step forward
    public Position getNextPositionForward() {
        int x = position.x();
        int y = position.y();
        switch (direction) {
            case NORTH: y++; break;
            case SOUTH: y--; break;
            case EAST:  x++; break;
            case WEST:  x--; break;
        }
        return new Position(x, y);
    }

    // Calculates the position one step backward
    public Position getNextPositionBackward() {
        int x = position.x();
        int y = position.y();
        switch (direction) {
            case NORTH: y--; break; // Opposite of North is South
            case SOUTH: y++; break; // Opposite of South is North
            case EAST:  x--; break; // Opposite of East is West
            case WEST:  x++; break; // Opposite of West is East
        }
        return new Position(x, y);
    }

     // JUMP ('J') and DUCK ('I') moves require checking the next cell first,
     // so actual position update happens in the main loop after validation.
     // The simple move methods just update the rabbit's internal state.
    public void move(Position newPosition) {
        this.position = newPosition;
    }
} 