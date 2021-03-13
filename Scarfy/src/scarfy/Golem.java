package scarfy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;
import static scarfy.Scarfy.PRINT_HITBOX;


public class Golem extends Enemy{
  
  private static boolean areImagesInitialized = false;
  private static BufferedImage[] idle;
  private static BufferedImage[][] casting;
  private static BufferedImage[][] walking;
  private static BufferedImage[][] starting;
  private static BufferedImage[][] attImg;
  private int castingCooldown = 80;
  private int castingTime = 0;
  private boolean drawAttack = false;
  private ArrayList attDrawCoord;
  
  public Golem(float x, float y, int width, int height, float hp, float spd, float KBresistance, ArrayList entities, ArrayList enemies){
    super(x, y, width, height, hp, spd, KBresistance, entities, enemies);
    imgDim = width*2;
    int i;
    changeState("starting");
    ori = 1;
    attDrawCoord = new ArrayList();
    
    
    if( !areImagesInitialized )
    {
      areImagesInitialized = true;
      idle   = new BufferedImage[2];
      attImg  = new BufferedImage[2][2];
      casting  = new BufferedImage[2][7];
      walking   = new BufferedImage[2][10];   //nooo, you have to space things properly
      starting   = new BufferedImage[2][15];  //haha scaletta di = new goes brrrr

      try{
        for( i=0; i<attImg.length; i++)
          for( int j=0; j<attImg.length; j++)
            attImg[i][j] = resize( ImageIO.read( new File( "pepsi\\enemies\\golem\\laser_"+i+"\\f"+j+".png" ) ), width/2, height/2 );

        for( i=0; i<idle.length; i++)
          idle[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\golem\\idle_"+i+"\\f0.png" ) ), imgDim, imgDim );


        for( i=0; i<casting.length; i++)
          for( int j=0; j<casting[0].length-1; j++)
            casting[i][j] = resize( ImageIO.read( new File( "pepsi\\enemies\\golem\\attack_"+i+"\\frame_"+j%2+".png" ) ), imgDim, imgDim );

        for( i=0; i<starting.length; i++)
          for( int j=0; j<starting[0].length; j++)
            try{
              starting[i][j] = resize( ImageIO.read( new File( "pepsi\\enemies\\golem\\activation_"+i+"\\f"+j+".png" ) ), imgDim, imgDim );
            } catch (IOException ex) {
              starting[i][j] = starting[i][j-1];
            }

        for( i=0; i<casting.length; i++)
            casting[i][6] = resize( ImageIO.read( new File( "pepsi\\enemies\\golem\\attack_"+i+"\\frame_"+6+".png" ) ), imgDim, imgDim );

        for( i=0; i<walking.length; i++)
          for( int j=0; j<walking[0].length; j++)
            walking[i][j] = resize( ImageIO.read( new File( "pepsi\\enemies\\golem\\run_"+i+"\\f"+j+".png" ) ), imgDim, imgDim );
      } catch (IOException ex) {
        System.out.println("error opening Golem image");
      }
    }
  }
  
  public static int normalizeOri(int n)
  {
    if( n == 0 )
      return 1;
    else
      return 3;
  }
  
  @Override
  public void hitted(float damage, float knock, float frameKnockBack, int ori){
    super.hitted(damage, knock, frameKnockBack, ori);
  }
  
  @Override
  public int normPixel(int n){
    return ((int)((imgDim/(float)64)*(float)n)); //accurata descrizione di java
  } //trasforma i pixel delle animazioni in base ai pixel dello schermo
  
  
  public void attack(Player p){
    
    attDrawCoord.clear();
    drawAttack = true;
    if( ori==0 )
      attDrawCoord.add(new EntityAlive( x+width/2, y+height/2, width/2, height/2, entities ));
    else
      attDrawCoord.add(new EntityAlive( x-width/3, y+height/2, width/2, height/2, entities ));
    
    if( ori==0 )
      while( ((EntityAlive)attDrawCoord.get( 0 )).move( 10, 0) && !(((EntityAlive)attDrawCoord.get( 0 )).x > 1920) );
    else
      while( ((EntityAlive)attDrawCoord.get( 0 )).move(-10, 0) && !(((EntityAlive)attDrawCoord.get( 0 )).x < 0) );
    
    while( !((EntityAlive)attDrawCoord.get( attDrawCoord.size()-1 )).isColliding(this)  ){
      if( ori==0 )
        attDrawCoord.add(new EntityAlive( ((EntityAlive)attDrawCoord.get( attDrawCoord.size()-1 )).x-width/2, y+height/2, width/2, height/2, entities ));
      else
        attDrawCoord.add(new EntityAlive( ((EntityAlive)attDrawCoord.get( attDrawCoord.size()-1 )).x+width/2, y+height/2, width/2, height/2, entities ));
    }
    
    if( ori == 0 ){
      ((EntityAlive)attDrawCoord.get( attDrawCoord.size()-1 )).x =  x+width/2+width/2;
      attDrawCoord.add(new EntityAlive( x+width/2, y+height/2, width/2, height/2, entities ));
    }else{
      ((EntityAlive)attDrawCoord.get( attDrawCoord.size()-1 )).x =  x-width/2;
      attDrawCoord.add(new EntityAlive( x, y+height/2, width/2, height/2, entities ));
    }
    
    for( int i=0; i<attDrawCoord.size(); i++ ){
      if( ((EntityAlive)attDrawCoord.get( i )).isColliding(p) ){
        if( p.cy > ((EntityAlive)attDrawCoord.get( i )).y+((EntityAlive)attDrawCoord.get( i )).height/2 )
          p.hitted(20, 30, 3, 2);
        else
          p.hitted(20, 30, 3, 0);
        return;
      }
    }
  }
  
  @Override
  public void draw(Graphics g){
    gg = g;
    
    if( PRINT_HITBOX )
      g.drawRect((int)x, (int)y, width, height);
    

    switch(state)
    {
      case "starting":
        drawImage( starting[ori][(int)Math.floor(frame/5)], (int)x-normPixel(17), (int)y-normPixel(29), null );
        frame++;
        if( frame >= 15*5 ){
          changeState("walking");
        }
      
      break;
      case "idle":
        drawImage( idle[ori], (int)x, (int)y, null );
      break;
      case "casting":
        drawImage( casting[ori][(int)Math.floor(frame/10)], (int)x-normPixel(17), (int)y-normPixel(29), null );
        frame++;
        if( frame >= 60 ){
          this.setKnockBack(50, 7, backOri(normalizeOri(ori)));
        }
        if( frame >= 67 ){
          drawAttack = false;
          changeState("walking");
        }
      break;
      case "walking":
        drawImage( walking[ori][(int)Math.floor(frame/10)], (int)x-normPixel(17), (int)y-normPixel(29), null );
        frame++;
        if( frame >= 100 )
          frame = 0;
      break;
    }
    
    if( drawAttack )
    {
      for( int i=0; i<attDrawCoord.size()-1; i++ )
        g.drawImage( attImg[ori][1], (int)((EntityAlive)attDrawCoord.get(i)).x, (int)y+height/2, null );
      g.drawImage( attImg[ori][0], (int)((EntityAlive)attDrawCoord.get(attDrawCoord.size()-1)).x, (int)y+height/2, null );
      
      
      if( PRINT_HITBOX )
      {
        g.setColor(Color.red);
        for( int i=0; i<attDrawCoord.size(); i++ )
          ((EntityAlive)attDrawCoord.get(i)).getHitbox().draw(g);
      }
    }
    
  }
  
  
  @Override
  public void update( Player p ){
    if( redFrameLeft != 0 )
      return;
    
    if( checkKnockback() )
    {
      applyKnockBack();
    }
    
    calculateCenter();
    
    if( !state.equals("casting") )
      setOri( this.cx-p.getX()+p.getWidth()/2 );
    
    if( state.equals("starting") )
      return;
    
    
    //-------------
    
    if( state.equals("casting") ){
      
      castingTime++;
      if( castingTime >= 60 )
      { //qui spawno la palla
        //new Projectile(0, x+normPixel(16), y-normPixel(5), normPixel(11), normPixel(11), (float) 6, p, entities, enemies  );
        attack( p );
      }
      
    }else{
      castingCooldown++;
    }
    
    if( castingCooldown >= 4*60 ){  //quando finisce il cooldown dell'attacco
      changeState("casting");
      castingCooldown = 0; //resetto il cooldown
      castingTime = 0; //e anche il tempo che ci mette a spawnare la palla
    }
    
    //-------------
    
    if( !state.equals("casting") ){
      
      updateMovementTowards( p, spd );
      move(spdX, spdY);
      
    }
    
  }
  
  
  protected void setOri(float a)
  {
    
    if( a > 0 )
      changeOri(1);
    else
      changeOri(0);
      
  }
    
  
}
