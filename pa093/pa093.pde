import g4p_controls.*;

GButton btnHelp;
GWindow window;

boolean addPoints; // adding points mode
boolean removePoints; // removing points mode
boolean movePoint; // moving points mode
ArrayList<Point> points;
Point dragPoint = null;

// Number of random points
static final int RANDOM_POINTS_NUM = 10;

void setup() {
  size(840, 480);
  background(255);
  points = new ArrayList<Point>();
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
  Point p = new Point(x, y, Point.POINT_RADIUS);
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
        addPoint(random(width), random(height));
  }
}

void drawConvexHull(){
  // Get the convex hull
  if(points.isEmpty()) return;
  
  ArrayList<Point> convexHull = ConvexHull.giftWrapping(points);
  
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

void keyPressed() {
  switch (key) {
    case('c') : clear();
                points.clear();
                background(255);
                break;
    case('a') : setMode(true, false, false);
                break;
    case('d') : setMode(false,true, false);
                break;
    case('r') : randomPoints(RANDOM_POINTS_NUM);
                break;
    case('m') : setMode(false,false, true);
                break;
    case('h') : drawConvexHull();
                break;
  }

}

void setMode(boolean add, boolean remove, boolean move){
  addPoints = add;
  removePoints = remove;
  movePoint = move;
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
  appc.text("Adding points....................a", 10,60);
  appc.text("Removing points...............d", 10,80);
  appc.text("Move points......................m", 10,100);
  appc.text("Generate random points....r", 10,120);
  appc.text("Clear screen......................c", 10,140);
} 
 
void createWindow() {
  window = GWindow.getWindow(this, "Help", 500, 50, 200,180, JAVA2D);
  window.addDrawHandler(this, "window_draw");
  window.addOnCloseHandler(this, "windowClosing");
  window.setActionOnClose(GWindow.CLOSE_WINDOW);
}// createWindow
 
public void windowClosing(PApplet w, GWinData data){
  btnHelp.setEnabled(true);
}