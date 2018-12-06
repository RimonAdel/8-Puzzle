package Nodes;

import java.util.LinkedList;

public class NormalNode extends Node{


    public NormalNode(int[][] array, Node previousNode, int depth) {
        super(array, previousNode, depth);
    }

    public NormalNode(int[][] array, Node previousNode, int depth, String direction) {
        super(array, previousNode, depth, direction);
    }

    @Override
    public Iterable<NormalNode> neighbors() {
        LinkedList<NormalNode> neighbors = new LinkedList<NormalNode>();
        int[] location = getZeroCoordinates();
        int spaceRow = location[0];
        int spaceCol = location[1];
        if (spaceRow > 0) neighbors.add(new NormalNode(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol),this,depth+1,"UP"));
        if (spaceRow < 2) neighbors.add(new NormalNode(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol),this,depth+1,"Down"));
        if (spaceCol > 0) neighbors.add(new NormalNode(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1),this,depth+1,"Left"));
        if (spaceCol < 2) neighbors.add(new NormalNode  (swap(spaceRow, spaceCol, spaceRow, spaceCol + 1),this,depth+1,"Right"));
        return neighbors;
    }
}
