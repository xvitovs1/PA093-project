import java.util.ArrayList;
import java.util.Collections;

public class VoronoiDiagram{
  
  // Voronoi diagram using Delaunay triangulation by connecting circumcenters
  public static ArrayList<LineSegment> createDiagram(ArrayList<Point> points){
    ArrayList<LineSegment> vd = new ArrayList<LineSegment>();
    
    ArrayList<LineSegment> dt = DelaunayTriangulation.triangulate(points);
    
    ArrayList<Point> circumCenters = new ArrayList<Point>();
    
    for(int i = 0; i < dt.size() - 2; i++){
      
    }
    
    return vd;
  }

}