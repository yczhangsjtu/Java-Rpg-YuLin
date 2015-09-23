package com.arpg;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.arpg.avg.ScriptController;
import com.arpg.fight.FightController;
import com.arpg.sprite.Sprite;
import com.arpg.sprite.SpriteType;
import com.arpg.utils.MathUtils;
import com.arpg.utils.RpgConstants;

/**
 * 游戏地图控制器
 * 
 * @author zhou
 *
 */
public final class RpgController extends AbstarctController{
  /* 游戏剧情脚本控制器 */
  private ScriptController sc;
  private FightController fc;
  // 是否可以发生战斗
  private boolean fighting = false;
  // 当前英雄在追逐精灵(在自由寻路时此为null)
  private Sprite dest;

  private MapContainer map;
  private Sprite hero;
  // 角色自动寻路的路径集合
  // private List<Point> paths;

  public RpgController(MapContainer mc){
    this.map = mc;
    this.hero = mc.getHero();
  }

  public void handle(Event event){
    if(sc != null && sc.isRunning()){
      sc.handle(event);
      return;
    }

    super.preMouseEvent(event);
    if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
      MouseEvent me = (MouseEvent) event;
      int trueX = (int) (me.getX() - map.getOffsetX()) / RpgConstants.CS;
      int trueY = (int) (me.getY() - map.getOffsetY()) / RpgConstants.CS;
      Sprite pointTo = hero.talkWith(trueX, trueY);
      if(pointTo == null){
        this.hero.paths = map.findPath(hero.getPoint(), new Point(trueX, trueY));
      }else{
        dest = pointTo;
        this.hero.paths = hero.isAttackArea(RpgConstants.CS * 3 / 2, pointTo) ? null : map.findPath(
            hero.getPoint(), new Point(trueX, trueY));

        this.sc = new ScriptController(pointTo.getScript());
      }
    }else if(event.getEventType() == KeyEvent.KEY_PRESSED){
      this.hero.paths = null;
      KeyEvent ke = (KeyEvent) event;
      KeyCode code = ke.getCode();
      if(code == KeyCode.UP || code == KeyCode.W){
        setHeroMove(RpgConstants.UP);
      }else if(code == KeyCode.DOWN || code == KeyCode.S){
        setHeroMove(RpgConstants.DOWN);
      }else if(code == KeyCode.LEFT || code == KeyCode.A){
        setHeroMove(RpgConstants.LEFT);
      }else if(code == KeyCode.RIGHT || code == KeyCode.D){
        setHeroMove(RpgConstants.RIGHT);
      }
    }
  }

  private void setHeroMove(int direction){
    if(!hero.isMoving()){
      hero.setDirection(direction);
      hero.setMoving(true);
    }
  }

  public void draw(GraphicsContext context){
    /* 如果不存在目标或未追到目标,角色正常移动 */
    if(!isChase()) spriteMove();

    map.draw(context, getMousePoint());

    scriptAndFight(context);
  }

  /**
   * 处理游戏剧情脚本,当剧情脚本结束时,执行战斗系统
   * 
   * @param context
   */
  private void scriptAndFight(GraphicsContext context){
    if(!isChase()) return;

    if(sc.isRunning()){
      sc.draw(context);
	  if(sc.state == 123) dest.follow();
	  if(sc.state == 567) 
	  {
	  	dest.removeScript();
		sc.setState(-1);
	  }
    }else if(dest.getSpriteType() == SpriteType.MONSTER){
      this.fighting = true;
      this.fc = new FightController(this, hero, dest);
      dest = null;
    }else{
      dest = null;
    }
  }

  /**
   * 角色群移动
   */
  private void spriteMove(){
    /* 处理角色自动寻路 */
    if(this.hero.paths != null && this.hero.paths.size() > 1 && !hero.isMoving()){
      int direction = RpgConstants.getDirection(this.hero.paths.get(0), this.hero.paths.get(1));
      hero.setDirection(direction);
      hero.setMoving(true);
      this.hero.paths.remove(0);
    }

    for(Sprite role : map.getRoles()){
      if(role.isMoving()){
        role.move();
	  }else if(!role.isHero() && role.isFollowing())
	  {
	  	if(role.paths != null && role.paths.size() > 1 && !role.isMoving()){
			int direction = RpgConstants.getDirection(role.paths.get(0),role.paths.get(1));
			role.setDirection(direction);
			role.setMoving(true);
			role.paths.remove(0);
		}
		else if(this.hero.isMoving() && !role.isMoving())
		{
			role.paths = map.findPath(role.getPoint(), this.hero.getPoint());
		}
      }else if(!role.isHero() && MathUtils.getRandomGen().nextDouble() < Sprite.PROB_MOVE
          && !role.isInjury()){
        role.setDirection(MathUtils.getRandomGen().nextInt(8));
        role.setMoving(MathUtils.getRandomGen().nextInt(4) == 1);
      }
    }
  }

  @Override
  public Controller invoke(){
    Controller next = this;
    if(fighting){
      next = fc;
      fighting = false;
    }else{
      List<Tepleporter> transfers = map.getTransfers();
      for(Tepleporter event : transfers){
        MapContainer temp = event.send(hero);
        if(temp != null){
          hero.setPosition(event.getTransfer());
          temp.initHero(hero);
		  for(Sprite role : map.getRoles())
		  {
		  	if(role.isFollowing())
			{
				role.setPosition(event.getTransfer());
				temp.addCharacter(role);
			}
		  }
          next = new RpgController(temp);
          break;
        }
      }
    }

    return next;
  }

  /**
   * 删除精灵
   * 
   * @param sprite
   */
  public void removeSprite(Sprite sprite){
    map.removeSprite(sprite);
  }

  /**
   * 是否追逐到目标
   * 
   * @return
   */
  private boolean isChase(){
    boolean value = dest != null && hero.isAttackArea(RpgConstants.CS * 3 / 2, dest);
    if(value) this.hero.paths = null;

    return value;
  }

}
