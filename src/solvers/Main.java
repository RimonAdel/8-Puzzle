//package solvers;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//
//        Scanner scanner = new Scanner(System.in);
//        int[][] blocks = new int[3][3];
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 3; j++)
//                blocks[i][j] = scanner.nextInt();
//        System.out.println("stop");
//
//            BFSSolver bfsSolver = new BFSSolver(blocks);
//            bfsSolver.solve();
//            for (int[][] board : bfsSolver.solution())
//                printMatrix(board);
//            System.out.println("path_to_goal :\t"+bfsSolver.getOperations());
//            System.out.println("cost_of_path:\t"+bfsSolver.getSolutionDepth());
//            System.out.println("nodes_expanded:\t"+bfsSolver.getNodesExpanded());
//            System.out.println("search_depth:\t"+bfsSolver.getSolutionDepth());
//            System.out.println("running_time\t"+bfsSolver.getDuration());
//            writeToFile("BFS"+twoDArrayToString(blocks),bfsSolver.solution(),bfsSolver.getOperations(), bfsSolver.getSolutionDepth(),bfsSolver.getNodesExpanded(),bfsSolver.getDuration());
//
//
////        DFSSolver dfsSolver = new DFSSolver(blocks);
////        dfsSolver.solve();
////        for (int[][] board : dfsSolver.solution())
////            printMatrix(board);
////        System.out.println("path_to_goal :\t"+dfsSolver.getOperations());
////        System.out.println("cost_of_path:\t"+dfsSolver.getSolutionDepth());
////        System.out.println("nodes_expanded:\t"+dfsSolver.getNodesExpanded());
////        System.out.println("search_depth:\t"+dfsSolver.getSolutionDepth());
////        System.out.println("running_time\t"+dfsSolver.getDuration());
////        writeToFile("DFS"+twoDArrayToString(blocks),dfsSolver.solution(),dfsSolver.getOperations(), dfsSolver.getSolutionDepth(),dfsSolver.getNodesExpanded(),dfsSolver.getDuration());
//
////
////        AStarSolver aStarSolver = new AStarSolver(blocks);
////        aStarSolver.solve();
////        for (int[][] board : aStarSolver.solution())
////            printMatrix(board);
////        System.out.println("path_to_goal :\t"+aStarSolver.getOperations());
////        System.out.println("cost_of_path:\t"+aStarSolver.getSolutionDepth());
////        System.out.println("nodes_expanded:\t"+aStarSolver.getNodesExpanded());
////        System.out.println("search_depth:\t"+aStarSolver.getSolutionDepth());
////        System.out.println("running_time\t"+aStarSolver.getDuration());
////        writeToFile("A*"+twoDArrayToString(blocks),aStarSolver.solution(),aStarSolver.getOperations(), aStarSolver.getSolutionDepth(),aStarSolver.getNodesExpanded(),aStarSolver.getDuration());
//    }
//
//    private static void writeToFile(String name,Iterable<int[][]> boards, StringBuilder operations, int solutionDepth, int numberOfNodesExpanded, double duration) {
//        BufferedWriter writer = null;
//        try {
//            //create a temporary file
//            File logFile = new File("files" + File.separator + name+".txt");
//            writer = new BufferedWriter(new FileWriter(logFile));
//            for (int[][] board : boards) {
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        writer.write(board[i][j] + " ");
//                    }
//                    writer.newLine();
//                }
//                writer.newLine();
//            }
//            writer.write("path_to_goal :\t" + operations+"\n");
//            writer.write("cost_of_path:\t" + solutionDepth+"\n");
//            writer.write("nodes_expanded:\t" + numberOfNodesExpanded+"\n");
//            writer.write("search_depth:\t" + solutionDepth+"\n");
//            writer.write("running_time\t" + duration+"\n");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // Close the writer regardless of what happens...
//                writer.close();
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    private static String twoDArrayToString(int[][] board){
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < 3; i++){
//            for (int j = 0; j < 3; j++)
//                stringBuilder.append(board[i][j]+",");
//        }
//        stringBuilder.deleteCharAt(stringBuilder.length()-1);
//        return stringBuilder.toString();
//    }
//
//    private static void printMatrix(int[][] matrix) {
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 3; col++)
//                System.out.print(String.format("%2d ", matrix[row][col]));
//            System.out.println();
//        }
//        System.out.println();
//    }
//}