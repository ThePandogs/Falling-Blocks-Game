package model;

import Controllers.Game;
import static java.awt.Color.BLUE;
import javax.swing.JPanel;

/**
 * Represents the "O" piece.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public class PieceO extends Piece {

    private Block firstBlock;

    /**
     * Constructs a new PieceO.
     *
     * @param game the game instance
     * @param panel the parent panel where the piece is rendered
     */
    public PieceO(Game game, JPanel panel) {
        super(game, panel, BLUE);
        firstBlock = new Block(panel.getWidth() / 2 - blockSide, 0, color);
        blocks.add(firstBlock);
        blocks.add(new Block(firstBlock.getX() + blockSide, firstBlock.getY(), color));
        blocks.add(new Block(firstBlock.getX(), firstBlock.getY() + blockSide, color));
        blocks.add(new Block(firstBlock.getX() + blockSide, firstBlock.getY() + blockSide, color));
    }

    /**
     * This piece cannot be rotated.
     *
     * @return always returns true
     */
    @Override
    public boolean rotate() {
        return true;
    }
}
