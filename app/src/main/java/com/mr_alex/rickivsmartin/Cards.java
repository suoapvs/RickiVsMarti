package com.mr_alex.rickivsmartin;

public class Cards {
    private boolean[][] board;
    private int n, m, control;

    public Cards(int N, int M) {
        n = N; m = M;
        board = new boolean [n][m];
    }

    public void getNewCards() {
        board = new boolean [n][m];
        int rand;
        do {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++) {
                    rand = (int) (Math.random() * 2);
                    board[i][j] = rand == 1;
                }
        } while(finished());
    }

    public void moveCards(int column, int row){
        board[column][row] = !board[column][row];
        for (int i = 0; i < n; i++)
            board[i][row] = !board[i][row];
        for (int i = 0; i < m; i++)
            board[column][i] = !board[column][i];
    }

    public boolean finished(){
        control = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (board[i][j])
                    control++;
                else if (!board[i][j])
                    control--;
        return (Math.abs(control) == (n * m));
    }

    public int valueControl() {
        return control;
    }

    public boolean getValueBoard(int i, int j) {
        return board[i][j];
    }

    public void setValueBoard(int i, int j, boolean value) {
        board[i][j] = value;
    }
}
