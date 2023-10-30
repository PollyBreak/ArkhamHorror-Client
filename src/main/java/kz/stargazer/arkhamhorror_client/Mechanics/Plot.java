package kz.stargazer.arkhamhorror_client.Mechanics;

public class Plot {
    private Game game;
    private int doom = 0;
    private int clues = 0;

    public int getDoom() {
        return doom;
    }

    public void setDoom(int doom) {
        this.doom = doom;
        if (doom >= 15) {
            game.finish();
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getClues() {
        return clues;
    }

    public void setClues(int clues) {
        this.clues = clues;
    }
}
