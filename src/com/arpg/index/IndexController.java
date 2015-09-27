package com.arpg.index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Point;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.List;
import java.util.LinkedList;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import com.arpg.AbstarctController;
import com.arpg.Controller;
import com.arpg.MapContainer;
import com.arpg.RpgController;
import com.arpg.fight.Magic;
import com.arpg.sprite.Sprite;
import com.arpg.sprite.SpriteType;
import com.arpg.utils.IOUtils;
import com.arpg.utils.ImageUtils;
import com.arpg.utils.JavafxImageWriter;
import com.arpg.utils.ResourceManager;
import com.arpg.utils.RpgConstants;
import com.arpg.utils.LineReader;

/**
 * 游戏启动界面
 * 
 * @author zhou
 *
 */
public final class IndexController extends AbstarctController{
	private static Image bg = ResourceManager.loadImage("resource/image/index/index.png");
	private Controller next;
	private StackPane menu;

	private HashMap<String,MapContainer> maps;
	private HashMap<String,Sprite> sprites;
	public LinkedList<String> actions;
	public String hero = null;

	public IndexController(){
		menu = new StackPane();
		menu.getChildren().add(new ImageView(initIndexImage(300, 240)));
		menu.setLayoutX(RpgConstants.CANVAS_WIDTH / 2 - 200);
		menu.setLayoutY(RpgConstants.CANVAS_HEIGHT / 2 - 120);
		maps = new HashMap<String,MapContainer>();
		sprites = new HashMap<String,Sprite>();
		actions = new LinkedList<String>();
	}

	@Override
	public void draw(GraphicsContext context){
		context.drawImage(bg, 0, 0);
	}

	@Override
	public void handle(Event event){
		if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
			enter();
		}
	}

	public String getHeroId()
	{
		return hero;
	}

	public void exec(String script)
	{
		String instruct = null, obj = null;
		String param1 = null, param2 = null, param3 = null, param4 = null, param5 = null;

		StringTokenizer token = new StringTokenizer(script);
		if(token.hasMoreTokens())
			instruct = token.nextToken();
		if(token.hasMoreTokens())
			obj = token.nextToken();
		if(token.hasMoreTokens())
			param1 = token.nextToken();
		if(token.hasMoreTokens())
			param2 = token.nextToken();
		if(token.hasMoreTokens())
			param3 = token.nextToken();
		if(token.hasMoreTokens())
			param4 = token.nextToken();
		if(token.hasMoreTokens())
			param5 = token.nextToken();

		if("map".equals(instruct)){
			maps.put(obj,new MapContainer(param1,param2,param3,this,obj));
		}else if("sprite".equals(instruct)){
			sprites.put(obj,new Sprite(param1,getRpgConstant(param2),obj));
		}else if("setpos".equals(instruct)){
			int X = Integer.parseInt(param1), Y = Integer.parseInt(param2);
			sprites.get(obj).setPosition(new Point(X,Y));
		}else if("setname".equals(instruct)){
			sprites.get(obj).setName(param1);
		}else if("addmagic".equals(instruct)){
			int f = Integer.parseInt(param3);
			Color c = getColor(param4);
			sprites.get(obj).addMagic(new Magic(param1, param2, f, c));
		}else if("setdefeatless".equals(instruct)){
			sprites.get(obj).setDefeatless();
		}else if("sethero".equals(instruct)){
			this.hero = obj;
		}else if("addscript".equals(instruct)){
			sprites.get(obj).addScript(param1,param2);
		}else if("settype".equals(instruct)){
			sprites.get(obj).setSpriteType(getType(param1));
		}else if("addsprite".equals(instruct)){
			maps.get(obj).addCharacter(sprites.get(param1));
		}else if("addaction".equals(instruct)){
			actions.add(transformToAction(obj));
		}else if("readaction".equals(instruct)){
			readActionFile(obj);
		}else if("transfer".equals(instruct)){
			int x1 = Integer.parseInt(param1);
			int y1 = Integer.parseInt(param2);
			int x2 = Integer.parseInt(param4);
			int y2 = Integer.parseInt(param5);
			maps.get(obj).addTransfer(x1,y1,maps.get(param3),x2,y2);
		}else if("follow".equals(instruct)){
			sprites.get(obj).follow(sprites.get(param1));
		}else if("start".equals(instruct)){
			next = new RpgController(maps.get(obj),this);
		}
	}

	public boolean act(String action)
	{
		String instruct = null, obj = null;
		String param1 = null, param2 = null, param3 = null, param4 = null, param5 = null;
		
		StringTokenizer token = new StringTokenizer(action);
		if(token.hasMoreTokens())
			instruct = token.nextToken();
		if(token.hasMoreTokens())
			obj = token.nextToken();
		if(token.hasMoreTokens())
			param1 = token.nextToken();
		if(token.hasMoreTokens())
			param2 = token.nextToken();
		if(token.hasMoreTokens())
			param3 = token.nextToken();
		if(token.hasMoreTokens())
			param4 = token.nextToken();
		if(token.hasMoreTokens())
			param5 = token.nextToken();

		if("move".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			int x = Integer.parseInt(param2), y = Integer.parseInt(param3);
			sprite.paths = maps.get(param1).findPath(sprite.getPoint(), new Point(x,y));
			return true;
		}
		if("moveup".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			if(!sprite.isMoving()){
				sprite.setDirection(RpgConstants.UP);
				sprite.setMoving(true);
			}
			return true;
		}
		if("movedown".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			if(!sprite.isMoving()){
				sprite.setDirection(RpgConstants.DOWN);
				sprite.setMoving(true);
			}
			return true;
		}
		if("moveleft".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			if(!sprite.isMoving()){
				sprite.setDirection(RpgConstants.LEFT);
				sprite.setMoving(true);
			}
			return true;
		}
		if("moveright".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			if(!sprite.isMoving()){
				sprite.setDirection(RpgConstants.RIGHT);
				sprite.setMoving(true);
			}
			return true;
		}
		if("goto".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			Sprite dest = sprites.get(param1);
			sprite.dest = dest;
			sprite.startTalk(dest);
			return true;
		}
		if("changemap".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			MapContainer map = maps.get(param1);
			MapContainer dest = maps.get(param2);
			int x = Integer.parseInt(param3), y = Integer.parseInt(param4);
			map.removeSprite(sprite);
			dest.addCharacter(sprite);
			sprite.setPosition(new Point(x,y));
			for(Sprite follower : map.getRoles())
			{
				if(follower.following == sprite)
				{
					map.removeSprite(follower);
					dest.addCharacter(follower);
					follower.setPosition(new Point(x,y));
				}
			}
			return true;
		}
		if("checkpos".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			int x = Integer.parseInt(param2), y = Integer.parseInt(param3);
			return sprite.getX() == x && sprite.getY() == y &&
			   sprite.getMapContainer() == maps.get(param1);
		}
		if("checkclose".equals(instruct))
		{
			Sprite sprite = sprites.get(obj);
			Sprite dest = sprites.get(param1);
			return sprite.getX() >= dest.getX()-1 && sprite.getX() <= dest.getX()+1
			 &&sprite.getY() >= dest.getY()-1 && sprite.getY() <= dest.getY()+1;
		}
		if("disablecontrol".equals(instruct))
		{
			this.hero = "";
			return true;
		}
		if("sethero".equals(instruct)){
			this.hero = obj;
			return true;
		}

		return true;
	}

	private String transformToAction(String script)
	{
		return script.replace('_',' ');
	}

	private void readActionFile(String filename)
	{
		String path = "src/com/arpg/index/"+filename;
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(path));
			String statement = null;
			while((statement = reader.readLine()) != null){
				statement = statement.trim();
				if(statement.length() == 0)
					continue;
				if(statement.startsWith("//") || statement.startsWith("#"))
					continue;
				actions.add(statement);
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		IOUtils.closeQuietly(reader);
	}
  }

  private void loadIndexFile(String filename)
  {
  	String path = "src/com/arpg/index/"+filename;
  	BufferedReader reader = null;
	LineReader scripts = new LineReader();
  	try
	{
		reader = new BufferedReader(new FileReader(path));
		String statement = null;
		while((statement = reader.readLine()) != null){
			statement = statement.trim();
			if(statement.length() == 0)
				continue;
			if(statement.startsWith("//") || statement.startsWith("#"))
				continue;
			scripts.addLine(statement);
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		IOUtils.closeQuietly(reader);
	}

	while(scripts.hasNext()){
		String script = scripts.next();
		exec(script);
	}
  }

  private SpriteType getType(String type)
  {
  	if("NPC".equals(type)) return SpriteType.NPC;
  	if("MONSTER".equals(type)) return SpriteType.MONSTER;
	return null;
  }

  private int getRpgConstant(String cst)
  {
  	if("LEFT".equals(cst)) return RpgConstants.LEFT;
  	if("RIGHT".equals(cst)) return RpgConstants.RIGHT;
  	if("UP".equals(cst)) return RpgConstants.UP;
  	if("DOWN".equals(cst)) return RpgConstants.DOWN;
	return 0;
  }

  private Color getColor(String color)
  {
  	if("RED".equals(color)) return Color.RED;
  	if("GRAY".equals(color)) return Color.GRAY;
  	if("GREEN".equals(color)) return Color.GREEN;
  	if("SKYBLUE".equals(color)) return Color.SKYBLUE;
  	if("YELLOW".equals(color)) return Color.YELLOW;
  	if("SNOW".equals(color)) return Color.SNOW;
	return null;
  }

  private void enter(){
	loadIndexFile("main.idx");
  }

  @Override
  public Controller invoke(){
    return next != null ? next : this;
  }

  @Override
  public Node getControlBar(){
    return menu;
  }

  /**
   * 创建程序启动选择菜单背景(大于240*120)
   * 
   * @param width
   * @param height
   * @return
   */
  static Image initIndexImage(int width, int height){
    JavafxImageWriter iw = new JavafxImageWriter(width, height);
    // 上
    Image topSide = ResourceManager.loadImage("resource/image/index/top_side.png");
    iw.fillImage(topSide);
    iw.fillImage(ImageUtils.reverseH(topSide), width - (int) topSide.getWidth(), 0);
    int fixTopWidth = width - 2 * (int) topSide.getWidth();
    if(fixTopWidth > 0){
      Image topCenter = ResourceManager.loadImage("resource/image/index/top_center.png");
      Image fixCenter = ImageUtils.clip(topCenter, fixTopWidth, (int) topCenter.getHeight(), 0, 0,
          (int) topCenter.getWidth(), (int) topCenter.getHeight());
      iw.fillImage(fixCenter, (int) topSide.getWidth(), 0);
    }

    // 下
    Image bottomSide = ResourceManager.loadImage("resource/image/index/bottom_side.png");
    iw.fillImage(bottomSide, 0, height - (int) bottomSide.getHeight());
    iw.fillImage(ImageUtils.reverseH(bottomSide), width - (int) bottomSide.getWidth(), height
        - (int) bottomSide.getHeight());
    int fixBottomWidth = width - 2 * (int) bottomSide.getWidth();
    if(fixBottomWidth > 0){
      Image bottomCenter = ResourceManager.loadImage("resource/image/index/bottom_center.png");
      Image fixCenter = ImageUtils.clip(bottomCenter, fixBottomWidth, (int) bottomSide.getHeight(),
          0, 0, (int) bottomCenter.getWidth(), (int) bottomCenter.getHeight());
      iw.fillImage(fixCenter, (int) bottomSide.getWidth(), height - (int) bottomSide.getHeight());
    }

    // 左右边框
    int fixHeight = height - (int) topSide.getHeight() - (int) bottomSide.getHeight();
    if(fixHeight > 0){
      Image lrCenter = ResourceManager.loadImage("resource/image/index/left_right_center.png");
      Image fixLrCenter = ImageUtils.clip(lrCenter, (int) lrCenter.getWidth(), fixHeight, 0, 0,
          (int) lrCenter.getWidth(), (int) lrCenter.getHeight());
      iw.fillImage(fixLrCenter, 0, (int) topSide.getHeight());

      iw.fillImage(ImageUtils.reverseH(fixLrCenter), width - (int) lrCenter.getWidth(),
          (int) topSide.getHeight());

      // 中心区域
      int fixCenterWidth = width - 2 * (int) lrCenter.getWidth();
      if(fixCenterWidth > 0){
        Image center = ResourceManager.loadImage("resource/image/index/center.png");
        Image fixCenter = ImageUtils.clip(center, fixCenterWidth, fixHeight, 0, 0,
            (int) center.getWidth(), (int) center.getHeight());
        iw.fillImage(fixCenter, (int) lrCenter.getWidth(), (int) topSide.getHeight());
      }
    }

    return iw.getImage();
  }

}
