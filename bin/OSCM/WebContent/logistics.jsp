<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Operations and Supply Chain Management</title>
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />

<!--Graph-->
    <script src="js/graph/RGraph.common.core.js" ></script>
    <script src="js/graph/RGraph.common.dynamic.js" ></script>
    <script src="js/graph/RGraph.common.tooltips.js" ></script>
    <script src="js/graph/RGraph.common.effects.js" ></script>
    <script src="js/graph/RGraph.line.js" ></script>
    <script src="js/graph/jquery.min.js" ></script>
<!--Graph-->

<script src="js/jquery-1.1.3.1.pack.js" type="text/javascript"></script>
<script src="js/jquery.history_remote.pack.js" type="text/javascript"></script>
<script src="js/jquery.tabs.pack.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {

		$('#container-4').tabs({ fxFade: true, fxSpeed: 'slow' });

	});
</script>

	<!--Graph-->
	<link rel="stylesheet" href="css/demos.css" type="text/css" media="screen" />
    <!--Graph-->

<link rel="stylesheet" href="css/jquery.tabs.css" type="text/css" media="print, projection, screen" />

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
            <a href="index.jsp"><p style="color:#21C434"><font size="8">OSCM</font></p><span>Welcome to our company!</span></a>
        </div> 
    	<!-- end of site_title -->
    </div><!-- end of header -->
    
    <div id="templatemo_menu">
    	<ul>
            <li><a href="index.jsp">Home</a></li>         
            <li><a href="farm.jsp">Farm</a></li>
            <li><a href="processor.jsp">Processor</a></li>
            <li><a href="logistics.jsp" class="current">Logistics</a></li>
            <li><a href="retailer.jsp">Retailer</a></li>
            <li><a href="consumer.jsp">Consumer</a></li>
			<li><a href="about.jsp">About Us</a></li>
        </ul>  
    </div> <!-- end of menu -->
    
    <div id="templatemo_middle">
        <div id="container-4">
            <div id="service-1">
                <h1>Aggregate time</h1>
                <p>Aggregate time of improper quality control for the foods (e.g. percentage of time when temperature is beyond required range) at each stage of the supply chain, in particular, in the transfer processes between two stages, through tabled and graphical presentations. </p>
            </div>
            <div id="service-2">
                <h1>Estimate impact</h1>
                <p>Estimated impact of this improper quality control on the foods’ shelf-life through some simple rules (e.g. percentage of shelf-life will be reduced), through tabled and graphical presentations.</p>
            </div>
            <div id="service-3">
                <h1>Estimated time</h1>
                <p>Estimated time that the food will stay at a stage or process (e.g. storage at a store or sell on a shelf).</p>
            </div>
             <div id="service-4">
                <h1>Comparison</h1>
                <p>Comparison of the above estimated time with the remaining shelf-life of the foods.  Showing risk in spoilage and opportunity of reducing waste/increasing sales.</p>
            </div>
            <ul>
                <li><a href="#service-1">Service One<span>Aggregate time</span></a></li>
                <li><a href="#service-2">Service Two<span>Estimate impact</span></a></li>
                <li><a href="#service-3">Service Three<span>Estimated time</span></a></li>
                <li><a href="#service-4">Service Four<span>Comparison</span></a></li>
            </ul>
        </div>
    </div>
    
    <div id="templatemo_main_base">
    <div id="templatemo_main">
    	
        <div class="content_box">
          <div class="col_w410">
                <h2>Search</h2>
				<form action="search" method="post">
				    <select name="select"　onchange="alert(this.value)">
					<option value="1"  selected="selected">Package_ID</option>
					<option value="2">Location_ID</option>
					<option value="3">Company_ID</option>
					</select>
					<input name="KeyWord" type="text" />
					<button>Search</button>
				</form>
				
				<br />
				
				<h3>Package List</h3>
				<ul>
				<li>package 1 though location A, location B</li>
				<li>package 2 though location B, location C</li>
				</ul>
          </div>
        
		
          <div class="col_w410 last_col">
                <h2>Operation Tools</h2>
				<ul>
					<li>
					Input parameter: 
					<form action="parameter" method="post">
					<input name="paraInput" type="text" />
					<button type="submit">submit</button>
					<button type="reset">reset</button>
					</form>
					</li>
					<br />
					
					<li>
					Compute the during time of package: <em><font color="green">results</font></em>
					</li>
					<br />
					
					<li>
					Compute the remaining time of package: <em><font color="green">results</font></em>
					</li>
					<br />
					
					<li> Max and Min temperatures
						<ul>
						<li>Max temperature in location A: <em><font color="green">5</font></em></li>
						<li>Min temperature in location A: <em><font color="green">-5</font></em></li>
						</ul>
						
						<ul>
						<li>Max temperature in location B: <em><font color="green">4</font></em></li>
						<li>Min temperature in location B: <em><font color="green">-3</font></em></li>
						</ul>
					</li>
					<br />
								
				</ul>
          </div>
            <div class="cleaner"></div>
        </div>
        
		
        <div class="content_box">
		   <h2>Graph</h2>
		<!--graph-->    
		<canvas id="cvs" width="600" height="250">[No canvas support]</canvas>
    <script>
        window.onload = function ()
        {
            var line = new RGraph.Line('cvs', [4,5,1,6,3,5,2], [7,7,8,6,2,1,1]);
            line.Set('chart.curvy', true);
            line.Set('chart.curvy.tickmarks', true);
            line.Set('chart.curvy.tickmarks.fill', null);
            line.Set('chart.curvy.tickmarks.stroke', '#aaa');
            line.Set('chart.curvy.tickmarks.stroke.linewidth', 2);
            line.Set('chart.curvy.tickmarks.size', 5);
            line.Set('chart.linewidth', 3);
            line.Set('chart.hmargin', 5);
            line.Set('chart.labels', ['Mon','Tue','Wed','Thu','Fri','Sat','Sun']);
            line.Set('chart.tooltips', ['Mon','Tue','Wed','Thu','Fri','Sat','Sun']);
            line.Set('chart.tickmarks', 'circle');
            RGraph.Effects.Line.jQuery.Trace(line);
        }
    </script>
	<!--end-->
            <div class="cleaner"></div>
        </div>
		
		<div class="content_box">
		  <h3>Warning</h3>
       	  <div class="hp_news_box">
                	<p class="date">Warning 1</p>
                	<h5>Max temperature in location A is out of 5!</h5>
				</div>
                <div class="hp_news_box last_box">
                	<p class="date">Warning 2</p>
                	<h5>Min temperature in location A is out of -5!</h5>
			</div>
		   <div class="cleaner"></div>
        </div>
    
    </div>    
    </div>	<!-- end of main_base -->
	
    <div id="templatemo_main_bottom"></div>
    
    <div id="templatemo_footer">
    	
         <a href="index.jsp" class="current">Home</a> | <a href="farm.jsp">Farm</a> | <a href="processor.jsp">Processor</a> | <a href="logistics.jsp">Logistics</a> | <a href="retailer.jsp">Retailer</a> | <a href="consumer.jsp">Consumer</a> | <a href="about.jsp">About Us</a> <br /><br />
    	Copyright &copy 2012 <a href="index.jsp">Operations and Supply Chain Management</a> | Designed by <a href="http://www.csc.liv.ac.uk/~x1yl2" target="blank">Yiming Li</a>
    
    </div> <!-- end of footer -->
	
    <div class="cleaner"></div>
</div> <!-- end of wrapper -->
</div> <!-- end of body_wrapper -->

</body>
</html>