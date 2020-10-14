package com.lorenzotinfena.goliardictrisfresko;

public class Game {
    private final Cell[][] cells = new Cell[3][3];
    public boolean move(int i, int j, boolean isCross)
    {
        if (isCross)
            cells[i][j] = Cell.Cross;
        else
            cells[i][j] = Cell.Nought;

        return isVictory();
    }
    private boolean isVictory()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if ()//TOO
            }
        }
    }
}
