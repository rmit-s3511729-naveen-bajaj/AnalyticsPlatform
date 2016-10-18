app = angular.module("dataSourceApp", ['nvd3','ngDraggable','ngFileUpload','ui.bootstrap','angular-jqcloud','angularUtils.directives.dirPagination']);
app.run(function ($rootScope) {
    $rootScope.$on('scope.stored', function (event, data) {
        console.log("scope.stored", data);
    });
});

app.factory('Scopes', function ($rootScope) {
    var mem = {};
 
    return {
        store: function (key, value) {
            $rootScope.$emit('scope.stored', key);
            mem[key] = value;
        },
        get: function (key) {
            return mem[key];
        }
    };
});

SUCCESS_INDICATOR="&success&";
FAILURE_INDICATOR="&failure&";
NULL_INDICATOR="&null&";

HEADER_COLORS=["#3498db","#ed4b11","#25c43d","#2417d6","#b512c4","#c21345"];



appElement = document.querySelector('[ng-app=dataSourceApp]');

var getUrl = window.location;

SUPPORT_CHART_TYPES = ["Bar","Line","Pie"];
SUPPORT_CHART_TYPES_NVD3_INDEX = {"Bar":"discreteBarChart","Line":"lineChart","Pie":"pieChart"};

BASE_URL = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

SAVE_DASHBOARD_SERVICE_NAME=BASE_URL + "/services/saveNewDashboard/";
GET_DASHBOARD_DETAILS_SERVICE_NAME=BASE_URL + "/services/getDashboardDetails/";
EDIT_DASHBOARD_SERVICE_NAME=BASE_URL + "/services/editDashboard/";
DELETE_DASHBOARD_SERVICE_NAME=BASE_URL + "/services/deleteDashboard/";
EDIT_DATASOURCE_SERVICE_NAME=BASE_URL + "/services/editDataSource/";
DELETE_DATASOURCE_SERVICE_NAME=BASE_URL + "/services/deleteDataSource/";
SAVE_RDBMS_DATASOURCE_SERVICE_NAME = BASE_URL + "/services/saveRdbmsDS/";
EDIT_RDBMS_DATASOURCE_SERVICE_NAME = BASE_URL + "/services/editRdbmsDS/"; 
SAVE_NOSQL_MONGODB_DATASOURCE_SERVICE_NAME = BASE_URL + "/services/saveNoSqlMongoDBDS/";
EDIT_NOSQL_MONGODB_DATASOURCE_SERVICE_NAME = BASE_URL + "/services/editNoSqlMongoDBDS/";
SAVE_FILE_DATASOURCE_SERVICE_NAME = BASE_URL + "/services/saveFileDS/";
EDIT_FILE_DATASOURCE_SERVICE_NAME = BASE_URL + "/services/editFileDS/"; 
EDIT_FILE_DATASOURCE_WITHOUT_UPLOAD_SERVICE_NAME = BASE_URL + "/services/editFileDSWithoutUpload/"; 
GET_DATASOURCE_DETAILS_SERVICE_NAME = BASE_URL + "/services/getDataSource/";
SAVE_CHART_SERVICE_NAME = BASE_URL + "/services/saveNewChart/";
GET_COLUMN_NAMES_SERVICE_NAME = BASE_URL + "/services/getColumns/";

DELETE_CHART_SERVICE_NAME = BASE_URL + "/services/deleteChart/";

GET_CHART_DETAILS_SERVICE_NAME = BASE_URL + "/services/getChartObject/";
SET_CHART_DIMENSIONS_SERVICE_NAME = BASE_URL + "/services/setChartDimensions/";

ADD_CHART_TO_DASH_SERVICE_NAME=BASE_URL + "/services/addChartToDashboard/";

EDIT_CHART_SERVICE_NAME = BASE_URL + "/services/editChartObject/";

GET_CHART_DATA_REST_SERVICE = BASE_URL + "/services/queryForChart/";

UPDATE_FILTER_IN_DASHBOARD_SERVICE_NAME = BASE_URL + "/services/getChartDataWithUpdateFilter/"; 
UPDATE_CHART_AXIS_TO_DASH_SERVICE_NAME = BASE_URL + "/services/updateChartAxis/"; 
UPDATE_CHART_TYPE_TO_DASH_SERVICE_NAME = BASE_URL + "/services/updateChartType/"; 



	

