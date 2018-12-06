package solvers;

import Nodes.Node;
import Nodes.NormalNode;
import java.util.*;

public class BFSSolver {
    private Queue<NormalNode> frontier ;
    private ArrayList<NormalNode> visited ;
    private Queue<int[][]> pathToGoal ;
    private NormalNode initialNode;
    private StringBuilder operations;
    private Node lastNode;
    private int nodesExpanded ;
    private int solutionDepth=0;
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

    public BFSSolver(int[][] initialState){
        this.initialNode =new NormalNode(initialState,null,0);
        initialNode.setDirection("");
        operations = new StringBuilder();
        frontier = new LinkedList<NormalNode>();
        visited = new ArrayList<NormalNode>();
        frontier.add(initialNode);
    }

    public void solve(){
        startTime = System.nanoTime();
        NormalNode current ;
        while (!frontier.isEmpty() ){
            current = frontier.remove();
            visited.add(current);
            if(current.isGoal()){
                solutionDepth = current.getDepth();
                lastNode = current;
                break;
            }
            for ( NormalNode neighbor : current.neighbors()) {
                if (!isVisited(neighbor.getBoard()) && !inFrontier(neighbor.getBoard())) {
                    frontier.add(neighbor);
                }
            }
        }
        endTime = System.nanoTime();
        duration = (endTime - startTime)/1000000000.0;
        prepairAnswer();
    }

    private void prepairAnswer(){
        nodesExpanded = (visited.size()-1);
        Node current = lastNode;
        pathToGoal  = new LinkedList<>() ;
        Stack<Node> temp = new Stack<Node>();
        while (current != null) {
            temp.push(current);
            current = current.getPreviousNode();
        }
        while(!temp.isEmpty()){
            operations.append(temp.peek().getDirection()+",");
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
        while(!moves.isEmpty()){
            operations.append(moves.peek().getDirection()+",");
            movesIncorrectOrder.add(moves.pop());

        }
        operations.deleteCharAt(0);
        operations.deleteCharAt(operations.length()-1);
        return movesIncorrectOrder;
    }

    private boolean isVisited(int[][] testMatrix){
    int[][] matriix2;
        for (Node current :visited) {
         matriix2 = current.getBoard();
        if (Arrays.deepEquals(matriix2,testMatrix)){
            return true;
        }
    }
        return false;
    }


    private boolean inFrontier(int[][] testMatrix){
    int[][] matriix2;
         if (frontier.isEmpty()){
        return false;
    }
        for (Node current :frontier){
        matriix2 = current.getBoard();
        if (Arrays.deepEquals(matriix2,testMatrix)){
            return true;
        }
    }
        return false;
    }
}
