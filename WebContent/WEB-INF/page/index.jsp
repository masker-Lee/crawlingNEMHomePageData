<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class="cye-disabled cye-lm">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/s/dt/jqc-1.11.3,dt-1.10.10/datatables.min.css">

<script src="./index/jquery-1.10.1.min.js"></script>

<script >	$(document).ready(function () {
	$("td:contains(Deactivated)").css("color","red");
	$('#table').DataTable({ "lengthMenu": [[100, -1], [100, "All"]], "ordering": false });
	
})
</script>
 
<script type="text/javascript" src="./index/datatables.min.js"></script><style id="nightModeStyle">
html.cye-enabled.cye-nm:not(*:-webkit-full-screen) body,
 html.cye-enabled.cye-nm:not(*:-webkit-full-screen) #cye-workaround-body 
 {-webkit-filter:contrast(91%) brightness(84%) invert(1);}
 </style><style id="cyebody">html.cye-enabled.cye-lm 
 body{background-color:#c0dcc3 !important;border-color:rgb(48, 55, 48) !important;
 background-image:none !important;color:#000000  !important}
 </style><style id="cyediv">html.cye-enabled.cye-lm 
 div{background-color:#c0dcc3 !important;border-color:rgb(48, 55, 48) !important;
 background-image:none !important;color:#000000  !important}</style>
 <style id="cyetable">html.cye-enabled.cye-lm th{background-color:#c0dcc3 !important;
 border-color:rgb(48, 55, 48) !important;
 background-image:none !important;color:#000000  !important}
 html.cye-enabled.cye-lm td{background-color:#c0dcc3 !important;
 border-color:rgb(48, 55, 48) !important;
 background-image:none !important;
 color:#000000  !important}</style>
 <style id="cyetextInput">html.cye-enabled.cye-lm input[type=text]
 {background-color:#c0dcc3 !important;border-color:rgb(48, 55, 48) !important;
 background-image:none !important;color:#000000  !important}
 html.cye-enabled.cye-lm textarea{background-color:#c0dcc3 !important;
 border-color:rgb(48, 55, 48) !important;background-image:none !important;
 color:#000000  !important}</style><style id="cyeselect">
 html.cye-enabled.cye-lm select{background-color:#c0dcc3 !important;
 border-color:rgb(48, 55, 48) !important;background-image:none !important;
 color:#000000  !important}</style><style id="cyeul">
 html.cye-enabled.cye-lm ul{background-color:#c0dcc3 !important;
 border-color:rgb(48, 55, 48) !important;background-image:none !important;
 color:#000000  !important}</style><style id="cyeChangeByClick">
 html.cye-enabled.cye-lm .cye-lm-tag,html.cye-enabled.cye-lm.cye-lm-tag{background-color:#c0dcc3 !important;
 border-color:rgb(48, 55, 48) !important;background-image:none !important;
 color:#000000  !important}</style>

<style>
a{
font-weight:bold;
}

#links a{
padding:10px;
}
.table > tbody > tr:hover {
background-color:#FFE487;
}
.table > thead:first-child > tr:first-child > th {
text-align:center;
}
</style>



</head>

<body style="height:100%">



<div class="container" style="min-height: 100%;height: auto !important;margin: 0px auto -60px;padding: 0px 0px 60px;">

<div class="col-md-7">
<center><h1 class="text-center" style="color:#444;border:2px solid black">
	<b>PARTICIPANT NODES</b></h1></center>
	<p class="bg-info text-center">
	<b><i class="fa fa-info-circle"></i> Total: ${requestScope.accounts.size()} Nodes</b></p>

<table class="table table-bordered text-center" id="table">
      <thead style="background-color: #444; color: white; border-top: 2px solid rgb(255, 165, 0);">
        <tr class="text-center">
          <th>#</th>
          <th>Address</th>
          <th>Name</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
		<c:forEach items="${requestScope.accounts}" var="e">
        <tr>
        <td scope="row"><a href="details.html?id=${e.id}" style="color:#337AB7" target="_blank">${e.id}</a></td>
        <td>${e.ip}</td>
	<td><a href="details.html?id=${e.id}" style="color:#DD4814" target="_blank">${e.name}</a></td>
	
	<td style="color:green">${e.status}</td>
	
        </tr>
	</c:forEach>
	
</tbody>
    </table>
	
	
</script>
	
	</div>
	
	
	<br>
	
    <hr>
</div>

</body>
</html>
        
