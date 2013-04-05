<!DOCTYPE html>
<html>
<head>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>

<link rel="stylesheet" href="css/ui-lightness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/login.css" />

<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.1.custom.js"></script>
<script src="js/bootstrap.js"></script>

</head>

<body>
	<div class="block">
		<div ><img src="/img/logo-940.png" id="logo"></img></div>
	</div>
	<div class="form">
	<form action="login" method="post">
		<div class="row-fluid">
			<div class="span2 offset4">
				<input type="text" name="user" class="input-large"
					placeholder="Username">
			</div>
			<div class="span2">
				<input type="password" name="pwd" class="input-large"
					placeholder="Password">
			</div>
		</div>

		<div class="row-fluid">
			<%
				String isLogin = (String) request.getSession().getAttribute("loginStatus");
				if (isLogin != null) {
					if (isLogin.equals("false")) {
			%><div id="error" class="span6 offset3">
				<div class="alert alert-error">Wrong Username or Password</div>
			</div>
			<%
					} else {
						response.sendRedirect("/main.jsp");
					}
				}
			%>
		</div>
		
		<div class="row-fluid">
			<div class="span2 offset4 leftButton">
				<a href="#myModal" role="button" class="btn btn-large btn-primary" data-toggle="modal">Register</a>
			</div>
			<div class="span2 rightButton">
				<button id="signin" type="submit" class="btn btn-large btn-primary">Sign in</button>
			</div>
		</div>
	</form>
	</div>
	<!-- Modal -->
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
			<h3 id="myModalLabel">New User</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
				<div class="control-group">
					<label class="control-label" for="userName">Username</label>
					<div class="controls">
						<input type="text" id="userName" placeholder="User Name">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputPassword">Password</label>
					<div class="controls">
						<input type="password" id="inputPassword" placeholder="Password">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="verifyPassword">Confirm password</label>
					<div class="controls">
						<input type="password" id="verifyPassword" placeholder="Confirm password">
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<label class="checkbox"><input type="checkbox">I agree the private policy</label>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary">Register</button>
		</div>
	</div>

</body>
</html>