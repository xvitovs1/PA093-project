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
  
  // Gets triangles from list of line segments
  private static ArrayList<Triangle> getTriangles(ArrayList<LineSegment> lines){
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();

    for(int i = 0; i < lines.size(); i++){
      for(int j = 0; j < lines.size(); j++){
        if(!lines.get(i).equals(lines.get(j))){
          if((lines.get(i).x.equals(lines.get(j).x)) || (lines.get(i).x.equals(lines.get(j).y)) ||
             (lines.get(i).y.equals(lines.get(j).y)) || (lines.get(i).y.equals(lines.get(j).x))){
            for(int k = 0; k < lines.size(); k++){
              if(lines.get(j).equals(lines.get(k)) || lines.get(j).equals(lines.get(i))) continue;
              if(isTriangleABC(lines.get(i), lines.get(j), lines.get(k))){
                Triangle t = new Triangle(lines.get(i), lines.get(j), lines.get(k));
                if(triangles.contains(t)) continue;
                triangles.add(t);
                break;
              }
                
             if(isTriangleACB(lines.get(i), lines.get(j),lines.get(k))){
                  Triangle t = new Triangle(lines.get(i), lines.get(k), lines.get(j));
                  if(triangles.contains(t)) continue;
                  triangles.add(t);
                  break;
              }
           }
        }
      }      
    }       
  }
       
    return triangles;
  }
  
  // Check if the given lines form a triangle ABC
  private static boolean isTriangleABC(LineSegment a, LineSegment b, LineSegment c){
    if((a.x.equals(b.x) && b.y.equals(c.x)
              && a.y.equals(c.y)) 
              || (a.x.equals(b.x) && b.y.equals(c.y)
              && a.y.equals(c.x)) 
              || (a.x.equals(b.y) && b.x.equals(c.x)
              && a.y.equals(c.y)) 
              || (a.x.equals(b.y) && b.x.equals(c.y)
              && a.y.equals(c.x)))
              return true;
              
    return false;
  }
  
  // Check if the given lines form a triangle ACB
  private static boolean isTriangleACB(LineSegment a, LineSegment b, LineSegment c){
    if((a.y.equals(b.x) && b.y.equals(c.x)
                && a.x.equals(c.y)) 
                || (a.y.equals(b.x) && b.y.equals(c.y)
                && a.x.equals(c.x)) 
                || (a.y.equals(b.y) && b.x.equals(c.x)
                && a.x.equals(c.y)) 
                || (a.y.equals(b.y) && b.x.equals(c.y)
                && a.x.equals(c.x)))
              return true;
              
    return false;
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