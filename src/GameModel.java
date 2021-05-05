

import java.util.Observable;

public class GameModel extends Observable {
    private static Board connect4Board;
    private final static int w = 7;
    private final static int h = 6;



    private static int currentPlayer = 0;
    private static int player1 = 1;
    private static int player2 = 2;

    private static boolean playerturn;

    private static int winner;


    public GameModel() {
        connect4Board = new Board(h, w);
        currentPlayer = player1;
        playerturn = true;
    }


    public void flip(){
        if (this.playerturn){
            this.playerturn = false;
            currentPlayer = player2;
        } else {
            this.playerturn = true;
            currentPlayer = player1;
        }
    }


    public int getPlayer(){
        return currentPlayer;
    }


    public void selectColumn(int player, int col){
        boolean placed = false;
        boolean valid = true;

        while (valid) {
            for (int i = 0; i < h; i++) {
                if (!connect4Board.isOccupied(i, col - 1)) {
                    connect4Board.place(player, i, col - 1);
                    flip();
                    placed = true;
                    break;
                }
            }

            if (placed) {
                valid = false;
            } else {

                GUI.colFull = true;
                valid = false;
            }
        }

        checkWin(player);

        setChanged();
        notifyObservers();
    }


    public void clearBoard(){
        connect4Board.clear();
        currentPlayer = 1;
        playerturn = true;
        setChanged();
        notifyObservers();
    }


    public Board getBoard(){
        return connect4Board;
    }


    public int getWinningPlayer(){ return winner; }



    public void checkWin(int player){
        if (connect4Board.checkWin()) {
            winner = player;
            System.out.println("THE WINNER IS PLAYER " + " " + player);

        } else if (connect4Board.fullCheck()) {
            GUI.full = true;
        }
    }



}
