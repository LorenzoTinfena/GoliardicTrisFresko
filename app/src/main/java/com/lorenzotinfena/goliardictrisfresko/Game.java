package com.lorenzotinfena.goliardictrisfresko;

public class Game {
    public final Cell[][] cells = new Cell[3][3];
    public boolean move(int i, int j, Cell cell) //contraint cell != Cell.Empty
    {
        if (cell == Cell.Cross)
            cells[i][j] = Cell.Cross;
        else
            cells[i][j] = Cell.Nought;

        return isVictory(cell);
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

        for (int i = 0; i < 3; i++)
        {
            if (lati[0][i] == 3 || lati[1][i] == 3)
                return true;
        }
        if (diagonali[0] == 3 || diagonali[1] == 3)
            return true;
        return false;
    }
}
