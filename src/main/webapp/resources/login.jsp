<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Analytics Platform</title>

    <!-- Bootstrap core CSS -->

<!--     <link href="resources/css/bootstrap.min.css" rel="stylesheet">

    <link href="resources/fonts/css/font-awesome.min.css" rel="stylesheet">
    <link href="resources/css/animate.min.css" rel="stylesheet">

    Custom styling plus plugins
    <link href="resources/css/custom.css" rel="stylesheet">
    <link href="resources/css/icheck/flat/green.css" rel="stylesheet">


    <script src="resources/js/jquery.min.js"></script> -->

  <!-- build:css(.tmp) styles/main.css -->
    <link rel="stylesheet" href="resources/styles/main.css">
    <link rel="stylesheet" href="resources/styles/sb-admin-2.css">
    <link href="resources/styles/custom.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/bower_components/metisMenu/dist/metisMenu.min.css">
    <link rel="stylesheet" href="resources/bower_components/angular-loading-bar/build/loading-bar.min.css">
    <link rel="stylesheet" href="resources/bower_components/font-awesome/css/font-awesome.min.css" type="text/css">
    <!-- endbuild -->
    
    <!-- build:js(.) scripts/vendor.js -->
    <!-- bower:js -->
    <script src="resources/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="resources/bower_components/angular/angular.min.js"></script>
    <script src="resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="resources/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
    <script src="resources/bower_components/json3/lib/json3.min.js"></script>
    <script src="resources/bower_components/oclazyload/dist/ocLazyLoad.min.js"></script>
    <script src="resources/bower_components/angular-loading-bar/build/loading-bar.min.js"></script>
    <script src="resources/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
    <script src="resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

</head>

<body style="background:#F7F7F7;">
    
    <div class="">
        <a class="hiddenanchor" id="toregister"></a>
        <a class="hiddenanchor" id="tologin"></a>

        <div id="wrapper">
            <div id="login" class="animate form">
                <section class="login_content">
        				<form method="post" action="j_spring_security_check" modelAttribute="users">
                        <h1>Login Form</h1>
                        <div>
                            <input type="text" name="username" class="form-control" placeholder="Username" required="" />
                        </div>
                        <div>
                            <input type="password" name="password" class="form-control" placeholder="Password" required="" />
                        </div>
                        <div>
                            <input type="submit" value="Log in" />
                            <input type="reset" value="Reset" />
                        </div>
                        <div class="clearfix"></div>
                        <div class="separator">

                           <!--  <p class="change_link">New to site?
                                <a href="#toregister" class="to_register"> Create Account </a>
                            </p> -->
                        </div>
                    </form>
                    <!-- form -->
                </section>
                <!-- content -->
            </div>
                        </div>
                    </form>
                    <!-- form -->
                </section>
                <!-- content -->
            </div>
        </div>
    </div>

</body>

</html>