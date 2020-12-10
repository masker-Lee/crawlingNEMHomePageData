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

<script src="https://cdn.bootcss.com/angular.js/1.7.7/angular.min.js"></script>
<script
	src="https://cdn.bootcss.com/ngInfiniteScroll/1.3.0/ng-infinite-scroll.js"></script>
<script
	src="https://cdn.bootcss.com/angular-sanitize/1.7.7/angular-sanitize.js"></script>

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
	/* border-color: #337AB7; */
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



	<div class="container"
		style="min-height: 100%; height: auto !important; margin: 0px auto -60px; padding: 0px 0px 60px;">
		<center>
			<h1 style="color: #444; border: 2px solid black">
				<b>${requestScope.acc.alias}</b>
			</h1>
		</center>
		<p class="bg-info text-center">
			<b><i class="fa fa-info-circle"></i> Round:
				${requestScope.acc.round+1}</b>
		</p>
		<h2>
			<a href="index.html">&lt;&lt;返回首页</a>
		</h2>



		<p>
		<h2>
			<b>PREVIOUS ROUNDS</b>
		</h2>

		<div>

			<div ng-app="scrollApp" ng-controller='scrollController'	class="table table-striped table-bordered">
				<div infinite-scroll='nextPage()' infinite-scroll-disabled='isData'	infinite-scroll-distance='0'>
					<center>
						<table class="table table-striped table-bordered"
							style="border: 2px solid orange; font-size: 16px" id="table">

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

							<tbody class="text-center">
								<tr ng-repeat='x in items'>
									<td style="color: #337AB7; font-weight: bold">{{x.row }}</td>

									<td id="systemDate{{ x.round }}">{{ x.date }}</td>

									<td style="color: #DD4814; font-weight: bold;">{{ x.alias
										}}</td>

									<td ng-bind-html="x.bandwidth | trustHtml"></td>

									<td ng-bind-html="x.height | trustHtml"></td>

									<td ng-bind-html="x.chainPart | trustHtml"></td>

									<td ng-bind-html="x.computingPower | trustHtml"></td>

									<td ng-bind-html="x.balance | trustHtml"></td>

									<td ng-bind-html="x.version | trustHtml"></td>

									<td ng-bind-html="x.ping | trustHtml"></td>

									<td ng-bind-html="x.responsiveness | trustHtml"></td>

									<td><a href="round.html?round={{x.round }}"
										style="color: black; font-weight: normal;">{{x.round }}</a></td>

									<td ng-bind-html="x.result | trustHtml"></td>

								</tr>

							</tbody>

						</table>

					</center>
				</div>
				<h2>
					<div ng-show='busy ' class="bottom" align="center">loading....</div>
				</h2>
				<h2>
					<div ng-show='isData' class="bottom" align="center">已经到底了</div>
				</h2>
			</div>
		</div>
	</div>

	<script>
		var scrollAppModule = angular.module('scrollApp', [ 'infinite-scroll','ngSanitize' ]);//程序名字与数组内的插件名字
		scrollAppModule.controller(	'scrollController',
						function($scope, $http) {
							$scope.items = [];//页面存放数据的数组
							$scope.busy = false;//设置繁忙状态,防止重复提交请求数据,相当于上锁
							$scope.page = 0;//当前页
							$scope.tablelength = 50;//每页显示的条数
							$scope.isData = false;//用显示数据是否已全部请求完成
							$scope.recordsTotal = 0;//数据总条数
							$scope.nextPage = function() {
								if ($scope.busy)
									return;//判断当前数据是否请求完成
								$scope.busy = true;
								$http(
										{
											method : "GET",
											url : "data.html?id=${requestScope.acc.id}&&length="
													+ $scope.tablelength
													+ "&&start=" + $scope.page
										/* data:{"length":$scope.tablelength,"start":$scope.page},
										url : "data.html?id=${requestScope.acc.id}"   */
										})
										.then(
												function successCallback(
														response) {
													var data = response.data.data;
													$scope.page++;
													if (response.data.recordsTotal < $scope.tablelength
															* $scope.page) {
														$scope.isData = true;
													}

													for (var i = 0; i < data.length; i++) {
														data[i].date = new Date(
																data[i].date)
																.toLocaleString();
														$scope.items
																.push(data[i]);
													}
													$scope.busy = false;

												},
												function errorCallback(response) {
													// 请求失败执行代码
													$scope.busy = false;
												});
							};
						});
		scrollAppModule.filter('trustHtml', function($sce) {
			return function(input) {
				return $sce.trustAsHtml(input);
			}
		});
	</script>

</body>

</html>

