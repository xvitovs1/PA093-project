import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import processing.core.PVector;

public class DelaunayTriangulation{
  
  // Delaunay triangulation
  public static ArrayList<LineSegment> triangulate(ArrayList<Point> points){
    
    ArrayList<LineSegment> DT = new ArrayList<LineSegment>();
    ArrayList<LineSegment> AEL = new ArrayList<LineSegment>();
    
    // get random point from points
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
    
    // Find the point with smallest Delanay distance on the left from e
    ArrayList<Point> pointsOnLeft = getPointsOnLeft(e, points);
    Point p = getPointWithSmallestDD(pointsOnLeft, e);
    
    if(p == null){
      e = new LineSegment(p2, p1);
      p = getPointWithSmallestDD(points, e);
    }
    
    LineSegment e2 = new LineSegment(p2, p);
    LineSegment e3 = new LineSegment(p, p1);
    
    // Add e, e2, e3 to AEL
    addToAEL(e, AEL, DT);
    addToAEL(e2, AEL, DT);
    addToAEL(e3, AEL, DT);
    System.out.println(AEL.size());
    int i = 10;
    while(!AEL.isEmpty() && i>0){ //<>//
      for(LineSegment l : AEL) System.out.println(l);
      i--;
      e = AEL.get(0);
      System.out.println("Got " + e);
      LineSegment oe = new LineSegment(e.y, e.x);
      // Find the point with smallest Delanay distance on the left from e
      pointsOnLeft = getPointsOnLeft(oe, points);

      p = getPointWithSmallestDD(pointsOnLeft, oe);
      if(p != null){
        e2 = new LineSegment(oe.x, p);
        e3 = new LineSegment(p, oe.y);
        
        if(!AEL.contains(e2) && !DT.contains(e2) && !AEL.contains(e3) && !DT.contains(e3)){
          addToAEL(e2, AEL, DT);
          addToAEL(e3, AEL, DT);
        }
        //if(!AEL.contains(e3) && !DT.contains(e3)) addToAEL(e3, AEL, DT);
      }
      
      DT.add(oe);
      
      AEL.remove(e);
      System.out.println(AEL.size());
    }
    
    return DT;      
  }
  
  // Computes delaunay distance between point p and line segment ls
  private static float delaunayDistance(LineSegment ls, Point p){
    Point circumcenter = getCircumcircleCenter(p, ls);
    float r = (float)Point.distance(p, circumcenter);
    
    if((Point.getOrientation(p, ls.x, ls.y) < 0 && Point.getOrientation(circumcenter, ls.x, ls.y) > 0) 
    || (Point.getOrientation(p, ls.x, ls.y) > 0 && Point.getOrientation(circumcenter, ls.x, ls.y) < 0)){
      return -r;
    }
    else {
      return r;
    }
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
  
  private static ArrayList<Point> getPointsOnLeft(LineSegment ls, ArrayList<Point> points){
    ArrayList<Point> pointsOnLeft = new ArrayList<Point>();
    for(Point cp : points){
      if(Point.getOrientation(cp, ls.x, ls.y) > 0){
        pointsOnLeft.add(cp); //TODO check this
      }
    }
    return pointsOnLeft;
  }
  
  // Adds edge to Active Edge List
  private static void addToAEL(LineSegment e, ArrayList<LineSegment> AEL, ArrayList<LineSegment> DT){
    LineSegment oe = new LineSegment(e.y, e.x);
    if(AEL.contains(oe)){
      AEL.remove(e);
            System.out.println("removed.");
    }
    else {
      AEL.add(e);
              System.out.println("added.");
    }
    
    DT.add(e);
  }
  
  private static Point getCircumcircleCenter(Point p, LineSegment ls){
    /*Point p1 = ls.x;
    Point p2 = ls.y;
    Point midDiff = new Point((p2.x-p.x)/2, (p2.y-p.y)/2);
    Point u = new Point(p.y-p1.y, p1.x-p.x);
    Point v = new Point(p2.x-p1.x, p2.y-p1.x);
    float t = Point.dot(midDiff, v)/Point.dot(u, v);
    return new Point(t*u.x + (p.x+p1.x)/2, t*u.y + (p.y+p1.y)/2);*/
    Point p2 = ls.x;
    Point p3 = ls.y;
    float cp = Point.cross(p, p2, p3);
    if (cp != 0) {
      float pSq = (p.x * p.x)+ (p.y * p.y);
      float p2Sq = (p2.x * p2.x)+ (p2.y * p2.y);
      float p3Sq = (p3.x * p3.x)+ (p3.y * p3.y);
      float num = pSq * (p2.y -p3.y) + p2Sq *(p3.y-p.y) + p3Sq *(p.y-p2.y); 
      float cx= num / (2.0f * cp);
      num = pSq *(p3.x -p2.x) + p2Sq*(p.x-p3.x) + p3Sq*(p2.x -p.x);
      float cy= num / (2.0f * cp);
      return new Point(cx, cy); 
    }
    return p;
  }
  
  
  
  
}