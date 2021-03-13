package scarfy;

import java.awt.Point;
import static scarfy.EntityAlive.backOri;

public final class MapLeaf {
  
  protected Room room;
  protected MapLeaf[] closeRoom;
  protected Point p;
  protected StringBuffer mapCode;
  public static int mm;
  public boolean hasBeenVisited = false;
  public boolean hasBeenPrinted = false;
  public MapTreeManager manager;
  
  
  public MapLeaf( MapTreeManager manager, int minRoom){
    mm = minRoom--;
    closeRoom = new MapLeaf[4];
    this.manager = manager;
    for( MapLeaf m: closeRoom ){
      m = null;
    }
    
    mapCode = new StringBuffer("----");
    manager.addToArrayList( this );
    
    this.p = new Point(0, 0);
    
    for( int i=0; i<closeRoom.length; i++ )
    {
      if( Math.floor( (Math.random()*2734)%2 ) == 0 && mm > 0 ){
        mapCode.setCharAt(i, '1');
        closeRoom[i] = new MapLeaf( manager, minRoom, this, coordinatesOf(i) );
      }else
        mapCode.setCharAt(i, '0');
    }
    
    /*
    if( mapCode.toString().equals("0000") ){
      int n = (int)Math.floor( (Math.random()*2734)%4 );
        mapCode.setCharAt(n, '1');
        closeRoom[n] = new MapLeaf( manager, minRoom, this, coordinatesOf(n) );
    }*/
    
    this.room = manager.getRoom( mapCode.toString() );
    
    while(!this.room.enemies.isEmpty())
      this.room.enemies.remove(0);  //visto che è la prima stanza non ci sono nemici
    
  }
  

  public MapLeaf(MapTreeManager manager, int minRoom, MapLeaf lastLeaf, Point p){
    
    closeRoom = new MapLeaf[4];
    for( MapLeaf m: closeRoom ){
      m = null;
    }
    
    mapCode = new StringBuffer("----");
    manager.addToArrayList( this );
    
    mm--;
    this.p = p;
    
    closeRoom[oriOf(lastLeaf)] = lastLeaf;
    mapCode.setCharAt(oriOf(lastLeaf), '1');
    
    
    
    for( int i=0; i<closeRoom.length; i++ )  //scorre le stanze vicine
    {
      if( oriOf(lastLeaf) != i ){   //skippa la stanza precedente
        
        closeRoom[i] = manager.getLeafOn(coordinatesOf(i));  //controlla se c'è una stanza vicina (da null se non c'è)
        
        if( closeRoom[i] == null ){                                   //se non ha enssuna stanza vicino
          if( Math.floor( (Math.random()*2000)%2 ) > 0 && mm > 0 ){  //ne crea una
            mapCode.setCharAt(i, '1');
            closeRoom[i] = new MapLeaf( manager, minRoom, this, coordinatesOf(i) );
          }else{                                                      //non ne crea una
            mapCode.setCharAt(i, '0');
          }
        }else{
          if( closeRoom[i].mapCode.charAt(backOri(i)) == '1' )        //se la stanza che ha vicino ha la porta in quel lato
            mapCode.setCharAt(i, '1');                                //la mette anche questa stanza
          
          /*else if( closeRoomNumber()<2 ){       //se non la ha rolla un d2 per mettergliela o no
            mapCode.setCharAt(i, '1');                  //--update: se voglio farlo è meglio farlo dopo che l'albero è creato, perchè se lo si fa mentre si crea tante stanze hanno un solo 'vicino' ma solo perchè gli altri 'vicini' non li ha nemmeno calcolati
            closeRoom[i].mapCode.setCharAt(backOri(i), '1');
            closeRoom[i].room = manager.getRoom( closeRoom[i].mapCode.toString() );
            closeRoom[i].closeRoom[backOri(i)] = this;
          }*/else
            mapCode.setCharAt(i, '0');
        }
      }
    }
    
    this.room = manager.getRoom( mapCode.toString() );
    
  }//constructor

  
    //      -----     è solo per il boss 
  public MapLeaf( MapTreeManager manager, MapLeaf lastLeaf, Point p ){ 
    closeRoom = new MapLeaf[4];
    hasBeenVisited = false;
    this.manager = manager;
    this.p = p;
    for( MapLeaf m: closeRoom )
      m = null;
    mapCode = new StringBuffer("0000");
    closeRoom[oriOf(lastLeaf)] = lastLeaf;
    mapCode.setCharAt(oriOf(lastLeaf), '1');
    manager.addToArrayList( this );
    this.room = manager.getBossRoom( mapCode.toString() );
  }
  
  
  public Room getRoom() {
    return room;
  }
  
  public int closeRoomNumber(){
    int n=0;
    for( int i=0; i<4; i++ )
      if(mapCode.charAt(i)=='1')
        n++;
    return n;
  }
  
  public Point getP() {
    return p;
  }
  
  public MapLeaf getNearRoom(int ori){
    return closeRoom[ori];
  }

  public StringBuffer getMapCode() {
    return mapCode;
  }
  
  public Point coordinatesOf(int ori){
    
    switch(ori)
    {
      case 0:
        return new Point(p.x, p.y+1);
      case 1:
        return new Point(p.x+1, p.y);
      case 2:
        return new Point(p.x, p.y-1);
      case 3:
        return new Point(p.x-1, p.y);
    }
    
    return null;
  }
  
  public int oriOf(MapLeaf ml){
    if( this.p.x != ml.p.x )
      if( this.p.x > ml.p.x )
        return 3;
      else
        return 1;
    if( this.p.y != ml.p.y )
      if( this.p.y > ml.p.y )
        return 2;
      else
        return 0;
    
    
    System.out.println("errore lol nella creazione della foglia in oriOf x: "+ml.p.x+" y: "+ml.p.y);
    return 4;
  }
  
}
