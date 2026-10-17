package path;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

// draws a static image of the grid after search is done
// shows: obstacles, explored cells as heatmap, and the final path
public class GridImage {

	public static void show(BufferedImage img, String title) {
	    JFrame frame = new JFrame(title);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.add(new JLabel(new ImageIcon(img)));
	    frame.pack();
	    frame.setVisible(true);
	}

    static final int CELL = 10; // pixels per cell

    public static void save(boolean[][] grid, List<int[]> path, int[] start, int[] goal,
                            int[][] visitCount, String filename) {

        int rows = grid.length;
        int cols = grid[0].length;
        int w = cols * CELL;
        int h = rows * CELL;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // find max visits for heatmap scaling
        int maxVisit = 1;
        for (int[] row : visitCount)
            for (int v : row)
                if (v > maxVisit) maxVisit = v;

        // draw each cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int px = c * CELL;
                int py = r * CELL;

                if (grid[r][c]) {
                    g.setColor(new Color(50, 50, 50)); // dark = obstacle
                } else if (visitCount[r][c] > 0) {
                    // heatmap: light blue for rarely visited, orange for heavily visited
                    float t = (float) visitCount[r][c] / maxVisit;
                    int red   = (int)(t * 220);
                    int green = (int)((1 - t) * 150 + 50);
                    int blue  = (int)((1 - t) * 220);
                    g.setColor(new Color(red, green, blue));
                } else {
                    g.setColor(new Color(240, 240, 240)); // unvisited = light gray
                }

                g.fillRect(px, py, CELL, CELL);
            }
        }

        // draw the path
        if (path != null) {
            g.setColor(new Color(255, 220, 0)); // yellow path
            g.setStroke(new BasicStroke(3f));
            for (int i = 0; i < path.size() - 1; i++) {
                int[] a = path.get(i);
                int[] b = path.get(i + 1);
                int ax = a[1] * CELL + CELL / 2;
                int ay = a[0] * CELL + CELL / 2;
                int bx = b[1] * CELL + CELL / 2;
                int by = b[0] * CELL + CELL / 2;
                g.drawLine(ax, ay, bx, by);
            }
        }

        // draw start (green) and goal (red)
        int dotSize = CELL + 4;

        g.setColor(Color.GREEN);
        g.fillOval(start[1] * CELL - 2, start[0] * CELL - 2, dotSize, dotSize);

        g.setColor(Color.RED);
        g.fillOval(goal[1] * CELL - 2, goal[0] * CELL - 2, dotSize, dotSize);

        g.dispose();

        try {
            ImageIO.write(img, "PNG", new File(filename));
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
        show(img, filename);
    }
    
}
