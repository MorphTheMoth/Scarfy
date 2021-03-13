package scarfy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;
import static scarfy.Scarfy.PRINT_HITBOX;


public class Wizard extends Enemy{
  
  private static boolean areImagesInitialized = false;
  private static BufferedImage idle;
  private static BufferedImage[] casting;
  private static BufferedImage[] tp;
  private int castingCooldown;
  private int tpCooldown;
  private int castingTime = 0;
  private int ncast;        //numero di attacchi prima di un tp
  
  public Wizard(float x, float y, int width, int height, float hp, float KBresistance, ArrayList entities, ArrayList enemies){
    
    super(x, y, width, height, hp, 0, KBresistance, entities, enemies);
    
    castingCooldown = (int)(Math.random()*50); //randomizza il cooldown così i maghi nella stanza non castano tutti assieme
    tpCooldown = 0;
    
    ncast = (int)( (Math.random() * 100) / 40 );
    ncast++;
    
    
    
    if( !areImagesInitialized ){
      areImagesInitialized = true;
      casting = new BufferedImage[10];
      tp = new BufferedImage[11];
      
      try {
        idle = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\idle\\frame_0.png" ) ), width, height );
        
        for( int i=0; i<casting.length; i++)
          casting[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\casting\\frame_"+i+".png" ) ), width/32*64, height/40*64 );
        
        tp[0] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\tp\\"+0+".png" ) ), width, height );             //il primo frame viene deppio
        for( int i=1; i<tp.length; i++)
          tp[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\tp\\"+(i-1)+".png" ) ), width, height);
      } catch (IOException ex) {
        System.out.println("error opening wizard image");
      }
    }
  }
  
  @Override
  public void hitted(float damage, float knock, float frameKnockBack, int ori){
    super.hitted(damage, knock, frameKnockBack, ori);
    changeState("idle");
    castingTime = 0;
  }
  
  
  @Override
  public void draw(Graphics g){
    gg = g;
    
    if( PRINT_HITBOX )
      g.drawRect((int)x, (int)y, width, height);
    
    switch(state)
    {
      case "idle":
        drawImage( idle, (int)x, (int)y, null );
      break;
      case "casting":
        drawImage( casting[(int)Math.floor(frame/10)], (int)x-normPixel(16), (int)y-normPixel(24), null );
        frame++;
        if( frame >= 100 )
          changeState("idle");
      break;
      case "teleporting":

        if( frame < 111 ){                        // animazione despawn
          drawImage( tp[(int)Math.floor(frame/10)], (int)x, (int)y, null );
          frame += 2;

          if( frame >= 109 ){              //scompare l'hitbox
            frame = 330;
          }
        }

        if( frame > 111 ){
          frame -= 2;
          //int bugFixImpovvisato = 0; //la data di consegna era tra un'ora <3
          if( frame == 310 )
          {
            x = 0;
            y = 0;
            while( !moveIstant((float)Math.random()*(1660-width)+80, (float)Math.random()*(730-height)+170) );
          }

          if( frame < 310 ){
            drawImage( tp[(int)Math.floor( (frame-200) /10)], (int)x, (int)y, null );
          }

        }

        if( frame == 200 )
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
    
    //-------------
    
    if( state.equals("casting") ){
      
      castingTime++;
      if( castingTime == 80 ) // multiplo di 10 è meglio 
      {  //qui spawno la palla
        new Projectile(0, x+normPixel(16), y-normPixel(5), normPixel(11), normPixel(11), 3f, p, entities, enemies  );
      }
      
    }else{
      if( !state.equals("teleporting") )
        castingCooldown++;
    }
    
    if( castingCooldown >= 3*60 ){  //quando finisce il cooldown dell'attacco
      changeState("casting");
      tpCooldown++;
      castingCooldown = 0; //resetto il cooldown
      castingTime = 0; //e anche il tempo che ci mette a spawnare la palla
    }
    
    //-------------
    
    if( !state.equals("casting") && tpCooldown >= ncast ){
      changeState("teleporting");
      tpCooldown = 0; 
    }
    
  }
  
  public boolean checkIfOutBorder( float xx, float yy ){ 
    if( ( ( x + xx ) < 0) || ( ( xx + x ) > 1920) )
      return true;
    
    if( ( ( y + yy ) < 0) || ( ( yy + y ) > 1080) )
      return true;
    
    return false;
  }
}
