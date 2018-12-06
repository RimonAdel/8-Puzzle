package solvers;

import Nodes.Node;
import Nodes.aStarNode;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class AStarSolver {
    MinPQ<aStarNode> frontier;
    private ArrayList<aStarNode> visited;
    private Queue<int[][]> pathToGoal;
    private aStarNode initialNode;
    private aStarNode lastNode;
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

    public AStarSolver(int[][] initialState) {
        frontier = new MinPQ<aStarNode>();
        visited = new ArrayList<>();
        operations = new StringBuilder();
        this.initialNode = new aStarNode(initialState, null, 0);
        initialNode.setDirection("");
        frontier.insert(initialNode);
    }

    public void solve() {
        aStarNode current;
        startTime = System.nanoTime();
        while (!frontier.isEmpty()) {
            current = frontier.delMin();
            visited.add(current);
            if (current.isGoal()) {
                solutionDepth = current.getDepth();
                lastNode = current;
                break;
            }
            for (aStarNode neighbor : current.neighbors()) {
                if (current.getPreviousNode() == null || (!neighbor.isSame(current.getPreviousNode()) && !isVisited(neighbor.getBoard()))) {
                    frontier.insert(neighbor);
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

    public Iterable<int[][]> solution(){
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

}

// 6,1,8,4,0,2,7,3,5