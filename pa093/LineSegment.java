import java.util.ArrayList;

// Represents a line segment defind by two ending points x, y
public class LineSegment {
   Point x;
   Point y;
  
   public LineSegment(Point x, Point y){
    this.x = x;
    this.y = y;
   }
   
   // Returns true if point p lies on the line segment l
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
  
  // Returns middle of the line segment
  public Point getMiddle(){
    return new Point((this.x.x+this.y.x)/2, (this.x.y+this.y.y)/2);
  }
  
  @Override
  public boolean equals(Object obj) {
      if (obj == null) {
          return false;
      }
      if (!(obj instanceof LineSegment)) {
          return false;
      }
      
      LineSegment other = (LineSegment) obj;
      if (!(this.x.equals(other.x)) || (!this.y.equals(other.y))) {
          return false;
      }

      return true;
  }
  
  @Override
  public int hashCode() {
      int hash = 877 * Math.round(this.x.x) + Math.round(this.y.y) * Math.round(this.y.x);
      return hash;
  }
  
  @Override
  public String toString(){
    return "(" + x + "," + y + ")";
  }

}