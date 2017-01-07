import java.util.Comparator;

// Comparator for comparing points by angle they form
public class PointsByAngleComparator implements Comparator<Point> { 
  private Point basePoint;
  private Point x;
  
  public PointsByAngleComparator(Point basePoint){
    this.basePoint = basePoint;
    this.x = new Point(basePoint.x - 1, basePoint.y);
  }

  public int compare(Point a, Point b) {
    float angleDiff = Point.getAngle(basePoint, x, a) - Point.getAngle(basePoint, x, b);
    if (angleDiff < 0){
      return -1;
    } else if (angleDiff > 0){
      return 1;
    } else {
      return 0;
    }
  }

  public boolean equals(Object obj) {
    return this.equals(obj);
  }

}