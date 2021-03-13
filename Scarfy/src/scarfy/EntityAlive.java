package scarfy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static scarfy.Scarfy.colorImage;


public class EntityAlive extends Entity {
  
  protected String state = "idle";
  protected ArrayList entities, enemies;
  protected float pX, pY;//previous x e y
  protected int ori = 2;
  protected int frame; //numero del frame da stampare
  protected float cx, cy; //centro del rettangolo
  protected float hp; //health points
  protected int redFrameLeft = 0; //se vieni hittato esce un frame rosso
  protected Graphics gg;
  protected int imgDim;
  
  protected int offsetHitboxY = 0;
  
  protected float knockX, knockY; //calcolati a runtime
  protected float knockBack; //coordinate di quanto vieni knockbackato
  protected float frameKnockBack = 4; //i frame che dura il knockback
  protected int knockCount = 0; //un contatore a caso
  protected boolean isKnockBacking = false;
  protected float KBresistance = 1;//numero da 0 a 1 che indica la resistenza al knockback in percentuale
  
  //variabili a caso come contatori o flag che servono a un solo scopo e dargli un nome significativo è difficile
  protected boolean bool0; //dice se nel move() il personaggio si è mosso o no
  
  public EntityAlive( float x, float y, int width, int height, float hp, ArrayList entities, ArrayList enemies ){
    super(x,y,width,height);
    imgDim = width;
    this.entities = entities;
    this.enemies = enemies;
  }
  
   public EntityAlive( float x, float y, int width, int height, ArrayList entities ){
    super(x,y,width,height);
    imgDim = width;
    this.entities = entities;
  }
  
  public void hitted( float damage, float knock, float frameKnockBack, EntityAlive e ){
    redFrameLeft = 5;
    
    hp -= damage;
    if( hp <= 0 )
      this.die();
    else
      setKnockBack(  knock, frameKnockBack, e );
    
    //System.out.println("sono stato hittato");
    //newParticle(0, 200, 200, 100, 100, ori);
  }
  
  
  public void hitted( float damage, float knock, float frameKnockBack, int ori ){
    redFrameLeft = 5;
    hp -= damage;
    if( hp <= 0 )
      this.die();
    else
      setKnockBack(  knock, frameKnockBack, ori );
    
    //newParticle(0, 200, 200, 100, 100, ori);
    //System.out.println("sono stato hittato");
  }
  
    
  //object n sarebbe null ma dettagli (ImageObserver)
  public void drawImage( BufferedImage img, int x, int y, Object n ){
    
    if( redFrameLeft != 0 ){
      gg.drawImage( colorImage( img ), x, y - offsetHitboxY, null );
      redFrameLeft--;
      return;
    }
    
    gg.drawImage( img, x, y - offsetHitboxY, null );
  }
  
  
  public void die(){
    
    enemies.remove(this);
    try {
      this.finalize();
    } catch (Throwable ex) {
      Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  
    
  public void setKnockBack( float knockBack, float frameKnockBack, EntityAlive e ){
    this.knockBack = knockBack*KBresistance;
    this.frameKnockBack = frameKnockBack;
    this.isKnockBacking = true;
    calculateKnockBack(this, e);
  }
  public void setKnockBack( float knockBack, float frameKnockBack, int ori ){
    this.knockBack = knockBack*KBresistance;
    this.frameKnockBack = frameKnockBack;
    this.isKnockBacking = true;
    calculateKnockBack(this, ori);
  }
  
  public static int backOri(int ori){
    switch(ori){
      case 0:
        return 2;
        case 1:
        return 3;
      case 2:
        return 0;
      case 3:
        return 1;
    }
    
    return 2;
  }
  
  protected void changeState(String s){
    if( state.equals(s) )
      return;
    
    frame = -1; //-1 che diventerà 0 quando verrà updatato
    state = s;
  }
  
  public static void calculateKnockBack(EntityAlive a, int ori){

    switch(ori)
    {
      case 0:
        a.knockY = -a.knockBack;
        a.knockX = 0;
      break;case 1:
        a.knockY = 0;
        a.knockX = a.knockBack;
      break;case 2:
        a.knockX = 0;
        a.knockY = a.knockBack;
      break;case 3:
        a.knockX = -a.knockBack;
        a.knockY = 0;
    }
    
  }
    
  public static void calculateKnockBack(EntityAlive a, EntityAlive b){
    
    if( Math.abs( a.cx-b.cx ) > Math.abs( a.cy-b.cy ) ){
      a.knockX = a.knockBack;
      a.knockY = Math.abs(a.cy-b.cy) / Math.abs(a.cx-b.cx)*a.knockBack;
    }else{
      a.knockY = a.knockBack;
      a.knockX = Math.abs(a.cx-b.cx) / Math.abs(a.cy-b.cy)*a.knockBack;
    }
    if( a.cx<b.cx )
      a.knockX = -a.knockX;
    if( a.cy<b.cy )
      a.knockY = -a.knockY;
    
  }
  
  public void applyKnockBack( )
  {
    pX = x;
    pY = y;
    x += knockX / frameKnockBack;
    for( int i=0; i<entities.size(); i++ )
      if( this.getHitbox().isColliding( (Entity)entities.get(i) ) )
        this.goBack();
    
    pX = x;
    pY = y;
    y += knockY / frameKnockBack;
    for( int i=0; i<entities.size(); i++ )
      if( this.getHitbox().isColliding( (Entity)entities.get(i) ) )
        this.goBack();
    
  }
  
  public Enemy isCollidingWith(){
    for( int i=0; i<enemies.size(); i++ )
      if( this.getHitbox().isColliding( (Enemy)enemies.get(i) ) )
      {
        if( ((Enemy)enemies.get(i)).isDead() )
          continue;
        
        return (Enemy)enemies.get(i);
      }
    return null;
  }
  
  protected void calculateCenter(){
    cx = x+width/2;
    cy = y+height/2;
  }
  
  public boolean checkKnockback(){
    
    if( isKnockBacking )
    {
      if( knockCount >= frameKnockBack ){ //se il knockback è finito
        isKnockBacking = false;
        knockCount = 0;
      }
      
      knockCount++;
      return true;
    }
    
    return false;
  }
  
        //true se si è mosso
        //false se non si è mosso
  public boolean moveIstant( float xx, float yy ){ //muove l'entità e restituisce se si è mossa o no
    
    bool0 = true;
    
    pX = x;
    pY = y;
    x += xx;
    y += yy;
    
    for( int i=0; i<entities.size(); i++ )
      if( this.getHitbox().isColliding( (Entity)entities.get(i) ) )
        this.goBack();
    
    return bool0;
  }
  //            //si muove prima con le x e poi con le y
  public boolean move( float xx, float yy ){ //muove l'entità e restituisce se si è mossa o no
    
    bool0 = true;
    
    pX = x;
    pY = y;
    x += xx;
    for( int i=0; i<entities.size(); i++ )
      if( this.getHitbox().isColliding( (Entity)entities.get(i) ) )
        this.goBack();
    
    pX = x;
    pY = y;
    y += yy;
    for( int i=0; i<entities.size(); i++ )
      if( this.getHitbox().isColliding( (Entity)entities.get(i) ) )
        this.goBack();
    
    return bool0;
  }
  
  public int normPixel(int n){
    return ((int)((imgDim/(float)32)*(float)n)); //accurata descrizione di java
  } //trasforma i pixel delle animazioni in base ai pixel dello schermo
  
  public void goBack(){
    bool0 = false;
    x = pX;
    y = pY;
  }
  
  public Entity getHitbox(){
    
    return new Entity( x, y, width, height );
    
  }
  
  
}
