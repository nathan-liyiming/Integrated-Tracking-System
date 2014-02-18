<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Operations and Supply Chain Management</title>
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />

<!--Graph-->
<script src="js/graph/RGraph.common.core.js"></script>
<script src="js/graph/RGraph.common.dynamic.js"></script>
<script src="js/graph/RGraph.common.tooltips.js"></script>
<script src="js/graph/RGraph.common.effects.js"></script>
<script src="js/graph/RGraph.common.key.js"></script>
<script src="js/graph/RGraph.line.js"></script>
<script src="js/graph/jquery.min.js"></script>
<!--Graph-->

<script src="js/jquery-1.1.3.1.pack.js" type="text/javascript"></script>
<script src="js/jquery.history_remote.pack.js" type="text/javascript"></script>
<script src="js/jquery.tabs.pack.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {

		$('#container-4').tabs({
			fxFade : true,
			fxSpeed : 'slow'
		});

	});
</script>

<!--Graph-->
<link rel="stylesheet" href="css/demos.css" type="text/css"
	media="screen" />
<!--Graph-->

<link rel="stylesheet" href="css/jquery.tabs.css" type="text/css"
	media="print, projection, screen" />

<style type="text/css" media="screen, projection">
#templatemo_middle {
	overflow: hidden;
	width: 960px;
	height: 320px;
	background: url(images/templatemo_middle.jpg) no-repeat
}
</style>


</head>
<body>

	<div id="templatemo_body_wrapper">
		<div id="templatemo_wrapper">

			<div id="templatemo_header">
				<div id="site_title">
					<a href="index.jsp"><p style="color: #21C434">
							<font size="8">OSCM</font>
						</p> <span>Welcome to our company!</span></a>
				</div>
				<!-- end of site_title -->
			</div>
			<!-- end of header -->

			<div id="templatemo_menu">
				<ul>
					<li><a href="index.jsp">Home</a></li>
					<li><a href="farm.jsp">Farm</a></li>
					<li><a href="processor.jsp" class="current">Processor</a></li>
					<li><a href="logistics.jsp">Logistics</a></li>
					<li><a href="retailer.jsp">Retailer</a></li>
					<li><a href="consumer.jsp">Consumer</a></li>
					<li><a href="about.jsp">About Us</a></li>
				</ul>
			</div>
			<!-- end of menu -->

			<div id="templatemo_middle">
				<div id="container-4">
					<div id="service-1">
						<h1>Aggregate time</h1>
						<p>Aggregate time of improper quality control for the foods
							(e.g. percentage of time when temperature is beyond required
							range) at each stage of the supply chain, in particular, in the
							transfer processes between two stages, through tabled and
							graphical presentations.</p>
					</div>
					<div id="service-2">
						<h1>Estimate impact</h1>
						<p>Estimated impact of this improper quality control on the
							foods’ shelf-life through some simple rules (e.g. percentage of
							shelf-life will be reduced), through tabled and graphical
							presentations.</p>
					</div>
					<div id="service-3">
						<h1>Estimated time</h1>
						<p>Estimated time that the food will stay at a stage or
							process (e.g. storage at a store or sell on a shelf).</p>
					</div>
					<div id="service-4">
						<h1>Comparison</h1>
						<p>Comparison of the above estimated time with the remaining
							shelf-life of the foods. Showing risk in spoilage and opportunity
							of reducing waste/increasing sales.</p>
					</div>
					<ul>
						<li><a href="#service-1">Service One<span>Aggregate
									time</span></a></li>
						<li><a href="#service-2">Service Two<span>Estimate
									impact</span></a></li>
						<li><a href="#service-3">Service Three<span>Estimated
									time</span></a></li>
						<li><a href="#service-4">Service Four<span>Comparison</span></a></li>
					</ul>
				</div>
			</div>

			<!-- Package to use -->
			<%@ page import="util.*"%>
			<%@ page import="java.util.*"%>
			<%@ page import="java.sql.*"%>

			<div id="templatemo_main_base">
				<div id="templatemo_main">

					<div class="content_box">
						<div class="col_w410">
							<h2>Search</h2>
							<form action="searchProcessor" method="post">
								<select name="select">
									<%
										HttpSession ss = request.getSession();
										String ID = (String) ss.getAttribute("ID");
										if (ID != null) {
											if (ID.equals("Package_ID")) {
									%>
									<option value="1" selected="selected">Package_ID</option>
									<option value="2">Location_ID</option>
									<option value="3">Company_ID</option>
									<%
										} else if (ID.equals("Location_ID")) {
									%>
									<option value="1">Package_ID</option>
									<option value="2" selected="selected">Location_ID</option>
									<option value="3">Company_ID</option>
									<%
										} else {
									%>
									<option value="1">Package_ID</option>
									<option value="2">Location_ID</option>
									<option value="3" selected="selected">Company_ID</option>
									<%
										}
										} else {
									%>
									<option value="1" selected="selected">Package_ID</option>
									<option value="2">Location_ID</option>
									<option value="3">Company_ID</option>
								</select>
								<%
									}

									String keyword = (String) ss.getAttribute("keyword");
									if (keyword == null) {
										keyword = "";
									}
								%>
								<input name="KeyWord" type="text" value="<%=keyword%>" />
								<button>Search</button>
							</form>

							<br />

							<h3>Package List</h3>

							<%
								if (ID != null) {
									if (ID.equals("Package_ID")) {

										int i = 0;
										Connection con = MySQLConnection.connection();

										try {
											Statement state = con.createStatement();
											String query = "SELECT * FROM package_information WHERE package_ID='"
													+ keyword + "';";
											ResultSet rs = state.executeQuery(query);

											while (rs.next()) {
												i = 1;
												String location_ID = (String) rs
														.getString("location_ID");
							%>
							<p>
								Location <em><font color="green"><%=location_ID%></font></em>
								has <em><font color="green"><%=keyword%></font></em> though
							</p>
							<ul>
								<%
									state = con.createStatement();
													query = "SELECT * FROM company_information WHERE location_ID='"
															+ location_ID
															+ "' AND company_Name='processor';";
													ResultSet rs1 = state.executeQuery(query);
													while (rs1.next()) {

														String location_name = (String) rs1
																.getString("location_name");
								%>

								<li>Location Name: <%=location_name%>
								</li>

								<%
									}
												}
								%>
							</ul>
							<%
								if (i == 0) {
							%>
							<p>There is no package to search!</p>
							<%
								}
											state.close();
											con.close();
										} catch (SQLException e) {

										}

									} else if (ID.equals("Location_ID")) {
										int i = 0;
										Connection con = MySQLConnection.connection();
										try {
											Statement state = con.createStatement();
											String query = "SELECT * FROM package_information WHERE location_ID='"
													+ keyword + "';";
											ResultSet rs = state.executeQuery(query);

											while (rs.next()) {
												i = 1;
												String package_ID = (String) rs
														.getString("package_ID");
							%>
							<p>
								Location <em><font color="green"><%=keyword%></font></em> has <em><font
									color="green"><%=package_ID%></font></em> through
							</p>
							<ul>
								<%
									state = con.createStatement();
													query = "SELECT * FROM company_information WHERE location_ID='"
															+ keyword
															+ "' AND company_Name='processor';";
													ResultSet rs1 = state.executeQuery(query);
													while (rs1.next()) {

														String location_name = (String) rs1
																.getString("location_name");
								%>

								<li>Location Name: <%=location_name%>
								</li>

								<%
									}
												}
								%>
							</ul>
							<%
								if (i == 0) {
							%>
							<p>There is no package in this location!</p>
							<%
								}
											state.close();
											con.close();
										} catch (SQLException e) {

										}
									}
								}
							%>
						</div>


						<div class="col_w410 last_col">
							<h2>Operation Tools</h2>
							<ul>
								<li>Input parameter:
									<form action="parameter" method="post">
										<input name="paraInput" type="text" />
										<button type="submit">submit</button>
										<button type="reset">reset</button>
									</form>
								</li>
								<br />


								<%
									String during_time = "result";
									String remaining_time = "result";
									if (ID != null) {
										if (ID.equals("Package_ID")) {
											Connection con = MySQLConnection.connection();
											try {
												Statement state = con.createStatement();
												String query = "SELECT * FROM package_time WHERE package_ID='"
														+ keyword + "';";
												ResultSet rs = state.executeQuery(query);

												while (rs.next()) {

													during_time = (String) rs.getString("during_time");

													remaining_time = (String) rs
															.getString("remaining_time");
												}
												state.close();
												con.close();
											} catch (SQLException e) {

											}
										}
									}
								%>
								<li>Compute the during time of package: <em><font
										color="green"><%=during_time%></font></em>
								</li>
								<br />

								<li>Compute the remaining time of package: <em><font
										color="green"><%=remaining_time%></font></em>
								</li>
								<br />


								<%
									int t = 0;
									String max = "value";
									int x = 0;
									String min = "value";
									int y = 0;
									if (ID != null) {
										if (ID.equals("Location_ID")) {
											Connection con = MySQLConnection.connection();
											String temperature;
											try {
												Statement state = con.createStatement();
												String query = "SELECT * FROM location_information WHERE location_ID='"
														+ keyword + "';";
												ResultSet rs = state.executeQuery(query);

												if (rs.next()) {

													temperature = (String) rs.getString("temperature");
													x = Integer.parseInt(temperature);
													y = Integer.parseInt(temperature);

												}
												while (rs.next()) {
													temperature = (String) rs.getString("temperature");
													t = Integer.parseInt(temperature);
													if (t < x) {
														x = t;
													}
													if (t > y) {
														y = t;
													}
												}
												max = x + "";
												min = y + "";
												state.close();
												con.close();
											} catch (SQLException e) {

											}
										}else if (ID.equals("Package_ID")){
											Connection con = MySQLConnection.connection();
											try {
												String temperature;
												String time;
												Statement state = con.createStatement();
												String query = "SELECT * FROM package_information WHERE package_ID='"
														+ keyword + "';";
												ResultSet rs = state.executeQuery(query);

												while (rs.next()) {
													String location_ID = (String) rs.getString("location_ID");
													String start_time = (String) rs.getString("start_time");
													String leaving_time = (String) rs.getString("leaving_time");
													
													Statement state1 = con.createStatement();
													String query1 = "SELECT * FROM location_information WHERE location_ID='"
															+ location_ID + "';";
													ResultSet rs1 = state1.executeQuery(query1);

													while (rs1.next()) {
														time = (String) rs1.getString("time");
														temperature = (String) rs1.getString("temperature");
													if (time.equals(start_time)) {

														x = Integer.parseInt(temperature);
														y = Integer.parseInt(temperature);
														break;
													}
													}
													while (rs1.next()) {
														time = (String) rs1.getString("time");
														temperature = (String) rs1.getString("temperature");
														if(time.equals(leaving_time)){
															break;
														}
														t = Integer.parseInt(temperature);
														if (t < x) {
															x = t;
														}
														if (t > y) {
															y = t;
														}
													}
													max = x + "";
													min = y + "";
													state1.close();
												}
											state.close();
											con.close();
										} catch (SQLException e) {

										}
										}
									}
								%>
								<li>Max and Min temperatures
									<ul>
										<li>Max temperature in <%=keyword%>: <em><font
												color="green"><%=max%></font></em></li>
										<li>Min temperature in <%=keyword%>: <em><font
												color="green"><%=min%></font></em></li>
									</ul>
								</li>
								<br />

							</ul>
						</div>
						<div class="cleaner"></div>
					</div>


					<div class="content_box">
						<h2>Graph</h2>


<%
   int[] tem = new int[100];
   String[] tim = new String[100];
   int n = 0; 
   if (ID != null) {
	if (ID.equals("Location_ID")) {
		Connection con = MySQLConnection.connection();
		try {
			Statement state = con.createStatement();
			String query = "SELECT * FROM location_information WHERE location_ID='"
					+ keyword + "';";
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {

				String temperature = (String) rs.getString("temperature");
				tem[n] = Integer.parseInt(temperature);				
				String time = (String) rs.getString("time");
				tim[n] = time;
				n++;
			}

	int[] real_tem = new int[n];
	String[] real_tim = new String[n];
	for(int i=0;i<n;i++){
		real_tem[i] = tem[i];
		real_tim[i] = tim[i]; 
	}
	if(n>0){
 %>
		<!--graph-->    
		<canvas id="cvs" width="600" height="250">[No canvas support]</canvas>
    <script>
        window.onload = function ()
        {
        	var low =[];
        	var high = [];
        	var real_tim = [];
        	var real_tem = [];
        	var real_timX = [];
        	<%for(int i=0;i<n;i++){%>
        		low.push(5);
        		high.push(10);
        		real_tem.push('<%=real_tem[i]%>');
        		real_tim.push('<%=real_tim[i]%>');
        		real_timX.push('<%=i%>');
        	<%}%>

            var line = new RGraph.Line('cvs', real_tem, low, high);
            line.Set('chart.title', 'A line chart showing time-temperature.');
            line.Set('chart.curvy', true);
            line.Set('chart.curvy.tickmarks', true);
            line.Set('chart.curvy.tickmarks.fill', null);
            line.Set('chart.curvy.tickmarks.stroke', '#aaa');
            line.Set('chart.curvy.tickmarks.stroke.linewidth', 2);
            line.Set('chart.curvy.tickmarks.size', 5);
            line.Set('chart.xaxispos', 'center');
            line.Set('chart.linewidth', 3);
            line.Set('chart.hmargin', 5);
            line.Set('chart.labels', real_timX);
            line.Set('chart.tooltips', real_tim);
            line.Set('chart.tickmarks', 'circle');
            line.Set('chart.key', ['temp','low temp','high temp']);
            line.Set('chart.key.position', 'graph');
            line.Set('chart.key.shadow', true);
            line.Set('chart.key.shadow.offsetx', 0);
            line.Set('chart.key.shadow.offsety', 0);
            line.Set('chart.key.shadow.blur', 15);
            line.Set('chart.key.shadow.color', '#ccc');
            line.Set('chart.key.interactive', true);
            RGraph.Effects.Line.jQuery.Trace(line);
        }
    </script>
 
 <%
	}
	state.close();
	con.close();
} catch (SQLException e) {

}
}
		}%>
		
		
		<%
   int[] tem1 = new int[100];
   String[] tim1 = new String[100];
   int n1 = 0; 
   if (ID != null) {
	if (ID.equals("Package_ID")) {
		Connection con = MySQLConnection.connection();
		try {
			Statement state = con.createStatement();
			String query = "SELECT * FROM package_information WHERE package_ID='"
					+ keyword + "';";
			ResultSet rs = state.executeQuery(query);

			while (rs.next()) {
				String location_ID = (String) rs.getString("location_ID");
				String start_time = (String) rs.getString("start_time");
				String leaving_time = (String) rs.getString("leaving_time");
				
				Statement state1 = con.createStatement();
				String query1 = "SELECT * FROM location_information WHERE location_ID='"
						+ location_ID + "';";
				ResultSet rs1 = state1.executeQuery(query1);
				int start = 0;
				while (rs1.next()) {
					String temperature = (String) rs1.getString("temperature");
					String time = (String) rs1.getString("time");
					if(time.equals(start_time)||start==1){
						start=1;
						if(time.equals(leaving_time)){
							break;
						}
						tem1[n1] = Integer.parseInt(temperature);								
						tim1[n1] = time;
						n1++;
					}
				}
				state1.close();
			}

	int[] real_tem = new int[n1];
	String[] real_tim = new String[n1];
	for(int i=0;i<n1;i++){
		real_tem[i] = tem1[i];
		real_tim[i] = tim1[i]; 
	}
	if(n1>0){
 %>
		<!--graph-->    
		<canvas id="cvs" width="600" height="250">[No canvas support]</canvas>
    <script>
        window.onload = function ()
        {
        	var low =[];
        	var high = [];
        	var real_tim = [];
        	var real_tem = [];
        	var real_timX = [];
        	<%for(int i=0;i<n1;i++){%>
        		low.push(5);
        		high.push(10);
        		real_tem.push('<%=real_tem[i]%>');
        		real_tim.push('<%=real_tim[i]%>');
        		real_timX.push('<%=i%>');
        	<%}%>

            var line = new RGraph.Line('cvs', real_tem, low, high);
            line.Set('chart.title', 'A line chart showing time-temperature.');
            line.Set('chart.curvy', true);
            line.Set('chart.curvy.tickmarks', true);
            line.Set('chart.curvy.tickmarks.fill', null);
            line.Set('chart.curvy.tickmarks.stroke', '#aaa');
            line.Set('chart.curvy.tickmarks.stroke.linewidth', 2);
            line.Set('chart.curvy.tickmarks.size', 5);
            line.Set('chart.xaxispos', 'center');
            line.Set('chart.linewidth', 3);
            line.Set('chart.hmargin', 5);
            line.Set('chart.labels', real_timX);
            line.Set('chart.tooltips', real_tim);
            line.Set('chart.tickmarks', 'circle');
            line.Set('chart.key', ['temp','low temp','high temp']);
            line.Set('chart.key.position', 'graph');
            line.Set('chart.key.shadow', true);
            line.Set('chart.key.shadow.offsetx', 0);
            line.Set('chart.key.shadow.offsety', 0);
            line.Set('chart.key.shadow.blur', 15);
            line.Set('chart.key.shadow.color', '#ccc');
            line.Set('chart.key.interactive', true);
            RGraph.Effects.Line.jQuery.Trace(line);
        }
    </script>
 
 <%
	}
	state.close();
	con.close();
} catch (SQLException e) {

}
}
		}else{%>
		<p>Please search package or location.</p>
		<%}%>
						<!--end-->
						<div class="cleaner"></div>
					</div>

					<div class="content_box">					
						<h3>Warning </h3>
						<%if(!max.equals("value")&&min.equals("value")){%>
						<div class="hp_news_box">
							<p class="date">Warning 1</p>
							<h5>Max temperature in location is higher than 10!</h5>
						</div>
						<%}else if(max.equals("value")&&!min.equals("value")){%>
						<div class="hp_news_box">
							<p class="date">Warning 1</p>
							<h5>Min temperature in location is lower than 5!</h5>
						</div>
						<%}else if(!max.equals("value")&&!min.equals("value")){%>
						<div class="hp_news_box">
							<p class="date">Warning 1</p>
							<h5>Max temperature in location is higher than 10!</h5>
						</div>
						<div class="hp_news_box">
							<p class="date">Warning 2</p>
							<h5>Min temperature in location is lower than 5!</h5>
						</div>
						<%}else{%>
						<p>There is no warning.</p>
						<%} %>
						<div class="cleaner"></div>
					</div>

				</div>
			</div>
			<!-- end of main_base -->

			<div id="templatemo_main_bottom"></div>

			<div id="templatemo_footer">

				<a href="index.jsp" class="current">Home</a> | <a href="farm.jsp">Farm</a>
				| <a href="processor.jsp">Processor</a> | <a href="logistics.jsp">Logistics</a>
				| <a href="retailer.jsp">Retailer</a> | <a href="consumer.jsp">Consumer</a>
				| <a href="about.jsp">About Us</a> <br /> <br /> Copyright &copy
				2012 <a href="index.jsp">Operations and Supply Chain Management</a>
				| Designed by <a href="http://www.csc.liv.ac.uk/~x1yl2"
					target="blank">Yiming Li</a>

			</div>
			<%
				ss.setAttribute("ID", null);
				ss.setAttribute("keyword", null);
			%>
			<!-- end of footer -->

			<div class="cleaner"></div>
		</div>
		<!-- end of wrapper -->
	</div>
	<!-- end of body_wrapper -->

</body>
</html>