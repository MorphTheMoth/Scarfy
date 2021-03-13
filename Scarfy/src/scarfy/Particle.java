package scarfy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;


public class Particle {
  
  private static ArrayList p = new ArrayList(); //Arraylist di tutte le particelle attive
  private static ArrayList images = new ArrayList(); //Arraylist di tutte le immagini delle particelle
  
  private BufferedImage[] img;
  private int count;
  private int timePerImg;
  private float x;
  private float y;
  
  
  public Particle( BufferedImage[] img, float x, float y, int timePerImg ){
    
    this.img = img;
    this.x = x;
    this.y = y;
    this.count = 0;
    this.timePerImg = timePerImg;
    p.add(this);
  }
  
  
  public boolean draw(Graphics g){
    
    if( count >= img.length * timePerImg ){
      try {
        this.finalize();
      } catch (Throwable ex) {
        Logger.getLogger(Particle.class.getName()).log(Level.SEVERE, null, ex);
      }
      return true;
    }
    g.drawImage( img[(int)Math.floor((float)count++ / (float)timePerImg)], (int)x, (int)y, null );
    return false;
    
  }
  
  public static void drawAll(Graphics g){
    
    for(int i=0; i<p.size(); i++)
      if( ((Particle)p.get(i)).draw(g) )
        p.remove(i--);
    
  }
  
  public static void inizializeParticles(){
    
    images.add( new BufferedImage[6] );
    try {
      for(int i=0; i<((BufferedImage[])images.get(0)).length; i++)
        ((BufferedImage[])images.get(0))[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\nightmareEcho\\aoe_attack\\f"+i+".png" ) ), 110, 110 );
    } catch (IOException ex) {
      System.out.println("errore nell'aprire le immagini dellae particelle");
    }
    
  }
  
  public static void newParticle( int type, float x, float y, int timePerImg ) {
    
    BufferedImage[] img;
    
    switch(type){
      
      case 0:
        new Particle( ((BufferedImage[])images.get(type)), x, y, timePerImg );
      break;
      
    }
  }
  
  
}
