package geomerative ;
import processing.core.*;


/**
 * RGeomElem is an interface to any Geometric element that can be drawn and transformed, such as Shapes, Polygons or Meshes.
 * @invisible
 */
public abstract class RGeomElem
{
  /**
   * @invisible
   */
  public static final int SHAPE = 0;
  /**
   * @invisible
   */
  public static final int SUBSHAPE = 1;
  /**
   * @invisible
   */
  public static final int COMMAND = 2;
  
  /**
   * @invisible
   */
  public static final int POLYGON = 3;
  /**
   * @invisible
   */
  public static final int CONTOUR = 4;
  
  /**
   * @invisible
   */
  public static final int MESH = 5;
  /**
   * @invisible
   */
  public static final int TRISTRIP = 6;
  
  /**
   * @invisible
   */
  public static final int GROUP = 7;
  
  /**
   * @invisible
   */
  public static final int UNKNOWN = 8;
  
  // Functions dependent of the type of element
  // They must be overrided
  public abstract void draw(PGraphics g);
  public abstract void draw(PApplet g);
  
  public void draw(){
    this.draw(RGeomerative.parent);
  }
  
  public abstract RPoint[] getPoints();
  public abstract RPoint[] getCurvePoints();
  public abstract int getType();
  public abstract RMesh toMesh();
  public abstract RPolygon toPolygon();
  public abstract RShape toShape();
  public void print(){};
  
  public String id = "";
  public PImage texture = null;
  
  public boolean fillDef = false;
  public boolean fill = false;
  public int fillColor = 0;

  public boolean strokeDef = false;
  public boolean stroke = false;
  public int strokeColor = 0;
  
  public boolean strokeWeightDef = false;
  public float strokeWeight = 0F;

  
  private boolean oldFill = false;
  private int oldFillColor = 0;
  
  private boolean oldStroke = false;
  private int oldStrokeColor = 0;
  private float oldStrokeWeight = 1F;

  protected void saveContext(PGraphics g){
    oldFill = g.fill;
    oldFillColor = g.fillColor;
    oldStroke = g.stroke;
    oldStrokeColor = g.strokeColor;
    oldStrokeWeight = g.strokeWeight;
  }

  protected void saveContext(PApplet p){
    oldFill = p.g.fill;
    oldFillColor = p.g.fillColor;
    oldStroke = p.g.stroke;
    oldStrokeColor = p.g.strokeColor;
    oldStrokeWeight = p.g.strokeWeight;    
  }

  protected void saveContext(){
    saveContext(RGeomerative.parent);
  }

  protected void restoreContext(PGraphics g){
    g.fill(oldFillColor);
    if(!oldFill){
      g.noFill();
    }

    g.stroke(oldStrokeColor);
    g.strokeWeight(oldStrokeWeight);
    if(!oldStroke){
      g.noStroke();
    }
  }

  protected void restoreContext(PApplet p){
    p.fill(oldFillColor);
    if(!oldFill){
      p.noFill();
    }

    p.stroke(oldStrokeColor);
    p.strokeWeight(oldStrokeWeight);
    if(!oldStroke){
      p.noStroke();
    }    
  }

  protected void restoreContext(){
    restoreContext(RGeomerative.parent);
  }

  protected void setContext(PGraphics g){
    /*
    RGeomerative.parent.println("Setting context");
    RGeomerative.parent.println("  fillDef: ");
    RGeomerative.parent.println(fillDef);
    RGeomerative.parent.println("  fillColor: ");
    RGeomerative.parent.println(RGeomerative.parent.hex(fillColor));

    RGeomerative.parent.println("  strokeDef: ");
    RGeomerative.parent.println(strokeDef);
    RGeomerative.parent.println("  strokeColor: ");
    RGeomerative.parent.println(RGeomerative.parent.hex(strokeColor));
    RGeomerative.parent.println("  strokeWeight: ");
    RGeomerative.parent.println(strokeWeight);
    */
    if(fillDef){
      g.fill(fillColor);
      if(!fill){
        g.noFill();
      }
    }

    if(strokeDef){      
      g.stroke(strokeColor);
      if(!stroke){
        g.noStroke();
      }
    }

    if(strokeWeightDef){      
      g.strokeWeight(strokeWeight);
    }
  }

  protected void setContext(PApplet p){
    if(fillDef){
      p.fill(fillColor);
      if(!fill){
        p.noFill();
      }
    }

    if(strokeWeightDef){      
      p.strokeWeight(strokeWeight);
    }

    if(strokeDef){      
      p.stroke(strokeColor);
      if(!stroke){
        p.noStroke();
      }
    }
  }

  protected void setContext(){
    setContext(RGeomerative.parent);
  }

  protected void setStyle(RGeomElem p){
    id = p.id;
    texture = p.texture;
    
    fillDef = p.fillDef;
    fill = p.fill;
    fillColor = p.fillColor;

    strokeDef = p.strokeDef;
    stroke = p.stroke;
    strokeColor = p.strokeColor;

    strokeWeightDef = p.strokeWeightDef;
    strokeWeight = p.strokeWeight;
  }

  protected void setStyle(String styleString){
    //RGeomerative.parent.println("Style parsing: " + styleString);
    String[] styleTokens = RGeomerative.parent.splitTokens(styleString, ";");
    
    for(int i = 0; i < styleTokens.length; i++){
      String[] tokens = RGeomerative.parent.splitTokens(styleTokens[i], ":");
      
      if(tokens[0].equals("fill")){
        setFill(tokens[1]);
        
      }else if(tokens[0].equals("stroke")){
        setStroke(tokens[1]);
      
      }else if(tokens[0].equals("stroke-width")){
        setStrokeWeight(tokens[1]);

      }
    }
  }

  public void setFill(boolean _fill){
    fillDef = true;
    fill = _fill;
  }

  public void setFill(int _fillColor){
    fillDef = true;
    fillColor = _fillColor;
  }

  public void setFill(String str){
    //RGeomerative.parent.println("  set fill: " + str);
    fillDef = true;
    if(str.equals("none")){
      fill = false;
    }else{
      fill = true;
      fillColor = getColor(str);
    }
  }

  public void setStroke(boolean _stroke){
    strokeDef = true;
    stroke = _stroke;
  }

  public void setStroke(int _strokeColor){
    strokeDef = true;
    strokeColor = _strokeColor;
  }

  public void setStroke(String str){
    //RGeomerative.parent.println("  set stroke: " + str);
    strokeDef = true;
    if(str.equals("none")){
      stroke = false;
    }else{
      stroke = true;
      strokeColor = getColor(str);
    }
  }

  public void setStrokeWeight(String str){
    //RGeomerative.parent.println("  set stroke-width: " + str);
    strokeWeightDef = true;
    strokeWeight = RGeomerative.parent.parseFloat(str);
  }
  
  private int getColor(String colorString){
    if(colorString.startsWith("#")){
      return RGeomerative.parent.unhex("FF"+colorString.substring(1));
    }else{
      if(colorString.equals("black")){
        return 0;

      }else if(colorString.equals("red")){
        return RGeomerative.parent.color(255, 0, 0);

      }else if(colorString.equals("green")){
        return RGeomerative.parent.color(0, 255, 0);

      }else if(colorString.equals("blue")){
        return RGeomerative.parent.color(0, 0, 255);
      }
    }
    return 0;
  }

  // Functions independent of the type of element
  // No need of being overrided
  public void transform(RMatrix m){
    RPoint[] ps = getPoints();
    if(ps != null){
      for(int i=0; i<ps.length; i++){
        ps[i].transform(m);
      }
    }
  }
  
  /**
   * Use this method to get the bounding box of the element. 
   * @eexample getBounds
   * @return RContour, the bounding box of the element in the form of a fourpoint contour
   * @related getCenter ( )
   */
  public RContour getBounds(){
    float xmin =  Float.MAX_VALUE ;
    float ymin =  Float.MAX_VALUE ;
    float xmax = -Float.MAX_VALUE ;
    float ymax = -Float.MAX_VALUE ;
    RPoint[] points = getPoints();
    if(points!=null){
      for(int i=0;i<points.length;i++){
        float tempx = points[i].x;
        float tempy = points[i].y;
        if( tempx < xmin ){xmin = tempx;}else if( tempx > xmax ){xmax = tempx;}
        if( tempy < ymin ){ymin = tempy;}else if( tempy > ymax ){ymax = tempy;}
      }
    }
    RContour c = new RContour();
    c.addPoint(xmin,ymin);
    c.addPoint(xmin,ymax);
    c.addPoint(xmax,ymax);
    c.addPoint(xmax,ymin);
    return c;
  }
  
  
  /**
   * Use this method to get the center point of the element.
   * @eexample RGroup_getCenter
   * @return RPoint, the center point of the element
   * @related getBounds ( )
   */
  public RPoint getCenter(){
    RContour c = getBounds();
    return new RPoint((c.points[2].x + c.points[0].x)/2,(c.points[2].y + c.points[0].y)/2);
  }
  
  /**
   * Use this method to get the centroid of the element.
   * @eexample RGroup_getCentroid
   * @return RPoint, the centroid point of the element
   * @related getBounds ( )
   * @related getCenter ( )
   */
  public RPoint getCentroid(){
    RPoint[] ps = getPoints();
    
    float areaAcc = 0.0f;
    float xAcc = 0.0f;
    float yAcc = 0.0f;
    
    for(int i=0;i<ps.length-1;i++)
      {
        areaAcc += ps[i].x*ps[i+1].y - ps[i+1].x*ps[i].y;
        xAcc += (ps[i].x + ps[i+1].x)*(ps[i].x*ps[i+1].y - ps[i+1].x*ps[i].y);
        yAcc += (ps[i].y + ps[i+1].y)*(ps[i].x*ps[i+1].y - ps[i+1].x*ps[i].y);
      }
    areaAcc /= 2.0f;
    RPoint p = new RPoint(xAcc/(6.0f*areaAcc), yAcc/(6.0f*areaAcc));
    return p;
  }
  
  /**
   * Use this method to get the area of an element.
   * @eexample RGroup_getArea
   * @return float, the area point of the element
   * @related getBounds ( )
   * @related getCenter ( )
   * @related getCentroid ( )
   */
  public float getArea(){
    RPoint[] ps = getPoints();
    
    float areaAcc = 0.0f;
    for(int i=0;i<ps.length-1;i++)
      {
        areaAcc += ps[i].x*ps[i+1].y - ps[i+1].x*ps[i].y;
      }
    areaAcc /= 2.0f;
    return areaAcc;
  }
  
  /**
   * Use this method to get the transformation matrix in order to fit and center the element on the canvas. Scaling and translation damping parameters are available, in order to create animations.
   * @eexample RGeomElem_getCenteringTransf
   * @return RMatrix, the transformation matrix
   * @param PGraphics g, the canvas to which to fit and center the path
   * @param float margin, the margin to take into account when fitting
   * @param float sclDamping, a value from 0 to 1. The damping coefficient for the scale, if the value is 0, then no scaling is applied.
   * @param float trnsDamping, a value from 0 to 1. The damping coefficient for the translation, if the value is 0, then no translation is applied.
   * @related getBounds ( )
   */
  public RMatrix getCenteringTransf(PGraphics g, float margin, float sclDamping, float trnsDamping) throws RuntimeException{
    RMatrix transf;
    
    float mrgn = margin*2;
    RContour c = getBounds();
    float scl = (float)Math.min((g.width-mrgn)/(float)Math.abs(c.points[0].x-c.points[2].x),(g.height-mrgn)/(float)Math.abs(c.points[0].y-c.points[2].y));
    RPoint trns = getCenter();
    transf = new RMatrix();
    
    if(sclDamping!=0){
      transf.scale(1+(scl-1)*sclDamping);
    }
    
    if(trnsDamping!=0){
      transf.translate(-trns.x*trnsDamping, -trns.y*trnsDamping);
    }
    
    return transf;
  }
  
  public RMatrix getCenteringTransf(PGraphics g) throws RuntimeException{
    return getCenteringTransf(g, 0, 1, 1);
  }
  
  public RMatrix getCenteringTransf(PGraphics g, float margin) throws RuntimeException{
    return getCenteringTransf(g, margin, 1, 1);
  }
  
  public void centerIn(PGraphics g){
    transform(getCenteringTransf(g));
  }
  
  public void centerIn(PGraphics g, float margin, float sclDamping, float trnsDamping) throws RuntimeException{
    transform(getCenteringTransf(g, margin, sclDamping, trnsDamping));
  }
  
  /**
   * Use this to apply a translation to the point.
   * @eexample RGeomElem_translate
   * @usage Geometry
   * @param tx float, the coefficient of x translation
   * @param ty float, the coefficient of y translation
   * @param t RPoint, the translation vector to be applied
   * @related transform ( )
   * @related rotate ( )
   * @related scale ( )
   */
  public void translate(float tx, float ty)
  {
    RMatrix transf = new RMatrix();
    transf.translate(tx, ty);
    transform(transf);
  }
  
  public void translate(RPoint t)
  {
    RMatrix transf = new RMatrix();
    transf.translate(t);
    transform(transf);  }
  
  /**
   * Use this to apply a rotation to the point.
   * @eexample RPoint_rotate
   * @usage Geometry
   * @param angle float, the angle of rotation to be applied
   * @param vx float, the x coordinate of the center of rotation
   * @param vy float, the y coordinate of the center of rotation
   * @param v RPoint, the position vector of the center of rotation
   * @related transform ( )
   * @related translate ( )
   * @related scale ( )
   */
  public void rotate(float angle)
  {
    RMatrix transf = new RMatrix();
    transf.rotate(angle);
    transform(transf);
  }
  
  public void rotate(float angle, float vx, float vy)
  {
    RMatrix transf = new RMatrix();
    transf.rotate(angle, vx, vy);
    transform(transf);
  }
  
  public void rotate(float angle, RPoint v)
  {
    RMatrix transf = new RMatrix();
    transf.rotate(angle, v);
    transform(transf);
  }
  
  /**
   * Use this to scale the point.
   * @eexample RPoint_scale
   * @usage Geometry
   * @param sx float, the scaling coefficient over the x axis
   * @param sy float, the scaling coefficient over the y axis
   * @param s float, the scaling coefficient for a uniform scaling
   * @param s RPoint, the scaling vector
   * @related transform ( )
   * @related translate ( )
   * @related rotate ( )
   */
  public void scale (float sx, float sy)
  {
    RMatrix transf = new RMatrix();
    transf.scale(sx, sy);
    transform(transf);
  }
  
  public void scale (float s)
  {
    RMatrix transf = new RMatrix();
    transf.scale(s);
    transform(transf);
  }
}