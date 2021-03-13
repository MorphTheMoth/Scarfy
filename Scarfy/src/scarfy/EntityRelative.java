
package scarfy;


public class EntityRelative extends Entity{               //il rettangolo è centrato!
  
  private Entity enty;
  float offX, offY;
  
  
  public EntityRelative( Entity enty, float offX, float offY, int width, int height )
  {
    super(0,0,width,height);
    this.enty = enty;
    this.offX = offX;
    this.offY = offY;
  }
  
  public void update(){
    
    this.x = enty.x + this.offX;
    this.y = enty.y + this.offY;
    
  }
  
  
}
