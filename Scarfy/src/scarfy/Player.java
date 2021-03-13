package scarfy;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static java.lang.Math.floorDiv;
import static java.lang.Math.floor;
import static scarfy.Scarfy.PRINT_HITBOX;



public class Player extends EntityAlive{
  
  private Input in;
  private float spd, bspd; //bspd = backup speed
  private BufferedImage[] imgIdle;
  private BufferedImage[] imgAttackNorth, imgAttackEast, imgAttackSouth, imgAttackWest;
  private BufferedImage[] imgRunNorth, imgRunEast, imgRunSouth, imgRunWest;
  private EntityRelative att[]; //l'attacco
  private boolean throwUpdate = false;
  private boolean doHitboxAttack = false;
  public  boolean canItMove = true;
  private int cooldownAttack = 0;
  private int immortalityFrames = 0;
  
  
  private Enemy e;
  int n; //indice che aumenta ogni update() e serve a fare qualcosa(?) dopo tot frame che il personaggio non fa nulla
  int keys; 
  
  
  //--------------------------------------------------------------------------------------
  private void initializeImage(){
    
    att = new EntityRelative[4];
    att[0] = new EntityRelative( this, -width, -height/2 - offsetHitboxY, width*3, height/4*3 );
    att[1] = new EntityRelative( this, width, 10 - offsetHitboxY, width/2*3, height-20 );
    att[2] = new EntityRelative( this, -width, height/4*3 - offsetHitboxY, width*3, height/4*3 );
    att[3] = new EntityRelative( this, -width/2*3, 10 - offsetHitboxY, width/2*3, height-20 );
    
    imgIdle = new BufferedImage[4];
    
    
    imgRunNorth = new BufferedImage[12];
    imgRunEast = new BufferedImage[12];
    imgRunSouth = new BufferedImage[8];
    imgRunWest = new BufferedImage[12];
    
    imgAttackNorth = new BufferedImage[6];
    imgAttackEast = new BufferedImage[6];
    imgAttackSouth = new BufferedImage[6];
    imgAttackWest = new BufferedImage[6];
    
    
    try {
      int i;
        
      for( i=0; i<5; i++){
        imgAttackNorth[i] = resize( ImageIO.read( new File( "pepsi\\player\\attack\\attack_up\\frame_"+i+".png" ) ), normPixel(64), normPixel(64) );
        imgAttackEast[i] = resize( ImageIO.read( new File( "pepsi\\player\\attack\\attack_right\\frame_"+i+".png" ) ), 158, 110 );
        imgAttackSouth[i] = resize( ImageIO.read( new File( "pepsi\\player\\attack\\attack_down\\frame_"+i+".png" ) ), normPixel(64), normPixel(64) );
        imgAttackWest[i] = resize( ImageIO.read( new File( "pepsi\\player\\attack\\attack_left\\frame_"+i+".png" ) ), 158, 110 );
      }
      for( i=0; i<imgIdle.length; i++)
        imgIdle[i] = resize( ImageIO.read( new File( "pepsi\\player\\idle\\frame_"+i+".png" ) ), width, height );
      
      for( i=0; i<12; i++)
      {
        imgRunEast[i] = resize( ImageIO.read( new File( "pepsi\\player\\run\\run_right\\frame_"+i+".png" ) ), normPixel(32), normPixel(35) );
        imgRunWest[i] = resize( ImageIO.read( new File( "pepsi\\player\\run\\run_left\\frame_"+i+".png" ) ), normPixel(32), normPixel(35) );
      }
      for( i=0; i<8; i++)
      {
        imgRunSouth[i] = resize( ImageIO.read( new File( "pepsi\\player\\run\\run_down\\frame_"+i+".png" ) ), normPixel(32), normPixel(32) );
        imgRunNorth[i] = resize( ImageIO.read( new File( "pepsi\\player\\run\\run_up\\frame_"+i+".png" ) ), normPixel(32), normPixel(32) );
      }
      
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }
  
  @Override 
  public int normPixel(int n){ //è overridata perchè si.
    return ((int)((110/(float)32)*(float)n)); //accurata descrizione di java
  } //trasforma i pixel delle animazioni in base ai pixel dello schermo
  

  
  public Player( float x, float y, int width, int height, float spd, Input in, ArrayList entities, ArrayList enemies ){
    super(x,y,51,103,100,entities,enemies);
    this.offsetHitboxY = 40;
    initializeImage();
    this.height -= offsetHitboxY;
    this.pX = x;
    this.pY = y;
    this.ori = 1;
    this.spd = spd;
    this.bspd = spd;
    this.in = in;
    this.hp = 100;
    
    
  } //costruttore
  
  //--------------------------------------------------------------------------------------
  
  
  private void idle(Graphics g){
    //stampa il frame
    drawImage(imgIdle[this.ori], (int)x, (int)y, null);
  }
  
  private void running(Graphics g, int n){
    //stampa il frame
    
    switch (ori) {
      case 0:
        drawImage(imgRunNorth[floorDiv(frame, 6)%8], (int)x-normPixel(9), (int)y-7, null);
        break;
      case 1:
        drawImage(imgRunEast[(int)floor(frame/ 3)%12], (int)x-normPixel(10), (int)y-7, null);
        break;
      case 2:
        drawImage(imgRunSouth[floorDiv(frame, 5)%8], (int)x-normPixel(9), (int)y, null);
        break;
      case 3:
        drawImage(imgRunWest[(int)floor(frame/ 4)%12], (int)x-normPixel(5), (int)y-7, null);
        break;
    }
    
  }
  
  public void drawKnockBack(Graphics g){
    
    drawImage(imgIdle[this.ori], (int)x, (int)y, null);
    
  }
  
  
  
  public void drawAttack(Graphics g, int n){
    
    switch (ori) {
      case 0:
        drawImage(imgAttackNorth[n], (int)x-normPixel(23), (int)y-normPixel(28), null);
        break;
      case 1:
        drawImage(imgAttackEast[n], (int)x-normPixel(8), (int)y, null);
        break;
      case 2:
        drawImage(imgAttackSouth[n], (int)x-normPixel(24), (int)y-normPixel(18), null);
        break;
      case 3:
        drawImage(imgAttackWest[n], (int)x-normPixel(19), (int)y, null);
        break;
    }
    
  }
  
  @Override
  public void hitted( float damage, float knock, float frameKnockBack, EntityAlive e ){
    if( immortalityFrames >= 0 )
      return;
    
    immortalityFrames = 80;
    hp -= damage;
    if( hp <= 0 )
      this.die();
    else
      setKnockBack(  knock, frameKnockBack, e );
    
    //newParticle(0, 200, 200, 100, 100, ori);
  }
  
  
  @Override
  public void hitted( float damage, float knock, float frameKnockBack, int ori ){
    if( immortalityFrames >= 0 )
      return;
    
    immortalityFrames = 40;
    hp -= damage;
    if( hp <= 0 )
      this.die();
    else
      setKnockBack(  knock, frameKnockBack, ori );
    
    //newParticle(0, 200, 200, 100, 100, ori);
  }
  
  //--------------------------------------------------------------------------------------
  
  @Override
  public void die(){}
  
  public void attack(Graphics g)  //viene chiamata una sola volta quando attacchi
  {
    
    att[ori].update();
    if( PRINT_HITBOX )
      att[ori].draw(g);
    
    in.setIsAttackCalled(false);
    
    int a=0;
    
    //disegna l'attacco e stoppa il programma
    //while( a==0 ){att[ori].draw(g);bs.show();try {Thread.sleep(1000);} catch (InterruptedException ex) {Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);}}
    
    for( int i=0; i<enemies.size(); i++ )
      if( att[ori].isColliding( (Enemy)enemies.get(i) ) )
      {
        ((Enemy)enemies.get(i)).hitted(10, 40, 3, this.ori ); // damage, knockback, orientation
        this.setKnockBack(20, 3, backOri(ori) );
      }
    
    for( int i=0; i<Projectile.p.size(); i++ )
      if( att[ori].isColliding( (Projectile)Projectile.p.get(i) ) )
      {
        ((Projectile)Projectile.p.get(i)).hitted(10, 40, 3, this.ori ); // damage, knockback, orientation
        this.setKnockBack(20, 3, backOri(ori) );
      }
    
  }
  
  //--------------------------------------------------------------------------------------
  
  @Override
  public void draw(Graphics g)
  {
    if( hp <= 0 )
      return;
    gg = g;
    if( PRINT_HITBOX )
      this.getHitbox().draw(g);
    if( immortalityFrames > 0 )
      if( immortalityFrames % 14 > 8 )
        return;
    if( !canItMove ){   //se non si può muovere non cambia neanche il frame
      this.idle(g); 
      return;
    }
    
    //----------------------
    switch(state){
      case "idle":
        
        this.idle(g);
      //----------------------
      break; case "running":
        if( frame == 12*8*3*5*6-1 ){
          frame = 0;
        }else{
          frame++;
        }
        this.running(g, frame); //la divisione serve per rallentare l'animazione
      break; case "attack":
        
        frame++;
        this.drawAttack(g, Math.floorDiv(frame, 3));
        
        switch (Math.floorDiv(frame, 3)) {
          case 1:
            doHitboxAttack = true;
          break;
          case 4:
            frame+=2;
          break;
        }
        
        if( frame == 5*3-1 )
          changeState("idle"); //quando finisce l'attacco
        
      //----------------------
      break;

    }
  }//draw
  

  
  private void preUpdate(Graphics g){
    
    throwUpdate = false;
    
    if( cooldownAttack >= 0 ){
      if( cooldownAttack >= 15/2 )
        in.setIsAttackCalled(false);
      cooldownAttack--;
    }
    if( immortalityFrames >= 0 ){
      //in.setIsAttackCalled(false);
      immortalityFrames--;
    }
        
    calculateCenter();
    
    //---------------------------------------------------------------knockback
    if( checkKnockback() ){
      in.setIsAttackCalled(false);
      applyKnockBack();
      throwUpdate = true;
      return;
    }else{ //-----------se non stai venendo knockbackato ma se stai attaccando fa questo

      e = this.isCollidingWith();  //controllo delle collisioni con i nemici
      if( e != null && immortalityFrames <= 0 )              //se stai collidendo prendi knockback e danno
      {
        //   distanza, frame della durata
        //if( e instanceof Slime )
          this.hitted( 10, 35, 3, e);
        //else
        //  this.setKnockBack( 35, 3, e);
        
        return; //sei bloccato mentre vieni knockbackato
      }
    }
    
    //--------------------------------------------------------------- attacco

    if( in.isAttackCalled() && !isKnockBacking ) //ci entra finchè non crea l'hitbox attacca
    {
      if( doHitboxAttack ){ //ora ha attaccato
        this.attack(g);
        cooldownAttack = 15;
      }
      changeState("attack");
    }
    
    doHitboxAttack = false;
    
    if( state.equals("attack") ) //ci entra finchè non finisce l'attacco
    {
      throwUpdate = true;
    }
    
    
  }
  
  
  
  
  //--------------------------------------------------------------------------------------
  
  public void update( Graphics g )
  {
    if( hp <= 0 )
      return;
    
    preUpdate(g);
    
    if( throwUpdate )
      return;
    
    
    
    //--------------------------------------------------------------- aggiorna lo stato del personaggio
    
    if( this.canItMove )
      ori = in.ori();
    
    keys = in.keysPressedAtOnce();
    
    if(  ( ( keys == 0 || keys == 4 ) || keys == 3) ){   //change to idle animation
      changeState("idle"); 
      return;
    }
    else if( keys == 1 && !state.equals("running") )   //change to run animation
      changeState("running");
    
    if( keys == 2 )
    {
      if( (in.up() && in.down()) || (in.left() && in.right()) ){
        changeState("idle");
        return;
      }else
        changeState("running");
      
      spd *= 0.75;
    }
    
    //----------------------movimento e controllo delle collisioni
    if( this.canItMove ){
      float ax=0, ay=0;

      if( in.left() )
        ax = -spd;
      if( in.right() )
        ax = spd;
      if( in.up() )
        ay = -spd;
      if( in.down() )
        ay = spd;

      move(ax, ay);
    }
    
    
    //----------------------
    spd = bspd;
    
  }
  
  
  //--------------------------------------------------------------------------------------
  
  public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics g = dimg.createGraphics();
    g.drawImage(tmp, 0, 0, null); 
    g.dispose();

    return dimg;
  }  
  
  public void setOri(int n){
    ori = n;
  }
  public void setSpd(float f){
    spd = f;
  }

  public float getHp() {
    return hp;
  }
  
}


