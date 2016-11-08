import java.util.ArrayList;
import java.util.Collections;

public class KdTree{
  public static KdNode buildTree(ArrayList<Point> points, int depth){
    if(points.size() == 1){
      return new KdNode(points.get(0), null, null, depth);
    } else{
      ArrayList<Point> p1 = new ArrayList<Point>();
      ArrayList<Point> p2 = new ArrayList<Point>();
      Point l;
      if(depth % 2 == 0){
        Collections.sort(points, new PointsByXComparator());
        l = points.get(points.size() / 2);
        splitPoints(l, points, p1, p2);
      } else{
        //TODO check this later
        Collections.sort(p, new PointsByYComparator());
        l = points.get(points.size() / 2);
        splitPoints(l, points, p1, p2);
      }
      KdNode v_left = buildTree(p1, depth+1);
      KdNode v_right = buildTree(p2, depth+1);
      KdNode v = new KdNode(l, v_left, v_right, depth);
      return v;
    }
  }
  
  private static void splitPoints(Point median, ArrayList<Point> points, ArrayList<Point> p1, ArrayList<Point> p2){
    int i = 0;
    while(!((points.get(i)).equals(median))){
      p1.add(points.get(i));
      i++;
    }
    
    p2.add(points.get(i));
    i++;
    while(i < points.size()){
      p2.add(points.get(i));
      i++;
    }
  }
}