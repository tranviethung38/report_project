/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP_Whiteboard;

/**
 *
 * @author ASUS
 */
import java.io.Serializable;
import java.awt.*;
import java.util.ArrayList;

public class DrawingObject implements Serializable {
    private ArrayList<ArrayList<Point>> freehandLines;
    private ArrayList<ShapeObject> shapes;
    private Color color;

    public DrawingObject(ArrayList<ArrayList<Point>> freehandLines, ArrayList<ShapeObject> shapes) {
        this.freehandLines = freehandLines;
        this.shapes = shapes;
        //this.color = color;
    }

    public ArrayList<ArrayList<Point>> getFreehandLines() {
        return freehandLines;
    }

    public ArrayList<ShapeObject> getShapes() {
        return shapes;
    }
    
    public Color getcolor(){
        return color;
    }
}
