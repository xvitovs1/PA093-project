import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import processing.core.PVector;

public class Triangulation{
  
  // Triangulation algorithm from http://www.cs.uu.nl/geobook/pseudo.pdf
  public static ArrayList<LineSegment> triangulate(ArrayList<Point> points, ArrayList<LineSegment> polygon){
    // Sort the points
    ArrayList<Point> sortedPoints = new ArrayList<Point>(points);
    Collections.sort(sortedPoints, new PointsByYComparator());

    ArrayList<Point> queueLeft = new ArrayList<Point>();
    ArrayList<Point> queueRight = new ArrayList<Point>();

    fillQueues(sortedPoints.get(0), sortedPoints.get(sortedPoints.size()-1), queueLeft, queueRight, points);
 
    Stack stack = new Stack();
    stack.push(sortedPoints.get(0));
    stack.push(sortedPoints.get(1));
    
    for(int i = 2; i < sortedPoints.size() - 1; i++){
      boolean bothOnLeft = queueLeft.contains(sortedPoints.get(i)) && queueLeft.contains(stack.peek());
      boolean bothOnRight = queueRight.contains(sortedPoints.get(i)) && queueRight.contains(stack.peek());
      if(!bothOnLeft && !bothOnRight) {
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
        //stack.pop();
        // Pop the other vertices as long as the diagonals from sortedPoints[i] to them are inside polygon and insert
        Point poppedPoint = (Point)stack.pop();
        float v1x = 0;
        float v1y = 0;
        float v2x =0;
        float v2y = 0;
        if (!stack.empty()) {
            v1x =  poppedPoint.x - ((Point)stack.peek()).x;
            v1y =  poppedPoint.y - ((Point)stack.peek()).y;
            v2x = sortedPoints.get(i).x - ((Point)stack.peek()).x;
            v2y = sortedPoints.get(i).y - ((Point)stack.peek()).y ;
        }
        while(!stack.empty() && ((v1x * v2y - v1y * v2x >= 0) ? bothOnLeft : bothOnRight)){
          LineSegment ls = new LineSegment(sortedPoints.get(i),poppedPoint);
          polygon.add(ls);
          poppedPoint = (Point)stack.pop();
          
          if(!stack.empty()){
            v1x =  poppedPoint.x - ((Point)stack.peek()).x;
            v1y =  poppedPoint.y - ((Point)stack.peek()).y;
            v2x = sortedPoints.get(i).x - ((Point)stack.peek()).x;
            v2y = sortedPoints.get(i).y - ((Point)stack.peek()).y ;
          }
          
         ls = new LineSegment(sortedPoints.get(i),poppedPoint);
         polygon.add(ls);
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
  
    public static ArrayList<LineSegment> triangulate(ArrayList<Point> points){
    // Get polygon
    ArrayList<LineSegment> polygon = new ArrayList<LineSegment>();
    for(int i = 1; i < points.size(); i++){
      polygon.add(new LineSegment(points.get(i-1), points.get(i)));
    }
    polygon.add(new LineSegment(points.get(0), points.get(points.size() - 1)));
    
    return triangulate(points, polygon);
  }
  
  private static void fillQueues(Point highestPoint, Point lowestPoint, ArrayList<Point> queueLeft, ArrayList<Point> queueRight, ArrayList<Point> points){
    
    ArrayList<Point> chain1 = new ArrayList<Point>();
    ArrayList<Point> chain2 = new ArrayList<Point>();
    int highest = points.indexOf(highestPoint);
    int i = highest;
    Point currentPoint = points.get(i);
    while(!currentPoint.equals(lowestPoint)){
      chain1.add(currentPoint);
      if(i == points.size() - 1){
        i = 0;
      } else {
        i++;
      }
      currentPoint = points.get(i);
    }
    
    chain1.add(currentPoint);
    if(i == points.size() - 1){
        i = 0;
      } else {
        i++;
      }
    currentPoint = points.get(i);
    
    while(!currentPoint.equals(highestPoint)){
      chain2.add(currentPoint);
      if(i == points.size() - 1){
        i = 0;
      } else {
        i++;
      }
      currentPoint = points.get(i);
    }
        
    int before;
    int after;
    if(highest == 0){
      after = highest+1;
      before = points.size() - 1;
    } else if(highest == points.size() - 1){
      after = 0;
      before = highest - 1;
    } else{
      before = highest - 1;
      after = highest + 1;
    }
    
    if(pIsOnRight(points.get(before), highestPoint, points.get(after))){
      if(chain1.contains(before)){
          queueLeft.addAll(chain2);
          queueRight.addAll(chain1);
      } else {
          queueLeft.addAll(chain1);
          queueRight.addAll(chain2);
      }
    }
    else{
      if(chain1.contains(before)){
          queueLeft.addAll(chain1);
          queueRight.addAll(chain2);
      } else {
          queueLeft.addAll(chain2);
          queueRight.addAll(chain1);
      }
    }
  }
  
  private static boolean pIsOnRight(Point p, Point a, Point b){
    float d = ((p.x-a.x)*(b.y-a.y)) - ((p.y-a.y)*(b.x-a.x));
    float left = (((a.x - 1)-a.x)*(b.y-a.y)) - ((a.y-a.y)*(b.x-a.x));
    if(left < 0 && d < 0){
      return false;
    }
    if(left < 0 && d > 0){
      return true;
    }
    if(left > 0 && d < 0){
      return true;
    }
    if(left > 0 && d > 0){
      return false;
    }
    
    return false;
  }
}