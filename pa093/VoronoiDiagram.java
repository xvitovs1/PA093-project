import java.util.ArrayList;
import java.util.Collections;

public class VoronoiDiagram{
  
  public static final float auxSize = 10000f;
  
  // Voronoi diagram using Delaunay triangulation by connecting circumcenters
  public static ArrayList<LineSegment> createDiagram(ArrayList<Point> points){
    ArrayList<LineSegment> vd = new ArrayList<LineSegment>();
    
    addAuxiliaryTriangle(points);
    
    ArrayList<LineSegment> dt = DelaunayTriangulation.triangulate(points);
    ArrayList<Triangle> triangles = getTriangles(dt);
    
    // Get circumcircle centers of all triangles
    for(Triangle t : triangles){
      Point center = null;
      if(t.a.x.equals(t.c.x)){
        center = Circumcircle.getCircumcircleCenter(t.c.y,t.a);
      }
      else{
        center = Circumcircle.getCircumcircleCenter(t.c.x,t.a);
      }
      
      // Join circumcircle center with adjacent triangles
      for(Triangle candidateT : triangles){
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
              if((lines.get(i).x.equals(lines.get(j).x) && lines.get(j).y.equals(lines.get(k).x)
              && lines.get(i).y.equals(lines.get(k).y)) 
              || (lines.get(i).x.equals(lines.get(j).x) && lines.get(j).y.equals(lines.get(k).y)
              && lines.get(i).y.equals(lines.get(k).x)) 
              || (lines.get(i).x.equals(lines.get(j).y) && lines.get(j).x.equals(lines.get(k).x)
              && lines.get(i).y.equals(lines.get(k).y)) 
              || (lines.get(i).x.equals(lines.get(j).y) && lines.get(j).x.equals(lines.get(k).y)
              && lines.get(i).y.equals(lines.get(k).x))){
                Triangle t = new Triangle(lines.get(i), lines.get(j), lines.get(k));
                if(!triangles.contains(t)){
                  triangles.add(t);
                  break;
                }else{
                  continue;
                }
              }
                
             if((lines.get(i).y.equals(lines.get(j).x) && lines.get(j).y.equals(lines.get(k).x)
                && lines.get(i).x.equals(lines.get(k).y)) 
                || (lines.get(i).y.equals(lines.get(j).x) && lines.get(j).y.equals(lines.get(k).y)
                && lines.get(i).x.equals(lines.get(k).x)) 
                || (lines.get(i).y.equals(lines.get(j).y) && lines.get(j).x.equals(lines.get(k).x)
                && lines.get(i).x.equals(lines.get(k).y)) 
                || (lines.get(i).y.equals(lines.get(j).y) && lines.get(j).x.equals(lines.get(k).y)
                && lines.get(i).x.equals(lines.get(k).x))){
                  Triangle t = new Triangle(lines.get(i), lines.get(k), lines.get(j));
                  if(!triangles.contains(t)){
                    triangles.add(t);
                    break;
                  }else{
                    continue;
                  }
            }
          }
        }
      }      
    }       
  }
    
    
    return triangles;
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