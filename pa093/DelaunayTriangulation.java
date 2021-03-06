import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import processing.core.PVector;

public class DelaunayTriangulation{
  public ArrayList<LineSegment> DT = new ArrayList<LineSegment>();
  public ArrayList<Triangle> triangles = new ArrayList<Triangle>();
  
  public DelaunayTriangulation(ArrayList<Point> points){
    triangulate(points);
  }
    
  private ArrayList<Triangle> triangulate(ArrayList<Point> points){
    ArrayList<LineSegment> AEL = new ArrayList<LineSegment>();
    
    // Get random point from points
    Random randomGenerator = new Random();
    int randIndex = randomGenerator.nextInt(points.size());
    Point p1 = points.get(randIndex);
    
    // Get the nearest point to p1
    Point p2 = null; 
    float distance = Float.MAX_VALUE;
    for (Point p : points){
      if(p.equals(p1)) continue;
      float d = (float)Point.distance(p, p1);
      if(d < distance){
        distance = d;
        p2 = p;
      }
    }
    
    // Create edge p1p2
    LineSegment e = new LineSegment(p1, p2);
    
    // Find the point with smallest Delaunay distance on the left from e
    ArrayList<Point> pointsOnLeft = getPointsOnLeft(e, points);
    Point p = getPointWithSmallestDD(pointsOnLeft, e);
    LineSegment e2 = new LineSegment(p2, p);
    LineSegment e3 = new LineSegment(p, p1);
    
    if(p == null){
      e = new LineSegment(p2, p1);
      pointsOnLeft = getPointsOnLeft(e, points);
      p = getPointWithSmallestDD(pointsOnLeft, e);
      e2 = new LineSegment(p, p2);
      e3 = new LineSegment(p1, p);
    }
        
    // Add e, e2, e3 to AEL
    addToAEL(e, AEL, DT); //<>//
    addToAEL(e2, AEL, DT);
    addToAEL(e3, AEL, DT);
    triangles.add(new Triangle(e, e2, e3));
    while(!AEL.isEmpty()){ 
      e = AEL.get(0);
      LineSegment oe = new LineSegment(e.y, e.x);

      // Find the point with smallest Delaunay distance on the left from e
      pointsOnLeft = getPointsOnLeft(oe, points);
      p = getPointWithSmallestDD(pointsOnLeft, oe);
      
      if(p != null){
        e2 = new LineSegment(p, oe.x);
        e3 = new LineSegment(oe.y, p);
        if((!AEL.contains(e2) && !AEL.contains(new LineSegment(e2.y, e2.x))) && (!DT.contains(e2) && !DT.contains(new LineSegment(e2.y, e2.x)) )) addToAEL(e2, AEL, DT);
        if((!AEL.contains(e3) && !AEL.contains(new LineSegment(e3.y, e3.x))) && (!DT.contains(e3) && !DT.contains(new LineSegment(e3.y, e3.x)) )) addToAEL(e3, AEL, DT);
        triangles.add(new Triangle(e, e2, e3));
      }
      
      //DT.add(oe);
      
      AEL.remove(e);
    }
   
    return triangles;      
  }
  
  // Computes delaunay distance between point p and line segment ls
  private float delaunayDistance(LineSegment ls, Point p){
    Point circumcenter = Circumcircle.getCircumcircleCenter(p, ls);
    float r = (float)Point.distance(p, circumcenter);
    
    if((Point.getOrientation(p, ls.x, ls.y) < 0 && Point.getOrientation(circumcenter, ls.x, ls.y) > 0) 
    || (Point.getOrientation(p, ls.x, ls.y) > 0 && Point.getOrientation(circumcenter, ls.x, ls.y) < 0)){
      return -r;
    }
    else {
      return r;
    }
  }
  
  // Gets point with smallest delaunay distance from line ls
  private Point getPointWithSmallestDD(ArrayList<Point> points, LineSegment ls){
    Point p = null;
    float distance = Float.MAX_VALUE;
    for (Point cp : points){
      if(cp.equals(ls.x) || cp.equals(ls.y)) continue;
      float d = delaunayDistance(ls, cp);
      if(d < distance){
        distance = d;
        p = cp;
      }
    }
    
    return p;
  }
  
  // Gets all points that are on the left side of given line segment
  private ArrayList<Point> getPointsOnLeft(LineSegment ls, ArrayList<Point> points){
    ArrayList<Point> pointsOnLeft = new ArrayList<Point>();
    for(Point cp : points){
      if(Point.getOrientation(cp, ls.x, ls.y) < 0){
        pointsOnLeft.add(cp); 
      }
    }
    return pointsOnLeft;
  }
  
  // Adds edge to Active Edge List
  private void addToAEL(LineSegment e, ArrayList<LineSegment> AEL, ArrayList<LineSegment> DT){
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