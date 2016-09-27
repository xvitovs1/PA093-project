import java.util.ArrayList;
import processing.core.PVector;

public class ConvexHull {
  public static ArrayList<Point> giftWrapping(ArrayList<Point> points){
    if(points.isEmpty()) return new ArrayList<Point>();
    
    ArrayList<Point> convexHull = new ArrayList<Point>();
    
    // Find pivot
    Point q = points.get(0);
    for(Point p : points){
      if(p.y < q.y){
        q = p;
      }
    }
    System.out.println("[" +q.x + "," + q.y+ "]");
    // Add pivot to convex hull
    //convexHull.add(q);
    
    // Any point on the x-axis
    Point px = new Point(q.x - 1, q.y, Point.POINT_RADIUS); //p_j-1
    
    Point middlePoint = q;
    Point newPoint = px;
    
    while(!newPoint.equals(q)){
      float angle = 360;
      for(Point p : points){
        if(convexHull.contains(p) || p.equals(middlePoint)){
          continue;
        }
        
        float newAngle = getAngle(middlePoint, px, p);
        if(newAngle < angle){
          angle = newAngle;
          newPoint = p;
        }
      }
      
      convexHull.add(newPoint);
      px = middlePoint;
      middlePoint = newPoint;
    }
    
    return convexHull;
    
  }
  
  private static float getAngle(Point middlePoint, Point p2, Point p3){
    PVector v1 = new PVector(p2.x-middlePoint.x, p2.y-middlePoint.y);
    PVector v2 = new PVector(middlePoint.x-p3.x,middlePoint.y-p3.y);
    return PVector.angleBetween(v1,v2);
  }
}