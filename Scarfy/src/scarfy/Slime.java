package scarfy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;
import static scarfy.Scarfy.PRINT_HITBOX;

public class Slime extends Enemy{
  
  private static boolean areImagesInitialized = false;
  private static BufferedImage[][] jump = new BufferedImage[4][5];
  private static BufferedImage[] idle = new BufferedImage[4];
  private static BufferedImage[] jumpShade = new BufferedImage[2];
  private int jumpCounter = 0;
  private static int imgDim;
  
  
  public Slime(float x, float y, int width, int height, float hp, float spd, float KBresistance, ArrayList entities, ArrayList enemies){
    super(x, y, width, height, hp, spd, KBresistance, entities, enemies);
    this.imgDim = (int)(width*(1.9));
    
    spdX = spd;
    spdY = spd;
    
    if( !areImagesInitialized )
    {
      areImagesInitialized = true;
      try {
        
        //greenChromaAll();
        for(int i=0; i<2; i++)
          jumpShade[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\slime\\jump_0\\ombra_"+i+".png" ) ), imgDim, imgDim );
      } catch (IOException ex) {
        System.out.println("error opening slime image");
      }
    }
  }
  
  @Override
  public void hitted(float damage, float knock, float frameKnockBack, int ori){
    super.hitted(damage, knock, frameKnockBack, ori);
  }
  
  private void jump(Graphics g, int n){
    switch( n )
    {
      case 0:
        drawImage( jumpShade[0], (int)x-normPixel(7), (int)y-normPixel(12), null );
        drawImage( jump[ori][1], (int)x-normPixel(7), (int)y-normPixel(20), null );
      break;case 1:case 2:case 3:case 4:case 5:
        drawImage( jumpShade[1], (int)x-normPixel(7), (int)y-normPixel(12), null );
        drawImage( jump[ori][2], (int)x-normPixel(7), (int)y-normPixel(25), null );
      break;case 6:
        drawImage( jump[ori][3], (int)x-normPixel(7), (int)y-normPixel(20), null );
      break;case 7:
        drawImage( jump[ori][4], (int)x-normPixel(7), (int)y-normPixel(14), null );
      break;
    }
    
  } 
  
  @Override
  public void draw(Graphics g){
    
    gg = g;
    
    if( PRINT_HITBOX )
      g.drawRect((int)x, (int)y, width, height);
    
    
    switch(state)
    {
      case "idle":
        drawImage( idle[ori], (int)x-normPixel(7), (int)y-normPixel(12), null );
      break;
      case "jumping":
        frame++;
        
        jump(g, (int)Math.floor(frame/5));
        if( frame == 40-1 )
          changeState("idle");
      break;
    }
    
  }
  

  @Override
  public void update( Player p ){
    if( redFrameLeft != 0 )
    {
      return;
    }
    
    calculateCenter();
    
    if( checkKnockback() )
    {
      applyKnockBack();
      return;
    }
    
    
    if( jumpCounter >= 60 ){ // quando finisce il cooldown del salto fa questa funzione
      changeState("jumping");
      
      //-------------------------------------------------inizializa alcune variabili
      setOri(this.cx-p.getX()+p.getWidth()/2, this.cy-p.getY()+p.getHeight()/2);
      updateMovementTowards( p, spd );
      
      if( spdX*40 > Math.abs(cx-p.cx) ) //40 = la durata del salto in frame       //-----gestione del salto in base a quanto è vicino il personaggio
        spdX = Math.abs(cx-p.cx)/40;
      if( spdY*40 > Math.abs(cy-p.cy) )
        spdY = Math.abs(cy-p.cy)/40;
      
      jumpCounter = 0;
    }
    
    if( state.equals("idle") )
      jumpCounter++;
    
    else if( state.equals("jumping") ) //soloquando salta si muove
      move(spdX, spdY);
    
    
  }  //fine dell'update
  
  public static void blackChromaAll(){
    
    System.out.println("black");
    try {
      
      for(int i=0; i<4; i++)
        for(int j=0; j<5; j++)
          jump[i][j] = resize( ImageIO.read( new File( "pepsi\\enemies\\slime\\black\\attack_"+i+"\\frame_"+j+".png" ) ), imgDim, imgDim );
      
      for(int i=0; i<4; i++)
        idle[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\slime\\black\\idle\\Livello_"+i+".png" ) ), imgDim, imgDim );
      
    } catch (IOException ex) {
      System.out.println("error opening black slime image");
    }
    
  }
  
  public static void greenChromaAll(){
    System.out.println("green");
    try {
      
      for(int i=0; i<4; i++)
      {
        for(int j=0; j<5; j++)
          jump[i][j] = resize( ImageIO.read( new File( "pepsi\\enemies\\slime\\jump_"+i+"\\frame_"+j+".png" ) ), imgDim, imgDim );
        
        idle[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\slime\\idle\\Livello_"+i+".png" ) ), imgDim, imgDim );
      }
    } catch (IOException ex) {
      System.out.println("error opening green slime image");
    }
    
  }
  
}
