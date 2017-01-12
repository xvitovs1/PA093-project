import java.util.ArrayList;
import java.util.Collections;

public class VoronoiDiagram{
  
  public static final float auxSize = 10000f;
  
  // Voronoi diagram using Delaunay triangulation by connecting circumcenters
  public static ArrayList<LineSegment> createDiagram(ArrayList<Point> points){
    ArrayList<LineSegment> vd = new ArrayList<LineSegment>();
    
    addAuxiliaryTriangle(points);
    DelaunayTriangulation dt = new DelaunayTriangulation(points);
    
    // Get circumcircle centers of all triangles
    for(Triangle t : dt.triangles){
      Point center = null;
      if(t.a.x.equals(t.c.x)){
        center = Circumcircle.getCircumcircleCenter(t.c.y,t.a);
      }
      else{
        center = Circumcircle.getCircumcircleCenter(t.c.x,t.a);
      }
      
      
      // Join circumcircle center with adjacent triangles
      for(Triangle candidateT : dt.triangles){
        if(candidateT.equals(t)) continue;
        
        Point center2;
        if(t.isAdjacentA(candidateT) || t.isAdjacentB(candidateT) || t.isAdjacentC(candidateT)){
          if(candidateT.a.x.equals(candidateT.c.x)){
            center2 = Circumcircle.getCircumcircleCenter(candidateT.c.y,candidateT.a);
          }
          else{
            center2 = Circumcircle.getCircumcircleCenter(candidateT.c.x,candidateT.a);
          }
         
          
          LineSegment l = new LineSegment(center, center2);
          if(!vd.contains(l)) vd.add(l);
        }
      }
    }
  
    // Remove auxiliary triangle
    points.remove(points.size() - 1);
    points.remove(points.size() - 1);
    points.remove(points.size() - 1);
    
    return vd;
  }
  
  // Adds auxiliary triangle needed for nice drawing of voronoi diagram
  private static void addAuxiliaryTriangle(ArrayList<Point> points){
    Point a = new Point(-auxSize, -auxSize);
    Point b = new Point(auxSize, -auxSize);
    Point c = new Point(0, auxSize);
    points.add(a);
    points.add(b);
    points.add(c);
  }
}