import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Observable;
import java.util.Observer;



public class GUI extends Application implements Observer {
    private final static int w = 7;
    private final static int h = 6;
    static int player1 = 1;
    static int player2 = 2;
     static int winner = 3;
    private static Color player1Color;
    private static Color player2Color;
    private GameModel connect4;
    private Stage stage;
    private BorderPane pane;
    private GridPane grid;
    private Text text;


    public static boolean full;
    public static boolean colFull;


    public void init(){

        this.connect4 = new GameModel();
        connect4.addObserver(this);
        full = false;
        colFull = false;
    }

    public void start(Stage stage){
        this.stage = stage;
        displayMain();
    }

    private HBox makeButtonPane() {
        HBox buttons = new HBox();

        for (int i = 1; i <= w; i++) {
            int col = i;
            Button button = new Button("" + col + "");
            button.setPrefWidth(100);
            button.setOnAction(click -> {
                connect4.selectColumn(connect4.getPlayer(), col);});
            buttons.getChildren().add(button);
        }

        return buttons;
    }

    public GridPane makeGridPane(){
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: White;");

        for (int i = h; i > 0; i--){
            for (int j = 0; j < w; j++){
                Circle circle = new Circle(50, Color.WHITE);
                circle.setStroke(Color.BLACK);
                grid.add(circle, j, i-1);
            }
        }

        return grid;
    }

    public void winner(boolean bool)
    {
        String result;
        if (bool) {
          result = new String("GAME OVER! PLAYER " + connect4.getWinningPlayer() + " WINS!");
           System.out.println(result);
        } else {
            result = new String("GAME OVER! BOARD IS FULL!");
           System.out.println(result);
        }

    }


    public void update(Observable o, Object arg){
        boolean over = false;

        if (colFull == true){
            this.text.setFill(Color.RED);
            this.text.setText("COLUMN IS FULL. PLAYER " + connect4.getPlayer() + " PLEASE TRY AGAIN.");
        } else {
            this.text.setFill(Color.BLACK);
            this.text.setText("Player " + connect4.getPlayer() + "'s turn!");
        }

        for (Node i : grid.getChildren()) {
            int idx = grid.getChildren().indexOf(i);
            Circle x = (Circle) i;


            if (connect4.getBoard().numbers[idx] == 0) {
                x.setFill(Color.WHITE);
            }

            if (connect4.getBoard().numbers[idx] == player1) {
                x.setFill(player1Color);
            }

            if (connect4.getBoard().numbers[idx] == player2) {
                x.setFill(player2Color);
            }

            if (connect4.getBoard().numbers[idx] == winner) {
                x.setFill(Color.GREEN);

                this.text.setText("GAME OVER! PLAYER " + connect4.getWinningPlayer() + " WINS!");
                over = true;
            }
        }

        colFull = false;

        if (over){
            disableButtons();
            winner(true);
        }
        if (full){
            disableButtons();
           winner(false);
           connect4.clearBoard();
        }


    }




    public void disableButtons(){
        FlowPane pane = ((FlowPane)((BorderPane) this.pane.getCenter()).getTop());
        HBox box = (HBox)pane.getChildren().get(0);
        for (Node button: box.getChildren()){
            Button k = (Button) button;
            k.setDisable(true);
        }
    }





    public void displayMain(){

        player1Color = Color.RED;
        player2Color = Color.BLACK;
        BorderPane pane = new BorderPane();

        //top
        FlowPane topPane = new FlowPane();
        this.text = new Text("Player " + connect4.getPlayer() + "'s turn!");
        topPane.setAlignment(Pos.CENTER);
        topPane.getChildren().add(text);
        pane.setTop(topPane);

        //center
        BorderPane innerPane = new BorderPane();

        FlowPane top = new FlowPane();
        HBox buttons = makeButtonPane();
        top.setAlignment(Pos.CENTER);
        top.getChildren().add(buttons);
        innerPane.setTop(top);

        FlowPane center = new FlowPane();
        grid = makeGridPane();
        center.setAlignment(Pos.CENTER);
        center.getChildren().add(grid);
        innerPane.setCenter(center);

        pane.setCenter(innerPane);

        //bottom
        FlowPane bot = new FlowPane();
        Button reset = new Button("RESET");

        reset.setPrefWidth(250);

        reset.setOnAction(event -> {
            connect4.clearBoard();
        });

        bot.setAlignment(Pos.CENTER);
        bot.getChildren().add(reset);
        pane.setBottom(bot);

        this.pane = pane;

        stage.setTitle("Connect 4");
        stage.setScene(new Scene(pane));
        stage.show();
    }



    public static void main(String[] args){
        Application.launch(args);
    }
}


