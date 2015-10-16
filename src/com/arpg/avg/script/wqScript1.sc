set wqImage = resource/image/role/man1_face.png
set jpImage = resource/image/role/man2_face.png
set ntyImage = resource/image/role/woman1_face.png
set hiddenblackImage = resource/image/role/hiddenblack.png

begin:

cg #{wqImage}
mes 小心！
mes 啊！！！

cg del #{wqImage}
cg #{jpImage} 400
cg #{ntyImage}
mes 大哥！

cg del #{jpImage}
cg del #{ntyImage}
cg #{wqImage}
mes （这两枚铜板也无甚力道，莫非抹了毒在上面？）
mes 别碰我！我已身中剧毒。赶快上马，小心偷袭！

cg del #{wqImage}
cg #{hiddenblackImage}
mes 原来剑源派的人净会使些下三滥的勾当。
mes 半路偷袭也就罢了，江湖上的名门正派也会干这事。
mes 半路用暗器偷袭，也就蛇灵内卫之流能干出这等事来，好&歹也有人做过，倒也罢了。
mes 暗器上还要再抹上毒，传出去可真是令江湖之人不耻了。

cg del #{hiddenblackImage}
mes 少废话！这不还剩两个人的吗？你们拿下他们，算你们的本事！

cg #{jpImage} 400
mes 江湖上哪路使阴招下毒的‘好汉’？赶快现身！

end:
