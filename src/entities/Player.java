package src.entities;
public class Player {
    private int userRow;
    private int userCol;

    public Player() {
        this.userRow = 6;
        this.userCol = 0;
    }

    public int getUserRow() {
        return userRow;
    }

    public void setUserRow(int userRow) {
        this.userRow = userRow;
    }

    public int getUserCol() {
        return userCol;
    }

    public void setUserCol(int userCol) {
        this.userCol = userCol;
    }

    
}
