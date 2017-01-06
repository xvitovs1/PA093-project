// Represents a triangle c---a---b
public class Triangle{
  
  public LineSegment a;
  public LineSegment b;
  public LineSegment c;
  
  public Triangle(LineSegment a, LineSegment b, LineSegment c){
    this.a = new LineSegment(a.x, a.y);

    if(a.x.equals(c.x) || a.x.equals(c.y)){
      this.b = b;
      this.c = c;
    }
    else{
      this.b = c;
      this.c = b;
    }
  }
  
  // Gets area of a triangle A(x1,y1), B(x2,y2), C(x3,y3)
  private float getArea(float x1, float y1, float x2, float y2, float x3, float y3)
  {
     return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2f);
  }
 
  //Checks whether point p lies inside the triangle
  public boolean isInside(Point p)
  {   
    Point a = this.a.x;
    Point b = this.a.y;
    Point c;
    if(this.c.x.equals(a)){
      c = this.c.y;
    }
    else{
      c = this.c.x;
    }

    // Calculate area of triangle ABC
    float mainArea = getArea(a.x, a.y, b.x, b.y, c.x, c.y);
   
    // Calculate area of triangle PBC 
    float area1 = getArea (p.x, p.y, b.x, b.y, c.x, c.y);
   
    // Calculate area of triangle PAC
    float area2 = getArea (a.x, a.y, p.x, p.y, c.x, c.y);
   
    // Calculate area of triangle PAB 
    float area3 = getArea (a.x, a.y, b.x, b.y, p.x, p.y);
     
    // Check if sum of A1, A2 and A3 equals A
    return (mainArea == area1 + area2 + area3 || Math.abs(mainArea - area1 - area2 - area3) < 0.02);
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
      if ((this.a.equals(other.a)) && (this.b.equals(other.b)) && (this.c.equals(other.c))) {
          return true;
      }
      
      if ((this.a.equals(other.a)) && (this.b.equals(other.c)) && (this.c.equals(other.b))) {
          return true;
      }
      
      if ((this.a.equals(other.b)) && (this.b.equals(other.c)) && (this.c.equals(other.a))) {
          return true;
      }
      
      if ((this.a.equals(other.b)) && (this.b.equals(other.a)) && (this.c.equals(other.c))) {
          return true;
      }
      
       if ((this.a.equals(other.c)) && (this.b.equals(other.b)) && (this.c.equals(other.a))) {
          return true;
      }
      
      if ((this.a.equals(other.c)) && (this.b.equals(other.a)) && (this.c.equals(other.b))) {
          return true;
      }

      return false;
  }
  
  @Override
  public int hashCode() {
      int hash = 877 * Math.round(this.a.x.x) + Math.round(this.b.y.y) * Math.round(this.c.y.x) - Math.round(this.c.y.y) * Math.round(this.b.y.x);
      return hash;
  }
  
  // Returns true if triangle is adjacent to triangle t on the side a
  public boolean isAdjacentA(Triangle t){   
    if(this.a.equals(t.a) || this.a.equals(t.b) || this.a.equals(t.c)){
      return true;
    }
    
    return false;
  }
  
  // Returns true if triangle is adjacent to triangle t on the side b
  public boolean isAdjacentB(Triangle t){   
    if(this.b.equals(t.a) || this.b.equals(t.b) || this.b.equals(t.c)){
      return true;
    }
    
    return false;
  }
  
  // Returns true if triangle is adjacent to triangle t on the side c
  public boolean isAdjacentC(Triangle t){   
    if(this.c.equals(t.a) || this.c.equals(t.b) || this.c.equals(t.c)){
      return true;
    }
    
    return false;
  }
  
  @Override
  public String toString(){
    return "triangle " + this.a + this.b + this.c;
  }
  
}