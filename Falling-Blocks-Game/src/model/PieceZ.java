package model;

import Controllers.Game;
import static java.awt.Color.MAGENTA;
import javax.swing.JPanel;

/**
 * Represents the "Z" piece.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public class PieceZ extends Piece {

    private Block firstBlock;

    /**
     * Constructs a new PieceZ.
     *
     * @param game the game instance
     * @param panel the parent panel where the piece is rendered
     */
    public PieceZ(Game game, JPanel panel) {
        super(game, panel, MAGENTA);
        firstBlock = new Block((panel.getWidth() / 2) - blockSide * 2, 0, color);
        blocks.add(firstBlock);
        blocks.add(new Block(firstBlock.getX() + blockSide, firstBlock.getY(), color));
        blocks.add(new Block(firstBlock.getX() + blockSide, firstBlock.getY() + blockSide, color));
        blocks.add(new Block(firstBlock.getX() + blockSide * 2, firstBlock.getY() + blockSide, color));
    }

    /**
     * Rotates the piece clockwise.
     *
     * @return true, indicating that the piece has been successfully rotated
     */
    @Override
    public boolean rotate() {
        boolean move = true;

        // Rotation logic for the "Z" piece
        int y = blocks.get(1).getLabel().getY();
        int x = blocks.get(1).getLabel().getX();

        switch (position) {
            case 0 -> {
                if (game.isValidPosition(x, y - blockSide) && game.isValidPosition(x - blockSide, y) && game.isValidPosition(x - blockSide, y + blockSide)) {
                    blocks.get(0).updateCoordinates(x, y - blockSide);
                    blocks.get(2).updateCoordinates(x - blockSide, y);
                    blocks.get(3).updateCoordinates(x - blockSide, y + blockSide);
                    position = 1;
                }
            }
            case 1 -> {
                if (game.isValidPosition(x - blockSide, y) && game.isValidPosition(x, y + blockSide) && game.isValidPosition(x + blockSide, y + blockSide)) {
                    blocks.get(0).updateCoordinates(x - blockSide, y);
                    blocks.get(2).updateCoordinates(x, y + blockSide);
                    blocks.get(3).updateCoordinates(x + blockSide, y + blockSide);
                    position = 0;
                }
            }
        }

        return move;
    }
}
