package model;

import Controllers.Game;
import static java.awt.Color.ORANGE;
import javax.swing.JPanel;

/**
 * Represents the "T" piece.
 * 
 * <p>Created by Carlos Fraile - ThePandogs</p>
 * 
 * @author Carlos Fraile - ThePandogs
 */
public class PieceT extends Piece {

    private Block firstBlock;

    /**
     * Constructs a new PieceT.
     * 
     * @param game the game instance
     * @param panel the parent panel where the piece is rendered
     */
    public PieceT(Game game, JPanel panel) {
        super(game, panel,ORANGE);
        firstBlock = new Block((panel.getWidth() / 2) -blockSide * 2,blockSide, color);
        blocks.add(firstBlock);
        blocks.add(new Block(firstBlock.getX() +blockSide, firstBlock.getY(), color));
        blocks.add(new Block(firstBlock.getX() +blockSide * 2, firstBlock.getY(), color));
        blocks.add(new Block(firstBlock.getX() +blockSide, firstBlock.getY() -blockSide, color));
    }

    /**
     * Rotates the piece clockwise.
     * 
     * @return true, indicating that the piece has been successfully rotated
     */
    @Override
    public boolean rotate() {
        boolean move = true;
        
        // Rotation logic for the "T" piece
        int y = blocks.get(1).getLabel().getY();
        int x = blocks.get(1).getLabel().getX();

        switch (position) {
            // The case 0 corresponds to position 3 in the diagram, following the order 3, 0, 1, 2
            case 0 -> {
                if (game.isValidPosition(x, y -blockSide) && game.isValidPosition(x +blockSide, y) && game.isValidPosition(x, y +blockSide)) {
                    blocks.get(0).updateCoordinates(x, y -blockSide);
                    blocks.get(2).updateCoordinates(x +blockSide, y);
                    blocks.get(3).updateCoordinates(x, y +blockSide);
                    position = 1;
                }
            }
            case 1 -> {
                if (game.isValidPosition(x -blockSide, y) && game.isValidPosition(x +blockSide, y) && game.isValidPosition(x, y +blockSide)) {
                    blocks.get(0).updateCoordinates(x -blockSide, y);
                    blocks.get(2).updateCoordinates(x +blockSide, y);
                    blocks.get(3).updateCoordinates(x, y +blockSide);
                    position = 2;
                }
            }
            case 2 -> {
                if (game.isValidPosition(x, y -blockSide) && game.isValidPosition(x, y +blockSide) && game.isValidPosition(x -blockSide, y)) {
                    blocks.get(0).updateCoordinates(x, y -blockSide);
                    blocks.get(2).updateCoordinates(x, y +blockSide);
                    blocks.get(3).updateCoordinates(x -blockSide, y);
                    position = 3;
                }
            }
            case 3 -> {
                if (game.isValidPosition(x -blockSide, y) && game.isValidPosition(x +blockSide, y) && game.isValidPosition(x, y -blockSide)) {
                    blocks.get(0).updateCoordinates(x -blockSide, y);
                    blocks.get(2).updateCoordinates(x +blockSide, y);
                    blocks.get(3).updateCoordinates(x, y -blockSide);
                    position = 0;
                }
            }
        }

        return move;
    }
}
