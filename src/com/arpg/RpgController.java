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
import com.arpg.index.IndexController;
import com.arpg.fight.FightController;
import com.arpg.sprite.Sprite;
import com.arpg.sprite.SpriteState;
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
  private IndexController ic;
  // 是否可以发生战斗
  private boolean fighting = false;
  // 当前英雄在追逐精灵(在自由寻路时此为null)

  private MapContainer map;

  public RpgController(MapContainer mc, IndexController idx){
    this.map = mc;
	this.ic = idx;
  }

  public void handle(Event event){
    if(sc != null && sc.isRunning()){
      sc.handle(event);
      return;
    }

    super.preMouseEvent(event);
	Sprite hero = map.getHero();
	if(hero != null)
	{
		if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
		  MouseEvent me = (MouseEvent) event;
		  int trueX = (int) (me.getX() - map.getOffsetX()) / RpgConstants.CS;
		  int trueY = (int) (me.getY() - map.getOffsetY()) / RpgConstants.CS;
		  Sprite pointTo = hero.canTalkWith(trueX, trueY);
		  if(pointTo == null){
		  	ic.actions.add("move "+hero.getId()+" "+map.getId()+" "+trueX+" "+trueY);
		  }else{
		  	if(hero != pointTo) ic.actions.add("goto "+hero.getId()+" "+pointTo.getId());
		  }
		}else if(event.getEventType() == KeyEvent.KEY_PRESSED){
		  hero.paths = null;
		  KeyEvent ke = (KeyEvent) event;
		  KeyCode code = ke.getCode();
		  if(code == KeyCode.UP || code == KeyCode.W){
		  	ic.actions.add("moveup "+hero.getId());
		  }else if(code == KeyCode.DOWN || code == KeyCode.S){
		  	ic.actions.add("movedown "+hero.getId());
		  }else if(code == KeyCode.LEFT || code == KeyCode.A){
		  	ic.actions.add("moveleft "+hero.getId());
		  }else if(code == KeyCode.RIGHT || code == KeyCode.D){
		  	ic.actions.add("moveright "+hero.getId());
		  }
		}
	}
  }

  public void draw(GraphicsContext context){
    map.draw(context, getMousePoint());
  	if(sc != null)
	{
		if(sc.isRunning()) sc.draw(context);
		else 
		{
			if(sc.talker1.getId() == ic.getHeroId() && sc.talker2.getSpriteType()==SpriteType.MONSTER)
			{
				this.fighting = true;
				this.fc = new FightController(this, sc.talker1, sc.talker2);
			}
			else if(sc.talker2.getId() == ic.getHeroId() && sc.talker1.getSpriteType()==SpriteType.MONSTER)
			{
				this.fighting = true;
				this.fc = new FightController(this, sc.talker2, sc.talker1);
			}
			sc.talker1.talkTo = null;
			sc.talker2.talkTo = null;
			sc = null;
		}
			
		return;
	}
	if(ic.actions.size() > 0)
	{
		String action = ic.actions.get(0);
		while(ic.act(action))
		{
			ic.actions.remove(0);
			if(ic.actions.size() == 0) break;
			action = ic.actions.get(0);
		}
	}
    spriteMove();

  }

  /**
   * 角色群移动
   */
  private void spriteMove(){
    /* 处理角色自动寻路 */

    for(Sprite role : map.getRoles()){
	  if(role.getSpriteState()==SpriteState.DEAD)
	  {
	  	map.removeSprite(role);
		continue;
	  }
	  if(role.talkTo != null){
	  	Sprite partner = role.talkTo;
	  	if(role.getX() >= partner.getX()-1 && role.getX() <= partner.getX()+1
		 &&role.getY() >= partner.getY()-1 && role.getY() <= partner.getY()+1)
		 {
			sc = new ScriptController(role.getScript(partner.getId()),ic,role,partner);
			return;
		}
      }
	  if(role.following != null) role.dest = role.following;
	  if(role.isMoving()){
        role.move();
	  }else if(role.dest!=null) {
	  	Sprite dest = role.dest;
	  	if(role.getX() >= dest.getX()-1 && role.getX() <= dest.getX()+1
		 &&role.getY() >= dest.getY()-1 && role.getY() <= dest.getY()+1)
		{
			role.paths = null;
			role.dest = null;
		}
	  	else if(role.paths != null && role.paths.size() > 1 && !role.isMoving()){
			int direction = RpgConstants.getDirection(role.paths.get(0),role.paths.get(1));
			role.setDirection(direction);
			role.setMoving(true);
			role.paths.remove(0);
		}
		else if(role.paths == null || dest.isMoving())
		{
			role.paths = map.findPath(role.getPoint(), dest.getPoint());
		}
	  }else if(role.paths != null && role.paths.size() > 1 && !role.isMoving()){
		  int direction = RpgConstants.getDirection(role.paths.get(0), role.paths.get(1));
		  role.setDirection(direction);
		  role.setMoving(true);
		  role.paths.remove(0);
		  if(role.paths.size() < 2) role.paths = null;
      }else if(role.randomMoving() && MathUtils.getRandomGen().nextDouble() < Sprite.PROB_MOVE
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
		  for(Sprite role : map.getRoles())
		  {
		  	role.print();
		  	if(role.getId().equals(ic.getHeroId()))
			{
				MapContainer temp = event.send(role);
				if(temp != null)
				{
					ic.actions.add("changemap "+role.getId()+" "+map.getId()+" "
						+temp.getId()+" "+event.getTransfer().x+" "+event.getTransfer().y);
					next = new RpgController(temp,ic);
			    }
			}
		  }
          break;
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
}
