import java.util.Comparator;

// Comparator for comparing points by x coordinate
public class PointsByXComparator implements Comparator<Point> {
  
   @Override
  public int compare(Point a, Point b) {
    float diff = a.x - b.x;
    if(diff > 0) return 1;
    if(diff < 0) return -1;
    
    return 0;
  }

  public boolean equals(Object obj) {
    return this.equals(obj);
  }

}