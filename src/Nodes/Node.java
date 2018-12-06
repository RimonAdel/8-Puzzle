package Nodes;

public abstract class Node {
    protected int[][] board;
    protected int depth;
    protected String direction;
    protected Node previousNode;

    public int getNumMoves() {
        return numMoves;
    }

    protected int numMoves = 0;

    public Node(int[][] array, Node previousNode, int depth) {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = array[i][j];
            }
        }
        this.previousNode = previousNode;
        this.depth = depth;
    }

    public Node(int[][] array, Node previousNode, int depth, String direction) {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = array[i][j];
            }
        }
        this.previousNode = previousNode;
        this.depth = depth;
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int[][] getBoard() {
        int[][] array = new int[3][3];
        for (int i = 0; i < 3; i++) {
            array[i] = board[i].clone();
        }
        return array;
    }

    public int getDepth() {
        return depth;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public int[] getZeroCoordinates() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == 0) {
                    return (new int[]{row, col});
                }
            }
        }
        return (new int[]{-1});
    }

    public boolean isSame(Node n) {
        boolean flag = true;
        int[][] array = n.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (array[i][j] != board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGoal() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != (i * 3) + j) {
                    return false;
                }
            }
        }
        System.out.println("ingoal");
        return true;
    }

    public abstract Iterable neighbors() ;

//    {     // all neighboring boards
//        LinkedList<Node> neighbors = new LinkedList<Node>();
//        int[] location = getZeroCoordinates();
//        int spaceRow = location[0];
//        int spaceCol = location[1];
//        if (spaceRow > 0)
//            neighbors.add(new Node(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol), this, depth + 1, "UP"));
//        if (spaceRow < 2)
//            neighbors.add(new Node(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol), this, depth + 1, "Down"));
//        if (spaceCol > 0)
//            neighbors.add(new Node(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1), this, depth + 1, "Left"));
//        if (spaceCol < 2)
//            neighbors.add(new Node(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1), this, depth + 1, "Right"));
//        return neighbors;
//    }

    private int[][] copyArray(int[][] array) {
        int[][] copy = new int[array[0].length][array[0].length];
        for (int i = 0; i < array[0].length; i++) {
            copy[i] = array[i].clone();
        }
        return copy;
    }

    protected int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = copyArray(board);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++)
                str.append(String.format("%2d ", board[row][col]));
            str.append("\n");
        }
        str.append("\n");
        return str.toString();
    }

}
