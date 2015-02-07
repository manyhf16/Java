<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="s" value="${pageContext.request.contextPath}/static"></c:set>
<!--================================================== -->
<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)
        <script data-pace-options='{ "restartOnRequestAfter": true }' src="${s}/js/plugin/pace/pace.min.js"></script>-->
<!-- Link to Baidu CDN's jQuery + jQueryUI; fall back to local -->
<script src="http://libs.baidu.com/jquery/2.0.2/jquery.js"></script>
<script>
	if (!window.jQuery) {
		document
				.write('<script src="${s}/js/libs/jquery-2.0.2.min.js"><\/script>');
	}
</script>
<script src="http://libs.baidu.com/jqueryui/1.10.2/jquery-ui.min.js"></script>
<script>
	if (!window.jQuery.ui) {
		document
				.write('<script src="${s}/js/libs/jquery-ui-1.10.3.min.js"><\/script>');
	}
</script>
<!-- JS TOUCH : include this plugin for mobile drag / drop touch events
    <script src="${s}/js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->

<!-- BOOTSTRAP JS -->
<script src="${s}/js/bootstrap/bootstrap.min.js"></script>

<!-- CUSTOM NOTIFICATION -->
<script src="${s}/js/notification/SmartNotification.min.js"></script>

<!-- JARVIS WIDGETS -->
<script src="${s}/js/smartwidgets/jarvis.widget.min.js"></script>

<!-- EASY PIE CHARTS -->
<script src="${s}/js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>

<!-- SPARKLINES -->
<script src="${s}/js/plugin/sparkline/jquery.sparkline.min.js"></script>

<!-- JQUERY VALIDATE -->
<script src="${s}/js/plugin/jquery-validate/jquery.validate.min.js"></script>

<!-- JQUERY MASKED INPUT -->
<script src="${s}/js/plugin/masked-input/jquery.maskedinput.min.js"></script>

<!-- JQUERY SELECT2 INPUT -->
<script src="${s}/js/plugin/select2/select2.min.js"></script>

<!-- JQUERY UI + Bootstrap Slider -->
<script src="${s}/js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

<!-- browser msie issue fix -->
<script src="${s}/js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

<!-- FastClick: For mobile devices: you can disable this in app.js -->
<script src="${s}/js/plugin/fastclick/fastclick.min.js"></script>

<!-- datepicker -->
<script src="${s}/js/plugin/datepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${s}/js/plugin/datepicker/bootstrap-datetimepicker.zh-CN.js"></script>

<!-- 
<script src="${s}/js/demo.js"></script> -->

<!-- MAIN APP JS FILE -->
<script src="${s}/js/app.js"></script>
<script type="text/javascript">
$.sound_path="${s}/sound/";
</script>
<!-- Your GOOGLE ANALYTICS CODE Below 
<script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-XXXXXXXX-X']);
      _gaq.push(['_trackPageview']);
    
      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();  
</script>
-->