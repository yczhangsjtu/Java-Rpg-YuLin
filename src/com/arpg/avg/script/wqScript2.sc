set wqImage = resource/image/role/man1_face.png
set jpImage = resource/image/role/man2_face.png
set ntyImage = resource/image/role/woman1_face.png
set blackImage = resource/image/role/black_face.png
set whiteImage = resource/image/role/white_face.png
set whiteImage2 = resource/image/role/white2_face.png

begin:

cg #{wqImage}
mes 啊！啊！！啊————

cg #{blackImage}
cg del #{wqImage}
mes 这到底是什么毒，怎么这等厉害？

cg #{whiteImage}
cg del #{blackImage}
mes 我说你这傻小子怎么今天脑子突然灵光了，居然把人打下马来了，&原来你在暗器上抹了毒。
mes 跟华石派暗地里切磋的时候用用也就罢了，今天真是丢我们的人。&快说，你这毒是哪来的？！

cg #{whiteImage2}
cg del #{whiteImage}
mes 启禀师傅，我……我的暗器上真没有毒呀！

end:
