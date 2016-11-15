import g4p_controls.*;

GButton btnHelp;
GWindow window;

boolean addPoints; // adding points mode
boolean removePoints; // removing points mode
boolean movePoint; // moving points mode
boolean createPolygon; // creating polygon mode
ArrayList<Point> points;
ArrayList<LineSegment> polygon;
Point dragPoint = null;

// Number of random points
static final int RANDOM_POINTS_NUM = 10;

void setup() {
  size(1200, 900);
  background(255);
  points = new ArrayList<Point>();
  polygon = new ArrayList<LineSegment>();
  btnHelp = new GButton(this, 10, 10, 140, 20, "Help");
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
      addPoint(new Point(mouseX, mouseY));
    }
    else if (removePoints) {
      removePoint(mouseX, mouseY);
      redrawPoints();
    }
    else if(createPolygon) {
      Point p = new Point(mouseX, mouseY);
      if (mouseButton == RIGHT) {
        setMode(false, false, false, false);
        if(points.size() > 0)
          addSegment(points.get(points.size() - 1), points.get(0));
        return;
      }
      if(points.size() > 0)
        addSegment(points.get(points.size() - 1), p);
      addPoint(p);
    }
}

void mouseDragged() {
  if (movePoint && dragPoint != null) {
    dragPoint.x = mouseX;
    dragPoint.y = mouseY;
    redrawPoints();
  }
}

// Add segment
void addSegment(Point x, Point y){
  polygon.add(new LineSegment(x,y));
  fill(0);
  line(x.x, x.y, y.x, y.y);
}

// Add point
void addPoint(Point p){
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

// Draw random points
void randomPoints(int count) {
  for(int i = 0; i < count; i++) {
        addPoint(new Point(random(width), random(height)));
  }
}

void drawConvexHull(boolean useGrahamScan){
  // Get the convex hull
  if(points.isEmpty()) return;
  
  ArrayList<Point> convexHull;
  
  if(useGrahamScan){
    convexHull= ConvexHull.grahamScan(points);
  } else{
    convexHull= ConvexHull.giftWrapping(points);
  }
  
  // Draw the convex hull
  for(int i = 0; i < convexHull.size() - 1; i++){
    Point p1 = convexHull.get(i);
    Point p2 = convexHull.get(i + 1);
    line(p1.x, p1.y, p2.x, p2.y);
  }
  
  Point p1 = convexHull.get(0);
  Point p2 = convexHull.get(convexHull.size() - 1);
  line(p1.x, p1.y, p2.x, p2.y);
}

// Draw triangulation
void drawTriangulation(){
  if(points.isEmpty()) return;
  ArrayList<LineSegment> triangulation;
  if(polygon.isEmpty()) {
    ArrayList<Point> convexHull= ConvexHull.giftWrapping(points);
    triangulation = Triangulation.triangulate(convexHull);
  }
  else{
    triangulation = Triangulation.triangulate(points, polygon);
  }

  for(LineSegment l : triangulation){
    line(l.x.x, l.x.y, l.y.x, l.y.y);
  }
}

// Draw Kd tree
void drawKdTree(){
  if(points.isEmpty()) return;
  KdNode root = KdTree.buildTree(points,0);
  ArrayList<LineSegment> tree = KdTree.getLines(root, height, width);
  
  for(LineSegment l : tree){
    line(l.x.x, l.x.y, l.y.x, l.y.y);
  }
}

void keyPressed() {
  switch (key) {
    case('c') : clear();
                points.clear();
                polygon.clear();
                background(255);
                break;
    case('a') : setMode(true, false, false, false);
                break;
    case('p') : setMode(false, false, false, true);
                break;
    case('d') : setMode(false,true, false, false);
                break;
    case('r') : randomPoints(RANDOM_POINTS_NUM);
                break;
    case('m') : setMode(false,false, true, false);
                break;
    case('h') : drawConvexHull(false);
                break;
    case('g') : drawConvexHull(true);
                break;
    case('t') : drawTriangulation();
                break;
    case('k') : drawKdTree();
                break;
  }

}

void setMode(boolean add, boolean remove, boolean move, boolean polygon){
  addPoints = add;
  removePoints = remove;
  movePoint = move;
  createPolygon = polygon;
}

void handleButtonEvents(GButton button, GEvent event) {
  if(event == GEvent.CLICKED && button == btnHelp) {
    createWindow();
    btnHelp.setEnabled(false);
  }
}

synchronized public void window_draw(PApplet appc, GWinData data) { 
  appc.background(255); 
  appc.fill(0);
  appc.text("Help", 10,30);
  appc.text("---------------------", 10,40);
  appc.text("a ... Adding points", 10,60);
  appc.text("d ... Removing points", 10,80);
  appc.text("p ... Create polygon (end with right mouse button click)", 10,100);
  appc.text("m ... Move points", 10,120);
  appc.text("r ... Generate random points", 10,140);
  appc.text("c ... Clear screen", 10,160);
  appc.text("h ... Convex Hull - gift wrapping", 10,180);
  appc.text("g ... Convex Hull - graham scan", 10,200);
  appc.text("t ... Triangulation", 10,220);
} 
 
void createWindow() {
  window = GWindow.getWindow(this, "Help", 500, 50, 400,260, JAVA2D);
  window.addDrawHandler(this, "window_draw");
  window.addOnCloseHandler(this, "windowClosing");
  window.setActionOnClose(GWindow.CLOSE_WINDOW);
}// createWindow
 
public void windowClosing(PApplet w, GWinData data){
  btnHelp.setEnabled(true);
}