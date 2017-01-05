import java.util.ArrayList;
import java.util.Collections;

public class VoronoiDiagram{
  
  // Voronoi diagram using Delaunay triangulation by connecting circumcenters
  public static ArrayList<LineSegment> createDiagram(ArrayList<Point> points){
    ArrayList<LineSegment> vd = new ArrayList<LineSegment>();
    
    ArrayList<LineSegment> dt = DelaunayTriangulation.triangulate(points);
    ArrayList<Triangle> triangles = getTriangles(dt);
    
    for(Triangle t : triangles){
      boolean adjacentA = false;
      boolean adjacentB = false;
      boolean adjacentC = false;
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
        if(!adjacentA) adjacentA = t.isAdjacentA(candidateT);
        if(!adjacentB) adjacentB = t.isAdjacentB(candidateT);
        if(!adjacentC) adjacentC = t.isAdjacentC(candidateT);
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
      
              
      Point middle;
      if(!adjacentA){
        middle = t.a.getMiddle();
        addOutsideLine(middle,center,t,vd);
      }
      if(!adjacentB){
        middle = t.b.getMiddle();
        addOutsideLine(middle,center,t,vd);
      }
      if(!adjacentC){
        middle = t.c.getMiddle();
        addOutsideLine(middle,center,t,vd);
      }
    }

    return vd;
  }
  
  // Adds line that does not connect circumcircle centers, but goes outside of triangle
  private static void addOutsideLine(Point middle, Point center, Triangle t, ArrayList<LineSegment> vd){
    if(t.isInside(center)){
      LineSegment l = new LineSegment(center, middle);
      Point newPoint = getPointOnLine(l, 100);
      LineSegment nl = new LineSegment(center, newPoint);
      if(!vd.contains(nl)) vd.add(nl);
    }else{
      LineSegment l = new LineSegment(middle, center);
      Point newPoint = getPointOnLine(l, 100);
      LineSegment nl = new LineSegment(center, newPoint);
      if(!vd.contains(nl)) vd.add(nl);
    }
  }
  
  private static Point getPointOnLine(LineSegment l, int distance) {
            float vectorX = l.y.x - l.x.x;
            float vectorY = l.y.y - l.x.y;
            double magnitude = Math.sqrt(vectorX * vectorX + vectorY * vectorY);
            vectorX /= magnitude;
            vectorY /= magnitude;
            return new Point(
                (float)((l.x.x + vectorX * (magnitude + distance)) + 0.5f)
              , (float)((l.x.y + vectorY * (magnitude + distance)) + 0.5f)
            );
        }
  
  private static ArrayList<Triangle> getTriangles(ArrayList<LineSegment> lines){
    ArrayList<Triangle> triangles = new ArrayList<Triangle>();
    
    /*for(int i = 0; i < lines.size(); i++){
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
                Triangle t = new Triangle(lines.get(i), firstCandidate, lines.get(j));
                if(!triangles.contains(t)){
                  triangles.add(t);
                  break;
                }else{
                  continue;
                }
          }
          
          if((lines.get(i).y.equals(firstCandidate.x) && firstCandidate.y.equals(lines.get(j).x)
              && lines.get(i).x.equals(lines.get(j).y)) 
              || (lines.get(i).y.equals(firstCandidate.x) && firstCandidate.y.equals(lines.get(j).y)
              && lines.get(i).x.equals(lines.get(j).x)) 
              || (lines.get(i).y.equals(firstCandidate.y) && firstCandidate.x.equals(lines.get(j).x)
              && lines.get(i).x.equals(lines.get(j).y)) 
              || (lines.get(i).y.equals(firstCandidate.y) && firstCandidate.x.equals(lines.get(j).y)
              && lines.get(i).x.equals(lines.get(j).x))){
                Triangle t = new Triangle(lines.get(i), lines.get(j), firstCandidate);
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
    
    return triangles;*/
    
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
}