
angular.module('bdaApp').directive('resizable', function ($http) {
    return {
        restrict: 'A',
        scope: {
            callback: '&onResize'
        	
        },
        link: function postLink(scope, elem, attrs) {
            $(elem).resizable();
            //$(elem).find('nvd3').imgPreload();
            elem.on('resizestop', function (evt, ui) {
            	var chart = JSON.parse(attrs.resizable);
            	console.log(chart);
            	
            	var postJson = {};
            	postJson["dashboardName"]="dashboard1";
            	postJson["chartName"]=$(elem).attr("id");
            	postJson["height"]=$(elem).height();
            	console.log(ui.size);
            	
            	var width = ui.size.width -20;
            	$(elem).width(width+"px");
            	postJson["width"]=width;
            	$(window).trigger('resize');
        		$http.post("/AnalyticsPlatform/services/setChartDimensions/",postJson).
        		then(function(response) {
        		
					if(response.data.status == "&success&"){
						if(chart.chartParams.chartType == "geo"){
							$("[id='"+chart.chartName+"']").find('#geo_div').empty();
							
							$("[id='"+chart.chartName+"']").find('#geo_div').height($(elem).height()-30);
							
							getGeoOptions(chart.chartName,chart.chartParams.xAxisLabel,chart.chartParams.yAxisLabel,chart.chartParams.xAxis,chart.chartParams.yAxis,chart.tableData,chart.chartName);
							
							
						}
						else{
							$(window).trigger('resize');
							window.dispatchEvent(new Event('resize'));
						}
					}else{
	        			alert("**Unexpected Error in setting dimensions of the div");
					}
        		}, function(response) {
        			alert("Unexpected Error in setting dimensions of the div");
        		});
        		
                if (scope.callback) { scope.callback(); }
            });
        }
    };
});