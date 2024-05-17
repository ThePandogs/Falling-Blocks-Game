package style;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Custom UI class for rendering a circular progress bar. This class extends
 * BasicProgressBarUI to customize the appearance of a JProgressBar. It draws a
 * circular progress bar with a customizable color and animates it.
 * <p>
 * The appearance of the progress bar can be customized by setting the
 * foreground color of the JProgressBar component.
 * </p>
 * <p>
 * This class is particularly useful for creating visually appealing progress
 * bars in Swing applications.
 * </p>

 * Usage example:
 * <pre>
 * JProgressBar progressBar = new JProgressBar();
 * progressBar.setUI(new ProgressCircleUI(progressBar));
 * progressBar.setForeground(Color.GREEN); // Customize the color of the progress bar
 * </pre>

 * <p>
 * Note: This class assumes that the JProgressBar has a square shape.
 * </p>
 * <p>
 * This class was created by Carlos Fraile - ThePandogs.
 * </p>
 *
 * @author Carlos Fraile - ThePandogs
 *
 */
public class ProgressCircleUI extends BasicProgressBarUI {

    private BufferedImage imageColor;
    private final JProgressBar pro;

    public ProgressCircleUI(JProgressBar pro) {
        this.pro = pro;
        pro.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                createImage();
            }
        });
    }

    private void createImage() {
        int width = pro.getWidth();
        int height = pro.getHeight();
        imageColor = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageColor.createGraphics();
        HSLColor color = new HSLColor(Color.YELLOW);
        int w = Math.min(width, height);
        int x = (width - w) / 2;
        int y = (height - w) / 2;
        for (int i = 1; i <= 360; i++) {
            g2.setColor(color.adjustHue(i));
            g2.fillArc(x - 2, y - 2, w + 4, w + 4, i, 1);
        }
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        int v = Math.max(d.width, d.height);
        d.setSize(v, v);
        return d;
    }

    private Area createCircle(double percentComplete) {
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - b.right - b.left;
        int barRectHeight = progressBar.getHeight() - b.top - b.bottom;
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return null;
        }
        double degree = 360 * percentComplete;
        double sz = Math.min(barRectWidth, barRectHeight);
        double cx = b.left + barRectWidth * .5;
        double cy = b.top + barRectHeight * .5;
        double or = sz * .5;
        double ir = or * .85; //or - 20;
        Shape inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
        Shape outer = new Arc2D.Double(
                cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);
        Area area = new Area(outer);
        area.subtract(new Area(inner));
        return area;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        int width = c.getWidth();
        int height = c.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(progressBar.getForeground());
        g2.fill(createCircle(progressBar.getPercentComplete()));
        g2.setComposite(AlphaComposite.SrcIn);
        if (imageColor == null) {
            createImage();
        }
        g2.drawImage(imageColor, 0, 0, null);
        g2.dispose();
        Graphics2D gg2 = (Graphics2D) g;
        gg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gg2.setColor(pro.getBackground());
        gg2.fill(createCircle(100));
        g.drawImage(img, 0, 0, null);
        if (progressBar.isStringPainted()) {
            paintString(g);
        }
    }

    private void paintString(Graphics g) {
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - b.right - b.left;
        int barRectHeight = progressBar.getHeight() - b.top - b.bottom;
        g.setColor(pro.getForeground());
        paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
    }
}
