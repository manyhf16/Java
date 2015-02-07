<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<c:set var="s" value="${pageContext.request.contextPath}/static"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
<title>吴忠中学94届高三八班同学录</title>
<jsp:include page="/WEB-INF/view/includes/head.jsp" />
</head>

<body class="">
	<jsp:include page="/WEB-INF/view/includes/header.jsp" />
	<jsp:include page="/WEB-INF/view/includes/aside.jsp" />
	<!-- MAIN PANEL -->
	<div id="main">
		<!-- RIBBON -->
		<div id="ribbon">
			<span class="ribbon-button-alignment"> 
				<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> 警告，点击此按钮会重置您的页面偏好设置" data-html="true">
					<i class="fa fa-refresh"></i>
				</span> 
			</span>

			<!-- breadcrumb -->
			<ol class="breadcrumb">
				<li>首页</li><li>同学</li>
			</ol>
			<!-- end breadcrumb -->
		</div>
		<div id="content">
			<!-- widget grid -->
			<section id="widget-grid" class="">

				<!-- row -->
				<div class="row">

					<!-- NEW WIDGET START -->
					<article class="col-xs-12 col-sm-12 col-md-12 col-lg-6">

						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget" id="wid-classmate-all" data-widget-editbutton="false">
							<!-- widget options:
								usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">
				
								data-widget-colorbutton="false"
								data-widget-editbutton="false"
								data-widget-togglebutton="false"
								data-widget-deletebutton="false"
								data-widget-fullscreenbutton="false"
								data-widget-custombutton="false"
								data-widget-collapsed="true"
								data-widget-sortable="false"
				
								-->
							<header>
								<span class="widget-icon"> <i class="fa fa-table"></i>
								</span>
								<h2>同学列表</h2>

							</header>

							<!-- widget div-->
							<div>

								<!-- widget edit box -->
								<div class="jarviswidget-editbox">
									<!-- This area used as dropdown edit box -->

								</div>
								<!-- end widget edit box -->

								<!-- widget content -->
								<div class="widget-body no-padding">
									<table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
										<thead>
											<tr>
												<th>用户名</th>
												<th>姓名</th>
												<th>手机</th>
												<th>邮件</th>
												<th>详情</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="c">
												<tr>
													<td>${c.username}</td>
													<td>${c.name}</td>
													<td>${c.mobile }</td>
													<td>${c.email }</td>
													<td>
														<a href="javascript:void(0);" class="btn btn-primary btn-circle"><i class="glyphicon glyphicon-list"></i></a>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>
								<!-- end widget content -->

							</div>
							<!-- end widget div -->

						</div>
						<!-- end widget -->
					</article>
					<!-- WIDGET END -->
					
					<article class="col-sm-12 col-md-12 col-lg-6 sortable-grid ui-sortable">			
						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget jarviswidget-sortable" id="wid-classmate-one" data-widget-editbutton="true" data-widget-custombutton="false" role="widget">
							<header role="heading"><div class="jarviswidget-ctrls" role="menu">   <a href="#" class="button-icon jarviswidget-toggle-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Collapse"><i class="fa fa-minus "></i></a> <a href="javascript:void(0);" class="button-icon jarviswidget-fullscreen-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Fullscreen"><i class="fa fa-expand "></i></a> <a href="javascript:void(0);" class="button-icon jarviswidget-delete-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Delete"><i class="fa fa-times"></i></a></div><div class="widget-toolbar" role="menu"><a data-toggle="dropdown" class="dropdown-toggle color-box selector" href="javascript:void(0);"></a><ul class="dropdown-menu arrow-box-up-right color-select pull-right"><li><span class="bg-color-green" data-widget-setstyle="jarviswidget-color-green" rel="tooltip" data-placement="left" data-original-title="Green Grass"></span></li><li><span class="bg-color-greenDark" data-widget-setstyle="jarviswidget-color-greenDark" rel="tooltip" data-placement="top" data-original-title="Dark Green"></span></li><li><span class="bg-color-greenLight" data-widget-setstyle="jarviswidget-color-greenLight" rel="tooltip" data-placement="top" data-original-title="Light Green"></span></li><li><span class="bg-color-purple" data-widget-setstyle="jarviswidget-color-purple" rel="tooltip" data-placement="top" data-original-title="Purple"></span></li><li><span class="bg-color-magenta" data-widget-setstyle="jarviswidget-color-magenta" rel="tooltip" data-placement="top" data-original-title="Magenta"></span></li><li><span class="bg-color-pink" data-widget-setstyle="jarviswidget-color-pink" rel="tooltip" data-placement="right" data-original-title="Pink"></span></li><li><span class="bg-color-pinkDark" data-widget-setstyle="jarviswidget-color-pinkDark" rel="tooltip" data-placement="left" data-original-title="Fade Pink"></span></li><li><span class="bg-color-blueLight" data-widget-setstyle="jarviswidget-color-blueLight" rel="tooltip" data-placement="top" data-original-title="Light Blue"></span></li><li><span class="bg-color-teal" data-widget-setstyle="jarviswidget-color-teal" rel="tooltip" data-placement="top" data-original-title="Teal"></span></li><li><span class="bg-color-blue" data-widget-setstyle="jarviswidget-color-blue" rel="tooltip" data-placement="top" data-original-title="Ocean Blue"></span></li><li><span class="bg-color-blueDark" data-widget-setstyle="jarviswidget-color-blueDark" rel="tooltip" data-placement="top" data-original-title="Night Sky"></span></li><li><span class="bg-color-darken" data-widget-setstyle="jarviswidget-color-darken" rel="tooltip" data-placement="right" data-original-title="Night"></span></li><li><span class="bg-color-yellow" data-widget-setstyle="jarviswidget-color-yellow" rel="tooltip" data-placement="left" data-original-title="Day Light"></span></li><li><span class="bg-color-orange" data-widget-setstyle="jarviswidget-color-orange" rel="tooltip" data-placement="bottom" data-original-title="Orange"></span></li><li><span class="bg-color-orangeDark" data-widget-setstyle="jarviswidget-color-orangeDark" rel="tooltip" data-placement="bottom" data-original-title="Dark Orange"></span></li><li><span class="bg-color-red" data-widget-setstyle="jarviswidget-color-red" rel="tooltip" data-placement="bottom" data-original-title="Red Rose"></span></li><li><span class="bg-color-redLight" data-widget-setstyle="jarviswidget-color-redLight" rel="tooltip" data-placement="bottom" data-original-title="Light Red"></span></li><li><span class="bg-color-white" data-widget-setstyle="jarviswidget-color-white" rel="tooltip" data-placement="right" data-original-title="Purity"></span></li><li><a href="javascript:void(0);" class="jarviswidget-remove-colors" data-widget-setstyle="" rel="tooltip" data-placement="bottom" data-original-title="Reset widget color to default">Remove</a></li></ul></div>
								<span class="widget-icon"> <i class="fa fa-edit"></i> </span>
								<h2>修改</h2>				
								
							<span class="jarviswidget-loader"><i class="fa fa-refresh fa-spin"></i></span></header>
			
							<!-- widget div-->
							<div role="content">
								
								<!-- widget edit box -->
								<div class="jarviswidget-editbox">
									<!-- This area used as dropdown edit box -->
									
								</div>
								<!-- end widget edit box -->
								
								<!-- widget content -->
								<div class="widget-body no-padding">
									
									<form id="update-form" class="smart-form" novalidate="novalidate">
			
										<fieldset>
											<div class="row">
												<section class="col col-6">
													<label class="input"> <i class="icon-prepend fa fa-user"></i>
														<input type="text" name="username" placeholder="用户名" readonly>
													</label>
												</section>
												<section class="col col-6">
													<label class="input"> <i class="icon-prepend fa fa-coffee"></i>
														<input type="text" name="name" placeholder="姓名" readonly>
													</label>
												</section>
											</div>
											<div class="row">
												<section class="col col-6">
													<label class="input"> <i class="icon-prepend fa fa-male"></i>
														<input type="text" name="username" placeholder="性别" readonly>
													</label>
												</section>
												<section class="col col-6">
													<label class="input"> <i class="icon-prepend fa fa-gift"></i>
														<input id="birthday" type="text" name="birthday" placeholder="生日" >
													</label>
												</section>
											</div>			
											<div class="row">
												<section class="col col-6">
													<label class="input"> <i class="icon-prepend fa fa-envelope-o"></i>
														<input type="email" name="email" placeholder="E-mail">
													</label>
												</section>
												<section class="col col-6">
													<label class="input"> <i class="icon-prepend fa fa-phone"></i>
														<input type="tel" name="phone" placeholder="Phone" data-mask="(999) 999-9999-9999">
													</label>
												</section>
											</div>
										</fieldset>
			
										<fieldset>
											<div class="row">
												<section class="col col-5">
													<label class="select">
														<select name="country">
															<option value="0" selected="" disabled="">Country</option>
															<option value="244">Aaland Islands</option>
															<option value="1">Afghanistan</option>
															<option value="2">Albania</option>
															<option value="3">Algeria</option>
															<option value="4">American Samoa</option>
															<option value="5">Andorra</option>
															<option value="6">Angola</option>
															<option value="7">Anguilla</option>
															<option value="8">Antarctica</option>
															<option value="9">Antigua and Barbuda</option>
															<option value="10">Argentina</option>
															<option value="11">Armenia</option>
															<option value="12">Aruba</option>
															<option value="13">Australia</option>
															<option value="14">Austria</option>
															<option value="15">Azerbaijan</option>
															<option value="16">Bahamas</option>
															<option value="17">Bahrain</option>
															<option value="18">Bangladesh</option>
															<option value="19">Barbados</option>
															<option value="20">Belarus</option>
															<option value="21">Belgium</option>
															<option value="22">Belize</option>
															<option value="23">Benin</option>
															<option value="24">Bermuda</option>
															<option value="25">Bhutan</option>
															<option value="26">Bolivia</option>
															<option value="245">Bonaire, Sint Eustatius and Saba</option>
															<option value="27">Bosnia and Herzegovina</option>
															<option value="28">Botswana</option>
															<option value="29">Bouvet Island</option>
															<option value="30">Brazil</option>
															<option value="31">British Indian Ocean Territory</option>
															<option value="32">Brunei Darussalam</option>
															<option value="33">Bulgaria</option>
															<option value="34">Burkina Faso</option>
															<option value="35">Burundi</option>
															<option value="36">Cambodia</option>
															<option value="37">Cameroon</option>
															<option value="38">Canada</option>
															<option value="251">Canary Islands</option>
															<option value="39">Cape Verde</option>
															<option value="40">Cayman Islands</option>
															<option value="41">Central African Republic</option>
															<option value="42">Chad</option>
															<option value="43">Chile</option>
															<option value="44">China</option>
															<option value="45">Christmas Island</option>
															<option value="46">Cocos (Keeling) Islands</option>
															<option value="47">Colombia</option>
															<option value="48">Comoros</option>
															<option value="49">Congo</option>
															<option value="50">Cook Islands</option>
															<option value="51">Costa Rica</option>
															<option value="52">Cote D'Ivoire</option>
															<option value="53">Croatia</option>
															<option value="54">Cuba</option>
															<option value="246">Curacao</option>
															<option value="55">Cyprus</option>
															<option value="56">Czech Republic</option>
															<option value="237">Democratic Republic of Congo</option>
															<option value="57">Denmark</option>
															<option value="58">Djibouti</option>
															<option value="59">Dominica</option>
															<option value="60">Dominican Republic</option>
															<option value="61">East Timor</option>
															<option value="62">Ecuador</option>
															<option value="63">Egypt</option>
															<option value="64">El Salvador</option>
															<option value="65">Equatorial Guinea</option>
															<option value="66">Eritrea</option>
															<option value="67">Estonia</option>
															<option value="68">Ethiopia</option>
															<option value="69">Falkland Islands (Malvinas)</option>
															<option value="70">Faroe Islands</option>
															<option value="71">Fiji</option>
															<option value="72">Finland</option>
															<option value="74">France, skypolitan</option>
															<option value="75">French Guiana</option>
															<option value="76">French Polynesia</option>
															<option value="77">French Southern Territories</option>
															<option value="126">FYROM</option>
															<option value="78">Gabon</option>
															<option value="79">Gambia</option>
															<option value="80">Georgia</option>
															<option value="81">Germany</option>
															<option value="82">Ghana</option>
															<option value="83">Gibraltar</option>
															<option value="84">Greece</option>
															<option value="85">Greenland</option>
															<option value="86">Grenada</option>
															<option value="87">Guadeloupe</option>
															<option value="88">Guam</option>
															<option value="89">Guatemala</option>
															<option value="241">Guernsey</option>
															<option value="90">Guinea</option>
															<option value="91">Guinea-Bissau</option>
															<option value="92">Guyana</option>
															<option value="93">Haiti</option>
															<option value="94">Heard and Mc Donald Islands</option>
															<option value="95">Honduras</option>
															<option value="96">Hong Kong</option>
															<option value="97">Hungary</option>
															<option value="98">Iceland</option>
															<option value="99">India</option>
															<option value="100">Indonesia</option>
															<option value="101">Iran (Islamic Republic of)</option>
															<option value="102">Iraq</option>
															<option value="103">Ireland</option>
															<option value="104">Israel</option>
															<option value="105">Italy</option>
															<option value="106">Jamaica</option>
															<option value="107">Japan</option>
															<option value="240">Jersey</option>
															<option value="108">Jordan</option>
															<option value="109">Kazakhstan</option>
															<option value="110">Kenya</option>
															<option value="111">Kiribati</option>
															<option value="113">Korea, Republic of</option>
															<option value="114">Kuwait</option>
															<option value="115">Kyrgyzstan</option>
															<option value="116">Lao People's Democratic Republic</option>
															<option value="117">Latvia</option>
															<option value="118">Lebanon</option>
															<option value="119">Lesotho</option>
															<option value="120">Liberia</option>
															<option value="121">Libyan Arab Jamahiriya</option>
															<option value="122">Liechtenstein</option>
															<option value="123">Lithuania</option>
															<option value="124">Luxembourg</option>
															<option value="125">Macau</option>
															<option value="127">Madagascar</option>
															<option value="128">Malawi</option>
															<option value="129">Malaysia</option>
															<option value="130">Maldives</option>
															<option value="131">Mali</option>
															<option value="132">Malta</option>
															<option value="133">Marshall Islands</option>
															<option value="134">Martinique</option>
															<option value="135">Mauritania</option>
															<option value="136">Mauritius</option>
															<option value="137">Mayotte</option>
															<option value="138">Mexico</option>
															<option value="139">Micronesia, Federated States of</option>
															<option value="140">Moldova, Republic of</option>
															<option value="141">Monaco</option>
															<option value="142">Mongolia</option>
															<option value="242">Montenegro</option>
															<option value="143">Montserrat</option>
															<option value="144">Morocco</option>
															<option value="145">Mozambique</option>
															<option value="146">Myanmar</option>
															<option value="147">Namibia</option>
															<option value="148">Nauru</option>
															<option value="149">Nepal</option>
															<option value="150">Netherlands</option>
															<option value="151">Netherlands Antilles</option>
															<option value="152">New Caledonia</option>
															<option value="153">New Zealand</option>
															<option value="154">Nicaragua</option>
															<option value="155">Niger</option>
															<option value="156">Nigeria</option>
															<option value="157">Niue</option>
															<option value="158">Norfolk Island</option>
															<option value="112">North Korea</option>
															<option value="159">Northern Mariana Islands</option>
															<option value="160">Norway</option>
															<option value="161">Oman</option>
															<option value="162">Pakistan</option>
															<option value="163">Palau</option>
															<option value="247">Palestinian Territory, Occupied</option>
															<option value="164">Panama</option>
															<option value="165">Papua New Guinea</option>
															<option value="166">Paraguay</option>
															<option value="167">Peru</option>
															<option value="168">Philippines</option>
															<option value="169">Pitcairn</option>
															<option value="170">Poland</option>
															<option value="171">Portugal</option>
															<option value="172">Puerto Rico</option>
															<option value="173">Qatar</option>
															<option value="174">Reunion</option>
															<option value="175">Romania</option>
															<option value="176">Russian Federation</option>
															<option value="177">Rwanda</option>
															<option value="178">Saint Kitts and Nevis</option>
															<option value="179">Saint Lucia</option>
															<option value="180">Saint Vincent and the Grenadines</option>
															<option value="181">Samoa</option>
															<option value="182">San Marino</option>
															<option value="183">Sao Tome and Principe</option>
															<option value="184">Saudi Arabia</option>
															<option value="185">Senegal</option>
															<option value="243">Serbia</option>
															<option value="186">Seychelles</option>
															<option value="187">Sierra Leone</option>
															<option value="188">Singapore</option>
															<option value="189">Slovak Republic</option>
															<option value="190">Slovenia</option>
															<option value="191">Solomon Islands</option>
															<option value="192">Somalia</option>
															<option value="193">South Africa</option>
															<option value="194">South Georgia &amp; South Sandwich Islands</option>
															<option value="248">South Sudan</option>
															<option value="195">Spain</option>
															<option value="196">Sri Lanka</option>
															<option value="249">St. Barthelemy</option>
															<option value="197">St. Helena</option>
															<option value="250">St. Martin (French part)</option>
															<option value="198">St. Pierre and Miquelon</option>
															<option value="199">Sudan</option>
															<option value="200">Suriname</option>
															<option value="201">Svalbard and Jan Mayen Islands</option>
															<option value="202">Swaziland</option>
															<option value="203">Sweden</option>
															<option value="204">Switzerland</option>
															<option value="205">Syrian Arab Republic</option>
															<option value="206">Taiwan</option>
															<option value="207">Tajikistan</option>
															<option value="208">Tanzania, United Republic of</option>
															<option value="209">Thailand</option>
															<option value="210">Togo</option>
															<option value="211">Tokelau</option>
															<option value="212">Tonga</option>
															<option value="213">Trinidad and Tobago</option>
															<option value="214">Tunisia</option>
															<option value="215">Turkey</option>
															<option value="216">Turkmenistan</option>
															<option value="217">Turks and Caicos Islands</option>
															<option value="218">Tuvalu</option>
															<option value="219">Uganda</option>
															<option value="220">Ukraine</option>
															<option value="221">United Arab Emirates</option>
															<option value="222">United Kingdom</option>
															<option value="223">United States</option>
															<option value="224">United States Minor Outlying Islands</option>
															<option value="225">Uruguay</option>
															<option value="226">Uzbekistan</option>
															<option value="227">Vanuatu</option>
															<option value="228">Vatican City State (Holy See)</option>
															<option value="229">Venezuela</option>
															<option value="230">Viet Nam</option>
															<option value="231">Virgin Islands (British)</option>
															<option value="232">Virgin Islands (U.S.)</option>
															<option value="233">Wallis and Futuna Islands</option>
															<option value="234">Western Sahara</option>
															<option value="235">Yemen</option>
															<option value="238">Zambia</option>
															<option value="239">Zimbabwe</option>
														</select> <i></i> </label>
												</section>
			
												<section class="col col-4">
													<label class="input">
														<input type="text" name="city" placeholder="City">
													</label>
												</section>
			
												<section class="col col-3">
													<label class="input">
														<input type="text" name="code" placeholder="Post code">
													</label>
												</section>
											</div>
			
											<section>
												<label for="address2" class="input">
													<input type="text" name="address2" id="address2" placeholder="Address">
												</label>
											</section>
			
											<section>
												<label class="textarea"> 										
													<textarea rows="3" name="info" placeholder="Additional info"></textarea> 
												</label>
											</section>
										</fieldset>
			
										<fieldset>
											<section>
												<div class="inline-group">
													<label class="radio">
														<input type="radio" name="radio-inline" checked="">
														<i></i>Visa</label>
													<label class="radio">
														<input type="radio" name="radio-inline">
														<i></i>MasterCard</label>
													<label class="radio">
														<input type="radio" name="radio-inline">
														<i></i>American Express</label>
												</div>
											</section>
			
											<section>
												<label class="input">
													<input type="text" name="name" placeholder="Name on card">
												</label>
											</section>
			
											<div class="row">
												<section class="col col-10">
													<label class="input">
														<input type="text" name="card" placeholder="Card number" data-mask="9999-9999-9999-9999">
													</label>
												</section>
												<section class="col col-2">
													<label class="input">
														<input type="text" name="cvv" placeholder="CVV2" data-mask="999">
													</label>
												</section>
											</div>
			
											<div class="row">
												<label class="label col col-4">Expiration date</label>
												<section class="col col-5">
													<label class="select">
														<select name="month">
															<option value="0" selected="" disabled="">Month</option>
															<option value="1">January</option>
															<option value="1">February</option>
															<option value="3">March</option>
															<option value="4">April</option>
															<option value="5">May</option>
															<option value="6">June</option>
															<option value="7">July</option>
															<option value="8">August</option>
															<option value="9">September</option>
															<option value="10">October</option>
															<option value="11">November</option>
															<option value="12">December</option>
														</select> <i></i> </label>
												</section>
												<section class="col col-3">
													<label class="input">
														<input type="text" name="year" placeholder="Year" data-mask="2099">
													</label>
												</section>
											</div>
										</fieldset>
			
										<footer>
											<button type="submit" class="btn btn-primary">
												Validate Form
											</button>
										</footer>
									</form>
			
								</div>
								<!-- end widget content -->
								
							</div>
							<!-- end widget div -->
							
						</div>
						<!-- end widget -->
			
					</article>
				</div>
				<!-- end row -->
			</section>
			<!-- end widget grid -->
		</div>
	</div>
	<div id="shortcut">[METRO_SHORTCUT]</div>
	<jsp:include page="/WEB-INF/view/includes/scripts.jsp"></jsp:include>
	<!-- PAGE RELATED PLUGIN(S) -->
	<script src="${s}/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${s}/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${s}/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${s}/js/plugin/datatables/dataTables.bootstrap.min.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			pageSetUp();
			$('#dt_basic').dataTable({
				"pageLength" : 10,
				"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "所有"]],
				"language" : {
					"paginate" : {
						"previous" : "上页",
						"next" : "下页"
					},
					"infoFiltered": " - 从 _MAX_ 记录中过滤",
					"info": "显示<span class='txt-color-darken'>_START_</span>-<span class='txt-color-darken'>_END_</span> (共<span class='text-primary'>_TOTAL_</span>条记录)"
				}
			});
			$('#birthday').datetimepicker({
			    format: 'yyyy-mm-dd',
			    language:  'zh-CN',
			    initialDate: '1976-1-1',
				autoclose: 1,
				startView: 4,
				minView:2,
				bootcssVer:3
			});
		});
	</script>
</body>
</html>