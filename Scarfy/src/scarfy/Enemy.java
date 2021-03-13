package scarfy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Enemy extends EntityAlive{
  
  protected float spd;
  protected boolean isDead;
  protected float spdX, spdY;
  
  
  public Enemy( float x, float y, int width, int height, float hp, float spd, float KBresistance, ArrayList entities, ArrayList enemies ){
    super( x, y, width, height, hp, entities, enemies);
    this.hp = hp;
    this.KBresistance = KBresistance;
    this.spd = spd;
    
  }
  //                //danno //quanti pixel di knockback //orientamento


  
  protected void changeOri(int o){
    if( ori == o )
      return;
    this.ori = o;
    
  }
  
  protected void setOri(float a, float b)
  {
    if( Math.abs(a) > Math.abs(b) )
      if( a > 0 )
        changeOri(3);
      else
        changeOri(1);
    else
      if( b > 0 )
        changeOri(0);
      else
        changeOri(2);
    
  }
  
  //            calcola le due velocità
  public void updateMovementTowards( EntityAlive e, float spd  ){
    
    calculateCenter();
    //-------------------------------------------------calcola le due velocita x e y
    spdX = spd;
    spdY = spd;
    
    if( Math.abs(cx-e.cx) > Math.abs(cy-e.cy) ) //la più piccola viene diminuita
      spdY = ( Math.abs(cy-e.cy) / Math.abs(cx-e.cx) ) * spd;
    else
      spdX = ( Math.abs(cx-e.cx) / Math.abs(cy-e.cy) ) * spd;
    
    if( cx-e.cx > 0 )
      spdX = -spdX;
    if( cy-e.cy > 0 )
      spdY = -spdY;
    
  }
    
  public void update(Player p){
    
    checkKnockback();
    
  }
  
  @Override
  public void draw(Graphics g){
    if(isDead)
      return;
    
    g.setColor(Color.green);
    g.fillRect((int)x, (int)y, width, height);
      
  }
  
  
  
  public void moveX( float n ){
    pX = x;
    x += n;
    
    for( int i=0; i<entities.size(); i++ )
      if( this.isColliding( (Entity)entities.get(i) ) )
        x = pX;
  }
  public void moveY( float n ){
    pY = y;
    y += n;
    
    for( int i=0; i<entities.size(); i++ )
      if( this.isColliding( (Entity)entities.get(i) ) )
        y = pY;
  }
  
  
  public boolean isDead(){
    return isDead;
  }
  
}
