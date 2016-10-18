<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="resources/jslib/nv.d3.css">
<script type="text/javascript" src="resources/jslib/angular.min.js"></script>
<script type="text/javascript"
	src="resources/jslib/jquery-1.11.2.min.js"></script>
<script src="resources/jslib/d3.min.js"></script>
<script src="resources/jslib/nv.d3.js"></script> <!-- or use another assembly -->
<script src="resources/jslib/angular-nvd3.js"></script>
	
<script type="text/javascript" src="resources/jslib/jquery-latest.js"></script>
<script type="text/javascript" src="resources/jslib/jquery-ui.js"></script>
<script src="resources/modelDefination.js"></script>

<title>Add Data Sources</title>
</head>
<body>

	<div ng-app="dataSourceApp" ng-controller="dataSourceController" >
<div  ng-if="areaSelector=='DS'">
		<script src="resources/controllers/dataSourceController.js"></script>
		<script src="resources/directives/dataSourceDirective.js"></script>
		<label>Choose the Data Source type : </label>
		 <select ng-model="dsType">
			<option value="Select">Select</option>
			<option value="{{ x }}" ng-repeat="x in dsTypes">{{ x }}</option>
		</select>
		
		
		<div file-data-source ng-if="dsType=='file'"></div>
	</div>
<div  ng-if="areaSelector=='CHART'" ng-controller="chartController">
<script src="resources/js/constructDefaultnvd3Jsons.js"></script>
<script src="resources/controllers/chartController.js"></script>

<div style="height:1000px;width:1000px;border:1px solid">
    <nvd3 options="options" data="data"></nvd3>

</div>
</div>
</div>	



</body>
</html>