package scarfy;

import java.awt.Point;
import java.util.ArrayList;

public class MapTreeManager {
  
  private ArrayList leaves;
  private MapLeaf currentLeaf;
  private RoomManager roomManager;
  
  public MapTreeManager( RoomManager roomManager ){
    this.roomManager = roomManager;
    leaves = new ArrayList();
    
    currentLeaf = new MapLeaf(this, 7);
    currentLeaf.hasBeenVisited = true;
    
    for( int i=leaves.size()-1; i >= 0; i-- ){        //aggiunge la stanza del boss
      for( int j=0; j<4; j++ )
        if( getLeafOn( ((MapLeaf)leaves.get(i)).coordinatesOf(j) ) == null ){  
          ((MapLeaf)leaves.get(i)).mapCode.setCharAt(j, '1');
          ((MapLeaf)leaves.get(i)).room = roomManager.setupRoom(((MapLeaf)leaves.get(i)).mapCode.toString());
          ((MapLeaf)leaves.get(i)).closeRoom[j] = new MapLeaf( this, ((MapLeaf)leaves.get(i)), ((MapLeaf)leaves.get(i)).coordinatesOf(j) );
          return;
        }
      
    }
    
  }
  
  
  public int getNumber(){
    return leaves.indexOf( currentLeaf );
  }
  
  public Room getCurrentRoom() {
    return currentLeaf.getRoom();
  }
  
  public void changeRoom(int ori){
    currentLeaf = currentLeaf.getNearRoom(ori);
    currentLeaf.hasBeenVisited = true;
  }

  public void setHasBeenPrinted( Boolean b ){
    for( int i=0; i<leaves.size(); i++ )
      ((MapLeaf)leaves.get(i)).hasBeenPrinted = b;
  }
  
  
  
  public MapLeaf getCurrentLeaf() {
    return currentLeaf;
  }
  
  public RoomManager getRoomManager() {
    return roomManager;
  }
  
  public void addToArrayList( MapLeaf ml ){
    leaves.add(ml);
  }
  
  public MapLeaf getLeafOn( Point p ){
    
    for( int i=0; i<leaves.size(); i++ )
      if( p.equals( ((MapLeaf)leaves.get(i)).getP() ) )
        return ((MapLeaf)leaves.get(i));
    
    return null;
  }
  
  public Room getRoom( String roomCode ){
    return roomManager.setupRoom( roomCode );
  }
  
  public Room getBossRoom( String roomCode ){
    return roomManager.setupBossRoom( roomCode );
  }
 
}
