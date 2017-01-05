// Represents a triangle c---a---b
public class Triangle{
  
  public LineSegment a;
  public LineSegment b;
  public LineSegment c;
  
  public Triangle(LineSegment a, LineSegment b, LineSegment c){
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  @Override
  public boolean equals(Object obj) {
      if (obj == null) {
          return false;
      }
      if (!(obj instanceof Triangle)) {
          return false;
      }
      
      Triangle other = (Triangle) obj;
      if (!(this.a.equals(other.a)) || (!this.b.equals(other.b)) || (!this.c.equals(other.c))) {
          return false;
      }

      return true;
  }
  
  @Override
  public int hashCode() {
      int hash = 877 * Math.round(this.a.x.x) + Math.round(this.b.y.y) * Math.round(this.c.y.x);
      return hash;
  }
  
  public boolean isAdjacent(Triangle t){   
    if(this.a == t.a || this.a == t.b || this.a == t.c || 
    this.b == t.a || this.b == t.b || this.b == t.c || 
    this.c == t.a || this.c == t.b || this.c == t.c){
      return true;
    }
    
    return false;
  }
  
}