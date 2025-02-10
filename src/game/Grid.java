package src.game;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import src.entities.Cell;

public class Grid extends JComponent implements KeyListener, MouseListener {
    private Cell[][] cells;

    private JFrame frame;
    private int lastKeyPressed;
    private Location lastLocationClicked;

    private Color lineColor;

    public Grid(int numRows, int numCols) {
        init(numRows, numCols);
    }

    public Grid(String imageFileName) {
        BufferedImage image = loadImage(imageFileName);
        init(image.getHeight(), image.getWidth());
        showImage(image);
        setTitle(imageFileName);
    }

    private BufferedImage loadImage(String imageFileName) {
        URL url = getClass().getResource(imageFileName);
        if (url == null)
            throw new RuntimeException("cannot find file:  " + imageFileName);
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("unable to read from file:  " + imageFileName);
        }
    }

    // its gonna return the number of rows right away cuz it has initialized at the
    // very begining
    public int getNumRows() {
        return cells.length;
    }

    public int getNumCols() {
        return cells[0].length;
    }

    private void init(int numRows, int numCols) {
        lastKeyPressed = -1;
        lastLocationClicked = null;
        lineColor = null;

        cells = new Cell[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++)
                cells[row][col] = new Cell();
        }

        frame = new JFrame("Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);

        int cellSize = Math.max(Math.min(500 / getNumRows(), 500 / getNumCols()), 1);
        setPreferredSize(new Dimension(cellSize * numCols, cellSize * numRows));
        addMouseListener(this);
        frame.getContentPane().add(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void showImage(BufferedImage image) {
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumCols(); col++) {
                int x = col * image.getWidth() / getNumCols();
                int y = row * image.getHeight() / getNumRows();
                int c = image.getRGB(x, y);
                int red = (c & 0x00ff0000) >> 16;
                int green = (c & 0x0000ff00) >> 8;
                int blue = c & 0x000000ff;
                cells[row][col].setColor(new Color(red, green, blue));
            }
        }
        repaint();
    }

    private int getCellSize() {
        int cellWidth = getWidth() / getNumCols();
        int cellHeight = getHeight() / getNumRows();
        return Math.min(cellWidth, cellHeight);
    }

    public void keyPressed(KeyEvent e) {
        lastKeyPressed = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e) {
        // ignored
    }

    public void keyTyped(KeyEvent e) {
        // ignored
    }

    public void mousePressed(MouseEvent e) {
        int cellSize = getCellSize();
        int row = e.getY() / cellSize;
        if (row < 0 || row >= getNumRows())
            return;
        int col = e.getX() / cellSize;
        if (col < 0 || col >= getNumCols())
            return;
        lastLocationClicked = new Location(row, col);
    }

    public void mouseReleased(MouseEvent e) {
        // ignore
    }

    public void mouseClicked(MouseEvent e) {
        // ignore
    }

    public void mouseEntered(MouseEvent e) {
        // ignore
    }

    public void mouseExited(MouseEvent e) {
        // ignore
    }

    private static java.awt.Color toJavaColor(Color color) {
        return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public void paintComponent(Graphics g) {
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumCols(); col++) {
                Location loc = new Location(row, col);
                Cell cell = cells[loc.getRow()][loc.getCol()];

                Color color = cell.getColor();
                g.setColor(toJavaColor(color));
                int cellSize = getCellSize();
                int x = col * cellSize;
                int y = row * cellSize;
                g.fillRect(x, y, cellSize, cellSize);

                String imageFileName = cell.getImageFileName();
                if (imageFileName != null) {
                    URL url = getClass().getResource(imageFileName);
                    if (url == null)
                        System.out.println("File not found:  " + imageFileName);
                    else {

                        Image image = new ImageIcon(url).getImage();
                        int width = image.getWidth(null);
                        int height = image.getHeight(null);
                        int max;
                        if (width > height) {
                            int drawHeight = cellSize * height / width;
                            g.drawImage(image, x, y + (cellSize - drawHeight) / 2, cellSize, drawHeight, null);
                        } else {
                            int drawWidth = cellSize * width / height;
                            g.drawImage(image, x + (cellSize - drawWidth) / 2, y, drawWidth, cellSize, null);
                        }
                    }
                }

                if (lineColor != null) {
                    g.setColor(toJavaColor(lineColor));
                    g.drawRect(x, y, cellSize, cellSize);
                }
            }
        }
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public boolean isValid(Location loc) {
        int row = loc.getRow();
        int col = loc.getCol();
        return 0 <= row && row < getNumRows() && 0 <= col && col < getNumCols();
    }

    public void setColor(Location loc, Color color) {
        if (!isValid(loc))
            throw new RuntimeException("cannot set color of invalid location " + loc + " to color " + color);
        cells[loc.getRow()][loc.getCol()].setColor(color);
        repaint();
    }

    public Color getColor(Location loc) {
        if (!isValid(loc))
            throw new RuntimeException("cannot get color from invalid location " + loc);
        return cells[loc.getRow()][loc.getCol()].getColor();
    }

    // its gonna return nothing just set the file name of specified location as you
    // specified
    public void setImage(Location loc, String imageFileName) {// takes Object in as a parameter and fileName
        if (!isValid(loc)) {
            // throw new RuntimeException("cannot set image for invalid location " + loc + "
            // to \"" + imageFileName + "\"");
        } else {
            cells[loc.getRow()][loc.getCol()].setImageFileName(imageFileName);
        }
        // invoking a setImageFileName from Cell object because cells[][] array is in a
        // cell class
        // and pass an string from a parameter of this method
        // and it also passes its location to the index for the array
        repaint();
    }

    // it returns String at the specified location that contains file name
    public String getImage(Location loc) {
        if (!isValid(loc)) {
            // throw new RuntimeException("cannot get image for invalid location " + loc);
            return null;
        } else {
            return cells[loc.getRow()][loc.getCol()].getImageFileName();
        }
        // invoking getImageFileName method of reference variable of type Cell in a 2d
        // array cells
    }

    public static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);

        } catch (Exception e) {
            // ignore
        }
    }

    public int checkLastKeyPressed() {
        int key = lastKeyPressed;

        lastKeyPressed = -1;

        return key;
    }

    public Location checkLastLocationClicked() {
        Location loc = lastLocationClicked;

        lastLocationClicked = null;

        return loc;
    }

    public void load(String imageFileName) {
        showImage(loadImage(imageFileName));
        setTitle(imageFileName);
    }

    public void save(String imageFileName) {
        try {
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            paintComponent(bi.getGraphics());
            int index = imageFileName.lastIndexOf('.');
            if (index == -1)
                throw new RuntimeException("invalid image file name:  " + imageFileName);
            ImageIO.write(bi, imageFileName.substring(index + 1), new File(imageFileName));
        } catch (IOException e) {
            throw new RuntimeException("unable to save image to file:  " + imageFileName);
        }
    }

    public void setLineColor(Color color) {
        lineColor = color;
        repaint();
    }

    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public String showInputDialog(String message) {
        return JOptionPane.showInputDialog(this, message);
    }
}