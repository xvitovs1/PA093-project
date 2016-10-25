import java.util.ArrayList;

public class LineSegment {
  Point x;
  Point y;
  
   public LineSegment(Point x, Point y){
     this.x = x;
     this.y = y;
   }
   
     
 
   public static Point lineIntersect(Point p1, Point p2, Point p3, Point p4) {
      float denom = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
      if (denom == 0.0) { // Lines are parallel.
         return null;
      }
      float ua = ((p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x))/denom;
      float ub = ((p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x))/denom;
        if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
            // Get the intersection point.
            return new Point(p1.x + ua*(p2.x - p1.x), p1.y + ua*(p2.y - p1.y));
        }
    
      return null;
  }

  private static boolean isOnLine(Point p, LineSegment l){
    float dxc = p.x - l.x.x;
    float dyc = p.y - l.x.y;
    
    float dxl = l.y.x - l.x.x;
    float dyl = l.y.y - l.x.y;
    
    float cross = dxc * dyl - dyc * dxl;

    if (cross != 0)
      return false;
      
    return true;
  }
  
  //Checks if segment a is inside the polygon
  public static boolean segmentInPolygon(ArrayList<LineSegment> polygon, ArrayList<Point> vertices, LineSegment a){
    if(!Point.isPointInPolygon(vertices, new Point((a.x.x + a.y.x)/2, (a.x.y + a.y.y)/2))) return false;
    
    for(LineSegment s : polygon){
      Point intersect = lineIntersect(s.x, s.y, a.x, a.y);
      if(intersect != null && !isOnLine(a.x, s) && !isOnLine(a.y, s)) {System.out.println(intersect + " " + a.x + " " + a.y);return false;}
    }
    
    return true;
  }
 
}