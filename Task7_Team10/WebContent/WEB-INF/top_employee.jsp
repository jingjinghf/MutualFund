<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: ä¸å8:11
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">

<!-- Custom styles for this template -->
<link href="http://v3.bootcss.com/examples/dashboard/dashboard.css"
	rel="stylesheet">

<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>


<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>

<title>Main</title>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
/* Sticky footer styles
    -------------------------------------------------- */
html {
	position: relative;
	min-height: 100%;
}

body {
	/* Margin bottom by footer height */
	margin-bottom: 60px;
}

.footer {
	position: absolute;
	bottom: 0;
	width: 100%;
	/* Set the fixed height of the footer here */
	height: 60px;
	background-color: #f5f5f5;
}

body>nav>div>div.navbar-header>h4 {
	color: white;
}

body>footer>div>p.text-muted {
	float: right;
}
</style>
<style>
body>div>div>div.col-sm-3.col-md-2.sidebar>ul>li {
	margin-left: 15px;
}

body>div>div>div.col-sm-3.col-md-2.sidebar>ul>div:nth-child(2) {
	margin-left: 15px;
}

body>div>div>div.col-sm-3.col-md-2.sidebar>ul>div:nth-child(3) {
	margin-left: 15px;
}

body>div>div>div.col-sm-3.col-md-2.sidebar>ul>div:nth-child(4) {
	margin-left: 15px;
}

body>div>div>div.col-sm-3.col-md-2.sidebar>ul>div:nth-child(5) {
	margin-right: 15px;
}
</style>

</head>

<body>
	<!-- å¯¼èªæ¡ -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand">Carnegie Financial Services (Employee)</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Customer Management <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="listallcustomer.do">All Customer List</a></li>
							<li><a href="createcustomeracc.do">Create New Customer</a></li>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Employee Management <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="createemployeeacc.do">Create Employee Account</a></li>
							<li><a href="employeechangepassword.do">Change My Password</a></li>
							<li><a href="listallemployee.do">All Employee List</a></li>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Fund Management <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="createfund.do">Create Fund</a></li>
					        <li><a href="transition.do">Transition Day</a></li>
						</ul>
					</li>
					<li><a href="logout.do">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>