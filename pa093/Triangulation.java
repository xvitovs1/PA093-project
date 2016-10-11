import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Triangulation{
  
  // Triangulation algorithm from http://www.cs.uu.nl/geobook/pseudo.pdf
  public static ArrayList<LineSegment> triangulate(ArrayList<LineSegment> points){
    // Sort the points
    ArrayList<Point> sortedPoints = new ArrayList<Point>(points);
    Collections.sort(sortedPoints, new PointsByYComparator());
    
    // Get polygon
    ArrayList<LineSegment> polygon = new ArrayList<LineSegment>();
    for(int i = 1; i < points.size(); i++){
      polygon.add(new LineSegment(points.get(i-1), points.get(i)));
    }
    
    Stack stack = new Stack();
    stack.push(sortedPoints.get(0));
    stack.push(sortedPoints.get(1));
    
    for(int i = 2; i < polygon.size() - 1; i++){
      if(1) {
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
        while(!stack.empty() && (1)){
          polygon.add(new LineSegment(sortedPoints.get(j),(Point)stack.pop()));
        }
        // Push the last popped vertex back to the stack
        
        // Push sortedPoints[i] to the stack
        stack.push(sortedPoints.get(i));
      }
      
      // Add diagonals from sortedPoints[n] to all stack vertices except the first and the last one
      stack.pop();
       while(!stack.empty()){
          polygon.add(new LineSegment(sortedPoints.get(sortedPoints.size()-1),(Point)stack.pop()));
       }
       polygon.remove(polygon.size() - 1);
    }
    
    
    return null;
  }
}