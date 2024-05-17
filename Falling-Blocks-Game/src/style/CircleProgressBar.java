package style;

import java.awt.Color;
import javax.swing.JProgressBar;

/**
 * Custom progress bar component with a circular style.
 * Extends JProgressBar to inherit basic functionality.
 * Uses a custom UI (ProgressCircleUI) for rendering.
 * 
 * @author Carlos Fraile - ThePandogs
 */
public class CircleProgressBar extends JProgressBar {

    /**
     * Constructs a new CircleProgressBar with default settings.
     * The background color is set to a light gray (220, 220, 220).
     * The foreground color is set to a dark gray (97, 97, 97).
     * The progress string is displayed.
     * The custom UI (ProgressCircleUI) is applied for rendering.
     */
    public CircleProgressBar() {
        setOpaque(false);
        setBackground(new Color(220, 220, 220));
        setForeground(new Color(97, 97, 97));
        setStringPainted(true);
        setUI(new ProgressCircleUI(this));
    }

}