javafxc=javafxc -sourcepath src -d build
path=build/com/arpg/
indexpath=$(path)index/
astarpath=$(path)astar/
avgpath=$(path)avg/
fightpath=$(path)fight/
spritepath=$(path)sprite/
utilpath=$(path)utils/
goal=$(indexpath)Bootstrap.class
classfilelist=$(path)AbstarctController.class\
	$(path)ContextContainer.class\
	$(path)Controller.class\
	$(path)ImageMapFactory.class\
	$(path)ImageSpriteFactory.class\
	$(path)MapContainer.class\
	$(path)MenuController.class\
	$(path)RpgController.class\
	$(path)Tepleporter.class\
	$(path)ViewResolver.class\
	$(astarpath)Astar.class\
	$(astarpath)Node.class\
	$(avgpath)Cg.class\
	$(avgpath)Message.class\
	$(avgpath)ScriptController.class\
	$(fightpath)ActFightController.class\
	$(fightpath)FightController.class\
	$(fightpath)Magic.class\
	$(fightpath)MagicType.class\
	$(fightpath)Stopwatch.class\
	$(indexpath)IndexController.class\
	$(spritepath)Sprite.class\
	$(spritepath)SpriteContainer.class\
	$(spritepath)SpriteState.class\
	$(spritepath)SpriteType.class\
	$(utilpath)AudioManager.class\
	$(utilpath)FontUtils.class\
	$(utilpath)ImageUtils.class\
	$(utilpath)IOUtils.class\
	$(utilpath)JavafxImageWriter.class\
	$(utilpath)LineReader.class\
	$(utilpath)MathUtils.class\
	$(utilpath)NinePatch.class\
	$(utilpath)ResourceManager.class\
	$(utilpath)RpgConstants.class\
	$(utilpath)StringUtils.class\
	$(utilpath)Threads.class

.SECONDEXPANSION:

$(goal): $$(subst class,java,$$(subst build,src,$$@)) $(classfilelist)
	$(javafxc) $<

$(path)AbstarctController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)ContextContainer.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)Controller.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)ImageMapFactory.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)ImageSpriteFactory.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)MapContainer.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)MenuController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)RpgController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)Tepleporter.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(path)ViewResolver.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(astarpath)Astar.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(astarpath)Node.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(avgpath)Cg.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(avgpath)Message.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(avgpath)ScriptController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(fightpath)ActFightController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(fightpath)FightController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(fightpath)Magic.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(fightpath)MagicType.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(fightpath)Stopwatch.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(indexpath)IndexController.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(spritepath)Sprite.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(spritepath)SpriteContainer.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(spritepath)SpriteState.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(spritepath)SpriteType.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)AudioManager.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)FontUtils.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)ImageUtils.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)IOUtils.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)JavafxImageWriter.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)LineReader.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)MathUtils.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)NinePatch.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)ResourceManager.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)RpgConstants.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)StringUtils.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<

$(utilpath)Threads.class: $$(subst class,java,$$(subst build,src,$$@))
	$(javafxc) $<
