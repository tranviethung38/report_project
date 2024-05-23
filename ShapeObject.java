/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP_Whiteboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
class ShapeObject implements Serializable {
    private ShapeType shapeType;
    private Color color;
    private Point startPoint;
    private Point endPoint;
    private ArrayList<Point> freehandPoints;

    public ShapeObject(ShapeType shapeType, Color color, Point startPoint, Point endPoint) {
        this.shapeType = shapeType;
        this.color = color;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.freehandPoints = freehandPoints;
    }

    public void draw(Graphics g) {
        g.setColor(color);

        if (shapeType == ShapeType.RECTANGLE) {
            int width = endPoint.x - startPoint.x;
            int height = endPoint.y - startPoint.y;
            g.drawRect(startPoint.x, startPoint.y, width, height);
        } else if (shapeType == ShapeType.CIRCLE) {
            int radius = (int) startPoint.distance(endPoint);
            g.drawOval(startPoint.x - radius, startPoint.y - radius, 2 * radius, 2 * radius);
        }else if (shapeType == ShapeType.FilledRectangle) {
            int width = endPoint.x - startPoint.x;
            int height = endPoint.y - startPoint.y;
            g.fillRect(startPoint.x, startPoint.y, width, height);
        }else if (shapeType == ShapeType.FilledCircle) {
            int radius = (int) startPoint.distance(endPoint);
            g.fillOval(startPoint.x - radius, startPoint.y - radius, 2 * radius, 2 * radius);
        }else if (shapeType == ShapeType.LINE) {
            g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
    }
}
enum ShapeType {
    FREEHAND, RECTANGLE, CIRCLE, FilledRectangle, FilledCircle, LINE
}
