<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

<link rel="stylesheet"
	href="https://unpkg.com/bootstrap-table@1.14.1/dist/bootstrap-table.min.css">


<script src="./detail/jquery-1.10.1.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


<title>NEM Node Rewards</title>

<link rel="shortcut icon" href="./detail/Favicon.ico"
	type="image/x-icon">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style>
body {
	/*display: flex;

min-height: 100vh;

flex-direction: column;*/
	height: 100%;
}

.table {
	margin-bottom: 0px;
}

a {
	font-weight: bold;
}

.panel-body {
	padding: 0px;
}

.panel-default {
	border-color: #337AB7;
	width: 100%;
	text-align: left;
}

.panel-title {
	font-weight: bold;
}

.table>tbody>tr>td {
	padding: 3px; //
	font-size: 14px;
	vertical-align: middle;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.table>tbody>tr>th {
	padding: 2px;
	font-size: 14px;
	width: 170px;
}

.table>thead>tr>th {
	padding: 3px;
	font-size: 14px;
}

#links a {
	padding: 10px;
}

.table>thead:first-child>tr:first-child>th {
	text-align: center;
	vertical-align: middle;
}

.alignLeft {
	text-align: left;
}

.panel.with-nav-tabs .panel-heading {
	padding: 5px 5px 0 5px;
}

.panel.with-nav-tabs .nav-tabs {
	border-bottom: none;
}

.panel.with-nav-tabs .nav-justified {
	margin-bottom: -1px;
}

/********************************************************************/

/*** PANEL DEFAULT ***/
.with-nav-tabs.panel-default .nav-tabs>li>a, .with-nav-tabs.panel-default .nav-tabs>li>a:hover,
	.with-nav-tabs.panel-default .nav-tabs>li>a:focus {
	color: #777;
}

.with-nav-tabs.panel-default .nav-tabs>.open>a, .with-nav-tabs.panel-default .nav-tabs>.open>a:hover,
	.with-nav-tabs.panel-default .nav-tabs>.open>a:focus, .with-nav-tabs.panel-default .nav-tabs>li>a:hover,
	.with-nav-tabs.panel-default .nav-tabs>li>a:focus {
	color: #777;
	background-color: #ddd;
	border-color: transparent;
}

.with-nav-tabs.panel-default .nav-tabs>li.active>a, .with-nav-tabs.panel-default .nav-tabs>li.active>a:hover,
	.with-nav-tabs.panel-default .nav-tabs>li.active>a:focus {
	color: #FFF;
	background-color: #444;
	border-color: #ddd;
	border-bottom-color: transparent;
}

.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu {
	background-color: #f5f5f5;
	border-color: #ddd;
}

.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu>li>a {
	color: #777;
}

.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu>li>a:hover,
	.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu>li>a:focus
	{
	background-color: #ddd;
}

.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu>.active>a,
	.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu>.active>a:hover,
	.with-nav-tabs.panel-default .nav-tabs>li.dropdown .dropdown-menu>.active>a:focus
	{
	color: #fff;
	background-color: #555;
}

.panel.with-nav-tabs .panel-heading {
	padding: 0px 0px 0px;
}

.table>tbody>tr:hover {
	background-color: #FFE487;
}
</style>

</head>

<body>


<br/>
	<div class="container"	style="font-size: 40px";min-height: 100%; height: auto !important; margin: 0px auto -60px; padding: 0px 0px 60px;">
		<center>
		<p class="bg-info text-center">
			<b><i class="fa fa-info-circle"></i> Round:
				${requestScope.accList[0].round}</b>
				
		</p>
		</center>
		<h2>
			<a href="index.html">&lt;&lt;返回首页</a>
		</h2>

	


	<div>

					<table class="table table-striped table-bordered"
						style="border: 2px solid orange;font-size: 15px" id="table"    >

						<thead style="background-color: #444; color: white;">

							<tr>

								<th>#</th>

								<th>Date</th>

								<th>Alias</th>

								<th>Bandwidth</th>

								<th>Height</th>

								<th>Chain part</th>

								<th>Computing power</th>

								<th>Balance</th>

								<th>Version</th>

								<th>Ping</th>

								<th>Responsiveness</th>

								<th>Round</th>

								<th>Result</th>

							</tr>

						</thead>

						<tbody 	>
							<c:forEach  items="${requestScope.accList}"  var="acc">
							<tr class="text-center" >
							
								<td style="color:#337AB7;font-weight: bold;padding: 0px 5px 0px;">${acc.row }</td>

								<td id="date${acc.id }" style="padding: 0px 10px 0px;">${acc.date }</td>

								<td ><a href="details.html?id=${acc.id}" style="color:#DD4814;font-weight:bold;">${acc.alias }</a></td>

								<td>${acc.bandwidth }</td>

								<td>${acc.height }</td>

								<td>${acc.chainPart }</td>
								
								<td>${acc.computingPower }</td>

								<td>${acc.balance }</td>

								<td>${acc.version }</td>

								<td>${acc.ping }</td>

								<td>${acc.responsiveness }</td>

								<td>${acc.round }</td>

								<td>${acc.result }</td>
								<script>

$('#date${acc.id }').text(function(_, text) {
    var now = new Date(text);
    return now.toLocaleString();

});

</script>
								
							</tr>
							</c:forEach>
						</tbody>

					</table>

				</center>
	</div>

	<script>
		
		
		
	</script>
<br/><br/>
</body>

</html>

