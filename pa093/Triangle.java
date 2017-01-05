// Represents a triangle c---a---b
public class Triangle{
  
  public LineSegment a;
  public LineSegment b;
  public LineSegment c;
  
  public Triangle(LineSegment a, LineSegment b, LineSegment c){
    this.a = a;
    
    if(a.x.equals(c.x) || a.x.equals(c.y)){
      this.b = b;
      this.c = c;
    }
    else{
      this.b = c;
      this.c = b;
    }
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
  
  // Returns true if triangle is adjacent to triangle t on the side a
  public boolean isAdjacentA(Triangle t){   
    if(this.a == t.a || this.a == t.b || this.a == t.c){
      return true;
    }
    
    return false;
  }
  
  // Returns true if triangle is adjacent to triangle t on the side b
  public boolean isAdjacentB(Triangle t){   
    if(this.b == t.a || this.b == t.b || this.b == t.c){
      return true;
    }
    
    return false;
  }
  
  // Returns true if triangle is adjacent to triangle t on the side c
  public boolean isAdjacentC(Triangle t){   
    if(this.c == t.a || this.c == t.b || this.c == t.c){
      return true;
    }
    
    return false;
  }
  
}