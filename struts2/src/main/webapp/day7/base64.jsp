<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="m"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${root}/bootstrap/css/jquery.Jcrop.min.css">
</head>
<body>
  <jsp:include page="/common/nav.jsp"></jsp:include>
  <div class="panel panel-default">
    <div class="panel-heading">图片处理</div>
    <div class="panel-body">
        <div class="row">
          <div class="col-md-4"><img id="preview" /></div>
          <div class="col-md-3"><canvas id="c_preview" class="img-thumbnail"></canvas></div>
        </div>
        <br />
        <button type="button" class="btn btn-default" id="crop">裁剪</button>
        <button type="button" class="btn btn-default" id="watermark">水印</button>
        <button type="button" class="btn btn-default" id="resize">缩放</button>
    </div>
  </div>
  <jsp:include page="/common/footer.jsp"></jsp:include>
  <script type="text/javascript" src="${root}/bootstrap/js/jquery.Jcrop.min.js"></script>
  <script type="text/javascript">
      $(function() {
          var imgObj = new Image();
          var jcrop_api;
          imgObj.onload = function(){
              var img = $("#preview").attr("src", this.src);
              img.Jcrop({
                  boxWidth : 300,
                  boxHeight : 300
              }, function() {
                  jcrop_api = this;
              });
          };
          imgObj.src = "${root}/image/pool.jpg";
          
          $("#crop").click(function() {
              var o = jcrop_api.tellSelect();
              if(o.x == 0 && o.y == 0 && o.w == 0 && o.h == 0) {
                  return;
              }
              var canvas = document.getElementById("c_preview");  
              canvas.width = o.w;
              canvas.height = o.h;
              var ctx = canvas.getContext("2d");  
              ctx.drawImage(imgObj, o.x, o.y, o.w, o.h, 0, 0, o.w, o.h);
              save(canvas);
          });
          
          $("#watermark").click(function(){
              var canvas = document.getElementById("c_preview");  
              canvas.width = imgObj.width;
              canvas.height = imgObj.height;
              var ctx = canvas.getContext("2d");  
              ctx.drawImage(imgObj, 0, 0, imgObj.width, imgObj.height);              
              var wmObj = new Image();
              wmObj.onload = function(){
                  ctx.globalAlpha = 0.25;
                  ctx.drawImage(wmObj, 0, 0, wmObj.width, wmObj.height, imgObj.width-wmObj.width, imgObj.height-wmObj.height, wmObj.width, wmObj.height);
                  save(canvas);
              };
              wmObj.src = "${root}/image/1.png";
          });
          
          $("#resize").click(function(){
              var canvas = document.getElementById("c_preview");
              var ratio = 100/imgObj.width;
              canvas.width = 100;
              canvas.height = imgObj.height*ratio;
              var ctx = canvas.getContext("2d");  
              ctx.drawImage(imgObj, 0, 0, imgObj.width, imgObj.height, 0, 0, canvas.width, canvas.height);
              save(canvas);
          });
          
          function save(canvas) {
              $.ajax({
                  url:"${root}/day7/base64.action", 
                  method:"post",
                  data: {image:canvas.toDataURL("image/png")}
              });
          }

      });
  </script>
</body>
</html>