import java.util.ArrayList;
import java.util.Collections;

public class VoronoiDiagram{
  
  // Voronoi diagram using Delaunay triangulation by connecting circumcenters
  public static ArrayList<LineSegment> createDiagram(ArrayList<Point> points){
    ArrayList<LineSegment> vd = new ArrayList<LineSegment>();
    
    ArrayList<LineSegment> dt = DelaunayTriangulation.triangulate(points);
    ArrayList<Triangle> triangles = getTriangles(dt);
    
    System.out.println("Size " + triangles.size());
    for(Triangle t : triangles){
      Point center = null;
      if(t.a.x.equals(t.c.x)){
        center = Circumcircle.getCircumcircleCenter(t.c.y,t.a);
      }
      else{
        center = Circumcircle.getCircumcircleCenter(t.c.x,t.a);
      }
      
      for(Triangle candidateT : triangles){
        if(candidateT.equals(t)) continue;
        
        Point center2;
        if(candidateT.isAdjacent(t)){
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
  
  private static ArrayList<Triangle> getTriangles(ArrayList<LineSegment> lines){
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();
    
    for(int i = 0; i < lines.size(); i++){
      System.out.println("i " + lines.get(i).toString());
      LineSegment firstCandidate = null;
      for(int j = i + 1; j < lines.size(); j++){
        System.out.println("j " + lines.get(j).toString());
        if(firstCandidate == null){
          if((lines.get(i).x.equals(lines.get(j).x)) || (lines.get(i).x.equals(lines.get(j).y)) ||
             (lines.get(i).y.equals(lines.get(j).y)) || (lines.get(i).y.equals(lines.get(j).x))){
            firstCandidate = lines.get(j);
            System.out.println("first c " + firstCandidate.toString());
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