package Nodes;

import java.util.LinkedList;

public class aStarNode extends Node implements Comparable<aStarNode>{
    public aStarNode(int[][] array, Node previousNode, int depth) {
        super(array, previousNode, depth);
    }

    public aStarNode(int[][] array, Node previousNode, int depth, String direction) {
        super(array, previousNode, depth, direction);
        this.numMoves = previousNode.numMoves  + 1;

    }

    public int hamming() {                   // number of blocks out of place
        int counter = 0;
        int size = this.board[0].length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    continue;
                } else if ((row * size + col+1) != board[row][col]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int manhattan(){                 // sum of Manhattan distances between blocks and goal
        int counter = 0;
        int size = board[0].length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int indexofrow=((board[row][col])/board[0].length);
                int indexofcol=(board[row][col])-board[0].length*indexofrow;
//                if (board[row][col] == 0) {
//                    continue;
//                } else if (indexofrow != row || indexofcol !=col){
                    counter+=Math.abs(indexofrow-row);
                    counter+=Math.abs(indexofcol-col);
//                }
            }
        }
        return counter;
    }

    public int euclideanDistance(){                 // sum of euclideanDistance between blocks and goal
        int counter = 0;
        int size = board[0].length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int indexofrow=((board[row][col])/board[0].length);
                int indexofcol=(board[row][col])-board[0].length*indexofrow;
//                if (board[row][col] == 0) {
//                    continue;
//                } else if (indexofrow != row || indexofcol !=col){
                    counter+=Math.sqrt(Math.pow(row-indexofrow,2)+Math.pow(col-indexofcol,2));
//                }
            }
        }
        return counter;
    }

    public Iterable<aStarNode> neighbors() {     // all neighboring boards
        LinkedList<aStarNode> neighbors = new LinkedList<aStarNode>();
        int[] location = getZeroCoordinates();
        int spaceRow = location[0];
        int spaceCol = location[1];
        if (spaceRow > 0) neighbors.add(new aStarNode(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol),this,depth+1,"UP"));
        if (spaceRow < 2) neighbors.add(new aStarNode(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol),this,depth+1,"Down"));
        if (spaceCol > 0) neighbors.add(new aStarNode(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1),this,depth+1,"Left"));
        if (spaceCol < 2) neighbors.add(new aStarNode(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1),this,depth+1,"Right"));
        return neighbors;
    }

    @Override
    public int compareTo(aStarNode node) {
        return (this.manhattan() - node.manhattan()) + (this.numMoves - node.numMoves);
    }

//    @Override
//    public int compareTo(aStarNode node) {
//        return (this.euclideanDistance() - node.euclideanDistance()) + (this.numMoves - node.numMoves);
//    }
}
