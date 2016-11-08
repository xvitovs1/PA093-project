import java.util.Comparator;

public class PointsByXComparator implements Comparator<Point> {
  
  @Override
  public int compare(Point a, Point b) {
    return a.x - b.x;
  }

  public boolean equals(Object obj) {
    return this.equals(obj);
  }

}