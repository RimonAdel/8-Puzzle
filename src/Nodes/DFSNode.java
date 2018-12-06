package Nodes;

import java.util.LinkedList;

public class DFSNode extends Node {

    public DFSNode(int[][] array, Node previousNode, int depth) {
        super(array, previousNode, depth);
    }

    public DFSNode(int[][] array, Node previousNode, int depth, String direction) {
        super(array, previousNode, depth, direction);
    }
    @Override
    public Iterable<DFSNode> neighbors() {
        LinkedList<DFSNode> neighbors = new LinkedList<DFSNode>();
        int[] location = getZeroCoordinates();
        int spaceRow = location[0];
        int spaceCol = location[1];
        if (spaceCol < 2) neighbors.add(new DFSNode  (swap(spaceRow, spaceCol, spaceRow, spaceCol + 1),this,depth+1,"Right"));
        if (spaceCol > 0) neighbors.add(new DFSNode(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1),this,depth+1,"Left"));
        if (spaceRow < 2) neighbors.add(new DFSNode(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol),this,depth+1,"Down"));
        if (spaceRow > 0) neighbors.add(new DFSNode(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol),this,depth+1,"UP"));
        return neighbors;
    }
}
