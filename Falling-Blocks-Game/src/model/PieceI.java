package model;

import Controllers.Game;
import static java.awt.Color.YELLOW;
import javax.swing.JPanel;

/**
 * Represents the "I" piece.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public class PieceI extends Piece {

    private Block firstBlock;

    /**
     * Constructs a new PieceI.
     *
     * @param game the game instance
     * @param panel the parent panel where the piece is rendered
     */
    public PieceI(Game game, JPanel panel) {
        super(game, panel, YELLOW);

        firstBlock = new Block((panel.getWidth() / 2) - game.getBLOCK_SIDE() * 2, game.getBLOCK_SIDE(), YELLOW);
        blocks.add(firstBlock);
        blocks.add(new Block(firstBlock.getX() + game.getBLOCK_SIDE(), firstBlock.getY(), YELLOW));
        blocks.add(new Block(firstBlock.getX() + game.getBLOCK_SIDE() * 2, firstBlock.getY(), YELLOW));
        blocks.add(new Block(firstBlock.getX() + game.getBLOCK_SIDE() * 3, firstBlock.getY(), YELLOW));
    }

    /**
     * Gets the position of the piece.
     *
     * @return the position of the piece
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the piece.
     *
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean rotate() {
        boolean move = true;

        int y = blocks.get(1).getLabel().getY();
        int x = blocks.get(1).getLabel().getX();

        switch (getPosition()) {
            case 0 -> {
                if (game.isValidPosition(x, y - game.getBLOCK_SIDE()) && game.isValidPosition(x, y + game.getBLOCK_SIDE()) && game.isValidPosition(x, y + game.getBLOCK_SIDE() * 2)) {
                    blocks.get(0).updateCoordinates(x, y - game.getBLOCK_SIDE());
                    blocks.get(2).updateCoordinates(x, y + game.getBLOCK_SIDE());
                    blocks.get(3).updateCoordinates(x, y + game.getBLOCK_SIDE() * 2);
                    setPosition(1);
                }
            }
            case 1 -> {
                if (game.isValidPosition(x - game.getBLOCK_SIDE(), y) && game.isValidPosition(x + game.getBLOCK_SIDE(), y) && game.isValidPosition(x + game.getBLOCK_SIDE() * 2, y)) {
                    blocks.get(0).updateCoordinates(x - game.getBLOCK_SIDE(), y);
                    blocks.get(2).updateCoordinates(x + game.getBLOCK_SIDE(), y);
                    blocks.get(3).updateCoordinates(x + game.getBLOCK_SIDE() * 2, y);
                    setPosition(0);
                }
            }
        }

        return move;
    }
}
