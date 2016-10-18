<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Analytics Platform</title>
<style type="text/css">
[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak
	{
	display: none !important;
}
</style>

<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<link href="resources/fonts/css/font-awesome.min.css" rel="stylesheet">

<!-- Custom styling plus plugins -->
<link href="resources/css/custom.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.min.css">
<link rel="stylesheet" href="resources/css/nv.d3.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-multiselect.css">


<script type="text/javascript"
	src="resources/jslib/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="resources/jslib/angular.min.js"></script>
<script type="text/javascript" src="resources/jslib/d3.min.js"></script>
<script type="text/javascript" src="resources/jslib/nv.d3.min.js"></script>
<script type="text/javascript"
	src="resources/jslib/ng-file-upload-shim.js"></script>
<script type="text/javascript" src="resources/jslib/ng-file-upload.js"></script>

<!-- or use another assembly -->
<script type="text/javascript" src="resources/jslib/angular-nvd3.js"></script>
<script type="text/javascript" src="resources/jslib/jqcloud.js"></script>
<script type="text/javascript" src="resources/jslib/angular-jqcloud.js"></script>
<script type="text/javascript" src="resources/jslib/bootstrap.min.js"></script>
<script type="text/javascript"
	src="resources/jslib/ui-bootstrap-tpls-0.12.1.min.js"></script>
<script type="text/javascript"
	src="resources/jslib/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="resources/jslib/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="resources/jslib/smart-table.debug.js"></script>
<script type="text/javascript"
	src="resources/jslib/tablescrollplugin.js"></script>
<script type="text/javascript" src="resources/js/modelDefination.js"></script>
<script type="text/javascript" src="resources/js/chartOptions.js"></script>
<script type="text/javascript" src="resources/jslib/loadImg.js"></script>
<script src="resources/jslib/jquery.nicescroll.min.js"></script>
<script src="resources/jslib/jspdf.js"></script>
<script src="resources/jslib/sprintf.min.js"></script>
<script src="resources/jslib/base64.js"></script>
<script src="resources/jslib/html2canvas.js"></script>
<script src="resources/jslib/jspdf.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "geochart", "treemap" ]
	});
	google.setOnLoadCallback(getGeoOptions);
</script>
<script type="text/javascript" src="resources/jslib/ngDraggable.js"></script>
<!--
    <script src="resources/jslib/nprogress.js"></script>
    <script>
        NProgress.start();
    </script>
    
    [if lt IE 9]>
        <script src="../assets/js/ie8-responsive-file-warning.js"></script>
        <![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

</head>

<script src="resources/controllers/serviceController.js"></script>
<script
	src="https://rawgithub.com/eligrey/FileSaver.js/master/FileSaver.js"
	type="text/javascript"></script>



<body class="nav-md" ng-app="dataSourceApp"
	ng-controller="serviceController">



	<script type="text/javascript">
		if (location.href.lastIndexOf("#") == location.href.length - 1) {
			location.href = location.href.substr(0, location.href.length - 1);
		}
	</script>
	<div class="modal fade" id="deleteModalHorizontal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
				</div>

				<div class="modal-body">
					<p ng-cloak>{{ Text_Value }}</p>
					<p class="debug-url"></p>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<a class="btn btn-danger btn-ok" ng-click="deleteDashboard()">Delete</a>
				</div>
			</div>
		</div>
	</div>





	<div class="modal fade" id="myModalHorizontal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">{{ Model_Title_Label
						}}</h4>
				</div>

				<!-- Modal Body -->
				<div class="modal-body">

					<div class="errorMessage">{{ error_Message }}</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="inputEmail3">Name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control has-success"
								id="dashboardName" placeholder="Dashboard Name"
								ng-model="Text_Value" ng-change="dashchange()" />
						</div>
					</div>


					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Close</button>
						<button type="button" id="saveDashboard" ng-click="saveDash()"
							class="btn btn-primary">{{ Button_Label }}</button>
					</div>
				</div>
			</div>
		</div>
	</div>




	<div class="modal fade" id="addDSModel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">{{ Model_Title_Label
						}}</h4>
				</div>

				<!-- Modal Body -->
				<div class="modal-body">

					<div class="errorMessage">{{ error_Message }}</div>
					<div class="form-group">
						<form id="formId" method="POST" enctype="multipart/form-data"
							action="/services/saveFileDS/">
							<table>
								<tr>
									<td><label class="col-sm-2 control-label">DataSource
											Name </label></td>
									<td colspan=3>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="dsName"
												id="dsName" placeholder="DataSource Name" ng-model="dsName" />
										</div>
									</td>
								</tr>

								<tr>
									<td><label class="col-sm-2 control-label">DataSource
											Type </label></td>
									<td>
										<div class="col-sm-10">
											<select ng-model="dbType" class="form-control" name="dbType"
												id="dbType" placeholder="DataSource Name">
												<option ng-selected="dbType == db" ng-bind="db" ng-cloak
													ng-repeat="db in db_types" value="{{ db }}">{{ db
													}}</option>
											</select>
										</div>
									</td>
									<td><label class="col-sm-2 control-label">Sub Type</label>
									</td>
									<td>
										<div class="col-sm-10">
											<select ng-model="$parent.dsType" ng-init="$parent.dsType = supp_db[0]" ng-if="dbType == 'RDBMS'"
												class="form-control" id="dsType"
												placeholder="DataSource Name">
												<option ng-selected="dsType == db" ng-bind="db" ng-cloak
													ng-repeat="db in supp_db" value="{{ db }}">{{ db
													}}</option>
											</select> <select ng-model="$parent.dsType" ng-init="$parent.dsType = supp_files[0]" name="dsType"
												ng-if="dbType == 'File'" class="form-control" id="dsType">
												<option ng-selected="dsType == db" ng-bind="db" ng-cloak
													ng-repeat="db in supp_files" value="{{ db }}">{{
													db }}</option>
											</select>
											<select ng-model="$parent.dsType" ng-init="$parent.dsType = dsNoSqlTypes[0]" ng-if="dbType == 'NoSql'"
												class="form-control" id="dsType"
												placeholder="DataSource Name">
												<option ng-selected="dsType == db" ng-bind="db" ng-cloak
													ng-repeat="db in dsNoSqlTypes" value="{{ db }}">{{ db
													}}</option>
											</select>
										</div>
									</td>
								</tr>

								<tr ng-if="dbType == 'File'">
									<td><label class="col-sm-2 control-label">File
											Type </label></td>
									<td><select ng-model="$parent.dsFileType"
										name="dsFileType" class="form-control" id="dsFileType">
											<option ng-selected="dsFileType == fileType"
												ng-bind="fileType" ng-cloak
												ng-repeat="fileType in dsFileTypes" value="{{ fileType }}">{{
												fileType }}</option>
									</select></td>

									<td ng-if="dsFileType == 'Delimiter'"><label
										class="col-sm-2 control-label">Delimiter</label></td>
									<td ng-if="dsFileType == 'Delimiter'"><input type="text"
										class="form-control" id="delimiter" name="delimiter"
										placeholder="Delimiter" ng-model="$parent.$parent.delimiter" />
									</td>
								</tr>
								<tr ng-if="dbType == 'File'">
									<td><label class="col-sm-2 control-label">Location</label>
									</td>
									<td>
										<button type="file" ngf-select="uploadPic($file)"
											ngf-max-height="1000" ngf-max-size="1MB">Select File</button>
									<td>{{fileLocation.name}}</td>
								</tr>
								<tr ng-if="dbType == 'RDBMS' || dsType == 'MongoDB'">
									<td><label class="col-sm-2 control-label">Host
											Name </label></td>
									<td>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="host"
												placeholder="Host" ng-model="$parent.host" />
										</div>
									</td>
									<td><label class="col-sm-2 control-label">Port</label></td>
									<td>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="port"
												placeholder="Port" ng-model="$parent.port" />
										</div>
									</td>
								</tr>
								
								<tr ng-if="dbType == 'RDBMS' || dsType == 'MongoDB'">
									<td><label class="col-sm-2 control-label">DataBase
											Name </label></td>
									<td>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="dbname"
												placeholder="DB Name" ng-model="$parent.dbname" />
										</div>
									</td>
									<td><label class="col-sm-2 control-label">User
											Name </label></td>
									<td>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="userName"
												placeholder="UserName" ng-model="$parent.userName" />
										</div>
									</td>
								</tr>
								<tr ng-if="dbType == 'RDBMS' || dsType == 'MongoDB'">
									<td><label class="col-sm-2 control-label">Password</label>
									</td>
									<td>
										<div class="col-sm-10">
											<input type="password" class="form-control" id="password"
												placeholder="Password" ng-model="$parent.password" />
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>




					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Close</button>
						<button type="button" id="saveDashboard" ng-click="saveDash()"
							class="btn btn-primary">{{ Button_Label }}</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/directives/multiselectDirective.js"></script>
	<!-- chart model -->
	<div class="modal fade" id="addChartModel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">{{ Model_Title_Label
						}}</h4>
				</div>

				<!-- Modal Body -->
				<div class="modal-body">

					<div class="errorMessage">{{ error_Message }}</div>
					<div class="form-group">
						<table>
							<tr>
								<td><label class="col-sm-2 control-label">Chart
										Name </label></td>
								<td colspan="3">
									<div class="col-sm-10">
										<input type="text" class="form-control" id="chartName"
											placeholder="Chart Name" ng-model="chartName" />
									</div>
								</td>
							</tr>
							<tr>
								<td><label class="col-sm-2 control-label">DataSource
										Name </label></td>
								<td colspan="3">
									<div class="col-sm-10">
										<select ng-model="dsName" class="form-control" id="dsName"
											ng-change="updateTables()">
											<option ng-selected="dsName == ds" ng-bind="ds" ng-cloak
												ng-repeat="ds in dsnames" value="{{ ds }}">{{ ds }}</option>
										</select>
								</td>
								</td>
							</tr>
						</table>
					</div>




					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Close</button>
						<button type="button" id="saveDashboard" ng-click="saveChart()"
							class="btn btn-primary">{{ Button_Label }}</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div ng-cloak class="container body">

		<script src="resources/controllers/dashboardController.js"></script>


		<div class="main_container" ng-controller="dashboardController">

			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">

					<div class="navbar nav_title" style="border: 0;">
						<a href="index.html" class="site_title"><i class="fa fa-paw"></i>
							<span>Analytics Platform</span></a>
					</div>
					<div class="clearfix"></div>

					<!-- /menu prile quick info -->



					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">

						<div class="menu_section">

							<ul class="nav side-menu">
								<li><a><i class="fa fa-home"></i> Dashboards <span
										class="fa fa-chevron-down"></span><span
										ng-click="addDashModel()" data-toggle="modal"
										data-target="#myModalHorizontal" id="newDashboard"
										class="fa fa-plus-square" style="cursor: pointer"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li data- ng-cloak ng-repeat="dashboard in dashboards"><span
											ng-click="editDashModel(dashboard)" class="fa fa-pencil"
											data-toggle="modal" data-target="#myModalHorizontal"></span><span
											ng-click="deleteDashModel(dashboard)" class="fa fa-trash-o"
											data-toggle="modal" data-target="#deleteModalHorizontal"></span>
											<a ng-if="dashboard == active_dash_name" id="{{ dashboard }}"
											href="{{ BASE_URL }}?{{ dashboard }}" class="activeDashboard">{{
												dashboard }}</a> <a ng-if="dashboard != active_dash_name"
											id="{{ dashboard }}" href="{{ BASE_URL }}?{{ dashboard }}">{{
												dashboard }}</a></li>
									</ul></li>

								<li><a><i class="fa fa-desktop"></i> Charts <span
										class="fa fa-chevron-down"></span><span id="newChart"
										ng-click="addChartModel()" data-toggle="modal"
										data-target="#addChartModel" class="fa fa-plus-square"
										style="cursor: pointer"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li data- ng-cloak ng-repeat="chart in charts"><span
											ng-click="editChartModel(chart)" class="fa fa-pencil"
											data-toggle="modal" data-target="#addChartModel"></span><span
											ng-click="deleteChartModel(chart)" class="fa fa-trash-o"
											data-toggle="modal" data-target="#deleteModalHorizontal"></span><a
											id="{{ chart }}"
											href="{{ BASE_URL }}?{{ active_dash_name }}&{{ chart }}">{{
												chart }}</a></li>
									</ul></li>

								<li><a><i class="fa fa-table"></i> Tables <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li data- ng-cloak ng-repeat="chart in charts"><a
											id="{{ chart }}">{{ chart }}</a></li>
									</ul></li>

								<li><a><i class="fa fa-edit"></i> Data Sources <span
										class="fa fa-chevron-down"></span><span id="newDS"
										ng-click="addDSModel()" data-toggle="modal"
										data-target="#addDSModel" id="newDashboard"
										class="fa fa-plus-square" style="cursor: pointer"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li data- ng-cloak ng-repeat="ds in dsnames"><span
											ng-click="deleteDSModel(ds)" class="fa fa-trash-o"
											data-toggle="modal" data-target="#deleteModalHorizontal"></span><a
											ng-click="editDSModel(ds)" data-toggle="modal"
											data-target="#addDSModel" id="{{ ds }}">{{ ds }}</a></li>
									</ul></li>

								<li><a><i class="fa fa-bar-chart-o"></i> Filters <span
										class="fa fa-chevron-down"></span><span
										ng-click="addFilterModel()" data-toggle="modal"
										data-target="#myModalHorizontal" id="newDashboard"
										class="fa fa-plus-square" style="cursor: pointer"></span></a>
									<ul class="nav child_menu" style="display: none">
									</ul></li>

							</ul>
						</div>

					</div>
				</div>
			</div>

			<div class="top_nav">

				<div class="nav_menu">
					<div class="messageClass alert alert-success">
						<strong>Success!</strong> This alert box could indicate a
						successful or positive action.
					</div>
					<div class="messageClass alert alert-info">
						<strong>Info!</strong> This alert box could indicate a neutral
						informative change or action.
					</div>
					<div class="messageClass alert alert-warning">
						<strong>Warning!</strong> This alert box could indicate a warning
						that might need attention.
					</div>
					<div class="messageClass alert alert-danger">
						<strong>Danger!</strong> This alert box could indicate a dangerous
						or potentially negative action.
					</div>
					<nav class="" role="navigation">
					<div class="nav toggle">
						<a id="menu_toggle"><i class="fa fa-bars"></i></a>
					</div>

					<ul class="nav navbar-nav navbar-right">
					<li ng-click = "exportPdf()">Export PDF</li>
						<li class=""><a href="javascript:;"
							class="user-profile dropdown-toggle" data-toggle="dropdown"
							aria-expanded="false"> Admin <span class=" fa fa-angle-down"></span>
						</a>
							<ul
								class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
								<li><a href="javascript:;"> Profile</a></li>
								<li><a href="javascript:;"> <span
										class="badge bg-red pull-right">50%</span> <span>Settings</span>
								</a></li>
								<li><a href="javascript:;">Help</a></li>
								<li><a href="login.html"><i
										class="fa fa-sign-out pull-right"></i> Log Out</a></li>
							</ul></li>


					</ul>
					</li>

					</ul>
					</nav>
				</div>

			</div>
			<!-- /top navigation -->

			<!-- dashboard content -->
			<div class="right_col" role="main"
				ng-if="containerView == 'dashboard'">
				<!-- /top tiles -->
				<div class="row" id="dashcharts">
					<script src="resources/directives/divResizeDirective.js"></script>
					<script src="resources/directives/enterKeyPress.js"></script>
					<script src="resources/directives/dirPagination.js"></script>


					<input ng-cloak type="text" ng-model="$parent.userFilters"
						placeholder="Add Filters Ex:- country=USA,India;year=2015;"
						my-enter="changeUserDefinedFilter()">

					<div ng-cloak
						ng-style="{ 'border': '1px ' + chartObject.chartHeaderColor + ' solid' ,'width':chartObject.chartWidth+'%','height':chartObject.chartHeight}"
						ng-if="chartObject.dashboardIndicator"
						resizable="{{ chartObject }}" data- ng-cloak
						ng-repeat="chartObject in chartObjects"
						id="{{ chartObject.chartName }}"
						class="col-md-12 col-sm-12 col-xs-12 chartDiv ">


						<div
							ng-style="{ 'background-color':chartObject.chartHeaderColor }"
							class="chartHeader">
							{{ chartObject.chartName }} <i
								ng-if="!chartObject.maximizeIndicator"
								class="fa fa-close headericons"
								ng-click="removeChartFromDash(chartObject.chartName)"></i> <i
								class="fa fa-expand headericons expandChart"
								ng-click="maximizeChart(chartObject)"></i> <i
								ng-if="chartObject.maximizeIndicator  && chartObject.tableIndicator"
								class="fa fa-file-excel-o headericons"
								ng-click="exportToExcel(chartObject)"></i> <i
								ng-if="chartObject.maximizeIndicator && !chartObject.tableIndicator"
								class="fa fa-table headericons"
								ng-click="tableChart(chartObject)"></i> <i
								ng-if="chartObject.maximizeIndicator && !chartObject.tableIndicator"
								class="fa fa-file-image-o headericons"
								ng-click="exportToImage(chartObject)"></i>
						</div>
						<div ng-style="{ 'height': chartObject.chartHeight-30}"
							ng-if="!chartObject.tableIndicator && chartObject.chartParams.chartType == 'geo'"
							id="geo_div"></div>

						<nvd3
							ng-if="!chartObject.tableIndicator && chartObject.chartParams.chartType != 'geo'"
							options="chartObject.options" data="chartObject.data"></nvd3>

						<div ng-if="chartObject.tableIndicator">
							<!-- 	<table-pagination obj="chartObject"></table-pagination> -->
							<div id="exportable_{{ chartObject.chartName }}">
								<table class="table table-striped">
									<thead>
										<tr>
											<th ng-click="sort(key)" ng-cloak
												ng-repeat="(key,value) in chartObject.tableData[0]">{{
												key }}<span class="glyphicon sort-icon"
												ng-show="sortKey==key"
												ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
											</th>

										</tr>
									</thead>
									<tbody>

										<tr
											dir-paginate="row in chartObject.tableData|orderBy:sortKey:reverse|itemsPerPage:5">
											<td ng-cloak ng-repeat="(key,value) in row">{{ value }}</td>
										</tr>
									</tbody>
								</table>
							</div>
							<dir-pagination-controls style="margin-left: 40%;" max-size="5"
								direction-links="true" boundary-links="true">
							</dir-pagination-controls>

						</div>
					</div>
				</div>
			</div>
			<script src="resources/controllers/chartController.js"></script>

			<!-- chart content -->
			<div ng-cloak class="right_col tabbable"
				ng-controller="chartController" role="mainChart"
				ng-if="containerView == 'chart'">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">General</a></li>
					<li><a href="#tab2" data-toggle="tab">Style</a></li>
				</ul>
				<div class="tab-content">



					<!-- /top tiles -->

					<div class="row tab-pane active" id="tab1">
						<div
							class="chartpanel leftchartpanel col-md-12 col-sm-12 col-xs-12 panel list-group">

							<div ng-cloak ng-repeat="(table, columns) in tabColObject">
								<a ng-cloak ng-model="table" ng-click="getColumns(table)"
									href="#" class="list-group-item" data-toggle="collapse"
									data-target="#{{ table }}" data-parent="#menu">{{ table }}
									<span ng-hide="columns.length==0" class="label label-info">{{
										columns.length }}</span>
								</a>
								<div id="{{ table }}" class="sublinks collapse">
									<a ng-cloak class="list-group-item small" data- ng-cloak
										ng-repeat="column in columns  track by $index" ng-drag="true"
										ng-drag-data="getdragData(table,column)"
										data-allow-transform="true"><span
										class="glyphicon glyphicon-chevron-right"></span>{{ column }}</a>
								</div>
							</div>
							<script>
								$(".chartpanel").niceScroll();
							</script>
						</div>

						<div ng-drag-clone="">{{clonedData}}</div>


						<table class="chartparams">
							<tr>
								<td>
									<div class="axis dimension" ng-drop="true"
										ng-drop-success="onXDropComplete($data,$event)">
										<span>Dimensions</span>
										<div class="dimensionclass" ng-cloak
											ng-repeat="dmodel in dimensions">
											<input type="text" id="{{ dmodel }}" value="{{ dmodel }}" />
											<i class="fa fa-close headericons"
												ng-click="removeDimension(dmodel)"></i>
										</div>
									</div>
								</td>
								<td rowspan="2">
									<div class="axis">
										<span>Types</span>
										<table class="chartTypesImage">
											<tr>
												<td><img class="active"
													ng-click="typesSelected('discretebar')"
													src="resources/images/charts/xyaxis_bar.JPG"
													id="discretebar"></td>
												<td><img ng-click="typesSelected('horizontalbar')"
													src="resources/images/charts/horizontalbar.JPG"
													id="horizontalbar"></td>
											</tr>
											<tr>
												<td><img ng-click="typesSelected('multiline')"
													src="resources/images/charts/multiline.JPG" id="multiline"></td>
												<td><img ng-click="typesSelected('pie')"
													src="resources/images/charts/pie.JPG" id="pie"></td>
											</tr>
											<tr>
												<td><img ng-click="typesSelected('donut')"
													src="resources/images/charts/donut.JPG" id="donut"></td>
												<td><img ng-click="typesSelected('geo')"
													src="resources/images/charts/geo.JPG" id="geo"></td>
											</tr>
										</table>
									</div>
								</td>
								<td rowspan="2">
									<div class="axis">
										<span>Axis</span>
										<table class="chartImage">
											<tr>
												<td><img class="active"
													ng-click="axisSelected('xyaxis_bar')"
													src="resources/images/charts/xyaxis_bar.JPG"
													id="xyaxis_bar"></td>
												<td><img ng-click="axisSelected('xaxis_bar')"
													src="resources/images/charts/xaxis_bar.JPG" id="xaxis_bar"></td>
											</tr>
											<tr>
												<td><img ng-click="axisSelected('leftyaxis_bar')"
													src="resources/images/charts/leftyaxis_bar.JPG"
													id="leftyaxis_bar"></td>
												<td><img ng-click="axisSelected('noaxis_bar')"
													src="resources/images/charts/noaxis_bar.JPG"
													id="noaxis_bar"></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="axis dimension" ng-drop="true"
										ng-drop-success="onYDropComplete($data,$event)">
										<span>Expression</span>
										<div class="dimensionclass" ng-cloak
											ng-repeat="emodel in expressions">
											<select ng-model="emodel.aggregate" class="form-control">
												<option ng-selected="emodel.aggregate == agg" ng-bind="agg"
													ng-cloak ng-repeat="agg in aggregateMethods"
													value="{{ agg }}">{{ agg }}</option>
											</select> <input type="text" id="{{ emodel.expField }}"
												value="{{ emodel.expField }}" /> <i
												class="fa fa-close headericons"
												ng-click="removeExpression(emodel)"></i>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<button class="chartbtn" ng-click="plotChart()">Plot</button>
								</td>
								<td>
									<button class="chartbtn" ng-click="addToDashboard()">Add
										To Dashboard</button>
								</td>
							</tr>
						</table>
						<!--  </div>-->

					</div>
				</div>

				<div ng-if="stats == 'chart'" class="chartDiv chartpage">
					 <div ng-show="errorIndicator == 'true'" class="errorMessage">{{ error_Message }}</div>
					 <div ng-show="errorIndicator == 'false'" ng-if="chartType == 'geo'" id="geo_div"></div> 
					<!-- <jqcloud ng-if="chartType == 'geo'" words="data" steps="7"></jqcloud>-->
					<nvd3 ng-show="errorIndicator == 'false'" ng-if="chartType != 'geo'" options="options" data="data"></nvd3>
				</div>



			</div>
		</div>
	</div>


	<div class="overlayDiv"></div>
	<script src="resources/js/custom.js"></script>


</body>

</html>