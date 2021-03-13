package scarfy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static scarfy.Player.resize;
import static scarfy.Scarfy.PRINT_HITBOX;

public class NightmareEcho extends Enemy{
  
  private boolean areImagesInitialized = false;
  private BufferedImage idle[];
  private BufferedImage growl[];
  private BufferedImage aoe[];
  private BufferedImage summon[];
  private BufferedImage title;
  private int growlCounter = 0;
  private int attackProjCounter = 0;
  private int growlCooldown = 90;
  private int lastingTimeTeleport;
  private float tx, ty;
  private int CD, CDmax, CDmin;        //cooldown attacco
  private float hpFull;  
  private int attRoll;  
  private boolean flagProj, flagAOE, flagSumm, flagBulletHell, flagRotate, isStarting;
  private Scarfy scarfy;
  
  private Projectile[] balls;
  private int ballsTimer;
  private int bulletHellTimer;
  private int aoeTimer;
  private int projTimer;
  private int summonTimer;
  private int hittedCounter = 0;
  
  
  public NightmareEcho(Scarfy scarfy, ArrayList entities, ArrayList enemies){
    super(1920/2-55, 1080/2-55, 110, 110, 200, 0, 0, entities, enemies);
    this.scarfy = scarfy;
    
    balls = new Projectile[4];
    ballsTimer = 0;
    projTimer = 0;
    attackProjCounter = 0;
    
    imgDim = (int)(110*2.5f);
    hpFull = hp;
    CDmax = 450;
    CDmin = 200;
    CD = (int) ( 1 - (hp / hpFull)) * (CDmax - CDmin) + CDmin + 150;
    
    isStarting = true;
    flagProj = false;
    flagAOE = false;
    flagSumm = false;
    
    changeState("idle");
    
    if( !areImagesInitialized ){
      
        areImagesInitialized = true;
        idle = new BufferedImage[14];
        growl = new BufferedImage[3];
        aoe = new BufferedImage[14];
        summon = new BufferedImage[14];
        
      try {
        
        for( int i=0; i<idle.length; i++)
          idle[i] = resize( ImageIO.read( new File(  "pepsi\\enemies\\nightmareEcho\\idle\\f"+i+".png" ) ), imgDim, imgDim );
        for( int i=0; i<growl.length; i++)
          growl[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\nightmareEcho\\growl\\f"+i+".png" ) ), imgDim, imgDim );
        for( int i=0; i<aoe.length; i++)
          aoe[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\nightmareEcho\\boss_AOE\\f"+i+".png" ) ), imgDim, imgDim );
        for( int i=0; i<summon.length; i++)
          summon[i] = resize( ImageIO.read( new File( "pepsi\\enemies\\nightmareEcho\\summon\\boss_summon"+i+".png" ) ), imgDim, imgDim );
        
        title = resize( ImageIO.read( new File( "pepsi\\enemies\\nightmareEcho\\title.png" ) ), 362*3/2, 68*3/2 );
        
      } catch (IOException ex) {
        System.out.println("error opening KnightmareChild image");
      }
    }
    
  }
  
  public void draw(Graphics g){
    gg = g;
    
    if( PRINT_HITBOX )
      g.drawRect((int)x, (int)y, width, height);
    
    if( growlCounter < 130 ) //lo fa solo all'inizio
      drawImage( title, 60, 1080-68*3/2-10, null );
    
    
    switch(state)
    {
      case "idle":
        frame++;
        if( frame >= idle.length*10 )
          frame = 0;
        drawImage( idle[(int)Math.floor( frame/10 )], (int)x-normPixel(43), (int)y-normPixel(38), null );
      break;
      case "growl":
        if( frame < 20 ) //fa i primi due frame e poi aspetta con il terzo che lo stato venga cambiato
          frame++;
        drawImage( growl[(int)Math.floor( frame/10 )], (int)x-normPixel(43), (int)y-normPixel(38), null );
      break;
      case "aoe":
        frame++;
        if( frame >= aoe.length*10 ) //fa i primi due frame e poi aspetta con il terzo che lo stato venga cambiato
          changeState("idle");
        drawImage( aoe[(int)Math.floor( frame/10 )], (int)x-normPixel(43), (int)y-normPixel(38), null );
      break;
      case "summon":
        frame++;
        if( frame >= summon.length*10 ) //fa i primi due frame e poi aspetta con il terzo che lo stato venga cambiato
          changeState("idle");
        drawImage( summon[(int)Math.floor( frame/10 )], (int)x-normPixel(43), (int)y-normPixel(38), null );
      break;
    }
    
    
  }
  
  @Override
  public void update( Player p ){
    
    calculateCenter();
    
    if( checkKnockback() )
      applyKnockBack();
    
    if( lastingTimeTeleport > 0 ){ //se si sta teletrasportando
      lastingTimeTeleport--;
      if( lastingTimeTeleport == 0 ) //se ha finito il teletrasporto
        endTeleport(); //finisce il teletrasporto
    }
    
    if( hittedCounter == 3 ){
      startTeleport( (float)Math.random()*(1660-width)+80, (float)Math.random()*(730-height)+170, 20 );
      hittedCounter = 0;
    }
    if( isStarting ){
    //                              è in idle per 20 frame
      p.canItMove = false;
      growlCooldown--;
      if( growlCooldown <= 0 )
        Slime.blackChromaAll(); //cambia il colore di tutti gli slime in nero (è qui a caso, basta che viene chiamato una sola volta)
        changeState("growl");
    //                                ruggisce e fa vibrare lo schermo per 130 frame
      if( state.equals("growl") ){
        if( growlCounter >= 10  )
          scarfy.vibrate( 120, 15, growlCounter-10 );
        
          growlCounter++;
        if( growlCounter >= 130 ){  //qui ha finito di ruggire
          p.canItMove = true;
          isStarting = false;
          changeState("idle");
        }
      }
      
    } //if isStarting
    
    //if( gg != null )
      //gg.drawString("attRoll: "+attRoll, 400, 400);
    
    if( CD <= 0 ) {    //se il cooldown è a 0 ed rollo per che attacco fare e lo "inizializza"
      do{
        attRoll = (int) (Math.floor(Math.random() * 100) % 5);
      }while( (( attRoll == 0 ) && flagRotate ) || (( attRoll == 1 ) && flagAOE ) || (( attRoll == 2 ) && flagSumm ) || (( attRoll == 3 ) && flagBulletHell ) || (( attRoll == 4 ) && flagProj ));
      
      switch(attRoll){
        case 0:
          flagRotate = true;
          setUpRotatingBalls();
        break;case 1:
          changeState("aoe");
          flagAOE = true;
        break;case 2:
          changeState("summon");
          flagSumm = true;
        break;case 3:
          flagBulletHell = true;
        break;case 4:
          flagProj = true;
          attackProjCounter = (int)(Math.random()*3)+1;
        break;
      }   
      CD = (int) ( ( 1 - (hp / hpFull)) * (CDmax - CDmin) + CDmin );
    }
    else
      CD--;
    
    
    if( flagRotate ){           //call attacco proiettile
      rotateBalls();
    }
    if( flagAOE ){              //call attacco AOE
      AOE();
    }
    if( flagSumm ){             //call attacco summon
      summon();
    }
    if( flagBulletHell ){       //call attacco bullet hell
      bulletHell();
    }
    if( flagProj ){             //call attacco proiettili
      projectileAttack(p);
    }
    
  }
  
  public void projectileAttack(Player p){
    
    if( projTimer == 0 ){        //nuovo colpo
      startTeleport( (float)Math.random()*(1660-width)+80, (float)Math.random()*(730-height)+170, 20 );
      //System.out.println("si teleporta..");
    }else if( projTimer == 20 ){  //quando ha finito di tipparsi
      changeState("growl");
      //System.out.println("parte l'animazione di growl..");
    }else if( projTimer == 40 ){  //quando ha finito l'animazione del ruggito
      new Projectile( 0, (float)Math.floor(x), (float)Math.floor(y), 40, 40, 1, p, entities, enemies );
      //System.out.println("spara..");
      //System.out.println("x: "+this.x);
      //System.out.println("y: "+this.y);
    }else if( projTimer == 55 ){  //poco dopo che ha attaccato
      changeState("idle");
      //System.out.println("torna in idle..");
    }else if( projTimer == 100 ){ //quando finisce l'attacco ne fa un'altro per random volte
      projTimer = -1;
      attackProjCounter--;
      //System.out.println("finisce l'attacchino..");
    }
    if( attackProjCounter == 0 ){
      flagProj = false;
      //System.out.println("finisce tutto l'attacco..");
    }
    projTimer++;
  }
  
  private void bulletHell(){
    if( bulletHellTimer == 0 )
      startTeleport(1920/2-width/2, 1080/2-height/2, 30);

    if( bulletHellTimer == 30 ){
      changeState("growl");
    }

    if( bulletHellTimer < 400 && bulletHellTimer > 30 )
      
      if( bulletHellTimer%60 == 0 )            //public Projectile( int type, float x, float y, int width, int height, int spdX,int spdY, ArrayList entities, ArrayList enemies ){
      {
        new Projectile( 0, cx, cy, 40, 40, 0, 4, entities, enemies );
        new Projectile( 0, cx, cy, 40, 40, 0, -4, entities, enemies );
        new Projectile( 0, cx, cy, 40, 40, 4, 0, entities, enemies );
        new Projectile( 0, cx, cy, 40, 40, -4, 0, entities, enemies );
      }else if( bulletHellTimer%30 == 0 ){
        new Projectile( 0, cx, cy, 40, 40, 2.8f, 2.8f, entities, enemies );
        new Projectile( 0, cx, cy, 40, 40, 2.8f, -2.8f, entities, enemies );
        new Projectile( 0, cx, cy, 40, 40, -2.8f, 2.8f, entities, enemies );
        new Projectile( 0, cx, cy, 40, 40, -2.8f, -2.8f, entities, enemies );
      }
    
    if( bulletHellTimer >= 400 ){
      flagBulletHell = false;
      bulletHellTimer = 0;
      changeState("idle");
    }
    bulletHellTimer++;
  }
  
  public void rotateBalls(){
    if( ballsTimer == 0 )
      startTeleport(1920/2-width/2, 1080/2-height/2, 30);
    
    ballsTimer++;
    if( ballsTimer == 30 ){
      changeState("growl");
    }
    
    if( ballsTimer >= 150 )
    {
      if( ballsTimer == 150 )
        changeState("idle");
      
      for( int i=0; i<balls.length; i++ ){
        if( i%2 == 0 ){
          balls[i].x = (int) ( cos((float)(ballsTimer-150)/60*PI/2+i*PI/balls.length*2)*830 + 1920/2 );
          balls[i].y = (int) ( sin((float)(ballsTimer-150)/60*PI/2+i*PI/balls.length*2)*365 + 1080/2 );
        }else{
          balls[i].x = (int) ( cos((float)(ballsTimer-150)/60*PI/2+i*PI/balls.length*2)*830/3 + 1920/2 );
          balls[i].y = (int) ( sin((float)(ballsTimer-150)/60*PI/2+i*PI/balls.length*2)*365/3 + 1080/2 );
        }
      }
      if( ballsTimer > 600 ){
        flagProj = false;
        ballsTimer = 0;
        for( int i=0; i<balls.length; i++ ){
          balls[i].die();
        }
      }
    }else if( ballsTimer >= 50 )  //le palle che escono all'inizio
    {
      for( int i=0; i<balls.length; i++ ){
        switch( i%2 ){
          case 0: 
            balls[i].x = (int) ( sin((float)(ballsTimer-50)/100*PI/2+i*PI/balls.length*2)*830 + 1920/2 );
            balls[i].y = 1080/2+height/2;
          break;
          case 1:
            balls[i].x = 1920/2+width/2;
            balls[i].y = (int) ( cos((float)(50-ballsTimer)/100*PI/2+i*PI/balls.length*2)*365/3 + 1080/2 );
          break;
        }
      }
    }
  }
  
  
  private void setUpRotatingBalls(){
    for( int i=0; i < balls.length; i++ )
      balls[i] = new Projectile( 1, -300, -300, 40, 40, 0, null, entities, enemies );
  }
  
  //x da 0 a 15, yy da 0 a 6
  private void startAoeRectangleAnimation(int xx, int yy){
    xx *= 110;  // 110 è la larghezza dei qudratoni della mappa
    yy *= 110;
    
    xx += 80;  // offset iniziali del quadrato verde
    yy += 220;
    
    Particle.newParticle(0, xx, yy, 20); //0 è il tipo della particella
    //                                   //10 è il numero dei frame che dura ogni immagine dell'animazione
  }
  
  public void AOE(){
    if( aoeTimer == 0 )
      startTeleport( (float)Math.random()*(1660-width)+80, (float)Math.random()*(730-height)+170, 20 );
    if( aoeTimer == 20 ) {
      flagAOE = false;
      aoeTimer = 0;
      switch( (int)(Math.floor(Math.random()*100)%5 ) )
      {
        case 0:        //colonne pari
          for(int i=0; i<16; i += 2)
            for(int j=0; j<7; j++)
              startAoeRectangleAnimation( i, j);
        break;
        case 1:
          changeState("aoe"); // colonne dispari
          for(int i=1; i<16; i += 2)
            for(int j=0; j<7; j++)
              startAoeRectangleAnimation( i, j);
        break;
        case 2: //righe pari
          for(int i=0; i<7; i += 2)
            for(int j=0; j<16; j++)
              startAoeRectangleAnimation( j, i);
        break;
        case 3: //righe dispari
          for(int i=1; i<7; i += 2)
            for(int j=0; j<16; j++)
              startAoeRectangleAnimation( j, i);
        break;
        case 4: //scacchiera
           for( int i=0; i<16; i++ )
              for( int j=0; j<7; j++ )
                if( (i+j)%2 == 0 )
                  startAoeRectangleAnimation( i, j );
        break;
      }
      return;
    }
    aoeTimer++;
  }
  
  public void summon(){
    int rollx, rolly;
    int i;
    if(summonTimer == 100){
      for(i=0; i <= (Math.floor(Math.random()*99)%3)+2; i++){
        rollx = (int)Math.floor(Math.random()*16);
        rolly = (int)Math.floor(Math.random()*6);
        startAoeRectangleAnimation(  rollx, rolly + 1);        //80 + 28 = 128   //220 + 28 = 248
        enemies.add( new Slime( rollx * 110 + 108, ( ( rolly + 1 ) * 110) + 248, 55, 55, 20, 4, 1, entities, enemies ));
      }
      flagSumm = false;
      summonTimer = 0;
    }
    summonTimer++;
  }
  
  public void startTeleport( float tx, float ty, int lastingTimeTeleport ){
    this.lastingTimeTeleport = lastingTimeTeleport;
    this.tx = tx;
    this.ty = ty;
    x = -10000;
    y = -10000;
  }
  
  public void endTeleport(){
    x = tx;
    y = ty;
  }
  
  @Override
  public int normPixel(int n){
    return ((int)((imgDim/(float)128)*(float)n)); //accurata descrizione di java
  } //trasforma i pixel delle animazioni in base ai pixel dello schermo
  
    public void hitted( float damage, float knock, float frameKnockBack, EntityAlive e ){
    redFrameLeft = 5;
    hittedCounter++;
    hp -= damage;
    if( hp <= 0 )
      this.die();
    else
      setKnockBack(  knock, frameKnockBack, e );
  }
  
  
  public void hitted( float damage, float knock, float frameKnockBack, int ori ){
    redFrameLeft = 5;
    hp -= damage;
    hittedCounter++;
    if( hp <= 0 )
      this.die();
    else
      setKnockBack(  knock, frameKnockBack, ori );
  }
  
}
