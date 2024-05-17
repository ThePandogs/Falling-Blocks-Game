package model;

import Controllers.Game;
import static java.awt.Color.WHITE;
import javax.swing.JPanel;

/**
 * Represents the "J" piece.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public class PieceJ extends Piece {

    private Block firstBlock;

    /**
     * Constructs a new PieceJ.
     *
     * @param game the game instance
     * @param panel the parent panel where the piece is rendered
     */
    public PieceJ(Game game, JPanel panel) {
        super(game, panel, WHITE);
        firstBlock = new Block((panel.getWidth() / 2) - blockSide * 2, blockSide, WHITE);
        blocks.add(firstBlock);
        blocks.add(new Block(firstBlock.getX() + blockSide, firstBlock.getY(), WHITE));
        blocks.add(new Block(firstBlock.getX() + blockSide * 2, firstBlock.getY(), WHITE));
        blocks.add(new Block(firstBlock.getX(), firstBlock.getY() - blockSide, WHITE));
    }

    @Override
    public boolean rotate() {
        boolean move = true;

        int y = blocks.get(1).getLabel().getY();
        int x = blocks.get(1).getLabel().getX();

        switch (position) {

            case 0 -> {

                if (game.isValidPosition(x, y + blockSide) & game.isValidPosition(x, y - blockSide) & game.isValidPosition(x + blockSide, y - blockSide)) {
                    blocks.get(0).updateCoordinates(x, y + blockSide);
                    blocks.get(2).updateCoordinates(x, y - blockSide);
                    blocks.get(3).updateCoordinates(x + blockSide, y - blockSide);
                    position = 1;
                }

            }
            case 1 -> {

                if (game.isValidPosition(x - blockSide, y) & game.isValidPosition(x + blockSide, y) & game.isValidPosition(x + blockSide, y + blockSide)) {
                    blocks.get(0).updateCoordinates(x - blockSide, y);
                    blocks.get(2).updateCoordinates(x + blockSide, y);
                    blocks.get(3).updateCoordinates(x + blockSide, y + blockSide);
                    position = 2;
                }

            }
            case 2 -> {

                if (game.isValidPosition(x, y - blockSide) & game.isValidPosition(x, y + blockSide) & game.isValidPosition(x - blockSide, y + blockSide)) {
                    blocks.get(0).updateCoordinates(x, y - blockSide);
                    blocks.get(2).updateCoordinates(x, y + blockSide);
                    blocks.get(3).updateCoordinates(x - blockSide, y + blockSide);
                    position = 3;
                }

            }
            case 3 -> {

                if (game.isValidPosition(x + blockSide, y) & game.isValidPosition(x - blockSide, y) & game.isValidPosition(x - blockSide, y - blockSide)) {
                    blocks.get(0).updateCoordinates(x + blockSide, y);
                    blocks.get(2).updateCoordinates(x - blockSide, y);
                    blocks.get(3).updateCoordinates(x - blockSide, y - blockSide);
                    position = 0;
                }
            }
        }

        return move;
    }
}
