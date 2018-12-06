package solvers;

import Nodes.DFSNode;
import Nodes.Node;

import java.util.*;

public class DFSSolver {
    private Stack<DFSNode> frontier;
    private ArrayList<DFSNode> visited;
    private Queue<int[][]> pathToGoal;
    private DFSNode initialNode;
    private DFSNode lastNode;
    private StringBuilder operations;
    private int nodesExpanded = 0;
    private int solutionDepth = 0;
    private double duration;
    private double startTime;
    private double endTime;

    public StringBuilder getOperations() {
        return operations;
    }

    public int getSolutionDepth() {
        return solutionDepth;
    }

    public int getNodesExpanded() {
        return nodesExpanded;
    }

    public double getDuration() {
        return duration;
    }

    public DFSSolver(int[][] initialState) {
        frontier = new Stack<>();
        visited = new ArrayList<DFSNode>();
        operations = new StringBuilder();
        this.initialNode = new DFSNode(initialState, null, 0);
        initialNode.setDirection("");
        frontier.push(initialNode);
    }

    public void solve() {
        DFSNode current;
        startTime = System.nanoTime();
        while (!frontier.isEmpty()) {
            current = frontier.pop();
            visited.add(current);
            if (current.isGoal()) {
                solutionDepth = current.getDepth();
                lastNode = current;
                break;
            }
            for (DFSNode neighbor : current.neighbors()) {
                if (!isVisited(neighbor.getBoard()) && !inFrontier(neighbor.getBoard())) {
                    frontier.add(neighbor);
                }
            }
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        prepairAnswer();
    }

    private void prepairAnswer() {
        nodesExpanded = (visited.size() - 1);
        Node current = lastNode;
        pathToGoal = new LinkedList<>();
        Stack<Node> temp = new Stack<Node>();
        while (current != null) {
            temp.push(current);
            current = current.getPreviousNode();
        }
        while (!temp.isEmpty()) {
            operations.append(temp.peek().getDirection() + ",");
            pathToGoal.add(temp.pop().getBoard());
        }
        operations.deleteCharAt(0);
        if((operations.length() - 1)>0){
            operations.deleteCharAt(operations.length() - 1);
        }
    }

    public Iterable<int[][]> solution() {
        return pathToGoal;
    }

    public Iterable<Node> solutionInOrder() {
        Node current = lastNode;
        Queue<Node> movesIncorrectOrder = new LinkedList<Node>();
        Stack<Node> moves = new Stack<Node>();
        while (current != null) {
            moves.push(current);
            current = current.getPreviousNode();
        }
        while (!moves.isEmpty()) {
            operations.append(moves.peek().getDirection() + ",");
            movesIncorrectOrder.add(moves.pop());

        }
        operations.deleteCharAt(0);
        operations.deleteCharAt(operations.length() - 1);
        return movesIncorrectOrder;
    }

    private boolean isVisited(int[][] testMatrix) {

        int[][] matriix2;
        for (Node current : visited) {
            matriix2 = current.getBoard();
            if (Arrays.deepEquals(matriix2, testMatrix)) {
                return true;
            }
        }
        return false;
    }


    private boolean inFrontier(int[][] testMatrix) {

        int[][] matriix2;
        if (frontier.isEmpty()) {
            return false;
        }
        for (Node current : frontier) {
            matriix2 = current.getBoard();
            if (Arrays.deepEquals(matriix2, testMatrix)) {
                return true;
            }
        }
        return false;
    }

}


//
//    private void printMatrix(int[][] matrix) {
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 3; col++)
//                System.out.print(String.format("%2d ", matrix[row][col]));
//            System.out.println();
//        }
//    }
//
//    public void prinVisited() {
//        for (Node current : visited) {
//            System.out.println(current.toString());
//        }
//    }
//
//    public void checkVisited() {
//        for (int i = 0; i < visited.size(); i++) {
//            for (int j = i + 1; j < visited.size(); j++) {
//                if (visited.get(i).isSame(visited.get(j))) {
//                    System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrror     " + i + "   " + j);
//                }
//            }
//        }
//        System.out.println("noooooooooooooooooerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrror     ");
//    }