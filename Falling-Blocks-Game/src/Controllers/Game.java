package Controllers;

import java.util.Iterator;
import view.MainWindow;
import static java.awt.Color.gray;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import model.Piece;
import model.PieceI;
import model.PieceJ;
import model.PieceL;
import model.PieceO;
import model.PieceS;
import model.PieceT;
import model.PieceZ;
import model.Block;
import model.Block;
import model.Piece;
import model.PieceI;
import model.PieceJ;
import model.PieceL;
import model.PieceO;
import model.PieceS;
import model.PieceT;
import model.PieceZ;

/**
 * Represents the main logic and state of The Falling Blocks Game Manages game
 * state, piece movement, and interaction with the UI. It also handles game
 * difficulty and level progression.
 *
 * <p>
 * Created by Carlos Fraile - ThePandogs</p>
 *
 * @author Carlos Fraile - ThePandogs
 *
 */
public final class Game {

    // GAME MAP REFERENCE
    private final int BLOCK_SIDE = 50;
    private final int MAX_Y = 900;
    private final int MAX_X = 500;
    private final int SAFE_ZONE = BLOCK_SIDE * 2;

    // UI REFERENCE
    private MainWindow mainWindow;

    // DIFFICULTY REFERENCE
    private int initialDifficulty;
    private final int MIN_DIFFICULTY = 1000;
    private final int DIFFICULTY_PER_LEVEL = 40;
    private final int MAX_DIFFICULTY = 200;
    private final int LINES_NEXT_LEVEL = 5;

    // Blocks
    private Piece currentPiece;
    private Piece ghostPiece;
    private Piece nextPiece;
    private int counter = 0;

    private List<Block> filledBlocks = new ArrayList<>();
    private List<Block> line = new ArrayList<>();

    private int level;
    private int lineCount = 0;
    private int linesToNextLevel = 0;

    Random random = new Random();
    private boolean gameOver;

    List<Class<? extends Piece>> pieceList = Arrays.asList(
            PieceI.class,
            PieceO.class,
            PieceL.class,
            PieceJ.class,
            PieceT.class,
            PieceZ.class,
            PieceS.class
    );

    /**
     * Constructs a new game instance with the specified main window and initial
     * level.
     *
     * @param mainWindow the main window of the application
     * @param level the initial level of the game
     */
    public Game(MainWindow mainWindow, int level) {
        this.mainWindow = mainWindow;
        this.level = level;
        gameOver = false;
    }

    /**
     * Starts the game by initializing pieces, updating the UI, and setting the
     * initial state.
     */
    public void startGame() {
        shuffleArray(pieceList);
        nextPiece = generateNewPiece(mainWindow.getPanelNextPiece());
        moveNextPieceToCurrentPiece(mainWindow.getGamePanel());
        mainWindow.paintPiece(nextPiece);
        mainWindow.paintPiece(currentPiece);
        paintGhostPiece(ghostPiece);
        updateGhostPiece();
    }

    // <editor-fold defaultstate="collapsed" desc="GettersAndSetters">
    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the current line count.
     *
     * @return The current line count.
     */
    public int getLineCount() {
        return lineCount;
    }

    /**
     * Gets the number of lines needed to reach the next level.
     *
     * @return The number of lines needed to reach the next level.
     */
    public int getLinesToNextLevel() {
        return linesToNextLevel;
    }

    /**
     * Sets the line count to the specified value.
     *
     * @param lineCount The new line count value.
     */
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    /**
     * Gets the side length of a block.
     *
     * @return The side length of a block.
     */
    public int getBLOCK_SIDE() {
        return BLOCK_SIDE;
    }

    /**
     * Gets a list of filled blocks.
     *
     * @return A list of filled blocks.
     */
    public List<Block> getFilledBlocks() {
        return filledBlocks;
    }

    /**
     * Gets the current level.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the current piece.
     *
     * @return The current piece.
     */
    public Piece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * Gets the initial difficulty level.
     *
     * @return The initial difficulty level.
     */
    public int getInitialDifficulty() {
        return initialDifficulty;
    }

    /**
     * Sets the initial difficulty level to the specified value.
     *
     * @param initialDifficulty The new initial difficulty level.
     */
    public void setInitialDifficulty(int initialDifficulty) {
        this.initialDifficulty = initialDifficulty;
    }

    /**
     * Gets the minimum difficulty level.
     *
     * @return The minimum difficulty level.
     */
    public int getMIN_DIFFICULTY() {
        return MIN_DIFFICULTY;
    }

    /**
     * Gets the difficulty increment per level.
     *
     * @return The difficulty increment per level.
     */
    public int getDIFFICULTY_PER_LEVEL() {
        return DIFFICULTY_PER_LEVEL;
    }

    /**
     * Gets the maximum Y-coordinate.
     *
     * @return The maximum Y-coordinate.
     */
    public int getMAX_Y() {
        return MAX_Y;
    }

    /**
     * Gets the maximum X-coordinate.
     *
     * @return The maximum X-coordinate.
     */
    public int getMAX_X() {
        return MAX_X;
    }

    /**
     * Moves the current piece to the left if the position is valid.
     */
    public void moveLeft() {
        boolean validPosition = true;
        Iterator<Block> current = currentPiece.getBlocks().iterator();

        while (current.hasNext()) {
            Block block = current.next();
            int x = block.getX() - BLOCK_SIDE;
            int y = block.getY();
            if (!isValidPosition(x, y)) {
                validPosition = false;
            }
        }

        if (validPosition) {
            currentPiece.moveLeft();
            updateGhostPiece();
        }
    }

    /**
     * Moves the current piece to the right if the position is valid.
     */
    public void moveRight() {
        boolean validPosition = true;
        Iterator<Block> current = currentPiece.getBlocks().iterator();

        while (current.hasNext()) {
            Block block = current.next();
            int x = block.getX() + BLOCK_SIDE;
            int y = block.getY();
            if (!isValidPosition(x, y)) {
                validPosition = false;
            }
        }

        if (validPosition) {
            currentPiece.moveRight();
            updateGhostPiece();
        }
    }

    /**
     * Rotates the current piece if the rotation is valid.
     */
    public void rotatePiece() {
        if (currentPiece.rotate()) {
            updateGhostPiece();
        }
    }

    /**
     * Moves the current piece down. If it hits the bottom, it handles the piece
     * placement and line clearing.
     */
    public void movePieceDown() {
        if (!pieceHitsBottom(currentPiece)) {
            currentPiece.moveDown();
        } else {
            addPieceClearCompleteLinesGenerateNewPiece();
        }
    }

    /**
     * Updates the ghost piece to reflect the current piece's position.
     */
    private void updateGhostPiece() {
        for (int i = 0; i < currentPiece.getBlocks().size(); i++) {
            ghostPiece.getBlocks().get(i).updateCoordinates(currentPiece.getBlocks().get(i).getX(), currentPiece.getBlocks().get(i).getY());
        }
        movePieceDownToBottom(ghostPiece);
    }

    /**
     * Moves the specified piece down to the bottom of the game board.
     *
     * @param piece the piece to move down
     */
    public void movePieceDownToBottom(Piece piece) {
        while (!pieceHitsBottom(piece)) {
            piece.moveDown();
        }
    }

    /**
     * Generates a new piece to be used in the game.
     *
     * @param panel the panel to place the new piece on
     * @return the newly generated piece
     */
    private Piece generateNewPiece(JPanel panel) {
        Piece piece = null;
        Class<? extends Piece> selectedPieceClass;

        selectedPieceClass = pieceList.get(counter);
        counter++;
        try {
            Constructor<? extends Piece> constructor = selectedPieceClass.getDeclaredConstructor(Game.class, JPanel.class);
            piece = constructor.newInstance(this, panel);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | IllegalArgumentException e) {
            mainWindow.getLogException().addExceptionLog(e);
        }
        if (counter >= pieceList.size()) {
            counter = 0;
            shuffleArray(pieceList);
        }
        return piece;
    }

    /**
     * Shuffles the list of pieces to randomize their order.
     *
     * @param array the list of pieces to shuffle
     */
    private void shuffleArray(List<?> array) {
        Collections.shuffle(array);
    }

    /**
     * Paints the ghost piece on the game board.
     *
     * @param piece the ghost piece to paint
     */
    public void paintGhostPiece(Piece piece) {

        Iterator<Block> current = piece.getBlocks().iterator();
        while (current.hasNext()) {
            Block currentBlock = current.next();
            mainWindow.paintGhostBlock(currentBlock.getLabel(), piece.getParent());
        }
    }

    /**
     * Adds the current piece's blocks to the list of filled blocks.
     */
    public void addPieceToFilledBlocks() {
        Iterator<Block> current = currentPiece.getBlocks().iterator();
        while (current.hasNext()) {
            Block block = current.next();
            filledBlocks.add(block);
        }
    }

    /**
     * Checks if the specified piece hits the bottom of the game board or other
     * blocks.
     *
     * @param piece the piece to check
     * @return true if the piece hits the bottom, false otherwise
     */
    public boolean pieceHitsBottom(Piece piece) {
        boolean hitsBottom = false;
        Iterator<Block> current = piece.getBlocks().iterator();

        while (current.hasNext()) {
            Block currentBlock = current.next();
            if (!isValidPosition(currentBlock.getX(), currentBlock.getY() + BLOCK_SIDE)) {
                hitsBottom = true;
            }
        }
        return hitsBottom;
    }

    /**
     * Checks if the specified position is valid on the game board.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int x, int y) {
        boolean validPosition = true;
        if (x > MAX_X - BLOCK_SIDE || x < 0 || y > MAX_Y - BLOCK_SIDE || y < -BLOCK_SIDE) {
            validPosition = false;
        }
        Iterator<Block> filled = filledBlocks.iterator();
        while (filled.hasNext()) {
            Block filledBlock = filled.next();
            if ((filledBlock.getX() == x && filledBlock.getY() == y)) {
                validPosition = false;
            }
        }
        return validPosition;
    }

    /**
     * Clears complete lines from the game board and updates the score.
     */
    public void clearCompleteLines() {
        int completeLine = 10;
        Iterator<Block> current = currentPiece.getBlocks().iterator();
        while (current.hasNext()) {
            Block currentBlock = current.next();
            Iterator<Block> filled = filledBlocks.iterator();
            while (filled.hasNext()) {
                Block filledBlock = filled.next();
                if (filledBlock.getY() == currentBlock.getY()) {
                    line.add(filledBlock);
                }
            }
            if (line.size() == completeLine) {
                clearLines();
                lineCount++;
                mainWindow.addToPendingScore(mainWindow.getSCORELINE());
                linesToNextLevel++;
                increaseLevel(lineCount, mainWindow.getTimerSpeedGame().getDelay());
                updateBlocks();
            }
            line.clear();
        }
    }

    /**
     * Clears the lines that are full from the game board.
     */
    public void clearLines() {
        Iterator<Block> lineIterator = line.iterator();
        while (lineIterator.hasNext()) {
            Block thisOne = lineIterator.next();
            mainWindow.eraseBlock(thisOne.getLabel());
            filledBlocks.removeAll(line);
        }
        mainWindow.getSoundController().playLineSound();
    }

    /**
     * Updates the positions of the blocks above the cleared lines.
     */
    public void updateBlocks() {
        int y = line.get(0).getY();
        Iterator<Block> filled = filledBlocks.iterator();
        while (filled.hasNext()) {
            Block block = filled.next();
            if (y > block.getY()) {
                block.updateCoordinates(block.getX(), block.getY() + BLOCK_SIDE);
            }
        }
    }

    /**
     * Checks if the game is lost.
     *
     * @return true if the game is lost, false otherwise
     */
    private boolean checkLose() {
        Iterator<Block> current = currentPiece.getBlocks().iterator();
        while (current.hasNext()) {
            Block currentBlock = current.next();
            if (currentBlock.getY() < SAFE_ZONE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the next piece to the current piece position and generates a new
     * next piece.
     *
     * @param panel the panel to place the new current piece on
     */
    private void moveNextPieceToCurrentPiece(JPanel panel) {
        createNewPieceInstance(panel);
        updateNextPiecePanel();
        moveNextPiecePanelIfNeeded();
        mainWindow.paintPiece(nextPiece);
        updateGhostPiece();
    }

    /**
     * Creates a new instance of the current piece and the ghost piece.
     *
     * @param panel the panel to place the new current piece on
     */
    private void createNewPieceInstance(JPanel panel) {
        Class<? extends Piece> pieceClass = nextPiece.getClass();
        try {
            Constructor<? extends Piece> constructor = pieceClass.getDeclaredConstructor(Game.class, JPanel.class);
            currentPiece = constructor.newInstance(this, panel);
            ghostPiece = constructor.newInstance(this, panel);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            mainWindow.getLogException().addExceptionLog(e);
        }
        nextPiece = generateNewPiece(mainWindow.getPanelNextPiece());
        mainWindow.clearNextFigurePanel();
    }

    /**
     * Updates the next piece panel to display the new next piece.
     */
    private void updateNextPiecePanel() {
        mainWindow.paintPiece(nextPiece);
    }

    /**
     * Moves the next piece panel if the next piece is not an I or O piece.
     */
    private void moveNextPiecePanelIfNeeded() {
        if (!(nextPiece instanceof PieceI) && !(nextPiece instanceof PieceO)) {
            mainWindow.getPanelNextPiece().setLocation(mainWindow.getPanelNextPiece().getX() + BLOCK_SIDE / 2, mainWindow.getPanelNextPiece().getY());
        }
    }

    /**
     * Adds the current piece to the filled blocks, clears complete lines, and
     * generates a new piece.
     */
    private void addPieceClearCompleteLinesGenerateNewPiece() {
        if (!checkLose()) {
            addPieceToFilledBlocks();
            mainWindow.getSoundController().playFloorSound();
            mainWindow.addToPendingScore(mainWindow.getSCORETOUCHFLOOR());
            clearCompleteLines();
            clearGhostBlocks();
            moveNextPieceToCurrentPiece(mainWindow.getGamePanel());
            mainWindow.paintPiece(currentPiece);
            paintGhostPiece(ghostPiece);
        } else {
            mainWindow.showGameOverMenu();
            gameOver = true;
        }
    }

    /**
     * Clears the ghost blocks from the game board.
     */
    private void clearGhostBlocks() {
        for (Block block : ghostPiece.getBlocks()) {
            mainWindow.eraseBlock(block.getLabel());
        }
    }

    /**
     * Increases the game level if the conditions are met.
     *
     * @param lines the number of lines cleared
     * @param currentDelay the current delay of the game timer
     * @return true if the level is increased, false otherwise
     */
    public boolean increaseLevel(int lines, int currentDelay) {
        boolean levelUp = false;
        if (lines % LINES_NEXT_LEVEL == 0 && currentDelay > MAX_DIFFICULTY) {
            mainWindow.getTimerSpeedGame().setDelay(currentDelay - DIFFICULTY_PER_LEVEL);
            level++;
            mainWindow.addToPendingScore(mainWindow.getSCORETOUCHFLOOR());
            linesToNextLevel = 0;
            levelUp = true;
        }
        return levelUp;
    }

    /**
     * Generates a random number between the specified minimum and maximum
     * values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the generated random number
     */
    private int randomNumber(int min, int max) {
        return random.nextInt(min, max);
    }

    /**
     * Adds random blocks to the game board as a penalty.
     */
    public void addRandomBlocks() {
        ArrayList<Integer> positions = new ArrayList<>();
        int minBlocks = 1;
        Iterator<Block> ground = filledBlocks.iterator();
        while (ground.hasNext()) {
            Block nextBlocks = ground.next();
            nextBlocks.updateCoordinates(nextBlocks.getX(), nextBlocks.getY() - BLOCK_SIDE);
        }

        for (int i = 0; i < randomNumber(minBlocks, (MAX_X - BLOCK_SIDE) / BLOCK_SIDE); i++) {
            int randomPosition = randomNumber(0, (MAX_X - BLOCK_SIDE) / BLOCK_SIDE);
            while (positions.contains(randomPosition)) {
                randomPosition = randomNumber(minBlocks, (MAX_X - BLOCK_SIDE) / BLOCK_SIDE);
            }
            Block penaltyBlock = new Block(randomPosition * BLOCK_SIDE, MAX_Y - BLOCK_SIDE, gray);
            filledBlocks.add(penaltyBlock);
            mainWindow.paintBlock(penaltyBlock.getLabel(), mainWindow.getGamePanel());
            positions.add(randomPosition);
        }
        updateGhostPiece();
    }
}
