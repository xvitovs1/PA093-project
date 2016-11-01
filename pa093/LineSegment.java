import java.util.ArrayList;

public class LineSegment {
  Point x;
  Point y;
  
   public LineSegment(Point x, Point y){
     this.x = x;
     this.y = y;
   }
   
  public static boolean isOnLine(Point p, LineSegment l){
    float dxc = p.x - l.x.x;
    float dyc = p.y - l.x.y;
    
    float dxl = l.y.x - l.x.x;
    float dyl = l.y.y - l.x.y;
    
    float cross = dxc * dyl - dyc * dxl;

    if (cross != 0)
      return false;
      
    return true;
  }

}