package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import solvers.AStarSolver;
import solvers.BFSSolver;
import solvers.DFSSolver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class Main extends Application {

    private Stage main_Window;
    private Scene scene;
    private BorderPane mainLayout;
    private VBox topLayout;
    private GridPane topLayoutMid;
    private HBox topLayoutUpper;
    private HBox topLayoutLower;
    private VBox bottomLayout;
    private GridPane bottomLayoutUpper;
    private HBox bottomLayoutLower;
    private GridPane gridPane;
    private VBox centerLayout;
    private ScrollPane centerLayoutScroll;
    private Button BFS;
    private Button DFS;
    private Button AStar;
    private Button Solve;
    private Label path;
    private Label costOfPath;
    private Label nodesExpanded;
    private Label searchDepth;
    private Label time;
    private Label pathValu;
    private Label costOfPathValu;
    private Label nodesExpandedValu;
    private Label searchDepthValu;
    private Label timeValu;
    private TextField[][] textFields;
    private float loading = -1.0f;
    int whitchSearch = 0;

    public Main() {
        // creation
        mainLayout = new BorderPane();
        scene = new Scene(mainLayout, 500, 500);

        topLayout = new VBox();
        topLayoutUpper = new HBox();
        topLayoutMid = new GridPane();
        topLayoutLower = new HBox();

        centerLayout = new VBox();
        centerLayoutScroll = new ScrollPane();

        bottomLayout = new VBox();
        bottomLayoutUpper = new GridPane();
        bottomLayoutLower = new HBox();

        BFS = new Button("BFS");
        DFS = new Button("DFS");
        AStar = new Button("A*");
        Solve = new Button("Solve");
        path = new Label("Path to Goal is :");
        costOfPath = new Label("cost_of_path is :");
        nodesExpanded = new Label("nodes_expanded :");
        searchDepth = new Label("search_depth is :");
        time = new Label("time is :");
        pathValu = new Label("\t0");
        costOfPathValu = new Label("\t0");
        nodesExpandedValu = new Label("\t0");
        searchDepthValu = new Label("\t0");
        timeValu = new Label("\t0");
        textFields = new TextField[3][3];
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinHeight(1000);

        primaryStage.setMinWidth(1000);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMaxWidth(1000);
        main_Window = primaryStage;
        main_Window.setMaximized(true);
        main_Window.setTitle("AI project");

        // general layouts setting
        mainLayout.setTop(topLayout);
        mainLayout.setCenter(centerLayoutScroll);
        centerLayoutScroll.setContent(centerLayout);
        centerLayout.setSpacing(10);
        centerLayout.setPadding(new Insets(0, 0, 0, 400));
        bottomLayout.getChildren().add(bottomLayoutUpper);
        bottomLayout.getChildren().add(bottomLayoutLower);
        mainLayout.setBottom(bottomLayout);
        //end layouts setting

        //top layout components & settings
        topLayout.getChildren().add(topLayoutUpper);
        topLayout.getChildren().add(topLayoutMid);
        //top LayoutUpper
        topLayoutUpper.setAlignment(Pos.CENTER);
        topLayoutUpper.setSpacing(10);
        topLayoutUpper.setPadding(new Insets(10, 10, 10, 10));
        topLayoutUpper.setStyle("-fx-background-color: rgb(" + 0 + "," + 0 + ", " + 102 + ");");
        topLayoutUpper.getChildren().add(BFS);
        topLayoutUpper.getChildren().add(DFS);
        topLayoutUpper.getChildren().add(AStar);
        //end LayoutUpper
        //Top Mid Layout Components
        topLayoutMid.getColumnConstraints().add(new ColumnConstraints(100)); // column 0 is 100 wide
        topLayoutMid.getColumnConstraints().add(new ColumnConstraints(100)); // column 1 is 200 wide
        topLayoutMid.getColumnConstraints().add(new ColumnConstraints(100)); // column 2 is 100 wide
        topLayoutMid.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                textFields[i][j] = new TextField("0");
                topLayoutMid.add(textFields[i][j], j, i);
            }
        }
        //end

        //bottom layout component
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.setSpacing(10);
        bottomLayout.setPadding(new Insets(10, 10, 10, 10));
        bottomLayout.getChildren().add(Solve);
        //end bottom layout component

        //bottomLayoutUpper
        bottomLayoutUpper.add(path, 0, 0);
        bottomLayoutUpper.add(costOfPath, 0, 1);
        bottomLayoutUpper.add(nodesExpanded, 0, 2);
        bottomLayoutUpper.add(searchDepth, 0, 3);
        bottomLayoutUpper.add(time, 0, 4);
        bottomLayoutUpper.add(pathValu, 1, 0);
        bottomLayoutUpper.add(costOfPathValu, 1, 1);
        bottomLayoutUpper.add(nodesExpandedValu, 1, 2);
        bottomLayoutUpper.add(searchDepthValu, 1, 3);
        bottomLayoutUpper.add(timeValu, 1, 4);

        // buttons code
        BFS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                whitchSearch = 1;
            }
        });
        //
        DFS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                whitchSearch = 2;
            }
        });
        AStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                whitchSearch = 3;
            }
        });

        Solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int[] checkArray = {1, 1, 1, 1, 1, 1, 1, 1, 1};
                int[][] initialState = new int[3][3];
                boolean validInput = true;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        checkArray[Integer.valueOf(textFields[i][j].getText())]--;
                        initialState[i][j] = Integer.valueOf(textFields[i][j].getText());
                        if (checkArray[Integer.valueOf(textFields[i][j].getText())] < 0) {
                            validInput = false;
                        }
                    }
                }
                if (!validInput) {
                    Solve.setStyle("-fx-border-color: red ;  ");
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("error ,at least two nodes have same value.");
                    errorAlert.showAndWait();
                } else if (whitchSearch == 0) {
                    Solve.setStyle("-fx-border-color: red ;  ");
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("select search type ,please");
                    errorAlert.showAndWait();
                } else {
                    Solve.setDisable(true);
                    centerLayout.getChildren().clear();
                    ProgressIndicator pin = new ProgressIndicator();
                    pin.setProgress(loading);
                    centerLayout.setSpacing(5);
                    centerLayout.setAlignment(Pos.CENTER);
                    centerLayout.getChildren().add(pin);
                    Solve.setStyle("-fx-border-color: green ;");
                    switch (whitchSearch) {
                        case 1: {
                            System.out.println("1");                 //BFS
                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {

                                    BFSSolver bfsSolver = new BFSSolver(initialState);
                                    bfsSolver.solve();
                                    writeToFile("BFS" + twoDArrayToString(initialState), bfsSolver.solution(), bfsSolver.getOperations(), bfsSolver.getSolutionDepth(), bfsSolver.getNodesExpanded(), bfsSolver.getDuration());
                                    Platform.runLater(() -> {
                                        int counter = 0;
                                        for (int[][] each : bfsSolver.solution()) {
                                            if (counter < 4000) {
                                                GridPane gridPane = getGridPane(each);
                                                centerLayout.getChildren().add(gridPane);
                                            }
                                        }
                                        pathValu.setText("\t" + bfsSolver.getOperations().toString());
                                        costOfPathValu.setText("\t" + String.valueOf(bfsSolver.getSolutionDepth()));
                                        nodesExpandedValu.setText("\t" + String.valueOf(bfsSolver.getNodesExpanded()));
                                        searchDepthValu.setText("\t" + String.valueOf(bfsSolver.getSolutionDepth()));
                                        timeValu.setText("\t" + String.valueOf(bfsSolver.getDuration()));
                                        centerLayout.getChildren().removeAll(pin);
                                        Solve.setDisable(false);
                                    });
                                    return null;
                                }
                            };
                            new Thread(task).start();
                            break;
                        }
                        case 2: {
                            System.out.println("2");// DFS
                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    DFSSolver dfsSolver = new DFSSolver(initialState);
                                    dfsSolver.solve();
                                    writeToFile("DFS" + twoDArrayToString(initialState), dfsSolver.solution(), dfsSolver.getOperations(), dfsSolver.getSolutionDepth(), dfsSolver.getNodesExpanded(), dfsSolver.getDuration());
                                    Platform.runLater(() -> {
                                        int counter = 0;
                                        for (int[][] each : dfsSolver.solution()) {
                                            if (counter < 4000) {
                                                GridPane gridPane = getGridPane(each);
                                                centerLayout.getChildren().add(gridPane);
                                                counter++;
                                            }
                                            counter++;
                                        }
                                        pathValu.setText("\t" + dfsSolver.getOperations().toString());
                                        costOfPathValu.setText("\t" + String.valueOf(dfsSolver.getSolutionDepth()));
                                        nodesExpandedValu.setText("\t" + String.valueOf(dfsSolver.getNodesExpanded()));
                                        searchDepthValu.setText("\t" + String.valueOf(dfsSolver.getSolutionDepth()));
                                        timeValu.setText("\t" + String.valueOf(dfsSolver.getDuration()));
                                        centerLayout.getChildren().removeAll(pin);
                                        Solve.setDisable(false);
                                    });
                                    return null;
                                }
                            };
                            new Thread(task).start();
                            break;
                        }
                        case 3: {
                            System.out.println("3");  //A*
                            Task<Void> task = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    AStarSolver aStarSolver = new AStarSolver(initialState);
                                    aStarSolver.solve();
                                    writeToFile("A*" + twoDArrayToString(initialState), aStarSolver.solution(), aStarSolver.getOperations(), aStarSolver.getSolutionDepth(), aStarSolver.getNodesExpanded(), aStarSolver.getDuration());
                                    Platform.runLater(() -> {
                                        for (int[][] each : aStarSolver.solution()) {
                                            GridPane gridPane = getGridPane(each);
                                            centerLayout.getChildren().add(gridPane);
                                        }
                                        pathValu.setText("\t" + aStarSolver.getOperations().toString());
                                        costOfPathValu.setText("\t" + String.valueOf(aStarSolver.getSolutionDepth()));
                                        nodesExpandedValu.setText("\t" + String.valueOf(aStarSolver.getNodesExpanded()));
                                        searchDepthValu.setText("\t" + String.valueOf(aStarSolver.getSolutionDepth()));
                                        timeValu.setText("\t" + String.valueOf(aStarSolver.getDuration()));
                                        centerLayout.getChildren().removeAll(pin);
                                        Solve.setDisable(false);
                                    });
                                    return null;
                                }
                            };
                            new Thread(task).start();
                            break;
                        }
                    }
                }
            }
        });

        main_Window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                main_Window.close();
            }
        });

        main_Window.setScene(scene);
        main_Window.show();
    }


    private GridPane getGridPane(int[][] board) {
        GridPane newGridPane = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                newGridPane.add(new ImageView(new Image("new/" + (board[i][j]) + ".jpeg")), j, i);
            }
        }
        return newGridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void writeToFile(String name, Iterable<int[][]> boards, StringBuilder operations, int solutionDepth, int numberOfNodesExpanded, double duration) {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File("files" + File.separator + name + ".txt");
            writer = new BufferedWriter(new FileWriter(logFile));
            for (int[][] board : boards) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        writer.write(board[i][j] + " ");
                    }
                    writer.newLine();
                }
                writer.newLine();
            }
            writer.write("path_to_goal :\t" + operations + "\n");
            writer.write("cost_of_path:\t" + solutionDepth + "\n");
            writer.write("nodes_expanded:\t" + numberOfNodesExpanded + "\n");
            writer.write("search_depth:\t" + solutionDepth + "\n");
            writer.write("running_time\t" + duration + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    private String twoDArrayToString(int[][] board) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                stringBuilder.append(board[i][j] + ",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

}