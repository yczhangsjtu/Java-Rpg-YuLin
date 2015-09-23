set heroName = 宇林
set toHero = 盛舒湘
set heroImage = resource/image/role/assassin_face.png
set toImage = resource/image/role/rogue_face.png

cg  #{toImage}
mes #{heroName},你知道李元芳将军在哪里吗？

cg del #{toImage}
in
	A.好像大概应该是在洛阳。
	B.不知道……
out

if SELECT==0
	cg #{heroImage} 400
	mes 好像大概应该是在洛阳。

	cg del #{heroImage}
	cg  #{toImage}
	mes 洛阳在什么地方？

	state 123
	cg del #{toImage}
	cg  #{heroImage} 400
	mes ……我带你去吧？

	state 567
	cg del #{heroImage}
	cg  #{toImage}
	mes 好啊，多谢，我们现在就出发吧。
elseif SELECT==1
	mes 不知道……
endif

