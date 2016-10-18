<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<script type="text/javascript" src="resources/jslib/angular.min.js"></script>
        <script type="text/javascript"
	src="resources/jslib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="resources/bootstrap.min.js"></script>
        <script src="js/bootstrap/dist/js/bootstrap.min.js"></script>
        
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
       <script type="text/javascript" src="resources/js/modelDefination.js"></script>
        <script type="text/javascript" src="resources/controllers/realtime.js"></script>

</head>
<body>
 <div class="container" ng-app="TaskApp" ng-controller="TaskController">
            <h1>Real-time application <small>part 3</small></h1>
            
            <div ng-show="message" class="alert alert-{{message.type}}">
                <strong>
                    {{message.short}}: {{message.long}}
                </strong>
            </div>
            <div class="container-fluid">
                <div class="col-md-6 col-sm-12">
                    <h2>Add task</h2>
                    <form>
                        <div class="row">
                            <div class="form-group">
                                <label for="taskTitleFieldId">Title: </label>
                                <input type="text" id="taskTitleFieldId" 
                                       ng-model="task.title" 
                                       class="form-control"/>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="form-group">
                                <label for="taskDescriptionFieldId">
                                    Description: 
                                </label>
                                <textarea id="taskDescriptionFieldId" 
                                          ng-model="task.description" 
                                          class="form-control">
                                </textarea>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group">
                                <label for="durationFieldId">
                                    Duration (in seconds): 
                                </label>
                                <input type="number" id="durationFieldId" 
                                       class="form-control" 
                                       ng-model="task.duration"/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" 
                                               id="taskUniversalCheckId"
                                               ng-model="task.universal"/> 
                                        Public task
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <button type="button" 
                                        class="btn btn-success" 
                                        ng-click="addTask()">
                                    Add task
                                </button>
                                <button type="button" 
                                        class="btn btn-default" 
                                        ng-click="resetTask()">
                                    Reset form
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="col-md-6 col-sm-12">
                    <h2>Listing</h2>
                    <ul class="list-group" ng-hide="tasks.length == 0">
                        <li ng-repeat="curTask in tasks track by $index" 
                                class="list-group-item">
                            <strong>{{curTask.title}}</strong> - {{curTask.description}}
                            <span class="badge">{{curTask.duration}}</span>
                        </li>
                    </ul>
                    <p ng-show="tasks.length == 0" class="text-info">
                        No tasks to display.
                    </p>
                </div>
            </div>
        </div>
</body>
</html>