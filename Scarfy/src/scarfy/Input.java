package scarfy;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class Input implements KeyListener, MouseListener{
  
  private Scarfy frame;
  private boolean up, down, left, right;
  public int ori;
  public boolean isAttackCalled;
  public boolean isTabPressed = false;
  public boolean isEnterPressed = false;
  
  public Input(JFrame frame){
    this.frame = (Scarfy)frame;
    up = false;
    down = false;
    left = false;
    right = false;
    isAttackCalled = false;
    ori = 1;
    
    
  }
  
  
  
  //--------------------------------------------------------------------------------------
  
  public int mouseX(){
    int n = MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x;
    if( n < 0)
      return 0;
    else if( n > frame.getBounds().width )
      return frame.getBounds().width;
    
    return n;
  }
  public int mouseY(){
    int n = MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y;
    if( n < 0)
      return 0;
    else if( n > frame.getBounds().height )
      return frame.getBounds().height;
    
    return n;
  }
  
  //--------------------------------------------------------------------------------------
  
  public int keysPressedAtOnce(){
    int n=0;
    
    if( up )
      n++;
    if( down )
      n++;
    if( right )
      n++;
    if( left )
      n++;
    
    return n;
  }
  
  
  //--------------------------------------------------------------------------------------
  

  public boolean up() {
    return up;
  }
  public boolean down() {
    return down;
  }
  public boolean left() {
    return left;
  }
  public boolean right() {
    return right;
  }

  public boolean isAttackCalled() {
    return isAttackCalled;
  }

  public void setIsAttackCalled(boolean isAttackCalled) {
    this.isAttackCalled = isAttackCalled;
  }
  
  
  //--------------------------------------------------------------------------------------
  
  public int ori() {
    
    if( this.keysPressedAtOnce() == 1 )
    {
      if( up )
        ori = 0;
      if( right )
        ori = 1;
      if( down )
        ori = 2;
      if( left )
        ori = 3;
    }
    
    return ori;
  }
  public void setOri( int n ){
    ori = n;
  }

  
  //--------------------------------------------------------------------------------------

  @Override
  public void keyPressed(KeyEvent key) {
    if( key.getKeyChar() == 'k' ){
      frame.room.enemies.clear();
    }
    if( key.getKeyChar() == 'w' ){
      up = true;
      ori = 0;
    }
    if( key.getKeyChar() == 'd' ){
      right = true;
      ori = 1;
    }
    if( key.getKeyChar() == 's' ){
      down = true;
      ori = 2;
    }
    if( key.getKeyChar() == 'a' ){
      left = true;
      ori = 3;
    }
    if( key.getKeyCode() == 9 ){  //tab
      isTabPressed = true;
    }
    if( key.getKeyCode() == 10 ){ //enter
      isEnterPressed = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent key) {
    
    if( key.getKeyChar() == 'w' )
      up = false;
    if( key.getKeyChar() == 'a' )
      left = false;
    if( key.getKeyChar() == 's' )
      down = false;
    if( key.getKeyChar() == 'd' )
      right = false;
    if( key.getKeyCode() == 9 )
      isTabPressed = false;
  }

  @Override
  public void keyTyped(KeyEvent key) {   
    
    if( key.getKeyCode() == 27 ){ //ESC
      Runtime.getRuntime().exit(0);
    }
    
  }
  
  
  @Override
  public void mouseClicked(MouseEvent e) { }

  @Override
  public void mousePressed(MouseEvent e) {
      isAttackCalled = true;
  }

  @Override
  public void mouseReleased(MouseEvent e) { }

  @Override
  public void mouseEntered(MouseEvent e) { }

  @Override
  public void mouseExited(MouseEvent e) { }

  
}
