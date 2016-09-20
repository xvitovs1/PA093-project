boolean addPoints; // adding points mode
boolean removePoints; // removing points mode
boolean movePoint; // moving points mode
ArrayList<Point> points;
Point dragPoint = null;

// Point radius
static final int POINT_RADIUS = 5;
// Number of random points
static final int RANDOM_POINTS_NUM = 10;

class Point {
  float x;
  float y;
  float radius;
  
  Point(float x, float y, float radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
  
  // Check if given point is inside our ellipse
  boolean contains(float x, float y) {
    if((Math.pow((x - this.x),2) + Math.pow((y - this.y),2)) <= Math.pow(this.radius,2)) {
      return true;
    }
    return false;
  }
}

void setup() {
  size(840, 480);
  background(255);
  points = new ArrayList<Point>();
}

void draw(){
}

void mousePressed() {
  if(movePoint) {
    dragPoint = null;
    for(Point p : points) {
      if (p.contains(mouseX, mouseY)) {
        dragPoint = p;
      }
    }
  }
}

void mouseClicked() {
    if(addPoints) {
      addPoint(mouseX, mouseY);
    }
    else if (removePoints) {
      removePoint(mouseX, mouseY);
      redrawPoints();
    }
}

void mouseDragged() {
  if (movePoint && dragPoint != null) {
    dragPoint.x = mouseX;
    dragPoint.y = mouseY;
    redrawPoints();
  }
}

// Add point
void addPoint(float x, float y){
  Point p = new Point(x, y, POINT_RADIUS);
  points.add(p);
  fill(0);
  ellipse(p.x, p.y, p.radius * 2, p.radius * 2);
}

// Remove point
void removePoint(float x, float y){
  Point toRemove = null;
  for(Point p : points) {
      if(p.contains(x,y)) {
        toRemove = p;
        break;
     }
  }
  
  if(toRemove!=null) points.remove(toRemove);
}

// Redraw
void redrawPoints(){
  clear();
  background(255);
  for(Point p : points){
    fill(0);
    ellipse(p.x, p.y, p.radius * 2, p.radius * 2);
  }
}

void keyPressed() {
  switch (key) {
    case('c') : clear();
                background(255);
                break;
    case('a') : addPoints = true;
                removePoints = false;
                movePoint = false;
                break;
    case('d') : addPoints = false;
                removePoints = true;
                movePoint = false;
                break;
    case('r') : randomPoints(RANDOM_POINTS_NUM);
                break;
    case('m') : movePoint = true;
                addPoints = false;
                removePoints = false;
  }

}

// Draw random points
void randomPoints(int count) {
  for(int i = 0; i < count; i++) {
        addPoint(random(width), random(height));
  }
}