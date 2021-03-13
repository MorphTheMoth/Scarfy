package scarfy;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;
import static scarfy.Scarfy.colorImageSat;


public class HUD {
  
  private Player p;
  private BufferedImage[] imgHp;
  private BufferedImage[] imgMap;
  private BufferedImage[] imgBigMap;
  private BufferedImage miniMapBackground;
  private Point imgHpPos;
  private int mapImgDim;
  private int bigMapImgDim;
  private int offsetMiniMap;
  private int mapBackgroundDim;
  private Point MiniMapCenter;
  private MapTreeManager mapManager;
  private Input in;
  
  private int blinkCounter = 0;
  
  public HUD( Player p, MapTreeManager mapManager, Input in ){
    this.p = p;
    this.in = in;
    this.mapManager = mapManager;
    
    mapImgDim = 30;  //multiplo di 7 è >>>>>>>
    bigMapImgDim = 43;
    offsetMiniMap = 2;
    mapBackgroundDim = mapImgDim*7 + offsetMiniMap*8 + 16;
    //System.out.println(mapBackgroundDim);
    
    MiniMapCenter = new Point( 1920-mapBackgroundDim/2-mapImgDim/2-30, mapBackgroundDim/2-mapImgDim/2+30 );
    imgHpPos = new Point( 50, 50 );
    
    imgHp = new BufferedImage[2];
    imgMap = new BufferedImage[3];
    imgBigMap = new BufferedImage[3];
    
    try {
      
      for( int i=0; i<2; i++ )
        imgHp[i] = resize( ImageIO.read( new File( "pepsi\\player\\hp\\hp_"+i+".png" ) ), 27, 27 );
      for( int i=0; i<3; i++ )
        imgMap[i] = resize( ImageIO.read( new File( "pepsi\\miniMap\\Livello_"+i+".png" ) ), mapImgDim, mapImgDim );
      for( int i=0; i<3; i++ )
        imgBigMap[i] = colorImageSat( resize( ImageIO.read( new File( "pepsi\\miniMap\\Livello_"+i+".png" ) ), bigMapImgDim, bigMapImgDim ), 150);
      
      
      miniMapBackground = resize( ImageIO.read( new File( "pepsi\\miniMap\\wall.png" ) ), mapBackgroundDim, mapBackgroundDim );
    } catch (IOException ex) {
      System.out.println("Errore nel'apertura delle immagini dell'HUD");
    }
  }
  
  private void drawNearRoom( MapLeaf leaf, Graphics g ){
    if( leaf.hasBeenPrinted )
      return;
    
    //System.out.println(leaf.p);
    leaf.hasBeenPrinted = true;
    
    for( int i=0; i<4; i++ )
      if( leaf.getMapCode().charAt(i) == '1' )      //controllo se nella currentLeaf ci sono delle stanze vicine
      {
        if( !in.isTabPressed )
          if( Math.abs( leaf.p.x-mapManager.getCurrentLeaf().p.x ) > 3 || Math.abs(leaf.p.y-mapManager.getCurrentLeaf().p.y) > 3 ){
            drawNearRoom( leaf.closeRoom[i], g );
            break;
          }
        if( leaf.hasBeenVisited ){                  //se si le stampo e se sono già state visitate controllo se ci sono stanze vicine...
          
          if( !in.isTabPressed )
            g.drawImage(imgMap[1], (leaf.p.x-mapManager.getCurrentLeaf().p.x)*(mapImgDim + offsetMiniMap)+ MiniMapCenter.x, (-(leaf.p.y-mapManager.getCurrentLeaf().p.y))*( mapImgDim + offsetMiniMap) + MiniMapCenter.y, null);
          else
            g.drawImage(imgBigMap[1], (leaf.p.x-mapManager.getCurrentLeaf().p.x)*(mapImgDim + offsetMiniMap)+ MiniMapCenter.x, (-(leaf.p.y-mapManager.getCurrentLeaf().p.y))*( mapImgDim + offsetMiniMap) + MiniMapCenter.y, null);
          drawNearRoom( leaf.closeRoom[i], g );
          
        }else{
          
          if( !in.isTabPressed )
            g.drawImage(imgMap[0], (leaf.p.x-mapManager.getCurrentLeaf().p.x)*(mapImgDim + offsetMiniMap)+ MiniMapCenter.x, (-(leaf.p.y-mapManager.getCurrentLeaf().p.y))*( mapImgDim + offsetMiniMap) + MiniMapCenter.y, null);
          else
            g.drawImage(imgBigMap[0], (leaf.p.x-mapManager.getCurrentLeaf().p.x)*(mapImgDim + offsetMiniMap)+ MiniMapCenter.x, (-(leaf.p.y-mapManager.getCurrentLeaf().p.y))*( mapImgDim + offsetMiniMap) + MiniMapCenter.y, null);
          
        }
      }
  }
  
  public void draw(Graphics g){
    
    int i;
    for( i=0; i<Math.floor( p.getHp()/10 ); i++ )                        //stampa gli hp
      g.drawImage(imgHp[1], imgHpPos.x + 33*i, imgHpPos.y, null);
    while( i < 10 ){
      g.drawImage(imgHp[0], imgHpPos.x + 33*i, imgHpPos.y, null);
      i++;
    }
    
    

    if( in.isTabPressed ){                                               //stampa la minimappa
      mapImgDim = bigMapImgDim;
      MiniMapCenter.x = 1920/2;
      MiniMapCenter.y = 1080/2;
    }else{
      mapImgDim = 30;
      MiniMapCenter.x = 1920-mapBackgroundDim/2-mapImgDim/2-30;
      MiniMapCenter.y = mapBackgroundDim/2-mapImgDim/2+30;
      
      g.drawImage(miniMapBackground, MiniMapCenter.x-mapBackgroundDim/2+mapImgDim/2, MiniMapCenter.y-mapBackgroundDim/2+mapImgDim/2, null);
    }
    
    mapManager.setHasBeenPrinted(false);
    blinkCounter++;
    if( blinkCounter >= 900 )
      blinkCounter = 0;
    
    drawNearRoom( mapManager.getCurrentLeaf(), g );

    if( Math.floor( blinkCounter/30 ) % 2 == 0 )
      if( !in.isTabPressed )
        g.drawImage(imgMap[2], MiniMapCenter.x, MiniMapCenter.y, null);
      else
        g.drawImage(imgBigMap[2], MiniMapCenter.x, MiniMapCenter.y, null);
    
  }
  
  public void update(){
    
  }
  
}
