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
import static scarfy.Scarfy.PRINT_HITBOX;


public class Projectile extends Enemy{
  
  BufferedImage[] img;
  protected static ArrayList<Projectile> p = new ArrayList();
  protected int type;
  
  public Projectile( int type, float x, float y, int width, int height, float spdX, float spdY, ArrayList entities, ArrayList enemies ){
    
    super( x, y, width, height, 1, 0, 1, entities, enemies);
    
    this.type = type;
    this.spdX = spdX;
    this.spdY = spdY;
    
    
    try{
      switch(type){
        case 0:                                         //wizard ball
          img = new BufferedImage[10];
          for(int i=0; i<10; i++)
            img[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\orb\\idle\\f"+i+".png" ) ), width, height );
        break;
        case 1:                                         //boss ball
          img = new BufferedImage[10];
          for(int i=0; i<10; i++)
            img[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\orb\\idle\\f"+i+".png" ) ), width, height );
        break;
      }
    } catch (IOException ex) {
      System.out.println("Errore nell'aprire l'immagine di un orb (type : "+type+")");
    }
    
    p.add(this);
  }
  
  public Projectile( int type, float x, float y, int width, int height, float spd, EntityAlive e, ArrayList entities, ArrayList enemies ){
    
    super( x, y, width, height, 1, spd, 1, entities, enemies);
    
    if( e != null )
      updateMovementTowards( e, spd );
    
    this.type = type;
    
    try{
      switch(type){
        case 0:                                         //wizard ball
          img = new BufferedImage[10];
          for(int i=0; i<10; i++)
            img[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\orb\\idle\\f"+i+".png" ) ), width, height );
        break;
        case 1:                                         //boss ball
          img = new BufferedImage[10];
          for(int i=0; i<10; i++)
            img[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\wizard\\orb\\idle\\f"+i+".png" ) ), width, height );
        break;
      }
    } catch (IOException ex) {
      System.out.println("Errore nell'aprire l'immagine di un orb (type : "+type+")");
    }
    
    p.add(this);
  }
  
  public static void clear(){
    p.clear();
  }
  
  protected int getIndexImg(){
    frame++;
    switch(type){
      case 0:
        return ((int)Math.floor(frame/5))%10;
      case 1:
        return ((int)Math.floor(frame/5))%10;
    }
    return 0;
  }
  
  @Override
  public void draw(Graphics g){ 
    if( PRINT_HITBOX ){
      super.draw(g); //disegna l'hitbox
    }
    g.drawImage( img[this.getIndexImg()], (int)x, (int)y, null );
    
  }
  
  public static void drawAll(Graphics g){
    for(int i=0; i<p.size(); i++)
      p.get(i).draw(g);
  }
  
  public static void updateAll(Player pl){
    
    for(int i=0; i<p.size(); i++)
      if( p.get(i).uppdate(pl) )
        p.remove(i--);
    
  }
  
  public void die(){
    
    p.remove(this);
    try {
      this.finalize();
    } catch (Throwable ex) {
      Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  //uppdate con due p perchè mi serve restituire un boolean 
  //ma poi ha la stessa segnatura di draw(g) in entity 
  //e mettere @override boh non va <3
  
  protected boolean uppdate(Player pl){ //PL non P'uno' //restituise true se ha colpito qualcosa, e quindi viene eliminata
    
    x += spdX;
    y += spdY;
    if( !this.move(spdX, spdY) ) //lo muove e controlla se si è potuto muovere
      if( type != 1 )
        return true; //si rompe il proiettile
    
    if( this.isColliding(pl) ){ //controlla se ha hittato il player
      pl.hitted(10, 20, 3, this);
      if( type != 1 )
        return true; //si rompe il proiettile
    }
    
    return false;
    
  }
  
  
  public void moveTo(float xx, float yy){
    x = xx;
    y = yy;
  }
  
  
  
  
}
