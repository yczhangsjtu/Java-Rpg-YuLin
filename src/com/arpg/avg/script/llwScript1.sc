set heroName = 宇林
set toHero = 陆文龙
set heroImage = resource/image/role/hero_face.png
set toImage = resource/image/role/luwenlong_face.png

begin:

cg #{toImage}
mes 你是何人？

cg del #{toImage}
cg #{heroImage} 400
mes 在下宇林，要去洛阳。

cg del #{heroImage}
cg #{toImage}
mes 你去洛阳有什么事？

cg del #{toImage}
cg #{heroImage} 400
mes 不知道……

end:
