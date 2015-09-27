set heroName = 宇林
set toHero = 盛舒湘
set heroImage = resource/image/role/hero_face.png
set toImage = resource/image/role/shengshuxiang_face.png

begin:

cg  #{toImage}
mes #{heroName},你知道李元芳将军在哪里吗？

cg del #{toImage}
in
	A.好像大概应该是在洛阳。
	B.不知道……
out

if SELECT==0 yes
if SELECT==1 no
yes:
	cg #{heroImage} 400
	mes 好像大概应该是在洛阳。

	cg del #{heroImage}
	cg  #{toImage}
	mes 洛阳在什么地方？

	follow shengshuxiang yulin
	addscript yulin shengshuxiang ssxScript2.sc
	transfer jianyuanzhuang 22 1 hill 2 22

	cg del #{toImage}
	cg  #{heroImage} 400
	mes ……我带你去吧？

	cg del #{heroImage}
	cg  #{toImage}
	mes 好啊，多谢，我们现在就出发吧。
	goto end
no:
	mes 不知道……

end:
