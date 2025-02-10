package src.game;

import src.entities.Player;
import src.utils.ScoreManager;

public class Game {
    private Grid grid;
    private Player player;
    private int msElapsed;
    private ScoreManager scoreManager;

    public Game() {
        grid = new Grid(7, 15);
        player = new Player();
        scoreManager = new ScoreManager();
        msElapsed = 0;
        updateTitle();
        grid.setImage(new Location(player.getUserRow(), 0), "../../assets/images/Mario.gif");
    }

    public void play() {
        while (!isGameOver()) {
            grid.pause(100);
            handleKeyPress();
            if (msElapsed % 300 == 0) {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
        System.out.println();
    }

    public void handleKeyPress() {
        int key = grid.checkLastKeyPressed();

        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        if (key == 38) {
            jump();

            if (!grid.isValid(new Location(player.getUserRow(), player.getUserCol()))) {
                player.setUserRow(player.getUserRow()+1);
            }
        } else if (key == 39) {
            handleCollision(new Location(player.getUserRow(), player.getUserCol() + 1));
            player.setUserCol(player.getUserCol()+1);
            if (!grid.isValid(new Location(player.getUserRow(), player.getUserCol()))) {
                player.setUserCol(player.getUserCol()-1);
            }
        } else if (key == 40) {
            handleCollision(new Location(player.getUserRow() + 1, player.getUserCol()));
            player.setUserRow(player.getUserRow()+1);
            if (!grid.isValid(new Location(player.getUserRow(), player.getUserCol()))) {
                player.setUserRow(player.getUserRow()-1);
            }
        } else if (key == 37) {
            handleCollision(new Location(player.getUserRow(), player.getUserCol() - 1));
            player.setUserCol(player.getUserCol()-1);
            if (!grid.isValid(new Location(player.getUserRow(), player.getUserCol()))) {
                player.setUserCol(player.getUserCol()+1);
            }
        }

        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/Mario.gif");
    }

    public void handleKeyPressAir() {
        int key = grid.checkLastKeyPressed();

        if (key == 39) {
            grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
            handleCollision(new Location(player.getUserRow(), player.getUserCol() + 1));
            player.setUserCol(player.getUserCol()+1);
            if (!grid.isValid(new Location(player.getUserRow(), player.getUserCol()))) {
                player.setUserCol(player.getUserCol()-1);
            }
        } else if (key == 37) {
            grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
            handleCollision(new Location(player.getUserRow(), player.getUserCol() - 1));
            player.setUserCol(player.getUserCol()-1);
            if (!grid.isValid(new Location(player.getUserRow(), player.getUserCol()))) {
                player.setUserCol(player.getUserCol()+1);
            }
        }

        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/Mario.gif");
    }

    public void populateRightEdge() {
        int row = grid.getNumRows();
        int col = grid.getNumCols();

        int randNum1 = (int) (Math.random() * 10 + 1);
        if (randNum1 <= 2) {
            grid.setImage(new Location(row - 1, col - 1), "../../assets/images/Goomba.gif");
        }

        int randNum2 = (int) (Math.random() * 10 + 1);
        if (randNum2 <= 4) {
            grid.setImage(new Location((int) (Math.random() * 3 + 4), col - 1), "../../assets/images/Coin.gif");
        }

    }

    public void jump() {
        handleCollision(new Location(player.getUserRow() - 1, player.getUserCol()));
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        player.setUserRow(player.getUserRow()-1);
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/Mario.gif");

        grid.pause(100);
        handleKeyPressAir();
        if (msElapsed % 300 == 0) {
            scrollLeft();
            populateRightEdge();
        }
        msElapsed += 100;

        handleCollision(new Location(player.getUserRow() - 1, player.getUserCol()));
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        player.setUserRow(player.getUserRow()-1);
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/Mario.gif");

        grid.pause(100);
        handleKeyPressAir();
        if (msElapsed % 300 == 0) {
            scrollLeft();
            populateRightEdge();
        }
        msElapsed += 100;

        handleJumpOn(new Location(player.getUserRow() + 1, player.getUserCol()));
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        player.setUserRow(player.getUserRow()+1);
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/Mario.gif");

        grid.pause(100);
        handleKeyPressAir();
        if (msElapsed % 300 == 0) {
            scrollLeft();
            populateRightEdge();
        }
        msElapsed += 100;

        handleJumpOn(new Location(player.getUserRow() + 1, player.getUserCol()));
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        player.setUserRow(player.getUserRow()+1);

    }

    public void jumpOn() {
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        player.setUserRow(player.getUserRow()-1);
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/JumpOn.gif");
        grid.pause(200);
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), null);
        player.setUserRow(player.getUserRow()+1);
        grid.setImage(new Location(player.getUserRow(), player.getUserCol()), "../../assets/images/Mario.gif");
    }

    public void scrollLeft() {
        handleCollision(new Location(player.getUserRow(), player.getUserCol() + 1));

        for (int row = 0; row < grid.getNumRows(); row++) {
            for (int col = 0; col + 1 < grid.getNumCols(); col++) {
                String temp = grid.getImage(new Location(row, col + 1));
                if (grid.getImage(new Location(row, col + 1)) == null) {
                    // just a null checker

                    if (grid.getImage(new Location(row, col)) == null) {

                        grid.setImage(new Location(row, col), temp);
                    } else if (grid.getImage(new Location(row, col)).equals("../../assets/images/Mario.gif")) {
                        grid.setImage(new Location(row, col + 1), null);
                    } else {

                        grid.setImage(new Location(row, col), temp);

                    }
                } else if (grid.getImage(new Location(row, col + 1)).equals("../../assets/images/Mario.gif")) {
                    grid.setImage(new Location(row, col), null);
                } else {
                    if (grid.getImage(new Location(row, col)) == null) {

                        grid.setImage(new Location(row, col + 1), null);
                        grid.setImage(new Location(row, col), temp);
                    } else if (grid.getImage(new Location(row, col)).equals("../../assets/images/Mario.gif")) {
                        grid.setImage(new Location(row, col + 1), null);
                    } else {
                        grid.setImage(new Location(row, col + 1), null);
                        grid.setImage(new Location(row, col), temp);

                    }
                }
            }
        }

    }

    // this method should be called right before collistion
    public void handleCollision(Location loc) {
        if (grid.getImage(loc) == null) {
            // nothigs gonna happen
        } else if (grid.getImage(loc).equals("../../assets/images/Goomba.gif")) {
            scoreManager.setTimesAvoid(scoreManager.getTimesAvoid() + 1);
            grid.setImage(loc, null);
        } else if (grid.getImage(loc).equals("../../assets/images/Coin.gif")) {
            scoreManager.setTimesGet(scoreManager.getTimesGet() + 1);
            grid.setImage(loc, null);
        }
    }

    public void handleJumpOn(Location loc) {
        if (grid.getImage(loc) == null) {

        } else if (grid.getImage(loc).equals("../../assets/images/Goomba.gif")) {
            scoreManager.setTimesJumpOn(scoreManager.getTimesJumpOn() + 1);
            grid.setImage(loc, "../../assets/images/StepOn.gif");
            jumpOn();
            grid.setImage(loc, null);
        } else if (grid.getImage(loc).equals("../../assets/images/Coin.gif")) {
            scoreManager.setTimesGet(scoreManager.getTimesGet() + 1);
            grid.setImage(loc, null);
        }
    }

    public void updateTitle() {
        grid.setTitle("Your Score:  " + scoreManager.getScore());
    }

    public boolean isGameOver() {
        if (msElapsed > 100000 || scoreManager.getTimesAvoid() > 2) {
            return true;
        }
        return false;
    }

    public static void test() {
        Game game = new Game();
        game.play();
    }

    public static void main(String[] args) {
        test();
    }
}