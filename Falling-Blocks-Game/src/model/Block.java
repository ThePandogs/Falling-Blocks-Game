package model;

import java.awt.Color;
import javax.swing.JLabel;

/**
 * Represents a block in the game.
 *
 * @author Carlos Fraile - ThePandogs
 */
public class Block {

    private int x;
    private int y;
    private Color color;
    private final JLabel blockLabel;

    /**
     * Constructs a Block object with specified position and color.
     *
     * @param x The x-coordinate of the block.
     * @param y The y-coordinate of the block.
     * @param color The color of the block.
     */
    public Block(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.blockLabel = new JLabel();
        blockLabel.setBackground(color);
        blockLabel.setLocation(x, y);
    }

    /**
     * Gets the x-coordinate of the block.
     *
     * @return The x-coordinate of the block.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the block.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the block.
     *
     * @return The y-coordinate of the block.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the block.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the color of the block.
     *
     * @return The color of the block.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the block.
     *
     * @param color The new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the label associated with the block.
     *
     * @return The label associated with the block.
     */
    public JLabel getLabel() {
        return blockLabel;
    }

    /**
     * Gets the coordinates of the block.
     *
     * @return The coordinates of the block in the format "x=... : y=...".
     */
    public String getCoordinates() {
        return "x=" + getX() + " : y=" + getY();
    }

    /**
     * Updates the coordinates of the block and its label.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void updateCoordinates(int x, int y) {
        this.setX(x);
        this.setY(y);
        getLabel().setLocation(x, y);
    }
}
