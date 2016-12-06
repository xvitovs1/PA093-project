import java.util.ArrayList;
import java.util.Collections;

public class VoronoiDiagram{
  
  // Voronoi diagram using Delaunay triangulation by connecting circumcenters
  public static ArrayList<LineSegment> createDiagram(ArrayList<Point> points){
    ArrayList<LineSegment> vd = new ArrayList<LineSegment>();
    
    ArrayList<LineSegment> dt = DelaunayTriangulation.triangulate(points);
    ArrayList<Triangle> triangles = getTriangles(dt);
    
    ArrayList<Point> circumCenters = new ArrayList<Point>();
    
    for(Triangle t : triangles){
      Point center = null;
      if(t.a.x.equals t.c.x){
        center = Circumcircle.getCircumcircleCenter(t.c.y,t.a);
      }
      else{
        center = Circumcircle.getCircumcircleCenter(t.c.x,t.a);
      }

      if(!circumCenters.contains(center)) circumCenters.add(center);
    }

    return vd;
  }
  
  private static ArrayList<Triangle> getTriangles(ArrayList<LineSegment> lines){
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();
    
    for(int i = 0; i < lines.size(); i++){
      LineSegment firstCandidate = null;
      for(int j = i; j < lines.size(); j++){
        if(firstCandidate == null){
          if((lines.get(i).x.equals(lines.get(j).x)) || (lines.get(i).x.equals(lines.get(j).y)) ||
             (lines.get(i).y.equals(lines.get(j).y)) || (lines.get(i).y.equals(lines.get(j).x))){
            firstCandidate = lines.get(j);
          }
        } else{
          if((lines.get(i).x.equals(firstCandidate.x) && firstCandidate.y.equals(lines.get(j).x)
              && lines.get(i).y.equals(lines.get(j).y)) 
              || (lines.get(i).x.equals(firstCandidate.x) && firstCandidate.y.equals(lines.get(j).y)
              && lines.get(i).y.equals(lines.get(j).x)) 
              || (lines.get(i).x.equals(firstCandidate.y) && firstCandidate.x.equals(lines.get(j).x)
              && lines.get(i).y.equals(lines.get(j).y)) 
              || (lines.get(i).x.equals(firstCandidate.y) && firstCandidate.x.equals(lines.get(j).y)
              && lines.get(i).y.equals(lines.get(j).x))){
            triangles.add(new Triangle(lines.get(i), firstCandidate, lines.get(j)));
            break;
          }
          
          if((lines.get(i).y.equals(firstCandidate.x) && firstCandidate.y.equals(lines.get(j).x)
              && lines.get(i).x.equals(lines.get(j).y)) 
              || (lines.get(i).y.equals(firstCandidate.x) && firstCandidate.y.equals(lines.get(j).y)
              && lines.get(i).x.equals(lines.get(j).x)) 
              || (lines.get(i).y.equals(firstCandidate.y) && firstCandidate.x.equals(lines.get(j).x)
              && lines.get(i).x.equals(lines.get(j).y)) 
              || (lines.get(i).y.equals(firstCandidate.y) && firstCandidate.x.equals(lines.get(j).y)
              && lines.get(i).x.equals(lines.get(j).x))){
            triangles.add(new Triangle(lines.get(i), lines.get(j), firstCandidate));
            break;
          }
        }       
      }
    }
    
    return triangles;
  }


}