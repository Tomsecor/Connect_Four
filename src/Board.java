


public class Board  {
    private int h;
    private int w;
    int[][] connect4Board;


    int[] numbers = new int[42];




    public Board(int h, int w){
        int count = 0;
        this.h = h;
        this.w = w;
        this.connect4Board = new int[h][w];

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                connect4Board[i][j] = 0;
                numbers[count] = connect4Board[i][j];
                count += 1;
            }
        }
    }


    /**
     * numbers array created to be called in the gui to place the circles
     *and also for full checking
     */

    public void place(int player, int r, int c){
        connect4Board[r][c] = player;

        int idx = (7 * r) + c;
        numbers[idx] = player;
    }


    public boolean isOccupied(int r, int c){
        if (connect4Board[r][c] != 0){
            return true;
        } else {
            return false;
        }
    }


    public void clear(){
        int count = 0;

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                connect4Board[i][j] = 0;
                numbers[count] = 0;
                count +=1;
            }
        }
    }

    public boolean fullCheck(){
        for (int i: numbers){
            if (i == 0){
                return false;
            }
        }

        return true;
    }




    public boolean checkWin() {
        final int h = connect4Board.length;
        final int w = connect4Board[0].length;
        final int empty = 0;
        for (int r = 0; r < h; r++) {
            for (int c = 0; c <w; c++) {
                int player = connect4Board[r][c];
                if (player == empty)
                    continue;

                if (c + 3 < w &&
                        player == connect4Board[r][c+1] && player == connect4Board[r][c+2] && player == connect4Board[r][c+3])
                    return true;
                if (r + 3 < h) {
                    if (player == connect4Board[r+1][c] && player == connect4Board[r+2][c] && player == connect4Board[r+3][c])
                        return true;
                    if (c + 3 < w &&
                            player == connect4Board[r+1][c+1] && player == connect4Board[r+2][c+2] && player == connect4Board[r+3][c+3])
                        return true;
                    if (c - 3 >= 0 && player == connect4Board[r+1][c-1] && player == connect4Board[r+2][c-2] && player == connect4Board[r+3][c-3])
                        return true;
                }
            }
        }
        return false;
    }
}
