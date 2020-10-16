package com.lorenzotinfena.goliardictrisfresko;

import android.graphics.Point;

public class Game {
    public final Cell[][] cells = new Cell[3][3];
    public Point[] pointsVictory = new Point[3];
    public MoveResult move(int i, int j, Cell cell) //contraint cell != Cell.Empty
    {
        if (cell == Cell.Cross)
            cells[i][j] = Cell.Cross;
        else
            cells[i][j] = Cell.Nought;

        if (isVictory(cell))
            return MoveResult.Win;
        else if (isDraw())
            return MoveResult.Draw;
        return MoveResult.Continue;
    }
    private boolean isVictory(Cell cell)
    {
        int[][] lati = new int[2][3];
        int[] diagonali = new int[2];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (cells[i][j] == cell)
                {
                    lati[0][i]++;
                    lati[1][j]++;
                    if (i == j)
                        diagonali[0]++;
                    if (i+j == 2)
                        diagonali[1]++;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            if (lati[0][i] == 3) {
                this.pointsVictory[0] = new Point(i, 0);
                this.pointsVictory[1] = new Point(i, 1);
                this.pointsVictory[2] = new Point(i, 2);
                return true;
            }
            if (lati[1][i] == 3) {
                this.pointsVictory[0] = new Point(0, i);
                this.pointsVictory[1] = new Point(1, i);
                this.pointsVictory[2] = new Point(2, i);
                return true;
            }
        }
        if (diagonali[0] == 3){
            this.pointsVictory[0] = new Point(0, 0);
            this.pointsVictory[1] = new Point(1, 1);
            this.pointsVictory[2] = new Point(2, 2);
            return true;
        }
        if (diagonali[1] == 3){
            this.pointsVictory[0] = new Point(2, 0);
            this.pointsVictory[1] = new Point(1, 1);
            this.pointsVictory[2] = new Point(0, 2);
            return true;
        }
        return false;
    }
    private boolean isDraw()
    {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (cells[i][j] == null)
                    return false;
            }
        }
        return true;
    }
}
