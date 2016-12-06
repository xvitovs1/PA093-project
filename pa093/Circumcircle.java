public class Circumcircle{
  
   // Gets center of circumcircle of given line segment and point
  public static Point getCircumcircleCenter(Point p, LineSegment ls){
    Point p2 = ls.x;
    Point p3 = ls.y;
    float cp = Point.cross(p, p2, p3);
    if (cp != 0) {
      float pSq = (p.x * p.x)+ (p.y * p.y);
      float p2Sq = (p2.x * p2.x)+ (p2.y * p2.y);
      float p3Sq = (p3.x * p3.x)+ (p3.y * p3.y);
      float num = pSq * (p2.y -p3.y) + p2Sq *(p3.y-p.y) + p3Sq *(p.y-p2.y); 
      float cx= num / (2.0f * cp);
      num = pSq *(p3.x -p2.x) + p2Sq*(p.x-p3.x) + p3Sq*(p2.x -p.x);
      float cy= num / (2.0f * cp);
      return new Point(cx, cy); 
    }
    return p;
  }
  
}