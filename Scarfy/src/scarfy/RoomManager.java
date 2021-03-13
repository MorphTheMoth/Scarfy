package scarfy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;

public class RoomManager {
  
  private ArrayList blocks;
  private ArrayList entities;
  private ArrayList enemies;
  private Scarfy scarfy;
  
  
  public RoomManager(Scarfy scarfy){
    this.scarfy = scarfy;
    blocks = new ArrayList();
    entities = new ArrayList();
    enemies = new ArrayList();
    
    for( int i=0; i < 10; i++ )
      try {
        blocks.add( resize( ImageIO.read( new File( "pepsi\\map1\\"+i+".png" ) ), 110, 110 ) );
      } catch (IOException ex) {
        try {
          blocks.add( resize( ImageIO.read( new File( "pepsi\\map1\\0.png" ) ), 110, 110 ) );
        } catch (IOException ex1) {}
      }
    for( int i=0; i < 26; i++ )
      try {
        blocks.add( resize( ImageIO.read( new File( "pepsi\\map1\\"+(char)(97+i)+".png" ) ), 110, 110 ) );
      } catch (IOException ex) {
        try {
          blocks.add( resize( ImageIO.read( new File( "pepsi\\map1\\0.png" ) ), 110, 110 ) );
        } catch (IOException ex1) {}
      }
  }

  public Room setupBossRoom( String type ){
    entities = new ArrayList();
    enemies = new ArrayList();
    
    int[][] map = null;
    
    switch(type){
      case "1000":
      {
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,13,15,5,5,5,5,5,5,5,2},{2,3,3,3,3,3,3,3,12,31,3,3,3,3,3,3,3,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,0,0,0,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,22,0,0,2},{2,0,0,22,22,0,0,22,22,22,22,22,22,22,0,0,0,2},{2,0,0,0,22,22,22,22,0,22,22,22,22,22,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(80, 990, 1759, 89 ) );
        entities.add( new Entity(1840, 219, 79, 860 ) );
        entities.add( new Entity(1015, 0, 904, 219 ) );
        entities.add( new Entity(80, 0, 824, 220 ) );
        entities.add( new Entity(0, 0, 80, 1075 ) );
        map = roomMap;
      }break;
      case "0100":
      {
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2},{2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,0,0,0,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,22,0,0,5},{2,0,0,22,22,0,0,22,22,22,22,22,22,22,0,0,0,3},{2,0,0,0,22,22,22,22,0,22,22,22,22,22,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1840, 660, 79, 330 ) );
        entities.add( new Entity(1840, 219, 79, 331 ) );
        entities.add( new Entity(80, 990, 1839, 89 ) );
        entities.add( new Entity(0, 219, 80, 860 ) );
        entities.add( new Entity(0, 0, 1919, 219 ) );
        map = roomMap;
      }break;
      case "0010":
      {
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2},{2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,0,0,0,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,0,0,0,2},{2,0,0,22,22,0,0,22,22,22,22,22,22,0,0,0,0,2},{2,0,0,0,0,0,22,22,0,22,22,22,22,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(78, 989, 826, 90 ) );
        entities.add( new Entity(1015, 990, 825, 89 ) );
        entities.add( new Entity(1840, 219, 79, 860 ) );
        entities.add( new Entity(0, 219, 79, 860 ) );
        entities.add( new Entity(0, 0, 1919, 219 ) );
        map = roomMap;
      }break;
      case "0001":
      {
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2},{2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2},{2,0,0,22,22,0,0,0,0,0,22,22,22,22,0,0,0,2},{5,0,0,22,22,0,0,0,0,0,22,22,22,22,0,0,0,2},{3,0,0,22,22,0,0,22,22,22,22,22,22,22,0,0,0,2},{2,0,0,0,0,0,22,22,0,22,22,22,22,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(79, 990, 1762, 89 ) );
        entities.add( new Entity(1840, 219, 79, 860 ) );
        entities.add( new Entity(0, 659, 79, 420 ) );
        entities.add( new Entity(0, 219, 80, 331 ) );
        entities.add( new Entity(0, 0, 1919, 219 ) );
        map = roomMap;
      }
      
    }
    
    enemies.add( new NightmareEcho(scarfy, entities, enemies) );
    
    return new Room( type, blocks, map, entities, enemies, true );
  }
  
  public Room setupRoom( String type ){
    entities = new ArrayList();
    enemies = new ArrayList();
    
    int[][] map = null;
    
    //potrei aggiungere qui uno switch per i vari tipi di mappa
    
    
    
    switch(type){
      case "0001":
      {
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2},{2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2},{2,0,0,22,22,0,1,0,0,0,22,2,2,2,2,2,2,2},{5,0,0,22,22,0,0,0,0,0,22,5,5,5,5,5,5,2},{3,0,0,22,22,0,0,1,22,22,22,3,3,3,3,3,3,2},{2,0,1,0,22,22,22,22,0,22,22,2,2,2,2,2,2,2},{2,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,2},{2,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(79, 989, 1761, 90 ) );         
        entities.add( new Entity(0, 660, 79, 419 ) );
        entities.add( new Entity(1180, 329, 660, 219 ) );
        entities.add( new Entity(1180, 660, 660, 218 ) );
        entities.add( new Entity(1840, 218, 79, 861 ) );
        entities.add( new Entity(0, 218, 79, 330 ) );
        entities.add( new Entity(0, 0, 1919, 218 ) );
        enemies.add( new Golem( 853, 492, 170, 170, 40, 1, 1, entities, enemies ));
        enemies.add( new Slime( 571, 805, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 1723, 850, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1702, 173, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1705, 504, 96, 120, 10, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "0010":
      { 
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,2,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2,2},{2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2},{2,2,2,2,2,0,0,0,0,0,22,22,22,2,2,2,2,2},{2,2,5,5,5,0,0,0,0,17,17,22,0,5,5,5,2,2},{2,5,3,3,3,0,0,2,22,17,2,22,22,3,3,3,5,2},{2,3,1,0,22,19,2,2,0,17,2,2,22,22,0,0,3,2},{2,0,0,0,0,2,2,2,14,27,2,2,2,0,0,0,0,2},{2,0,0,0,0,2,2,2,14,27,2,2,2,0,0,0,0,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1840, 652, 79, 358 ) );
        entities.add( new Entity(1399, 990, 520, 89 ) );          
        entities.add( new Entity(1731, 549, 188, 108 ) );
        entities.add( new Entity(1730, 218, 189, 112 ) );
        entities.add( new Entity(1400, 330, 519, 219 ) );
        entities.add( new Entity(1290, 770, 110, 309 ) );
        entities.add( new Entity(1179, 660, 111, 419 ) );
        entities.add( new Entity(1070, 550, 109, 528 ) );
        entities.add( new Entity(630, 659, 111, 334 ) );
        entities.add( new Entity(520, 770, 110, 223 ) );
        entities.add( new Entity(739, 549, 110, 443 ) );
        entities.add( new Entity(80, 990, 768, 89 ) );
        entities.add( new Entity(191, 330, 328, 219 ) );
        entities.add( new Entity(81, 219, 109, 439 ) );
        entities.add( new Entity(0, 217, 80, 862 ) );
        entities.add( new Entity(0, 0, 1919, 218 ) );
        entities.add( new Entity(1016, 770, 53, 308 ) );
        entities.add( new Entity(850, 769, 54, 310 ) );
        enemies.add( new Wizard( 1661, 809, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 207, 805, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Slime( 1514, 635, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 1609, 176, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 238, 194, 96, 120, 10, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "0011":{
        
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,5,5,2,2,2,2,2,2,2,2},{2,3,3,3,3,3,3,3,3,3,5,5,5,5,5,2,2,2},{2,0,0,22,0,0,0,0,0,0,3,3,3,3,3,5,5,2},{5,0,0,22,2,2,2,0,0,0,1,0,0,0,0,3,3,2},{3,0,0,22,5,5,5,0,0,0,0,0,0,0,0,0,0,2},{2,0,1,0,3,3,3,0,0,0,0,22,0,0,0,0,0,2},{2,0,0,0,0,0,0,1,0,0,0,0,2,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1840, 1079, 0, 0 ) );          
        entities.add( new Entity(1016, 990, 824, 89 ) );
        entities.add( new Entity(850, 990, 54, 89 ) );
        entities.add( new Entity(76, 990, 773, 89 ) );
        entities.add( new Entity(1290, 769, 110, 310 ) );
        entities.add( new Entity(1840, 436, 79, 643 ) );
        entities.add( new Entity(1620, 325, 299, 112 ) );
        entities.add( new Entity(1070, 219, 849, 110 ) );
        entities.add( new Entity(0, 659, 80, 420 ) );
        entities.add( new Entity(410, 441, 329, 217 ) );
        entities.add( new Entity(0, 197, 80, 353 ) );
        entities.add( new Entity(0, 0, 1919, 218 ) );
        enemies.add( new Wizard( 150, 307, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1348, 404, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Slime( 157, 766, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 506, 297, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 765, 787, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Golem( 1476, 619, 170, 170, 40, 1.5f, 1, entities, enemies ));
        map = roomMap;

      }break;
      case "0100":{
        
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,2,2,2,2,5,5,5,2,2,2,5,5,5,5,5,5,2},{2,2,2,5,5,3,3,3,2,2,5,3,3,3,3,3,3,2},{2,2,5,3,3,0,0,2,2,2,3,0,22,0,0,0,0,2},{2,5,3,22,22,0,2,2,2,5,0,0,0,0,0,0,0,5},{2,3,0,0,22,0,2,5,5,3,0,0,2,2,2,0,0,3},{2,0,0,0,22,0,5,3,3,0,0,2,2,2,5,0,0,2},{2,0,0,0,0,0,3,0,0,0,2,2,2,5,3,0,0,2},{2,0,0,0,0,0,0,0,0,2,2,2,2,3,0,0,0,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1290, 550, 330, 108 ) );         
        entities.add( new Entity(1180, 659, 440, 110 ) );
        entities.add( new Entity(1069, 770, 440, 110 ) );
        entities.add( new Entity(960, 880, 439, 108 ) );
        entities.add( new Entity(79, 989, 1760, 90 ) );
        entities.add( new Entity(1840, 660, 79, 419 ) );
        entities.add( new Entity(1841, 216, 78, 333 ) );
        entities.add( new Entity(849, 218, 111, 112 ) );
        entities.add( new Entity(1068, 214, 112, 114 ) );
        entities.add( new Entity(959, 217, 110, 333 ) );
        entities.add( new Entity(740, 329, 218, 330 ) );
        entities.add( new Entity(630, 440, 109, 328 ) );
        entities.add( new Entity(300, 220, 220, 109 ) );
        entities.add( new Entity(191, 221, 109, 218 ) );
        entities.add( new Entity(80, 219, 110, 329 ) );
        entities.add( new Entity(0, 219, 79, 860 ) );
        entities.add( new Entity(0, 0, 1919, 220 ) );
        enemies.add( new Slime( 1170, 410, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 499, 830, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 252, 549, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 586, 268, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 819, 725, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1643, 828, 96, 120, 10, 1, entities, enemies ));
        map = roomMap;
        
      }break;
      
      case "0101":
      {
        
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,5,5,5,5,2,2,2,2,2,2,2,2,5,5,5,5,2},{2,3,3,3,3,5,5,5,2,2,5,5,5,3,3,3,3,2},{2,0,0,22,22,3,3,3,5,5,3,3,3,22,0,0,0,2},{5,0,0,0,22,0,0,0,3,3,22,22,22,22,22,1,0,5},{3,0,0,22,2,2,0,22,22,22,22,22,22,22,0,0,0,3},{2,2,2,2,2,5,0,22,0,22,22,22,22,22,0,0,2,2},{2,5,5,5,5,3,0,0,0,0,0,0,2,2,2,2,2,2},{2,3,3,3,3,0,0,2,2,2,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(74, 989, 681, 90 ) );      
        entities.add( new Entity(74, 659, 445, 219 ) );
        entities.add( new Entity(410, 549, 220, 220 ) );
        entities.add( new Entity(0, 659, 78, 420 ) );
        entities.add( new Entity(740, 879, 558, 200 ) );
        entities.add( new Entity(1290, 769, 442, 310 ) );
        entities.add( new Entity(1729, 660, 190, 419 ) );
        entities.add( new Entity(1839, 204, 80, 346 ) );
        entities.add( new Entity(850, 329, 220, 110 ) );
        entities.add( new Entity(520, 219, 879, 109 ) );
        entities.add( new Entity(79, 0, 1840, 218 ) );
        entities.add( new Entity(0, 0, 79, 550 ) );
        enemies.add( new Wizard( 1021, 699, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 681, 430, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Slime( 270, 356, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1385, 480, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 895, 547, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 494, 837, 96, 120, 10, 1, entities, enemies ));
        map = roomMap;
        
      }
      break;
      case "0110":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,2,5,5,5,2,2,2,2,2,2,2,5,5,5,2,2,2},{2,5,3,3,3,5,5,5,5,5,5,5,3,3,3,5,2,2},{2,3,0,0,0,3,3,3,3,3,3,3,0,22,0,3,5,2},{2,2,0,22,22,0,0,0,0,0,0,0,0,0,0,0,3,5},{2,2,2,22,22,0,0,2,2,2,2,2,2,2,0,0,0,3},{2,5,5,0,22,0,0,5,5,5,5,5,5,5,0,0,0,2},{2,3,3,0,0,0,0,3,3,3,3,3,3,3,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(520, 208, 770, 120 ) );      
        entities.add( new Entity(1620, 209, 111, 119 ) );
        entities.add( new Entity(1731, 213, 108, 226 ) );
        entities.add( new Entity(1839, 214, 80, 334 ) );
        entities.add( new Entity(739, 550, 770, 220 ) );
        entities.add( new Entity(1840, 659, 79, 231 ) );
        entities.add( new Entity(1730, 880, 189, 118 ) );
        entities.add( new Entity(1016, 991, 903, 88 ) );
        entities.add( new Entity(78, 990, 826, 89 ) );
        entities.add( new Entity(79, 440, 111, 112 ) );
        entities.add( new Entity(75, 550, 225, 218 ) );
        entities.add( new Entity(78, 217, 112, 112 ) );
        entities.add( new Entity(0, 216, 79, 863 ) );
        entities.add( new Entity(0, 0, 1919, 218 ) );
        enemies.add( new Slime( 1475, 843, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 959, 403, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 655, 388, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1244, 413, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Golem( 1432, 284, 170, 170, 40, 1, 1, entities, enemies ));
        enemies.add( new Golem( 468, 727, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "0111":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},{2,2,5,5,5,5,2,2,2,2,2,2,5,5,5,5,2,2},{2,5,3,3,3,3,2,2,2,2,2,2,3,3,3,3,5,2},{2,3,0,22,22,0,2,2,2,2,2,2,0,0,0,0,3,2},{5,0,0,22,22,0,5,5,2,2,5,5,22,22,22,1,0,5},{3,0,0,22,22,0,3,3,2,2,3,3,22,22,0,0,0,3},{2,0,1,0,22,22,0,0,5,5,0,0,0,22,0,0,0,2},{2,2,2,2,0,0,0,0,3,3,0,0,0,0,2,2,2,2},{2,2,2,2,0,0,0,0,0,0,0,0,1,0,2,2,2,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(850, 548, 219, 221 ) );        
        entities.add( new Entity(630, 210, 659, 339 ) );
        entities.add( new Entity(1015, 990, 498, 89 ) );
        entities.add( new Entity(1730, 217, 111, 111 ) );
        entities.add( new Entity(1840, 218, 79, 331 ) );
        entities.add( new Entity(1841, 659, 78, 420 ) );
        entities.add( new Entity(1510, 770, 329, 309 ) );
        entities.add( new Entity(407, 989, 498, 90 ) );
        entities.add( new Entity(80, 769, 330, 307 ) );
        entities.add( new Entity(0, 659, 80, 420 ) );
        entities.add( new Entity(0, 330, 79, 219 ) );
        entities.add( new Entity(0, 218, 190, 112 ) );
        entities.add( new Entity(0, 0, 1919, 219 ) );
        enemies.add( new Slime( 262, 379, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 455, 534, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1600, 328, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1428, 525, 55, 55, 20, 4, 1, entities, enemies ));
        if( (int)Math.floor( Math.random()*4324%2 ) == 0 )
          enemies.add( new Golem( 1302, 781, 170, 170, 40, 1, 1, entities, enemies ));
        else
          enemies.add( new Golem( 461, 782, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1000":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,5,5,13,15,5,5,2,2,2,2,2,2},{2,2,2,2,2,2,3,3,12,31,3,3,5,2,2,2,2,2},{2,2,2,2,2,2,0,0,0,0,22,22,3,5,5,5,2,2},{2,2,2,2,2,5,0,0,0,2,2,2,0,3,3,3,5,2},{2,5,5,5,5,3,0,22,22,2,2,5,0,22,0,0,3,2},{2,3,3,3,3,22,22,22,0,5,5,3,0,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,2,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(960, 660, 219, 110 ) );            
        entities.add( new Entity(960, 439, 329, 221 ) );
        entities.add( new Entity(80, 549, 440, 111 ) );
        entities.add( new Entity(79, 218, 550, 332 ) );
        entities.add( new Entity(0, 220, 80, 769 ) );
        entities.add( new Entity(0, 990, 1729, 89 ) );
        entities.add( new Entity(1729, 770, 111, 309 ) );
        entities.add( new Entity(1840, 550, 79, 529 ) );
        entities.add( new Entity(1729, 441, 190, 109 ) );
        entities.add( new Entity(1400, 331, 519, 109 ) );
        entities.add( new Entity(1290, 221, 629, 109 ) );
        entities.add( new Entity(1015, 0, 904, 220 ) );
        entities.add( new Entity(0, 0, 905, 219 ) );
        enemies.add( new Slime( 1427, 831, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 722, 666, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1505, 590, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 984, 841, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 1721, 610, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Golem( 192, 735, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1001":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,2,5,5,5,5,5,5,13,15,5,2,2,2,2,2,2,2},{2,5,3,3,3,3,3,3,12,31,3,5,5,5,5,2,2,2},{2,3,0,2,2,0,0,0,1,0,0,3,3,3,3,5,2,2},{5,0,0,5,5,0,0,0,0,0,22,0,0,22,22,3,5,2},{3,0,0,3,3,0,2,2,0,0,2,2,2,2,0,0,3,2},{2,0,1,0,0,0,5,5,0,0,5,2,2,5,0,0,0,2},{2,0,0,0,0,0,3,3,0,0,3,5,5,3,1,0,0,2},{2,2,2,2,0,0,0,0,0,0,0,3,3,0,0,0,2,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(408, 989, 1322, 3 ) );                 
        entities.add( new Entity(1180, 770, 219, 109 ) );
        entities.add( new Entity(1070, 550, 440, 220 ) );
        entities.add( new Entity(629, 549, 220, 220 ) );
        entities.add( new Entity(300, 329, 220, 220 ) );
        entities.add( new Entity(1840, 549, 79, 330 ) );
        entities.add( new Entity(1729, 440, 190, 109 ) );
        entities.add( new Entity(1619, 330, 300, 109 ) );
        entities.add( new Entity(1180, 220, 739, 110 ) );
        entities.add( new Entity(1730, 880, 189, 110 ) );
        entities.add( new Entity(408, 991, 1511, 88 ) );
        entities.add( new Entity(79, 880, 330, 199 ) );
        entities.add( new Entity(0, 660, 79, 419 ) );
        entities.add( new Entity(0, 331, 79, 218 ) );
        entities.add( new Entity(0, 219, 190, 111 ) );
        entities.add( new Entity(1015, 0, 904, 219 ) );
        entities.add( new Entity(0, 0, 904, 220 ) );
        enemies.add( new Slime( 505, 880, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1464, 847, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Golem( 1613, 642, 170, 170, 40, 1, 1, entities, enemies ));
        enemies.add( new Wizard( 927, 825, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1341, 392, 96, 120, 10, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1010":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,13,15,5,5,5,5,5,5,5,2},{2,3,3,3,3,3,3,3,12,31,3,3,3,3,3,3,3,2},{2,0,0,22,22,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,2,2,2,2,2,2,2,0,0,2,2,0,0,0,0,0,2},{2,5,5,5,5,5,2,2,0,0,2,5,0,0,0,0,0,2},{2,3,3,3,3,3,5,5,0,0,5,3,0,0,0,0,0,2},{2,0,0,0,0,0,3,3,0,0,3,0,0,0,0,0,0,2},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1839, 220, 80, 771 ) );            
        entities.add( new Entity(1015, 990, 904, 89 ) );
        entities.add( new Entity(79, 990, 825, 89 ) );
        entities.add( new Entity(1070, 659, 109, 110 ) );
        entities.add( new Entity(1070, 440, 220, 219 ) );
        entities.add( new Entity(629, 661, 220, 109 ) );
        entities.add( new Entity(79, 440, 771, 220 ) );
        entities.add( new Entity(0, 218, 79, 861 ) );
        entities.add( new Entity(1015, 0, 904, 220 ) );
        entities.add( new Entity(0, 0, 904, 218 ) );
        enemies.add( new Slime( 1720, 544, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1277, 292, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1243, 826, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 1696, 810, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1729, 277, 96, 120, 10, 1, entities, enemies ));
        if( (int)Math.floor( Math.random()*4324%2 ) == 0 )
          enemies.add( new Golem( 200, 241, 170, 170, 40, 1, 1, entities, enemies ));
        else
          enemies.add( new Golem( 215, 795, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1011":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,5,5,5,5,5,5,5,13,15,5,5,2,2,2,2,2,2},{2,3,3,3,3,3,3,3,12,31,3,3,2,2,2,2,2,2},{2,0,0,22,0,0,0,0,0,0,2,2,2,2,2,2,2,2},{5,0,0,22,22,0,2,2,2,2,2,2,2,2,2,2,2,2},{3,0,0,22,22,0,5,5,5,2,2,2,2,2,2,2,2,2},{2,0,1,0,22,0,3,3,3,5,5,2,2,2,2,2,2,2},{2,0,0,0,0,0,0,1,0,3,3,5,5,2,2,2,2,2},{2,0,0,0,0,0,0,0,0,0,0,3,3,2,2,2,2,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(0, 219, 80, 330 ) );           
        entities.add( new Entity(0, 0, 905, 219 ) );
        entities.add( new Entity(79, 990, 825, 89 ) );
        entities.add( new Entity(0, 660, 79, 419 ) );
        entities.add( new Entity(1069, 330, 221, 110 ) );
        entities.add( new Entity(1179, 771, 111, 108 ) );
        entities.add( new Entity(960, 660, 329, 110 ) );
        entities.add( new Entity(629, 440, 661, 220 ) );
        entities.add( new Entity(1015, 991, 385, 88 ) );
        entities.add( new Entity(1400, 878, 519, 201 ) );
        entities.add( new Entity(1290, 219, 629, 660 ) );
        entities.add( new Entity(1014, 0, 905, 220 ) );
        enemies.add( new Slime( 533, 670, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 239, 784, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 452, 352, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1170, 196, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 108, 189, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1268, 855, 96, 120, 10, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1100":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,2,5,5,5,5,5,5,13,15,2,2,2,5,5,5,5,2},{2,5,3,3,3,3,3,3,12,31,2,2,2,3,3,3,3,2},{2,3,0,22,22,0,0,0,0,0,2,2,2,22,0,0,0,2},{2,0,0,22,2,2,2,2,2,2,2,2,2,22,22,1,0,5},{2,0,0,22,5,5,2,5,5,5,5,2,5,22,0,0,0,3},{2,0,1,0,3,3,5,3,3,3,3,5,3,22,0,0,0,2},{2,0,0,0,0,0,3,0,0,0,0,3,0,1,0,0,2,2},{2,2,2,2,0,0,0,0,2,2,0,0,0,0,0,2,2,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1179, 660, 110, 108 ) );           
        entities.add( new Entity(630, 659, 109, 111 ) );
        entities.add( new Entity(1620, 879, 110, 110 ) );
        entities.add( new Entity(1730, 770, 110, 219 ) );
        entities.add( new Entity(1840, 660, 79, 330 ) );
        entities.add( new Entity(1839, 217, 80, 333 ) );
        entities.add( new Entity(1070, 219, 329, 219 ) );
        entities.add( new Entity(410, 439, 989, 221 ) );
        entities.add( new Entity(1015, 0, 904, 220 ) );
        entities.add( new Entity(850, 880, 220, 110 ) );
        entities.add( new Entity(408, 990, 1511, 89 ) );
        entities.add( new Entity(79, 880, 330, 199 ) );
        entities.add( new Entity(79, 219, 110, 111 ) );
        entities.add( new Entity(0, 219, 79, 860 ) );
        entities.add( new Entity(0, 0, 905, 219 ) );
        enemies.add( new Slime( 317, 287, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1422, 854, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 452, 318, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 170, 646, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1668, 244, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Golem( 874, 678, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1101":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,5,5,2,2,2,2,2,13,15,5,5,5,5,5,5,5,2},{2,3,3,5,2,2,2,2,12,31,3,3,3,3,3,3,3,2},{2,0,0,3,2,2,2,5,0,0,22,0,22,22,1,0,0,2},{5,0,1,22,2,2,2,3,0,0,22,2,2,22,0,0,0,5},{3,0,0,22,2,2,2,22,1,22,22,5,2,2,2,0,0,3},{2,0,0,0,5,2,2,22,0,22,22,3,5,2,2,2,2,2},{2,0,0,0,3,5,5,0,0,0,0,0,3,5,5,2,2,2},{2,0,0,0,0,3,3,0,0,0,0,0,0,3,3,2,2,2},{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1400, 768, 219, 112 ) );       
        entities.add( new Entity(1619, 660, 300, 333 ) );
        entities.add( new Entity(1290, 550, 329, 220 ) );
        entities.add( new Entity(1180, 440, 219, 220 ) );
        entities.add( new Entity(1840, 220, 79, 330 ) );
        entities.add( new Entity(1015, 0, 904, 219 ) );
        entities.add( new Entity(79, 989, 1840, 90 ) );
        entities.add( new Entity(0, 660, 79, 419 ) );
        entities.add( new Entity(0, 220, 80, 329 ) );
        entities.add( new Entity(520, 769, 219, 110 ) );
        entities.add( new Entity(410, 438, 329, 332 ) );
        entities.add( new Entity(299, 219, 111, 111 ) );
        entities.add( new Entity(410, 220, 440, 219 ) );
        entities.add( new Entity(0, 0, 905, 220 ) );
        enemies.add( new Slime( 609, 886, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1345, 316, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 957, 512, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 155, 225, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 1462, 854, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Golem( 1588, 254, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1110":{
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,5,5,13,15,5,5,5,5,5,2,2,2},{2,2,2,2,2,2,3,3,12,31,3,3,3,3,3,5,5,2},{2,2,2,2,2,2,2,0,0,0,1,22,22,0,0,3,3,2},{2,2,2,2,2,2,2,2,0,0,22,22,22,22,22,1,0,5},{2,2,2,5,5,5,5,5,22,22,22,2,2,22,0,0,0,3},{2,5,5,3,3,3,3,3,0,22,22,5,2,2,2,0,0,2},{2,3,3,0,0,1,0,0,0,0,0,3,2,2,2,2,2,2},{2,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1620, 769, 220, 220 ) );         
        entities.add( new Entity(1290, 661, 330, 328 ) );
        entities.add( new Entity(1180, 550, 220, 219 ) );
        entities.add( new Entity(1840, 659, 79, 330 ) );
        entities.add( new Entity(1015, 989, 904, 90 ) );
        entities.add( new Entity(1840, 329, 79, 221 ) );
        entities.add( new Entity(1620, 219, 299, 111 ) );
        entities.add( new Entity(1015, 0, 904, 219 ) );
        entities.add( new Entity(78, 990, 827, 89 ) );
        entities.add( new Entity(0, 770, 79, 309 ) );
        entities.add( new Entity(0, 661, 299, 109 ) );
        entities.add( new Entity(630, 330, 109, 110 ) );
        entities.add( new Entity(0, 219, 630, 221 ) );
        entities.add( new Entity(0, 440, 849, 220 ) );
        entities.add( new Entity(0, 0, 904, 219 ) );
        enemies.add( new Slime( 356, 643, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1498, 511, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Wizard( 1193, 830, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 648, 166, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Golem( 143, 765, 170, 170, 40, 1, 1, entities, enemies ));
        map = roomMap;
      }break;
      case "1111":{
      
        int[][] roomMap = { {2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,13,15,5,5,5,5,2,2,2,2},{2,2,2,2,2,2,2,5,12,31,3,3,3,3,5,5,5,2},{2,2,2,5,5,5,5,3,0,0,22,22,22,22,3,3,3,2},{5,5,5,3,3,3,3,0,0,0,22,22,22,22,22,1,0,5},{3,3,3,22,22,1,0,22,0,22,22,22,22,22,0,0,0,3},{2,0,1,0,22,22,22,22,0,22,22,22,22,22,0,17,17,2},{2,2,2,0,0,0,0,0,0,0,0,0,17,17,17,2,2,2},{2,2,2,2,2,2,0,0,0,0,17,17,2,2,2,2,2,2},{2,2,2,2,2,2,2,2,14,27,2,2,2,2,2,2,2,2}};
        entities.add( new Entity(1290, 879, 330, 109 ) );         
        entities.add( new Entity(1620, 769, 220, 220 ) );
        entities.add( new Entity(1840, 659, 79, 330 ) );
        entities.add( new Entity(1015, 989, 904, 90 ) );
        entities.add( new Entity(630, 989, 274, 90 ) );
        entities.add( new Entity(299, 880, 331, 199 ) );
        entities.add( new Entity(78, 769, 222, 310 ) );
        entities.add( new Entity(0, 660, 79, 419 ) );
        entities.add( new Entity(1840, 331, 79, 218 ) );
        entities.add( new Entity(1510, 219, 409, 111 ) );
        entities.add( new Entity(1015, 0, 904, 219 ) );
        entities.add( new Entity(0, 442, 299, 107 ) );
        entities.add( new Entity(738, 219, 112, 111 ) );
        entities.add( new Entity(738, 0, 167, 219 ) );
        entities.add( new Entity(0, 0, 739, 441 ) );
        enemies.add( new Wizard( 1077, 512, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Wizard( 888, 555, 96, 120, 10, 1, entities, enemies ));
        enemies.add( new Slime( 1025, 682, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 924, 374, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 1264, 524, 55, 55, 20, 4, 1, entities, enemies ));
        enemies.add( new Slime( 679, 595, 55, 55, 20, 4, 1, entities, enemies ));
        map = roomMap;
        
      }break;
      
    }
    
    return new Room( type, blocks, map, entities, enemies );
    
  }
  
  
}
