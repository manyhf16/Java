<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="m"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${root}/bootstrap/css/jquery.Jcrop.min.css">
<style type="text/css">
#hi {
	display: none;
}
</style>
</head>
<body>
  <jsp:include page="/common/nav.jsp"></jsp:include>
  <div class="panel panel-default">
    <div class="panel-heading">图片处理</div>
    <div class="panel-body">
      <form role="form" action="${root}/day7/crop.action" enctype="multipart/form-data" method="post" id="f">
        <div class="form-group">
          <label for="file">选择图片</label><input type="file" name="image" class="form-control" id="file">
        </div>
        <div class="row">
          <div class="col-md-4" id="preview"></div>          
          <div class="col-md-4">
            <img id="result" alt="结果" class="img-thumbnail" />
          </div>
        </div>
        <div id="hi">
          <label>X1 <input type="text" size="4" id="x1" name="x1" /></label> <label>Y1 <input type="text"
            size="4" id="y1" name="y1" /></label> <label>X2 <input type="text" size="4" id="x2" name="x2" /></label> <label>Y2
            <input type="text" size="4" id="y2" name="y2" />
          </label> <label>W <input type="text" size="4" id="w" name="w" /></label> <label>H <input type="text" size="4"
            id="h" name="h" /></label>
        </div>
        <br />
        <button type="button" class="btn btn-default" id="crop">裁剪</button>
        <button type="button" class="btn btn-default" id="watermark">水印</button>
        <button type="button" class="btn btn-default" id="resize">缩放</button>
      </form>
      
      
    </div>
  </div>
  <jsp:include page="/common/footer.jsp"></jsp:include>
  <script type="text/javascript" src="${root}/bootstrap/js/jquery.Jcrop.min.js"></script>
  <script type="text/javascript">
      $(function() {
          // 获得URL对象
          window.URL = window.URL || window.webkitURL;
          var cImage = new Image();
          var jcrop_api;
          $("#file").change(function(e) {
              $("#preview").empty();
              //.addClass("img-responsive")
              var blob = window.URL.createObjectURL(e.target.files[0]);
              var img = $("<img>").attr("src", blob).appendTo("#preview");
              cImage.src = blob;
              img.Jcrop({
                  boxWidth : 300,
                  boxHeight : 300,
                  //onChange : showCoords,
                  onSelect : showCoords,
                  onRelease : clearCoords
              }, function() {
                  // 当预览图显示完毕后，该blob资源就没用了，需要及时回收它
                  window.URL.revokeObjectURL(img.attr("src"));
                  jcrop_api = this;
              });
          });

          function showCoords(c) {
              $('#x1').val(parseInt(c.x));
              $('#y1').val(parseInt(c.y));
              $('#x2').val(parseInt(c.x2));
              $('#y2').val(parseInt(c.y2));
              $('#w').val(parseInt(c.w));
              $('#h').val(parseInt(c.h));
          }

          function clearCoords() {
              $('#hi input').val('');
          }

          function showResult(xhr) {
              var blob = window.URL.createObjectURL(xhr.response);
              var img = document.getElementById("result");
              img.src = blob;
              img.onload = function(e) {
                  window.URL.revokeObjectURL(img.src);
              };
          }

          $("#crop").click(function() {
              if(!isChooseFile() || !isSelected()) return;              
              var form = new FormData(document.getElementById("f"));
              var xhr = new XMLHttpRequest();
              xhr.open("post", "${root}/day7/crop.action");
              xhr.responseType = "blob";
              xhr.onload = function() {
                  showResult(xhr);
              };
              xhr.send(form);
          });

          $("#watermark").click(function() {
              if(!isChooseFile()) return;
              var form = new FormData(document.getElementById("f"));
              var xhr = new XMLHttpRequest();
              xhr.open("post", "${root}/day7/watermark.action");
              xhr.responseType = "blob";
              xhr.onload = function() {
                  showResult(xhr);
              };
              xhr.send(form);
          });

          $("#resize").click(function() {
              if(!isChooseFile()) return;
              var form = new FormData(document.getElementById("f"));
              var xhr = new XMLHttpRequest();
              xhr.open("post", "${root}/day7/resize.action");
              xhr.responseType = "blob";
              xhr.onload = function() {
                  showResult(xhr);
              };
              xhr.send(form);
          });
          
          function isChooseFile() {
           	  // 如果选择没选择图片，不做任何事
              var f = document.getElementById("file");
              return f.files.length > 0;
          }
          
          function isSelected() {
              var o = jcrop_api.tellSelect();
              if(o.x == 0 && o.y == 0 && o.w == 0 && o.h == 0) {
                  return false;
              }
              return true;
          }

      });
  </script>
</body>
</html>