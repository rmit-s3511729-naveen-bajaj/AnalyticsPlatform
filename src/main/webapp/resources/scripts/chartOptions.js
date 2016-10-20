function getNumberFormat(maxValue,minValue){

	if(maxValue >= 100000){
		return ",.2s";
	}else if(minValue>=0 && maxValue <=1){
		return ",.3f";
	}else if(minValue>=0 && maxValue <=1000){
		return ",.2f";
	}else{
		return ",.2s";
	}
}

function analyse2Ddata(data,x,y){
	var maxXAxisLength = 0;
	var maxYValue = 0;
	var minYValue = 0;
	for (var i in data){
		var row = data[i];
		if(row[x].length > maxXAxisLength){
			maxXAxisLength = row[x].length;
		}
		if(row[y] < minYValue){
			minYValue = row[y];
		}
		if(row[y] > maxYValue){
			maxYValue = row[y];
		}

		var analyzedResult = {};
		analyzedResult["yFormat"] = getNumberFormat(maxYValue,minYValue); 
	}
	var dataLength = data.length;
	if(dataLength <= 5){
		analyzedResult["xRotateAngle"] = "0";
	}
	else if(dataLength >5 && dataLength <=20){
		analyzedResult["xRotateAngle"] = "-60";
	}
	else{
		analyzedResult["xRotateAngle"] = "-90";
	}
	return analyzedResult;
}



function getBarOptions(title,xLabel,yLabel,xColName,yColName,data,height){
	var analyzedResult = analyse2Ddata(data,xColName,yColName);
	if(title ==  null){
		enableTitle = false;
		title="";
	}else{
		enableTitle = true;
	}
	var options =  {
			chart: {
				type: 'discreteBarChart',
				height:height,
				margin : {
					top: 20,
					right: 20,
					bottom: 150,
					left: 55
				},

				"x": function(d){ return d[xColName]; },
				"y": function(d){ return d[yColName]; },
				showValues: true,
				valueFormat: function(d){
					return d3.format(analyzedResult.yFormat)(d);
				},
				transitionDuration: 500,
				xAxis: {

					axisLabel: xLabel,
					"rotateLabels":analyzedResult.xRotateAngle
				},
				yAxis: {
					axisLabel: yLabel,
					axisLabelDistance: -10
				}
			}, "title": {
				"enable": enableTitle,
				"text": title
			}
	}
	var data = [
	            {
	            	key: "b0",
	            	values: data
	            }
	            ];
	return {
		"options":options,
		"data":data
	}
}




function getLineOptions(title,xLabel,yLabel,xColName,yColName,inData){

	if(title ==  null){
		enableTitle = false;
		title="";
	}else{
		enableTitle = true;
	}

	var outData=[];
	var indexedRow = {};
	for(var i in inData){
		var row = inData[i];
		indexedRow[i] = row[xColName];
		var outRow = {};
		outRow[xColName] = i;
		outRow[yColName] = row[yColName];		
		outData.push(outRow);
	}


	var data = [
	            {
	            	key: "l0",
	            	values: outData
	            }
	            ];


	var options = 	{
			"chart": {
				"type": "lineChart",
				"margin": {
					"top": 20,
					"right": 20,
					"bottom": 150,
					"left": 55
				},
				"x": function(d){ return d[xColName]; },
				"y": function(d){ return d[yColName]; },
				"useInteractiveGuideline": true,
				"dispatch": {},
				"showLegend": false,
				"xAxis": {
					tickFormat: function (d){
						return indexedRow[d];
					},
					"axisLabel": xLabel
				},
				"yAxis": {
					"axisLabel": yLabel,
					"axisLabelDistance": -10
				},
				"transitionDuration": 250
			},
			"title": {
				"enable": enableTitle,
				"text": title
			}
	}

	return {
		"options":options,
		"data":data
	}
}

function getTreeChartOptions(title,xLabel,yLabel,xColName,yColName,inData,chartdivid){
	var geoData = [];
	var geoHeader = [xLabel,yLabel];
	geoData.push(geoHeader);
	for(var index in inData){
		geoData.push([inData[index][xLabel],inData[index][yLabel]]);
	}
	
	var data = google.visualization.arrayToDataTable(geoData);

	var options = {};
	$("[id='"+chartdivid+"']").find('#geo_div').empty();
	var chart = new google.visualization.GeoChart($("[id='"+chartdivid+"']").find('#geo_div')[0]);

	chart.draw(data, options);
	return {
		"options":options,
		"data":data
	}
}

//function getGeoOptions(title,xLabel,yLabel,xColName,yColName,inData,chartdivid){
//	var geoData = [];
//	var geoHeader = [xLabel,yLabel];
//	geoData.push(geoHeader);
//	for(var index in inData){
//		geoData.push([inData[index][xLabel],inData[index][yLabel]]);
//	}
//	
//	var data = google.visualization.arrayToDataTable(geoData);
//
//	var options = {colorAxis: {colors: ['red', 'blue','green']}};
//	
//	 
//	if(chartdivid == "geo_div"){
//	
//	var geoTimeout = setTimeout(function(){
//			var chart = new google.visualization.GeoChart($("[id='"+chartdivid+"']")[0]);
//			chart.draw(data, options);
//			clearTimeout(geoTimeout);
//		}, 20);
//	}
//	else{
//		$("[id='"+chartdivid+"']").find('#geo_div').empty();
//		var chart = new google.visualization.GeoChart($("[id='"+chartdivid+"']").find('#geo_div')[0]);
//		chart.draw(data, options);
//	}
//	
//	return {
//		"options":options,
//		"data":data
//	}
//}

function getWordCloudOptions(title,xLabel,yLabel,xColName,yColName,inData){
	var options = {};
	var data = [
	             {text: "Lorem", weight: 13},
	             {text: "Ipsum", weight: 10.5},
	             {text: "Dolor", weight: 9.4},
	             {text: "Sit", weight: 8},
	             {text: "Amet", weight: 6.2},
	             {text: "Consectetur", weight: 5},
	             {text: "Adipiscing", weight: 5},
	             /* ... */
	           ];
	return {
		"options":options,
		"data":data
	}
}

function getPieOptions(title,xLabel,yLabel,xColName,yColName,inData){
	if(title ==  null){
		enableTitle = false;
		title="";
	}else{
		enableTitle = true;
	}

	var outData=[];
	var indexedRow = {};

	var options = 	{
			chart: {
				type: 'pieChart',

				"x": function(d){ return d[xColName]; },
				"y": function(d){ return d[yColName]; },
				showLabels: true,
//				pie: {
//				dispatch: { //container of event handlers
//				elementClick: function(e){ pieClickHandler(e) },
//				elementMouseover: function(e){ console.log('mouseover') },
//				elementMouseout: function(e){ console.log('mouseout') }
//				}
//				},

				transitionDuration: 500,
				labelThreshold: 0.01,
				legend: {
					margin: {
						top: 5,
						right: 35,
						bottom: 5,
						left: 0
					}
				}
			}
	};

	return {
		"options":options,
		"data":inData
	}
}

function get3DOptions(chartType,title,xLabel,yLabel,zLabel,xColName,yColName,zColName,data,height){

	var analyzedResult = analyse2Ddata(data,xColName,yColName);
	if(title ==  null){
		enableTitle = false;
		title="";
	}else{
		enableTitle = true;
	}
	var options =  {
			chart: {
				type: chartType,
				height:height,
				"useInteractiveGuideline": true,
				"scatter": {
					"onlyCircles": false
				},
				margin : {
					top: 20,
					right: 20,
					bottom: 150,
					left: 55
				},
				"x": function(d){ return d[xColName]; },
				"y": function(d){ return d[yColName]; },
				showValues: true,
				valueFormat: function(d){
					return d3.format(analyzedResult.yFormat)(d);
				},
				showLegend: false,
				transitionDuration: 500,
				xAxis: {
					tickFormat: function (d){
						return x.domain()[d];
					},
					axisLabel: xLabel,
					"rotateLabels":analyzedResult.xRotateAngle
				},
				yAxis: {
					axisLabel: yLabel,
					axisLabelDistance: 30
				}
			}, "title": {
				"enable": enableTitle,
				"text": title
			}
	}



	var z = d3.scale.ordinal();
	var x = d3.scale.ordinal();

	z.domain(data.map(function(d) {
		return d[zLabel]
	}));
	x.domain(data.map(function(d) {
		return d[xLabel]
	}));
	var index = 0;
	var chartData = []
	for (var i = 0; i < z.domain().length; i++) {
		valuesArray = []
		for (var j = 0; j < x.domain().length; j++) {
			var row = {};
			row[xLabel] = j;
			row[yLabel] = 0;

			valuesArray.push(row);
		}
		var row = {

				"key" : z.domain()[i],
				"values" : valuesArray
		}
		chartData.push(row);
	}

	data.forEach(function(d) {
		for (var i = 0; i < z.domain().length; i++) {
			chartData[z.domain().indexOf(d[zLabel])].values[x.domain().indexOf(
					d[xLabel])][yLabel] = parseFloat(d[yLabel]);

			chartData[z.domain().indexOf(d[zLabel])].values[x.domain().indexOf(
					d[xLabel])][xLabel] = x.domain().indexOf(d[xLabel]);

		}
	});

	var data = chartData;
	console.log(JSON.stringify(data));
	return {
		"options":options,
		"data":data
	}
}

function getChartDetails(cType,tableData,xLabel,yLabel,zLabel,chartdivid){
	var chartType = "";	
console.log(tableData);
	if((tableData[0].hasOwnProperty("series") && Object.keys(tableData[0]).length >3)
			|| (!tableData[0].hasOwnProperty("series") && Object.keys(tableData[0]).length>=3)){
		switch(cType){
		case 'discretebar':
			chartType = "multiBarChart";
			break;
		case 'multiline':
			chartType = "lineChart";
			break;
		case 'horizontalbar':
			chartType = "multiBarHorizontalChart";
			break;
		case 'pie':
			chartType = "stackedAreaChart";
			break;
		case 'donut':
			chartType = "lineWithFocusChart";
			break;

		}

		var barOptions = get3DOptions(chartType,null,xLabel,
				yLabel,zLabel,xLabel,
				yLabel,zLabel,tableData);


		return barOptions;
	}
	else{
		if(cType == "discretebar"){

			var barOptions =  getBarOptions("Discrete Bar",xLabel,
					yLabel,xLabel,
					yLabel,tableData);
			return barOptions;	
		}else if(cType == "horizontalbar"){
			var barOptions =  getBarOptions("Discrete Bar",xLabel,
					yLabel,xLabel,
					yLabel,tableData);
			barOptions.options.chart.type = "multiBarHorizontalChart";
			return barOptions;

		}else if(cType == "multiline"){
			var barOptions =  getLineOptions("Line Chart",xLabel,
					yLabel,xLabel,
					yLabel,tableData);
			return barOptions;


		}else if(cType == "pie"){
			var barOptions =  getPieOptions("Pie Chart",xLabel,
					yLabel,xLabel,
					yLabel,tableData);
			return barOptions;
		}else if(cType == "donut"){
			var barOptions =  getPieOptions("Pie Chart",xLabel,
					yLabel,xLabel,
					yLabel,tableData);
			barOptions.options.chart.donut = true;
			barOptions.options.chart.cornerRadius = 30;
			return barOptions;

		}
//		else if(cType == "geo"){
//			
//			var barOptions =  getGeoOptions("geoChart",xLabel,
//					yLabel,xLabel,
//					yLabel,tableData,chartdivid);
//
//			return barOptions;
//
//		}
//else if(cType == "geo"){
//			
//			var barOptions =  getWordCloudOptions("WordCloud",xLabel,
//					yLabel,xLabel,
//					yLabel,tableData,chartdivid);
//
//			return barOptions;
//
//		}
	}
}






