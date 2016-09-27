public class Point {
  float x;
  float y;
  float radius;
  
  // Point radius
public static final int POINT_RADIUS = 5;
  
  Point(float x, float y, float radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
  
  @Override
  public boolean equals(Object obj) {
      if (obj == null) {
          return false;
      }
      if (!(obj instanceof Point)) {
          return false;
      }
      
      Point other = (Point) obj;
      if ((this.x != other.x) || (this.y != other.y)) {
          return false;
      }

      return true;
  }
  
  @Override
  public int hashCode() {
      int hash = 840 * Math.round(this.x) + Math.round(this.y);
      return hash;
  }
  
  // Check if given point is inside our ellipse
  boolean contains(float x, float y) {
    if((Math.pow((x - this.x),2) + Math.pow((y - this.y),2)) <= Math.pow(this.radius,2)) {
      return true;
    }
    return false;
  }
}