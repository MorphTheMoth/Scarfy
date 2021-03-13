package scarfy;

import scarfy.Input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class Scarfy extends JFrame{
  
  public static final boolean PRINT_HITBOX = false;
  
  Graphics g;
  Input in;
  Player player;
  Color c;
  
  HUD hud;
  
  int width, height;
  
  MapTreeManager mapManager;
  RoomManager roomManager;
  Room room;
  
  BufferedImage black;
  BufferedImage[] blackScreenAnimation;
  int VIBRATION_LENGTH;
  float VIBRATION_VELOCITY;
  boolean isShacking;
  int countShake;
  int isEnding; //0 se non sta finendo, 1 se sei morto, 2 se hai vinto
  int endCounter;
  String endPhrase;
  
  //--------------------------------------------------------------------------------------
  
  public static BufferStrategy  bs;
  
  public void start(){
    
    
    endPhrase = "Press ENTER to try again...";
    endCounter = -1;
    isEnding = 0;
    isShacking = false;
    width = this.getBounds().width;
    height = this.getBounds().height;
    in = new Input( this );
    Particle.inizializeParticles();
    roomManager = new RoomManager(this);
    mapManager = new MapTreeManager(roomManager);
    room = mapManager.getCurrentRoom();
    addKeyListener(in);
    addMouseListener(in);
    player = new Player(100, height/2, 0, 0, 7.5f, in, room.entities, room.enemies );
    hud = new HUD(player, mapManager, in);
    Slime.greenChromaAll();
    
    
    int i=0;
    while( i != room.entities.size() )
    {
      player.x = 100+(int)(Math.random()*1720);
      player.y = 100+(int)(Math.random()*880);
      
      for( i=0; i<room.entities.size(); i++ ){
        if( player.isColliding(((Entity)room.entities.get(i))) )
          break;
      }
      
    }
    
    blackScreenAnimation = new BufferedImage[18];
    
    try {
      for( i=1; i<19; i++ )
        blackScreenAnimation[i-1] = resize( colorImageSat(ImageIO.read( new File( "pepsi\\black.png" ) ), (int) ((float)i*255/18)), 1920, 1080 );
    } catch (IOException ex) {
      System.out.println("errore nell'apertura di blackScreenAnimation");
    }
    
    
    
  }
  public Scarfy()
  {
    if( c == null )
      setSize(1920, 1080);
    getContentPane().setBackground(Color.darkGray);
		setResizable(false);
		setTitle("Scarfy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
    setFocusable(true);
    setLayout(null);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(true);
    setVisible(true);
    setFocusTraversalKeysEnabled(false);
    
    setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
    new ImageIcon("blank.png").getImage(),
    new Point(0,0),"blank"));
    
    start();
    
    new java.util.Timer().schedule(  //timer che chiama una funzione ogni periodo di tempo ( solo se ha già finito l'esecuzione precedente però)
    new java.util.TimerTask() {
      public void run() {
        draw();
      }
    },50,1000/60);
    
    new java.util.Timer().schedule(  //ogni 5 secondi chiama il garbage collector
    new java.util.TimerTask() {
      public void run() {
        System.gc();
      }
    },5000,5000);
  }
  
  
  //--------------------------------------------------------------------------------------
  
  
  
  public void draw()
  {
    bs = getBufferStrategy(); //serve a bufferizzare i frame in uscita e a crearli
		if(bs == null) {
			createBufferStrategy(3);
      bs = getBufferStrategy();
		}
		g = bs.getDrawGraphics();
    
    if( isShacking )
      vibrate(); //che vibes
    
    int n = checkPlayerOutOfScreen(player); //controlla se il player è uscito dalle porte
    
    if( n != -1 ){ //è -1 se il player non è fuori dalla stanza            //cambio della stanza
      
      Projectile.clear();
      mapManager.changeRoom(n);
      room = mapManager.getCurrentRoom();
      
      if( room.areDoorOpen )
        room.closeAllDoor();
      
      player.enemies = room.enemies;
      player.entities = room.entities;
      
      switch(n){
        case 0:
          player.y = 1080-100-player.height;
        break;
        case 1:
          player.x = 90;
        break;
        case 2:
          player.y = 230;
        break;
        case 3:
          player.x = 1920-90-player.width;
        break;
      }
      
    }
    
    if( !room.areDoorOpen )
      if( room.enemies.isEmpty() )
        if( !room.isBossRoom )
          room.openAllDoor();
        else
          isEnding = 1;
    if( player.hp <= 0 )
      isEnding = 2;
    
    
    room.draw(g);
    //------------------------------------------update
    
    
    player.update( g );
    room.update(player);
    Projectile.updateAll(player);
    
    //------------------------------------------draw
    
    
    Particle.drawAll(g);
    room.drawEnemies(g);
    
    Projectile.drawAll(g);
    
    player.draw( g );
    hud.draw(g);
    
    if( isEnding != 0 )
      endAnimation();
    
    
    //g.setColor(Color.red);
    //g.drawString(mapManager.getCurrentLeaf().p.toString(), 200, 100);
    //g.drawString(mapManager.getNumber()+"", 200, 150);
    //g.drawString("x: "+in.mouseX()+"y: "+in.mouseY(), 300, 300);
    
    //------------------------------------------
		bs.show();
    
  }
  
  //--------------------------------------------------------------------------------------
  
  
  
  
  
  
  public static void main(String[] args) {
    
    Scarfy s = new Scarfy();
    
  }
  
  public void endAnimation(){
    
    endCounter++;
    
    if( endCounter < 180 )
      g.drawImage( blackScreenAnimation[Math.floorDiv( endCounter, 10 )], 0, 0, null);
    
    if( endCounter >= 180 ){
      g.drawImage( blackScreenAnimation[17], 0, 0, null);
      
      
      g.setFont( new Font("Monospace", Font.PLAIN, 35 ) );
      g.setColor(Color.WHITE);
      if( endCounter < 180+27*6 ){
        if( isEnding == 1 )
          g.drawString( "Hai vinto", 300, 1080/2-100 );
        g.drawString( endPhrase.substring(0, Math.floorDiv( endCounter-180, 6) ), 300, 1080/2 );
      }else{
        if( isEnding == 1 )
          g.drawString( "Hai vinto", 300, 1080/2-100 );
        g.drawString( endPhrase.substring(0, 25+Math.floorDiv( endCounter-180-27*6, 40) ), 300, 1080/2 );
        if( in.isEnterPressed == true ){
          this.start();
          in.isEnterPressed = false;
        }
      }
    }
    
    if( endCounter >= 180+27*6+120-1 )
      endCounter = 180+27*6;
  }
  
  
  public void vibrate(){
        
    if( countShake%10 == 0 )
    {
      ((Graphics2D)g).translate( 0, VIBRATION_VELOCITY );
    }else if( countShake%5 == 0 ){
      ((Graphics2D)g).translate( 0, -VIBRATION_VELOCITY );
    }

    if( countShake%28 == 0 )
    {
      ((Graphics2D)g).translate( VIBRATION_VELOCITY/3*2, 0 );
    }else if( countShake%14 == 0 ){
      ((Graphics2D)g).translate( -VIBRATION_VELOCITY/3*2, 0 );
    }
    
  }
  
  public void vibrate(int VIBRATION_LENGTH, int VIBRATION_VELOCITY, int countShake) { 
    
    isShacking = true;
    if( countShake == VIBRATION_LENGTH){
      isShacking = false;
    }
    this.VIBRATION_LENGTH = VIBRATION_LENGTH;
    this.VIBRATION_VELOCITY = VIBRATION_VELOCITY;
    this.countShake = countShake;
    

  }
  
  public int checkPlayerOutOfScreen(Entity e){
    if( e.x+e.width >= 1920 )
      return 1;
    if( e.x <= 0)
      return 3;
    if( e.y+e.height >= 1080 )
      return 2;
    if( e.y <= 0)
      return 0;
    
    return -1;
      
  }
  
  public static BufferedImage colorImageSat(BufferedImage img, int s) {
    BufferedImage image = deepCopy(img);
    int width = image.getWidth();
    int height = image.getHeight();
    WritableRaster raster = image.getRaster();

    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        int[] pixels = raster.getPixel(xx, yy, (int[]) null);
        if( pixels[3] > 10 )
          pixels[3] = s;
        
        raster.setPixel(xx, yy, pixels);
      }
    }
    return image;
  }
    
  
  public static BufferedImage colorImage(BufferedImage img) {
    BufferedImage image = deepCopy(img);
    int width = image.getWidth();
    int height = image.getHeight();
    WritableRaster raster = image.getRaster();
    
    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        int[] pixels = raster.getPixel(xx, yy, (int[]) null);
        
        pixels[0] = 200;
        
        while(pixels[1] > 64 )
        {
          pixels[1] /= 2;
          pixels[2] /= 2;
        }
        raster.setPixel(xx, yy, pixels);
      }
    }
    return image;
  }
  
  public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics g = dimg.createGraphics();
    g.drawImage(tmp, 0, 0, null);
    g.dispose();
    
    return dimg;
  }  
  
  static BufferedImage deepCopy(BufferedImage bi) {
    
    ColorModel cm = bi.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = bi.copyData(null);
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    
  }
  
}