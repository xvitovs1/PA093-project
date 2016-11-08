public class KdNode{
  public Point point;
  public KdNode left;
  public KdNode right;
  public int depth = 0;
  
  public KdNode(Point p, KdNode left, KdNode right, int depth){
    this.point = p;
    this.left = left;
    this.right = right;
    this.depth = depth;
  }
}