package scarfy;

import java.awt.Graphics;

public class Entity {
  
  protected float x,y;
  protected int width, height;
  
  public Entity( float x, float y, int width, int height ){
    
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public void draw(Graphics g){
    g.drawRect((int)x, (int)y, width, height);
  }

  public float getX() {
    return x;
  }
  public float getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
  
  
  public boolean isColliding( Entity e ){
    return (this.x < e.x + e.width &&
            this.x + this.width > e.x &&
            this.y < e.y + e.height &&
            this.y + this.height > e.y);
  }
  
}