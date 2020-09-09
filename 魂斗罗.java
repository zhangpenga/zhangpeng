import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class MainFrame extends Frame
   implements Runnable
 {
   private boolean firstTime = true;
   private BufferedImage bi;
   private Graphics2D big;
   AlphaComposite alphaComposite = AlphaComposite.getInstance(3, 0.5F);
   AlphaComposite alphaComposite2 = AlphaComposite.getInstance(3, 1.0F);
   public float mapPosition = 0.0F;
   private FloatPoint bgPosition = new FloatPoint(20.0F, 90.0F);
   private FloatPoint scenePosition = new FloatPoint(0.0F, 0.0F);
   private int sceneWidth = 3328;
   private int backgroundSizeOfWidth = 900;
   private int backgroundSizeOfHeight = 672;
   private Player player;
   private static int FPS = 60;
   private int walkSpeed = 1;
   private GrassLand[] grassLands = new GrassLand[41];
   Toolkit toolkit = Toolkit.getDefaultToolkit();
   Dimension screenSize = this.toolkit.getScreenSize();
   List<Bullet> heroBullets;
   List<Bullet> enemyBullets;
   private boolean leftDown = false;
   private boolean rightDown = false;
   private boolean upDown = false;
   private boolean downDown = false;
   private boolean jumpDown = false;
   private int heroNum = 4;
   private List<Enemy> enemys = new ArrayList<Enemy>();
   private boolean showBossScene = false;
   boolean addEnemy1 = true;
   boolean addEnemy2 = true;
 
   int simpleEnemyNum = 0;
   boolean bossBattle = false;
   int timeCont = 0;
   boolean playPreBossStoneSound = true;
  
   int delta = 0;
   boolean preBossBattle = false;
 
   private boolean passedFirstMovingGrassLand = false;
   private boolean passedSecondMovingGrassLand = false;
 
   public MainFrame()
   { 
     this.player = new Player(new FloatPoint(50.0F, 30.0F), 2);
     this.heroBullets = new ArrayList<Bullet>();
     this.enemyBullets = new ArrayList<Bullet>();
     initGrassLands();
     initEnemys();
     setSize(this.backgroundSizeOfWidth, this.backgroundSizeOfHeight);
     setLocation((this.screenSize.width - getWidth()) / 2, 20);
     setVisible(true);
     setTitle("魂斗罗");
     setResizable(true);
     addWindowListener(new WindowAdapter() 
    	     {  
    	            public void windowClosing(WindowEvent e)  
    	            {  
    	               System.exit(0);  
    	            }});
     addKeyListener(new KeyListener()
     {
       public void keyTyped(KeyEvent e)
       {
         if (KeyEvent.getKeyText(e.getKeyChar()).equals("Esc"))
           System.exit(0);
       }
 
       public void keyReleased(KeyEvent e)
       {
         MainFrame.this.setKeyStateWhenKeyReleased(e);
         if ((MainFrame.this.player.state != 4) && (MainFrame.this.player.state != 5)) {
           if (KeyEvent.getKeyText(e.getKeyCode()).equals("J")) {
             MainFrame.this.player.canShot = true;
           }
           if (KeyEvent.getKeyText(e.getKeyCode()).equals("K"))
             MainFrame.this.player.canDrop = true;
         }
       }
 
       public void keyPressed(KeyEvent e)
       {
         int type = e.getKeyCode() - 48;
         if ((type > 0) && (type < 5)) {
           MainFrame.this.player.weapon = new Weapon(1);
         }
 
         MainFrame.this.setKeyStateWhenKeyPressed(e);
         if ((MainFrame.this.player.state != 4) && (MainFrame.this.player.state != 5)) {
           if (KeyEvent.getKeyText(e.getKeyCode()).equals("J")) {
             if (MainFrame.this.player.canShot) {
                if (((MainFrame.this.player.weapon instanceof Weapon)) && 
                		(MainFrame.this.heroBullets.size() < 6)) {
                 MainFrame.this.heroBullets.addAll(MainFrame.this.player.shot(1));
               }
              
             }
 
             MainFrame.this.player.canShot = false;
           }
           if ((KeyEvent.getKeyText(e.getKeyCode()).equals("K")) && 
             (MainFrame.this.player.canDrop) && (MainFrame.this.player.state == 3) && 
            (MainFrame.this.player.position.y < MainFrame.this.backgroundSizeOfHeight - 60)) {
             if (MainFrame.this.player.position.y < 206.0F)
               MainFrame.this.player.position.y += 6.0F;
             MainFrame.this.player.canDrop = false;
           }
         }
       }
     });
   }
 
   private void initEnemys()
   {
     Enemy[] enemy0 = { new Enemy(new FloatPoint(300.0F, 100.0F), 6, 0), 
       new Enemy(new FloatPoint(332.0F, 100.0F), 6, 0), 
       new Enemy(new FloatPoint(390.0F, 100.0F), 6, 0), 
       new Enemy(new FloatPoint(450.0F, 100.0F), 6, 0), 
       new Enemy(new FloatPoint(550.0F, 100.0F), 6, 0) };
     Enemy[] enemy1 = { new Enemy(new FloatPoint(600.0F, 100.0F), 6, 1), 
       new Enemy(new FloatPoint(600.0F, 100.0F), 6, 1), 
       new Enemy(new FloatPoint(620.0F, 100.0F), 6, 1), 
       new Enemy(new FloatPoint(1320.0F, 100.0F), 6, 1), 
       new Enemy(new FloatPoint(1380.0F, 100.0F), 6, 1), 
       new Enemy(new FloatPoint(1650.0F, 130.0F), 6, 1), 
       new Enemy(new FloatPoint(1750.0F, 130.0F), 6, 1), 
       new Enemy(new FloatPoint(3070.0F, 190.0F), 6, 1), 
       new Enemy(new FloatPoint(3140.0F, 190.0F), 6, 1), 
       new Enemy(new FloatPoint(3200.0F, 190.0F), 6, 1) };
     Enemy[] enemy2 = { new Enemy(new FloatPoint(1400.0F, 70.0F), 6, 2), 
       new Enemy(new FloatPoint(1430.0F, 70.0F), 6, 2), 
       new Enemy(new FloatPoint(2240.0F, 100.0F), 6, 2), 
       new Enemy(new FloatPoint(2460.0F, 100.0F), 6, 2), 
       new Enemy(new FloatPoint(2600.0F, 100.0F), 6, 2), 
       new Enemy(new FloatPoint(2860.0F, 165.0F), 6, 2), 
       new Enemy(new FloatPoint(2960.0F, 130.0F), 6, 2) };
     Enemy[] enemy3 = { new Enemy(new FloatPoint(465.0F, 138.0F), 6, 3), 
       new Enemy(new FloatPoint(662.0F, 148.0F), 6, 3), 
       new Enemy(new FloatPoint(900.0F, 100.0F), 6, 3), 
       new Enemy(new FloatPoint(1000.0F, 100.0F), 6, 3), 
       new Enemy(new FloatPoint(1650.0F, 70.0F), 6, 3), 
       new Enemy(new FloatPoint(2220.0F, 130.0F), 6, 3), 
       new Enemy(new FloatPoint(2490.0F, 70.0F), 6, 3), 
       new Enemy(new FloatPoint(2990.0F, 100.0F), 6, 3) };
     Enemy[] enemy4 = { new Enemy(new FloatPoint(306.0F, 200.0F), 6, 4), 
       new Enemy(new FloatPoint(440.0F, 100.0F), 6, 4), 
       new Enemy(new FloatPoint(600.0F, 195.0F), 6, 4), 
       new Enemy(new FloatPoint(950.0F, 100.0F), 6, 4), 
       new Enemy(new FloatPoint(1250.0F, 100.0F), 6, 4), 
       new Enemy(new FloatPoint(1550.0F, 70.0F), 6, 4), 
       new Enemy(new FloatPoint(1500.0F, 140.0F), 6, 4), 
       new Enemy(new FloatPoint(1750.0F, 70.0F), 6, 4), 
       new Enemy(new FloatPoint(1850.0F, 200.0F), 6, 4), 
       new Enemy(new FloatPoint(2090.0F, 70.0F), 6, 4), 
       new Enemy(new FloatPoint(2320.0F, 200.0F), 6, 4), 
       new Enemy(new FloatPoint(2470.0F, 200.0F), 6, 4), 
       new Enemy(new FloatPoint(3050.0F, 100.0F), 6, 4), 
       new Enemy(new FloatPoint(3050.0F, 150.0F), 6, 4), 
       new Enemy(new FloatPoint(3050.0F, 190.0F), 6, 4), 
       new Enemy(new FloatPoint(3110.0F, 100.0F), 6, 4), 
       new Enemy(new FloatPoint(3140.0F, 130.0F), 6, 4), 
       new Enemy(new FloatPoint(3180.0F, 160.0F), 6, 4) };
     for (int i = 0; i < enemy0.length; i++) {
       enemy0[i].state = 0;
       this.enemys.add(enemy0[i]);
     }
     for (int i = 0; i < enemy1.length; i++) {
       enemy1[i].state = 0;
       this.enemys.add(enemy1[i]);
     }
     for (int i = 0; i < enemy2.length; i++) {
       enemy2[i].state = 0;
       this.enemys.add(enemy2[i]);
     }
     for (int i = 0; i < enemy3.length; i++) {
       enemy3[i].state = 0;
       this.enemys.add(enemy3[i]);
     }
     for (int i = 0; i < enemy4.length; i++) {
       enemy4[i].state = 0;
       this.enemys.add(enemy4[i]);
     }
   }
 
   public void paint(Graphics g)
   {
     super.paint(g);
     g.setColor(Color.GRAY);
     g.fillRect(0, 0, this.backgroundSizeOfWidth, this.backgroundSizeOfHeight);
     g.setColor(Color.BLACK);
     g.setFont(new Font(null, 1, 30));
     g.setFont(new Font(null, 1, 20));
    g.setFont(new Font(null, 1, 16));
    
   }
 
   public void update(Graphics g)
   {
     Graphics2D g2 = (Graphics2D)g;
     if (this.firstTime) {
       Dimension dim = getSize();
       int w = dim.width;
       int h = dim.height;
       new Rectangle(dim);
       this.bi = ((BufferedImage)createImage(w, h));
       this.big = this.bi.createGraphics();  
       this.firstTime = false;
       this.big.setStroke(new BasicStroke(1.0F));
     }
     this.big.drawImage(Imgs.BACKGROUND01, (int)this.scenePosition.x, 
   (int)this.scenePosition.y, this.backgroundSizeOfWidth, (int)this.scenePosition.y +672, 
   (int)this.mapPosition, 0, (int)this.mapPosition + this.backgroundSizeOfWidth / 3, 224, null);
     drawNPC(this.big);
     if (this.showBossScene) {
       playPreBossBattleAnimation(this.big);
     }
     
     for (int i = 0; i < this.enemys.size(); i++) {
       ((Enemy)this.enemys.get(i)).drawPlayer(this.big, this.mapPosition);
     }
    
 
     if (!this.player.visible) {
       this.big.setComposite(this.alphaComposite);
     }
     this.player.drawPlayer(this.big, this.mapPosition);
     this.big.setComposite(this.alphaComposite2);
     drawHeroBullets(this.big);
     drawEnemyBullets(this.big);
 
     this.big.setColor(Color.WHITE);
     this.big.setFont(new Font(null, 1, 28));
     g2.setColor(Color.GRAY);
     g2.drawImage(this.bi, (int)this.bgPosition.x, (int)this.bgPosition.y, 
   (int)(this.backgroundSizeOfWidth / 1.3D), (int)(this.backgroundSizeOfHeight / 1.3D), this);
   }
 
   public void run()
   {
     try {
       Thread.sleep(1000L);
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
     setVisible(true);
     while (true) {
       repaint(0, 0, 720, this.backgroundSizeOfHeight);
       try {
         Thread.sleep(1000 / FPS);
       } catch (InterruptedException e) {
         e.printStackTrace();
       }
       heroMove();
       checkHeroIsDropping();
       heroDeathCheck();
       setEnemyVisibility();
       enemysMove();
       enemysDeathCheck();
       enemyShotControl();
       if ((this.player.position.x >= 880.0F) && (this.player.position.x <= 950.0F) 
    		   && (this.player.position.y >= 20.0F) && (this.player.position.y <= 111.0F))
         this.passedFirstMovingGrassLand = true;
       if ((this.player.position.x >= 1170.0F) && (this.player.position.y >= 20.0F) 
    		   && (this.player.position.y <= 111.0F)) {
         this.passedSecondMovingGrassLand = true;
       }
       if ((this.player.state == 5) && (this.heroNum > 0)) {
         heroBirth();
       }
 
       if ((this.player.position.x > 3260.0F) && (this.mapPosition < 3328.0F) && 
    		   (this.playPreBossStoneSound)) {
         this.showBossScene = true;
         this.playPreBossStoneSound = false;
       }
     }
   }
 
   private void enemyShotControl()
   {
     for (int i = 0; i < this.enemys.size(); i++)
       if ((this.player.position.x > ((Enemy)this.enemys.get(i)).position.x -
    		   this.backgroundSizeOfWidth / 3) && 
         (this.player.position.x < ((Enemy)this.enemys.get(i)).position.x + 
        		 this.backgroundSizeOfWidth / 3) && 
         (((Enemy)this.enemys.get(i)).type == 4)) {
         this.enemyBullets.addAll(((Enemy)this.enemys.get(i)).shot(new FloatPoint
        		 (this.player.position.x + 0.001F, this.player.position.y + 0.001F), 1));
         ((Enemy)this.enemys.get(i)).canShot = true;
       }
       else {
         ((Enemy)this.enemys.get(i)).canShot = false;
       }
   }
 
   private void playPreBossBattleAnimation(Graphics2D g)
   {
     this.sceneWidth = 3628;
     if (this.mapPosition < 3328.0F)
     {
       FloatPoint tmp22_19 = this.scenePosition; tmp22_19.y = (float)(tmp22_19.y +
    		   6.0D * Math.sin(this.delta++));
       this.mapPosition += 0.5F;
       this.player.position.x += 0.5F;
       if (this.player.position.x > this.mapPosition + this.backgroundSizeOfWidth / 6) {
         this.player.direction = 6;
       }
       else
         this.player.direction = 2;
     }
     else
     {
       this.mapPosition = 3328.0F;
       this.player.towardsLeft = true;
       this.player.towardsRight = false;
       this.scenePosition.y = 0.0F;
       this.showBossScene = false;
       this.preBossBattle = true;
      }
   }
 
   private void setEnemyVisibility()
   {
     for (int i = 0; i < this.enemys.size(); i++) {
       Enemy e = (Enemy)this.enemys.get(i);
       if ((e.position.x > this.mapPosition) && (e.position.x < this.mapPosition
    		   + this.backgroundSizeOfWidth / 3) && 
         (!e.visible)) {
         e.visible = true;
         if (e.type != 4) {
           e.state = 1;
           e.towardsLeft = true;
           e.towardsRight = false;
           e.direction = 6;
         }
       }
     }
   }
 
   private void heroDeathCheck()
   {
     if ((this.player.state != 4) && (this.player.state != 5)) {
       for (int i = 0; i < this.enemyBullets.size(); i++) {
         Bullet b = (Bullet)this.enemyBullets.get(i);
         if ((b.position.x > this.player.position.x - this.player.width / 2 / 3) &&
        		 (b.position.x < this.player.position.x + this.player.width / 2 / 3) && 
           (b.position.y > this.player.position.y - this.player.height / 3) && 
           (b.position.y < this.player.position.y) && 
           (this.player.visible)) {
           this.player.state = 4;
           this.player.deathEventType = 0;
            this.enemyBullets.remove(i);
         }
       }
 
       for (int i = 0; i < this.enemys.size(); i++) {
         Enemy e = (Enemy)this.enemys.get(i);
         if ((e.state != 4) && (e.state != 5) && 
           (this.player.position.x > e.position.x - this.player.width / 2 / 3 - 11.0F) 
           && (this.player.position.x < e.position.x + 11.0F + this.player.width / 2 / 3) && 
           (this.player.position.y > e.position.y - 26.0F) && (this.player.position.y <
        		   e.position.y + this.player.height / 3)) {
           if (this.player.visible) {
             this.player.state = 4;
             this.player.deathEventType = 2;
            }
           else {
             e.state = 4;
           }
         }
       }
 
     }
   }
 
   private void heroBirth()
   {
     if ((this.mapPosition > 680.0F) && (!this.passedFirstMovingGrassLand)) {
       GrassLand movingGrassLand = new GrassLand(new FloatPoint(24.0F, 110.0F), 1);
       movingGrassLand.movingDirection = 3;
       this.grassLands[(this.grassLands.length - 2)] = movingGrassLand;
       this.mapPosition = 680.0F;
       this.player = new Player(new FloatPoint(700.0F, 0.0F), 2);
     }
     else if ((this.player.position.x > 740.0F) && (!this.passedFirstMovingGrassLand)) {
       GrassLand movingGrassLand = new GrassLand(new FloatPoint(24.0F, 110.0F), 1);
       movingGrassLand.movingDirection = 3;
       this.grassLands[(this.grassLands.length - 2)] = movingGrassLand;
       this.player = new Player(new FloatPoint(this.mapPosition + 20.0F, 0.0F), 2);
     }
     else if ((this.mapPosition > 970.0F) && (!this.passedSecondMovingGrassLand)) {
       GrassLand movingGrassLand = new GrassLand(new FloatPoint(33.0F, 110.0F), 1);
       movingGrassLand.movingDirection = 3;
       this.grassLands[(this.grassLands.length - 1)] = movingGrassLand;
       this.mapPosition = 970.0F;
       this.player = new Player(new FloatPoint(990.0F, 0.0F), 2);
     }
     else if ((this.player.position.x > 1030.0F) && (!this.passedSecondMovingGrassLand)) {
       GrassLand movingGrassLand = new GrassLand(new FloatPoint(33.0F, 110.0F), 1);
       movingGrassLand.movingDirection = 3;
       this.grassLands[(this.grassLands.length - 1)] = movingGrassLand;
       this.player = new Player(new FloatPoint(this.mapPosition + 20.0F, 0.0F), 2);
     }
     else {
       this.player = new Player(findBirthPlace(), finBirthDirection());
     }
     this.player.state = 0;
     this.heroNum -= 1;
     this.leftDown = false;
     this.rightDown = false;
     this.upDown = false;
     this.downDown = false;
     this.jumpDown = false;}
     
 
   private int finBirthDirection()
   {
     return 2;
   }
 
   private FloatPoint findBirthPlace()
   {
     if ((this.passedFirstMovingGrassLand) && (this.grassLands[(this.grassLands.length -
    		 2)].position.x * 32.0F + 32.0F > this.mapPosition)) {
       for (int j = (int)(this.grassLands[(this.grassLands.length - 2)].position.x 
    	* 32.0F + 50.0F); j < this.backgroundSizeOfWidth / 3 + this.mapPosition; j += 32) {
         for (int i = 0; i < this.backgroundSizeOfHeight / 3; i += 3) {
           if ((isGrassLand(j, i)) && (isGrassLand(j + 10, i))) {
             if (j % 32 < 16) {
               j += 10;
             }
             return new FloatPoint(j, 0.0F);
           }
         }
       }
     }
     else if ((this.passedSecondMovingGrassLand) && (this.grassLands[(this.grassLands.length -
    		 1)].position.x * 32.0F + 32.0F > this.mapPosition)) {
       for (int j = (int)(this.grassLands[(this.grassLands.length - 1)].position.x * 
    		   32.0F + 50.0F); j < this.backgroundSizeOfWidth / 3 + this.mapPosition; j += 32) {
         for (int i = 0; i < this.backgroundSizeOfHeight / 3; i += 3) {
           if ((isGrassLand(j, i)) && (isGrassLand(j + 10, i))) {
             if (j % 32 < 16) {
               j += 10;
             }
             return new FloatPoint(j, 0.0F);
           }
         }
       }
     }
     else {
       for (int j = (int)this.mapPosition + 20; j < this.backgroundSizeOfWidth / 3 
    		   + this.mapPosition; j += 32) {
         for (int i = 0; i < this.backgroundSizeOfHeight / 3; i += 3) {
           if ((isGrassLand(j, i)) && (isGrassLand(j + 10, i)))
             return new FloatPoint(j + 10, 0.0F);
         }
       }
     }
     return null;
   }
 
   private void checkHeroIsDropping() {
     if ((this.player.state != 4) && (this.player.state != 5))
       if (!isGrassLand(this.player.position.x, this.player.position.y + 1.0F)) {
         if ((this.player.position.y < 206.0F) && (!this.player.jumping)) {
           this.player.state = 6;
           this.player.position.y += 2.0F;
         }
         else if ((this.player.position.y > 206.0F) && (this.player.jumping)) {
           this.player.position.y = 206.0F;
           this.player.state = 0;
         }
 
         if ((this.player.position.y >= 206.0F) && (!isGrassLand(this.player.position.x, 
        		 this.player.position.y + 1.0F)))
         {
           if (this.player.visible) {
             this.player.state = 4;
             this.player.deathEventType = 1;
             }
         }
       }
       else if ((this.player.jumping) && (this.player.jumpFinished)) {
         this.player.jumping = false;
         if ((this.player.direction == 0) || (this.player.direction == 1) || 
        		 (this.player.direction == 2) || (this.player.direction == 3)) {
           this.player.direction = 2;
         }
         else if ((this.player.direction == 4) || (this.player.direction == 5) ||
        		 (this.player.direction == 6) || (this.player.direction == 7)) {
           this.player.direction = 6;
         }
         this.player.state = 0;
         }
   }
 
   private void enemysMove()
   {
     for (int i = 0; i < this.enemys.size(); i++) {
       Enemy enemy = (Enemy)this.enemys.get(i);
       if ((!isGrassLand(enemy.position.x, enemy.position.y + 2.0F)) && (!enemy.jumping)) {
         enemy.position.y += 2.0F;
       }
       if (enemy.position.y <= 206.0F) {
         if (enemy.state == 0) {
           if (this.player.position.x > enemy.position.x) {
             if (this.player.position.y < enemy.position.y) {
               if (enemy.position.y - this.player.position.y > 
               this.player.position.x - enemy.position.x) {
                 enemy.direction = 1;
               }
               else {
                 enemy.direction = 2;
               }
 
             }
             else if (-enemy.position.y + this.player.position.y > 
             this.player.position.x - enemy.position.x) {
               enemy.direction = 3;
             }
             else {
               enemy.direction = 2;
             }
 
           }
           else if (this.player.position.y < enemy.position.y) {
             if (enemy.position.y - this.player.position.y > -
            		 this.player.position.x + enemy.position.x) {
               enemy.direction = 7;
             }
             else {
               enemy.direction = 6;
             }
 
           }
           else if (-enemy.position.y + this.player.position.y > -
        		   this.player.position.x + enemy.position.x) {
             enemy.direction = 5;
           }
           else {
             enemy.direction = 6;
           }
 
         }
         else if (enemy.state == 1) {
           if (enemy.type == 0) {
             if (enemy.direction == 6) {
               enemy.position.x -= this.walkSpeed;
             }
             else if (enemy.direction == 2) {
               enemy.position.x += this.walkSpeed;
             }
           }
   else if (enemy.type == 1) {
    if ((enemy.direction == 6) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
               if ((enemy.position.x > 10.0F + this.mapPosition) && 
            		   (isGrassLand(enemy.position.x - 10.0F, enemy.position.y + 2.0F))) {
                 enemy.position.x -= this.walkSpeed;
               }
               else {
                 enemy.direction = 2;
                 enemy.position.x += this.walkSpeed;
               }
             }
else if ((enemy.direction == 2) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
       if ((enemy.position.x < this.backgroundSizeOfWidth / 3 + this.mapPosition) 
    		   && (isGrassLand(enemy.position.x + 10.0F, enemy.position.y + 2.0F))) {
                 enemy.position.x += this.walkSpeed;
               }
               else {
                 enemy.direction = 6;
                 enemy.position.x -= this.walkSpeed;
               }
             }
           }
           else if (enemy.type == 2) {
             if ((enemy.direction == 6) && (!enemy.jumping) && 
               (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
               if (isGrassLand(enemy.position.x - 3.0F, enemy.position.y + 2.0F)) {
                 enemy.position.x -= this.walkSpeed;
               }
               else {
                 enemy.state = 2;
                 enemy.jumping = true;
               }
 
             }
 
             if ((enemy.direction == 2) && (!enemy.jumping) && 
               (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
               if (isGrassLand(enemy.position.x + 3.0F, enemy.position.y + 2.0F)) {
                 enemy.position.x += this.walkSpeed;
               }
               else {
                 enemy.state = 2;
                 enemy.jumping = true;
               }
             }
 
           }
           else if (enemy.type == 3) {
             if ((enemy.direction == 6) && (isGrassLand(enemy.position.x, 
            		 enemy.position.y + 2.0F))) {
               if ((enemy.position.x > this.mapPosition + 10.0F) && 
            		   (isGrassLand(enemy.position.x - 3.0F, enemy.position.y + 2.0F))) {
                 enemy.position.x -= this.walkSpeed;
               }
               else if (enemy.position.x <= this.mapPosition + 10.0F) {
                 enemy.direction = 2;
                 enemy.position.x += this.walkSpeed;
               }
               else if ((isGrassLand(enemy.position.x, enemy.position.y + 2.0F)) 
            		   && (!isGrassLand(enemy.position.x - 3.0F, enemy.position.y + 2.0F))) {
                 if (isASafetyJump(new FloatPoint(enemy.position.x, enemy.position.y), 
                		 enemy.direction)) {
                   enemy.state = 2;
                   enemy.jumping = true;
                 }
                 else {
                   enemy.direction = 2;
                   enemy.position.x += this.walkSpeed;
                 }
               }
             }
 else if ((enemy.direction == 2) && (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) 
 { if ((enemy.position.x < this.mapPosition + this.backgroundSizeOfWidth / 3 - 10.0F)
		 && (isGrassLand(enemy.position.x + 3.0F, enemy.position.y + 2.0F))) {
                 enemy.position.x += this.walkSpeed;
               }
               else if (enemy.position.x >= this.mapPosition + 
            		   this.backgroundSizeOfWidth / 3 - 10.0F) {
                 enemy.direction = 6;
                 enemy.position.x -= this.walkSpeed;
               }
               else if ((isGrassLand(enemy.position.x, enemy.position.y + 2.0F)) 
            		   && (!isGrassLand(enemy.position.x + 3.0F, enemy.position.y + 2.0F))) {
                 if (isASafetyJump(new FloatPoint(enemy.position.x, enemy.position.y), 
                		 enemy.direction)) {
                   enemy.state = 2;
                   enemy.jumping = true;
                 }
                 else {
                   enemy.direction = 6;
                   enemy.position.x -= this.walkSpeed;
                 }
               }
             }
           }
         }
         else if (enemy.state == 2) {
           if ((enemy.direction == 2) && (enemy.jumping)) {
             enemy.position.x += this.walkSpeed;
           }
           else if ((enemy.direction == 6) && (enemy.jumping)) {
             enemy.position.x -= this.walkSpeed;
           }
           if ((enemy.jumpFinished) && 
             (isGrassLand(enemy.position.x, enemy.position.y + 2.0F))) {
             enemy.state = 1;
             enemy.jumping = false;
           }
         }
 
       }
       else if (!isGrassLand(enemy.position.x, enemy.position.y + 2.0F))
         enemy.state = 4;
     }
   }
 
   private boolean isASafetyJump(FloatPoint position, int direction)
   {
     if (direction == 2) {
       if ((!this.grassLands[(this.grassLands.length - 2)].beTouched) && 
         (position.x < this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F)) {
         if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 3) {
        float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
           if (delta < 23.399999999999999D) {
             delta = 23.4F - delta + 23.4F;
           }
           if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
             return true;
           }
         }
         if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 1) {
         float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 0.8F;
           if (delta > 25.799999F) {
             delta = 25.799999F - (delta - 25.799999F);
           }
           if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
             return true;
           }
         }
       }
 
       if ((!this.grassLands[(this.grassLands.length - 1)].beTouched) && 
         (position.x < this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F)) {
         if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 3) {
         float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
           if (delta < 32.399999999999999D) {
             delta = 32.400002F - delta + 32.400002F;
           }
           if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
             return true;
           }
         }
         if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 1) {
         float delta = this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 0.8F;
           if (delta > 34.799999F) {
             delta = 34.799999F - (delta - 34.799999F);
           }
           if ((position.x + 40.0F > delta) && (position.x + 40.0F < delta + 32.0F)) {
             return true;
           }
         }
       }
       float x = position.x + 20.0F;
       for (float y = position.y - 40.0F; 
         (y < 206.0F) && (x < this.mapPosition + this.backgroundSizeOfWidth / 3 - 14.0F);
    		   y += 2.0F) {
         if (isGrassLand(x, y + 2.0F))
           return true;
         x += 1.0F;
       }
 
       return false;
     }
     if (direction == 6) {
       if ((!this.grassLands[(this.grassLands.length - 2)].beTouched) && 
      (position.x > this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 32.0F)) {
         if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 3) {
         float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
           if (delta < 23.399999999999999D) {
             delta = 23.4F - delta + 23.4F;
           }
           if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
             return true;
           }
         }
         if (this.grassLands[(this.grassLands.length - 2)].movingDirection == 1) {
           float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F + 0.8F;
           if (delta > 25.799999F) {
             delta = 25.799999F - (delta - 25.799999F);
           }
           if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
             return true;
           }
         }
       }
 
       if ((!this.grassLands[(this.grassLands.length - 1)].beTouched) && 
         (position.x > this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F + 32.0F))
       {if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 3) {
         float delta = this.grassLands[(this.grassLands.length - 2)].position.x * 32.0F - 0.8F;
           if (delta < 32.399999999999999D) {
             delta = 32.400002F - delta + 32.400002F;
           }
           if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
             return true;
           }
         }
         if (this.grassLands[(this.grassLands.length - 1)].movingDirection == 1) {
          float delta = this.grassLands[(this.grassLands.length - 1)].position.x * 32.0F+ 0.8F;
           if (delta > 34.799999F) {
             delta = 34.799999F - (delta - 34.799999F);
           }
           if ((position.x - 40.0F > delta) && (position.x - 40.0F < delta + 32.0F)) {
             return true;
           }
         }
       }
 
       float x = position.x - 20.0F;
       for (float y = position.y - 40.0F; 
         (y < 206.0F) && (x > 14.0F); y += 2.0F) {
         if (isGrassLand(x, y + 2.0F))
           return true;
         x -= 1.0F;
       }
 
       return false;
     }
 
     return false;
   }
 
   private void initGrassLands()
   {
     FloatPoint[] points = { new FloatPoint(1.0F, 110.0F), new FloatPoint(5.0F, 142.0F), 
    		 new FloatPoint(8.0F, 174.0F), 
       new FloatPoint(9.0F, 206.0F), new FloatPoint(11.0F, 174.0F), 
       new FloatPoint(13.0F, 142.0F), new FloatPoint(18.0F, 206.0F), 
       new FloatPoint(19.0F, 158.0F), new FloatPoint(27.0F, 110.0F), 
       new FloatPoint(36.0F, 110.0F), new FloatPoint(42.0F, 78.0F), 
       new FloatPoint(43.0F, 206.0F), new FloatPoint(46.0F, 160.0F), 
       new FloatPoint(49.0F, 142.0F), new FloatPoint(53.0F, 206.0F), 
       new FloatPoint(57.0F, 110.0F), new FloatPoint(59.0F, 174.0F),
       new FloatPoint(62.0F, 174.0F), new FloatPoint(63.0F, 78.0F), 
       new FloatPoint(65.0F, 158.0F), new FloatPoint(67.0F, 142.0F), 
       new FloatPoint(69.0F, 110.0F), new FloatPoint(72.0F, 142.0F), 
       new FloatPoint(72.0F, 206.0F), new FloatPoint(73.0F, 174.0F),
       new FloatPoint(76.0F, 110.0F), new FloatPoint(77.0F, 78.0F), 
       new FloatPoint(77.0F, 206.0F), new FloatPoint(78.0F, 158.0F),
       new FloatPoint(80.0F, 110.0F), new FloatPoint(81.0F, 142.0F), 
       new FloatPoint(84.0F, 206.0F), new FloatPoint(88.0F, 174.0F), 
       new FloatPoint(91.0F, 142.0F), new FloatPoint(93.0F, 110.0F), 
       new FloatPoint(93.0F, 206.0F), new FloatPoint(94.0F, 158.0F), 
       new FloatPoint(98.0F, 142.0F), new FloatPoint(99.0F, 174.0F), 
       new FloatPoint(24.0F, 110.0F), new FloatPoint(33.0F, 110.0F) };
     int[] lengths = { 22, 3, 1, 2, 1, 2, 2, 3, 5, 8, 16, 3, 2, 7, 6, 7, 2, 2, 5, 1, 3, 2, 2, 1, 3, 2, 
       2, 1, 1, 2, 5, 3, 2, 2, 5, 22, 4, 1, 1, 1, 1 };
     for (int i = 0; i < points.length - 2; i++) {
       GrassLand gl = new GrassLand(points[i], lengths[i]);
       this.grassLands[i] = gl;
     }
     GrassLand movingGrassLand1 = new GrassLand(new FloatPoint(24.0F, 110.0F), 1);
     GrassLand movingGrassLand2 = new GrassLand(new FloatPoint(33.0F, 110.0F), 1);
     movingGrassLand1.movingDirection = 3;
     movingGrassLand2.movingDirection = 3;
     this.grassLands[(this.grassLands.length - 2)] = movingGrassLand1;
     this.grassLands[(this.grassLands.length - 1)] = movingGrassLand2;
   }
 
   private boolean isGrassLand(float x, float y) {
     if (this.player.towardsRight) {
       for (int i = 0; i < this.grassLands.length; i++) {
         if ((x > this.grassLands[i].position.x * 32.0F - 7.0F) && (x < 
          (this.grassLands[i].position.x + this.grassLands[i].length) * 32.0F + 3.0F) && 
           (y > this.grassLands[i].position.y) && (y < this.grassLands[i].position.y + 5.0F))
           return true;
       }
     }
     else if (this.player.towardsLeft) {
       for (int i = 0; i < this.grassLands.length; i++) {
         if ((x > this.grassLands[i].position.x * 32.0F - 7.0F) && (x < 
        	(this.grassLands[i].position.x + this.grassLands[i].length) * 32.0F + 1.0F) && 
           (y > this.grassLands[i].position.y) && (y < this.grassLands[i].position.y + 5.0F))
           return true;
       }
     }
     else if ((!this.player.towardsLeft) && (!this.player.towardsRight)) {
       for (int i = 0; i < this.grassLands.length; i++) {
        if ((x > this.grassLands[i].position.x * 32.0F) && (x < (this.grassLands[i].position.x 
        		 + this.grassLands[i].length) * 32.0F) && 
           (y > this.grassLands[i].position.y) && (y < this.grassLands[i].position.y + 5.0F))
           return true;
       }
     }
     return false;
   }
 
   private void enemysDeathCheck() {
     for (int i = 0; i < this.enemys.size(); i++) {
       if ((((Enemy)this.enemys.get(i)).state == 5) || 
         (((Enemy)this.enemys.get(i)).position.x > this.mapPosition + 
        		 this.backgroundSizeOfWidth / 3)) {
         if (((Enemy)this.enemys.get(i)).visible)
           this.enemys.remove(i);
       }
       else if (((Enemy)this.enemys.get(i)).position.x < this.mapPosition) {
         this.enemys.remove(i);
       }
     }
     for (int i = 0; i < this.enemys.size(); i++) {
       Enemy e = (Enemy)this.enemys.get(i);
       for (int j = 0; j < this.heroBullets.size(); j++) {
         Bullet b = (Bullet)this.heroBullets.get(j);
         if (e.touchHeroBulletCheck(b)) {
           if ((e.state == 4) || (e.state == 5)) break;
           e.beShotted(Bullet.energy);
           this.heroBullets.remove(j);
 
           break;
         }
       }
     }

   }
 
   private void drawHeroBullets(Graphics2D g)
   {
     for (int i = 0; i < this.heroBullets.size(); i++)
     {
       int x = (int)((Bullet)this.heroBullets.get(i)).position.x;
       int y = (int)((Bullet)this.heroBullets.get(i)).position.y;
       boolean isAlive = Bullet.isAlive;
    if ((x < this.mapPosition) || (x > this.backgroundSizeOfWidth / 3.0F + 
    this.mapPosition) || (y < 0) || (y > this.backgroundSizeOfHeight / 3.0F) ||(!isAlive)) {
         this.heroBullets.remove(i);
       }
       else
         ((Bullet)this.heroBullets.get(i)).drawBubblet(g, this.mapPosition);
     }
   }
 
   private void drawEnemyBullets(Graphics2D g)
   {
     for (int i = 0; i < this.enemyBullets.size(); i++) {
       int x = (int)((Bullet)this.enemyBullets.get(i)).position.x;
       int y = (int)((Bullet)this.enemyBullets.get(i)).position.y;
       boolean isAlive = Bullet.isAlive;
       if ((x < this.mapPosition) || (x > this.backgroundSizeOfWidth / 3.0F + this.mapPosition) || (y < 0) || (y > this.backgroundSizeOfHeight / 3.0F) || (!isAlive)) {
         this.enemyBullets.remove(i);
       }
       else
         ((Bullet)this.enemyBullets.get(i)).drawBubblet(g, this.mapPosition);
     }
   }
 
   private void drawNPC(Graphics2D g)
   {
     this.grassLands[(this.grassLands.length - 2)].drawMovingGrassLand(this.player, 
       23.4F, 25.799999F, (int)this.mapPosition, this.backgroundSizeOfWidth, this, g);
     this.grassLands[(this.grassLands.length - 1)].drawMovingGrassLand(this.player, 
    	32.400002F, 34.799999F, (int)this.mapPosition, this.backgroundSizeOfWidth, this, g);
   }
 
   private void setKeyStateWhenKeyPressed(KeyEvent e) {
     if ((this.player.state != 4) && (this.player.state != 5)) {
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
         this.upDown = true;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
         this.downDown = true;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
         this.leftDown = true;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
         this.rightDown = true;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("K"))
         this.jumpDown = true;
     }
   }
 
   private void setKeyStateWhenKeyReleased(KeyEvent e)
   {
     if ((this.player.state != 4) && (this.player.state != 5)) {
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
         this.upDown = false;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
         this.downDown = false;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
         this.leftDown = false;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
         this.rightDown = false;
       }
       if (KeyEvent.getKeyText(e.getKeyCode()).equals("K"))
         this.jumpDown = false;
     }
   }
 
   private void heroMove()
   {
     if ((this.player.state != 4) && (this.player.state != 5))
       if ((!this.player.jumping) && (!this.jumpDown) && (!this.leftDown) && 
    		   (!this.rightDown) && (!this.upDown) && (!this.downDown)) {
         this.player.state = 0;
         if (this.player.towardsLeft) {
           this.player.direction = 6;
         }
         else if (this.player.towardsRight) {
           this.player.direction = 2;
         }
 
       }
       else if (this.player.jumping) {
         if (this.upDown) {
           this.player.direction = 0;
           this.player.state = 2;
           if ((this.leftDown) && (!this.showBossScene)) {
             this.player.direction = 7;
             this.player.towardsLeft = true;
             this.player.towardsRight = false;
             if (this.player.position.x > 8.0F + this.mapPosition)
               this.player.position.x -= this.walkSpeed;
           }
           else if ((this.rightDown) && (!this.showBossScene)) {
             this.player.direction = 1;
             this.player.towardsLeft = false;
             this.player.towardsRight = true;
             moveForwardToRight();
           }
         }
         else if (this.downDown) {
           this.player.direction = 4;
           this.player.state = 2;
           if (this.leftDown) {
             this.player.direction = 5;
             this.player.towardsLeft = true;
             this.player.towardsRight = false;
             if (this.player.position.x > 8.0F + this.mapPosition)
               this.player.position.x -= this.walkSpeed;
           }
           else if ((this.rightDown) && (!this.showBossScene)) {
             this.player.direction = 3;
             this.player.towardsLeft = false;
             this.player.towardsRight = true;
             moveForwardToRight();
           }
         }
         else if ((this.leftDown) && (!this.showBossScene)) {
           this.player.direction = 6;
           this.player.state = 2;
           this.player.towardsLeft = true;
           this.player.towardsRight = false;
           if (this.player.position.x > 8.0F + this.mapPosition)
             this.player.position.x -= this.walkSpeed;
         }
         else if ((this.rightDown) && (!this.showBossScene)) {
           this.player.direction = 2;
           this.player.state = 2;
           this.player.towardsLeft = false;
           this.player.towardsRight = true;
           moveForwardToRight();
         }
 
       }
       else if ((this.leftDown) && (!this.showBossScene)) {
         if ((this.jumpDown) && (isGrassLand(this.player.position.x, 
        		 this.player.position.y + 1.0F)))
         {
           this.player.direction = 6;
           this.player.state = 2;
           this.player.towardsLeft = true;
           this.player.towardsRight = false;
           this.player.jumping = true;
           this.player.jumpFinished = false;
         }
         else if (this.upDown) {
           this.player.state = 1;
           this.player.direction = 7;
           this.player.towardsLeft = true;
           this.player.towardsRight = false;
           if (this.player.position.x > 8.0F + this.mapPosition)
             this.player.position.x -= this.walkSpeed;
         }
         else if (this.downDown) {
           this.player.direction = 5;
           this.player.state = 1;
           this.player.towardsLeft = true;
           this.player.towardsRight = false;
           if (this.player.position.x > 8.0F + this.mapPosition)
             this.player.position.x -= this.walkSpeed;
         }
         else
         {
           this.player.state = 1;
           this.player.direction = 6;
           this.player.towardsLeft = true;
           this.player.towardsRight = false;
           if (this.player.position.x > 8.0F + this.mapPosition)
             this.player.position.x -= this.walkSpeed;
         }
       }
       else if ((this.rightDown) && (!this.showBossScene)) {
         if ((this.jumpDown) && (isGrassLand(this.player.position.x, 
        		 this.player.position.y + 1.0F))) {
           this.player.direction = 2;
           this.player.state = 2;
           this.player.towardsLeft = false;
           this.player.towardsRight = true;
           this.player.jumping = true;
           this.player.jumpFinished = false;
         }
         else if (this.upDown) {
           this.player.direction = 1;
           this.player.state = 1;
           this.player.towardsLeft = false;
           this.player.towardsRight = true;
           moveForwardToRight();
         }
         else if (this.downDown) {
           this.player.direction = 3;
           this.player.state = 1;
           this.player.towardsLeft = false;
           this.player.towardsRight = true;
           moveForwardToRight();
         }
         else
         {
           this.player.direction = 2;
           this.player.state = 1;
           this.player.towardsLeft = false;
           this.player.towardsRight = true;
           moveForwardToRight();
         }
       }
       else if (this.upDown) {
         if ((this.jumpDown) && (isGrassLand(this.player.position.x,
        		 this.player.position.y + 1.0F))) {
           this.player.direction = 0;
           this.player.state = 2;
           this.player.jumpFinished = false;
           this.player.jumping = true;
         }
         else
         {
           this.player.state = 0;
           this.player.direction = 0;
         }
       }
       else if (this.downDown) {
         if (this.jumpDown) {
           this.player.state = 1;
         }
         else
         {
           this.player.state = 3;
           if (this.player.direction == 3) {
             this.player.direction = 2;
           }
           else if (this.player.direction == 5) {
             this.player.direction = 6;
           }
         }
       }
       else if ((this.jumpDown) && (isGrassLand(this.player.position.x, 
    		   this.player.position.y + 1.0F)))
       {
         this.player.state = 2;
         this.player.jumping = true;
         this.player.jumpFinished = false;
       }
   }
 
   private void moveForwardToRight()
   {
     if ((this.player.state != 4) && (this.player.state != 5))
       if ((this.player.position.x - this.mapPosition < this.backgroundSizeOfWidth / 6.0F)
    		   || (this.mapPosition >= this.sceneWidth - this.backgroundSizeOfWidth / 3)) {
   if (this.player.position.x - this.mapPosition < (this.backgroundSizeOfWidth - 20.0F) / 3.0F)
           this.player.position.x += this.walkSpeed;
       }
       else {
         if (this.mapPosition < this.sceneWidth - this.backgroundSizeOfWidth / 3) {
           this.mapPosition += this.walkSpeed;
         }
         else {
           this.mapPosition = (this.sceneWidth - this.backgroundSizeOfWidth / 3.0F);
         }
         this.player.position.x = (this.backgroundSizeOfWidth / 6.0F + this.mapPosition);
       }
   }
 
   public static void main(String[] args)
   { new Thread(new MainFrame()).start();
   }
 }

