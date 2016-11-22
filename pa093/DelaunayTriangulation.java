import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import processing.core.PVector;

public class DelaunayTriangulation{
  
  // Delaunay triangulation
  public static ArrayList<LineSegment> triangulate(ArrayList<Point> points, ArrayList<LineSegment> AEL){
    
    ArrayList<LineSegment> DT = new ArrayList<LineSegment>();
    
    // get random point from points
    Random randomGenerator = new Random();
    int randIndex = randomGenerator.nextInt(points.size());
    Point p1 = points.get(randIndex);
    
    // Get the nearest point to p1
    Point p2 = null; 
    float distance = Float.MAX_VALUE;
    for (Point p : points){
      if(p.equals(p2)) continue;
      float d = Point.distance(p, p2);
      if(d < distance){
        distance = d;
        p2 = p;
      }
    }
    
    // Create edge p1p2
    LineSegment e = new LineSegment(p1, p2);
    
    // Find the point with smallest Delanay distance on the left from e
    Point p = getPointWithSmallestDD(points, e);
    
    if(p == null){
      e = new LineSegment(p2, p1);
      p = getPointWithSmallestDD(points, e);
    }
    
    LineSegment e2 = new LineSegment(p2, p);
    LineSegment e3 = new LineSegment(p, p1);
    
    // Add e, e2, e3 to AEL
    AddToAEL(e, AEL, DT);
    AddToAEL(e2, AEL, DT);
    AddToAEL(e3, AEL, DT);
    
    while(!AEL.isEmpty()){
      e = AEL.get(0);
      
    }
    
    return null;      
  }
  
  // Computes delaunay distance between point p and line segment ls
  private static float delaunayDistance(LineSegment ls, Point p){
    //TODO
    return 0;
  }
  
  private static Point getPointWithSmallestDD(ArrayList<Point> points, LineSegment ls){
    Point p = null;
    float distance = Float.MAX_VALUE;
    for (Point cp : points){
      float d = delaunayDistance(ls, cp);
      if(d < distance){
        distance = d;
        p = cp;
      }
    }
    
    return p;
  }
  
  // Adds edge to Active Edge List
  private static void AddToAEL(LineSegment e, ArrayList<LineSegment> AEL, ArrayList<LineSegment> DT){
    LineSegment oe = new LineSegment(e.y, e.x);
    if(AEL.contains(oe)){
      AEL.remove(e);
    }
    else {
      AEL.add(e);
    }
    
    DT.add(e);
  }
  
  
}