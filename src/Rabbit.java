public class Rabbit {
    private Position position;
    private Direction direction;

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

    public Position getNextPositionBackward() {
        int x = position.x();
        int y = position.y();
        switch (direction) {
            case NORTH: y--; break; 
            case SOUTH: y++; break; 
            case EAST:  x--; break; 
            case WEST:  x++; break; 
        }
        return new Position(x, y);
    }

    public void move(Position newPosition) {
        this.position = newPosition;
    }
} 