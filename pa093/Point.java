import processing.core.PVector;

public class Point {
  float x;
  float y;
  float radius;
  
  // Point radius
public static final int POINT_RADIUS = 5;
  
  Point(float x, float y) {
    this.x = x;
    this.y = y;
    this.radius = POINT_RADIUS;
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
  
  public static float getAngle(Point middlePoint, Point p2, Point p3){
    PVector v1 = new PVector(p2.x-middlePoint.x, p2.y-middlePoint.y);
    PVector v2 = new PVector(middlePoint.x-p3.x,middlePoint.y-p3.y);
    return PVector.angleBetween(v1,v2);
  }
  
  /**
  * Gets orientation of three points.
  * @param p1 point 1
  * @param p2 point 2
  * @param p2 point 3
  * @return 0 if the points lie on the same line, >0 if the points are counter-clockwise and <0 if the points are clockwise
  */
  public static float getOrientation(Point p1, Point p2, Point p3){
    return (p2.x-p1.x)*(p3.y-p1.y) - (p2.y-p1.y)*(p3.x-p1.x);
  }
}