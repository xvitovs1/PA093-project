import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Triangulation{
  
  // Triangulation algorithm from http://www.cs.uu.nl/geobook/pseudo.pdf
  public static ArrayList<LineSegment> triangulate(ArrayList<Point> points){
    // Sort the points
    ArrayList<Point> sortedPoints = new ArrayList<Point>(points);
    Collections.sort(sortedPoints, new PointsByYComparator());
    
    // Get polygon
    ArrayList<LineSegment> polygon = new ArrayList<LineSegment>();
    for(int i = 1; i < points.size(); i++){
      polygon.add(new LineSegment(points.get(i-1), points.get(i)));
    }
    polygon.add(new LineSegment(points.get(0), points.get(points.size() - 1)));
    
    ArrayList<Point> queueLeft = new ArrayList<Point>();
    ArrayList<Point> queueRight = new ArrayList<Point>();

    fillQueues(sortedPoints, queueLeft, queueRight);
 
    Stack stack = new Stack();
    stack.push(sortedPoints.get(0));
    stack.push(sortedPoints.get(1));
    
    for(int i = 2; i < sortedPoints.size() - 1; i++){
      if((queueLeft.contains(sortedPoints.get(i)) && queueRight.contains(stack.peek())) ||
      (queueRight.contains(sortedPoints.get(i)) && queueLeft.contains(stack.peek()))) {
        // Pop all vertices from stack and insert a diagonal from sortedPoints[i] to each popped vertex except the last one
        while(!stack.empty()){
          polygon.add(new LineSegment(sortedPoints.get(i),(Point)stack.pop()));
        }
        polygon.remove(polygon.size() - 1);
        
        // Push sortedPoints[i-1] and sortedPoints[i] to stack
        stack.push(sortedPoints.get(i-1));
        stack.push(sortedPoints.get(i));      
      } else {
        // Pop one vertex from the stack
        stack.pop();
        // Pop the other vertices as long as the diagonals from sortedPoints[i] to them are inside polygon and insert
        Point poppedPoint = null;
        while(!stack.empty()){
          poppedPoint = (Point)stack.pop();
          LineSegment ls = new LineSegment(sortedPoints.get(i),poppedPoint);
          if(LineSegment.segmentInPolygon(polygon, points,ls)) polygon.add(ls);
        }
        
        // Push the last popped vertex back to the stack
        stack.push(poppedPoint);
        
        // Push sortedPoints[i] to the stack
        stack.push(sortedPoints.get(i));
      }
      
    }
    
    // Add diagonals from sortedPoints[n] to all stack vertices except the first and the last one
    stack.pop();
    if(stack.empty())
      return polygon;
    while(!stack.empty()){
        polygon.add(new LineSegment(sortedPoints.get(sortedPoints.size()-1),(Point)stack.pop()));
    }
    polygon.remove(polygon.size() - 1);
    
    return polygon;
  }
  
  private static void fillQueues(ArrayList<Point> sortedPoints, ArrayList<Point> queueLeft, ArrayList<Point> queueRight){
    for(Point p : sortedPoints){
      if(p.getOrientation(p,sortedPoints.get(0), sortedPoints.get(sortedPoints.size() - 1)) > 0){
        queueLeft.add(p);
      } else {
        queueRight.add(p);
      }
    }
  }
}