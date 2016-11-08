import java.util.Comparator;

public class PointsByXComparator implements Comparator<Point> {
  
  @Override
  public int compare(Point a, Point b) {
    float diff = a.x - b.x;
    if(diff > 0) return -1;
    if(diff < 0) return 1;
    
    diff = a.y - b.y;
    if(diff > 0) return -1;
    if(diff < 0) return 1;
    
    return 0;
  }

  public boolean equals(Object obj) {
    return this.equals(obj);
  }

}