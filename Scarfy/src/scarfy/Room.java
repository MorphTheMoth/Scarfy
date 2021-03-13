package scarfy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static scarfy.Scarfy.PRINT_HITBOX;

public class Room/*ba*/ {
  
  ArrayList blocks;
  int[][] roomMap;
  ArrayList entities;
  ArrayList enemies;
  String type;
  public boolean areDoorOpen = true;
  public boolean isBossRoom = false;
  
  public Room( String type, ArrayList b, int[][] roomMap, ArrayList entities, ArrayList enemies  ){
    this.type = type;
    this.roomMap = roomMap;
    blocks = b;
    this.entities = entities;
    this.enemies = enemies;
  }
  public Room( String type, ArrayList b, int[][] roomMap, ArrayList entities, ArrayList enemies, boolean isBossRoom ){
    this.type = type;
    this.roomMap = roomMap;
    blocks = b;
    this.entities = entities;
    this.enemies = enemies;
    this.isBossRoom = isBossRoom;
  }
    
  public void openAllDoor(){
    if( roomMap == null )
      return;
    
    areDoorOpen = true;
    
    if( type.charAt(0) == '1' ){  //l'ordine dell'orientamento generale delle cose è invertito non ho la minima idea del perchè e non me lo chiederò ma così funziona
      entities.remove(entities.size()-1);
      for( int i=0; i<3; i++ ){
        if( i==0 ){
          roomMap[i][8] = 14;
          roomMap[i][9] = 27;
        }
        if( i==1 ){
          roomMap[i][8] = 13;
          roomMap[i][9] = 15;
        }
        if( i==2 ){
          roomMap[i][8] = 12;
          roomMap[i][9] = 31;
        }
      }
    }
    if( type.charAt(1) == '1' ){
      entities.remove(entities.size()-1);
      roomMap[4][17] = 5;
      roomMap[5][17] = 3;
    }
    if( type.charAt(2) == '1' ){
      entities.remove(entities.size()-1);
      roomMap[9][8] = 14;
      roomMap[9][9] = 27;
    }
    if( type.charAt(3) == '1' ){
      entities.remove(entities.size()-1);
      roomMap[4][0] = 5;
      roomMap[5][0] = 3;
    }
  }
  
  public void closeAllDoor(){
    if( roomMap == null )
      return;
    areDoorOpen = false;
    //metto le porte
    if( type.charAt(0) == '1' ){
      entities.add( new Entity(904, 0, 111, 219 ) );
      for( int i=0; i<3; i++ )
        for( int j=8; j<10; j++ ){
          if( i==0 )
            roomMap[i][j] = 2;
          if( i==1 )
            roomMap[i][j] = 5;
          if( i==2 )
            roomMap[i][j] = 3;
        }
    }
    if( type.charAt(1) == '1' ){
      entities.add( new Entity(1840, 478, 79, 181 ) );
      roomMap[4][17] = 2;
      roomMap[5][17] = 2;
    }
    if( type.charAt(2) == '1' ){
      entities.add( new Entity(849, 991, 219, 88 ) );
      roomMap[9][8] = 2;
      roomMap[9][9] = 2;
    }
    if( type.charAt(3) == '1' ){
      entities.add( new Entity(0, 478, 79, 181 ) );
      roomMap[4][0] = 2;
      roomMap[5][0] = 2;
    }
  }
  
  public void drawEnemies(Graphics g){
    
    for( int i=0; i<enemies.size(); i++ )
      if( !((Enemy)enemies.get(i)).isDead() )
        ((Enemy)enemies.get(i)).draw(g);
    
  }
  
  public void draw(Graphics g){
    
    
    for(int i=0; i<roomMap.length; i++)
      for(int j=0; j<roomMap[i].length; j++)
        g.drawImage( (BufferedImage)blocks.get(roomMap[i][j]), j*110-(roomMap[i].length*110-1920)/2, i*110, null );
    g.setColor(Color.red);
    if( PRINT_HITBOX )
      for( int i=0; i<entities.size(); i++)
        ((Entity)entities.get(i)).draw(g);
    
    for(int j=0; j<roomMap[0].length; j++)
      g.drawImage( (BufferedImage)blocks.get(roomMap[0][j]), j*110-(roomMap[0].length*110-1920)/2, -109, null);
    
  }
  
  public void update(Player p){
    
    for( int i=0; i<enemies.size(); i++ )
      if( !((Enemy)enemies.get(i)).isDead() )
        ((Enemy)enemies.get(i)).update(p);
    
  }
  
  
  public ArrayList getEntities() {
    return entities;
  }

  public ArrayList getEnemies() {
    return enemies;
  }
  
  
}
