import java.util.ArrayList;
import java.util.Collections;

public class ConvexHull {
  
  // Gift Wrapping
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

    // Get any point on the x-axis
    Point px = new Point(q.x - 1, q.y); //p_j-1
    
    Point middlePoint = q;
    Point newPoint = px;
    
    // Compute convex hull
    while(!newPoint.equals(q)){
      float angle = 360;
      for(Point p : points){
        if(p.equals(px) || p.equals(middlePoint)){
          continue;
        }
        
        float newAngle = Point.getAngle(middlePoint, px, p);
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
  
  // Graham Scan
  public static ArrayList<Point> grahamScan(ArrayList<Point> points){
    if(points.isEmpty()) return new ArrayList<Point>();
    
    ArrayList<Point> convexHull = new ArrayList();
    
    // Find pivot
    Point q = points.get(0);
    for(Point p : points){
      if(p.y < q.y){
        q = p;
      } else if(p.y == q.y && p.x < q.x){
        q = p;
      }
    }
    
    // Sort points by angle
    ArrayList<Point> sortedPoints = new ArrayList(points);
    Collections.sort(sortedPoints, new PointsByAngleComparator(q));
    
    int j = 2;
    
    convexHull.add(q);
    convexHull.add(sortedPoints.get(1));
    int i = 1;
    
    while(j<sortedPoints.size()){
      Point pj = sortedPoints.get(j);
      if(Point.getOrientation(pj, convexHull.get(i-1),convexHull.get(i)) > 0){
        convexHull.add(pj);
        i++;
        j++;
      }
      else{
        convexHull.remove(convexHull.size() - 1);
        if(i>1) i--;
      }
    }

    return convexHull;    
  }
}