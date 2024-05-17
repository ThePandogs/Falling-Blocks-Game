package model;

import Controllers.Game;
import static java.awt.Color.GREEN;
import javax.swing.JPanel;

/**
 * Represents the "L" piece.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public class PieceL extends Piece {

    private Block firstBlock;

    /**
     * Constructs a new PieceL.
     *
     * @param game the game instance
     * @param panel the parent panel where the piece is rendered
     */
    public PieceL(Game game, JPanel panel) {
        super(game, panel, GREEN);
        firstBlock = new Block((panel.getWidth() / 2) - blockSide * 2, blockSide, color);
        blocks.add(firstBlock);
        blocks.add(new Block(firstBlock.getX() + blockSide, firstBlock.getY(), color));
        blocks.add(new Block(firstBlock.getX() + blockSide * 2, firstBlock.getY(), color));
        blocks.add(new Block(firstBlock.getX() + blockSide * 2, firstBlock.getY() - blockSide, color));
    }

    @Override
    public boolean rotate() {
        boolean move = true;

        int y = blocks.get(1).getLabel().getY();
        int x = blocks.get(1).getLabel().getX();

        switch (position) {

            case 0 -> {

                if (game.isValidPosition(x - blockSide, y - blockSide) && game.isValidPosition(x, y - blockSide) && game.isValidPosition(x, y + blockSide)) {
                    blocks.get(0).updateCoordinates(x, y + blockSide);
                    blocks.get(2).updateCoordinates(x, y - blockSide);
                    blocks.get(3).updateCoordinates(x - blockSide, y - blockSide);
                    position = 1;
                }

            }
            case 1 -> {

                if (game.isValidPosition(x - blockSide, y + blockSide) && game.isValidPosition(x + blockSide, y) && game.isValidPosition(x - blockSide, y)) {
                    blocks.get(0).updateCoordinates(x - blockSide, y);
                    blocks.get(2).updateCoordinates(x + blockSide, y);
                    blocks.get(3).updateCoordinates(x - blockSide, y + blockSide);
                    position = 2;
                }

            }
            case 2 -> {

                if (game.isValidPosition(x, y - blockSide) && game.isValidPosition(x, y + blockSide) && game.isValidPosition(x + blockSide, y + blockSide)) {
                    blocks.get(0).updateCoordinates(x, y - blockSide);
                    blocks.get(2).updateCoordinates(x, y + blockSide);
                    blocks.get(3).updateCoordinates(x + blockSide, y + blockSide);
                    position = 3;
                }

            }
            case 3 -> {

                if (game.isValidPosition(x - blockSide, y) && game.isValidPosition(x + blockSide, y) && game.isValidPosition(x + blockSide, y - blockSide)) {
                    blocks.get(0).updateCoordinates(x - blockSide, y);
                    blocks.get(2).updateCoordinates(x + blockSide, y);
                    blocks.get(3).updateCoordinates(x + blockSide, y - blockSide);
                    position = 0;
                }

            }
        }

        return move;
    }
}
