/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP_Whiteboard;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel {

    private ArrayList<Point> currentLine;
    private ArrayList<ArrayList<Point>> freehandLines;
    private ArrayList<ShapeObject> shapes;
    private Color currentColor;
    private ObjectOutputStream outputStream;
    private ShapeType currentShapeType;
    
    private Point startPoint;

    public DrawingCanvas() {
        this.freehandLines = new ArrayList<>();
        this.currentLine = new ArrayList<>();
        this.shapes = new ArrayList<>();
        this.currentColor = Color.BLACK;
        this.currentShapeType = ShapeType.FREEHAND;

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentShapeType == ShapeType.FREEHAND) {
                    currentLine.add(e.getPoint());
                }
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentShapeType == ShapeType.FREEHAND) {
                    currentLine = new ArrayList<>();
                    currentLine.add(e.getPoint());
                } else {
                    startPoint = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShapeType == ShapeType.FREEHAND) {
                    freehandLines.add(new ArrayList<>(currentLine));
                    sendDrawingObject();
                    currentLine.clear();
                } else {
                    addShape(e.getPoint());
                }
            }
        });

        setFocusable(true);
        setupButtons();
    }


    private void setupButtons() {
        JButton freehandButton = new JButton("");
        freehandButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("Pen.png"))));
        freehandButton.addActionListener(e -> setCurrentShapeType(ShapeType.FREEHAND));

        JButton rectangleButton = new JButton();
        rectangleButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("vuongtrang.png"))));
        rectangleButton.addActionListener(e -> setCurrentShapeType(ShapeType.RECTANGLE));

        JButton circleButton = new JButton();
        circleButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("Trontrang.png"))));
        circleButton.addActionListener(e -> setCurrentShapeType(ShapeType.CIRCLE));
        
        JButton FilledRectangleButton = new JButton();
        FilledRectangleButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("vuongden.png"))));
        FilledRectangleButton.addActionListener(e -> setCurrentShapeType(ShapeType.FilledRectangle));

        JButton FilledCircleButton = new JButton();
        FilledCircleButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("tronden.png"))));
        FilledCircleButton.addActionListener(e -> setCurrentShapeType(ShapeType.FilledCircle));
        
        JButton LineButton = new JButton();
        LineButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("cheo.png"))));
        LineButton.addActionListener(e -> setCurrentShapeType(ShapeType.LINE));
        
        JButton colorButton = new JButton();
        colorButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("tronmau.png"))));
        colorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(this, "Choose Color", currentColor);
            if (selectedColor != null) {
                setCurrentColor(selectedColor);
            }
        });

        JButton clearButton = new JButton();
        clearButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(DrawingCanvas.class.getResource("xoa.png"))));
        clearButton.addActionListener(e -> clearCanvas());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(freehandButton);
        buttonPanel.add(rectangleButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(FilledRectangleButton);
        buttonPanel.add(FilledCircleButton);
        buttonPanel.add(LineButton);
        buttonPanel.add(colorButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void setCurrentShapeType(ShapeType shapeType) {
        this.currentShapeType = shapeType;
    }

    private void addShape(Point endPoint) {
        ShapeObject shape = new ShapeObject(currentShapeType, currentColor, startPoint, endPoint);
        shapes.add(shape);
        sendDrawingObject();
        repaint();
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void clearCanvas() {
        freehandLines.clear();
        shapes.clear();
        repaint();
    }

    public void sendDrawingObject() {
        try {
            DrawingObject drawingObject = new DrawingObject(new ArrayList<>(freehandLines), new ArrayList<>(shapes));
            outputStream.writeObject(drawingObject);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    public void setFreehandLines(ArrayList<ArrayList<Point>> freehandLines) {
        this.freehandLines = freehandLines;
        repaint();
    }

    public void setShapes(ArrayList<ShapeObject> shapes) {
        this.shapes = shapes;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(currentColor);


        for (ArrayList<Point> line : freehandLines) {
            for (int i = 0; i < line.size() - 1; i++) {
                Point p1 = line.get(i);
                Point p2 = line.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        for (ShapeObject shape : shapes) {
            shape.draw(g);
        }

        if (!currentLine.isEmpty() && currentShapeType == ShapeType.FREEHAND) {
            for (int i = 0; i < currentLine.size() - 1; i++) {
                Point p1 = currentLine.get(i);
                Point p2 = currentLine.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}
