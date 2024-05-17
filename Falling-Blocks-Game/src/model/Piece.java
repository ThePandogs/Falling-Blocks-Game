package model;

import Controllers.Game;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents an abstract piece. Provides methods for moving and rotating the
 * piece. Concrete subclasses must implement the rotation logic.
 *
 * <p>Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 */
public abstract class Piece {

    protected List<Block> blocks;
    protected Game game;
    protected JPanel parent;
    protected int position, blockSide;
    protected Color color;

    /**
     * Constructs a new Piece.
     *
     * @param game   the game instance
     * @param panel  the parent panel where the piece is rendered
     * @param color  the color of the piece
     */
    public Piece(Game game, JPanel panel, Color color) {
        blocks = new ArrayList<>();
        this.game = game;
        this.blockSide = game.getBLOCK_SIDE();
        this.parent = panel;
        this.color = color;
    }

    /**
     * Gets the list of blocks that make up this piece.
     *
     * @return the list of blocks
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * Moves the piece one step to the right.
     *
     * @return true if the move was successful, false otherwise
     */
    public boolean moveRight() {
        return move(1, 0);
    }

    /**
     * Moves the piece one step to the left.
     *
     * @return true if the move was successful, false otherwise
     */
    public boolean moveLeft() {
        return move(-1, 0);
    }

    /**
     * Moves the piece one step down.
     *
     * @return true if the move was successful, false otherwise
     */
    public boolean moveDown() {
        return move(0, 1);
    }

    /**
     * Moves the piece based on the given change in x and y coordinates.
     *
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     * @return true if the move was successful, false otherwise
     */
    private boolean move(int dx, int dy) {
        boolean move = false;
        Iterator<Block> it = blocks.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            int newX = block.getX() + dx * blockSide;
            int newY = block.getY() + dy * blockSide;
            if (game.isValidPosition(newX, newY)) {
                block.updateCoordinates(newX, newY);
                move = true;
            }
        }
        return move;
    }

    /**
     * Rotates the piece. This method must be implemented by subclasses.
     *
     * @return true if the rotation was successful, false otherwise
     */
    public abstract boolean rotate();

    /**
     * Returns the parent panel of this piece.
     *
     * @return The parent panel of this piece.
     */
    public JPanel getParent() {
        return parent;
    }
}
