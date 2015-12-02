<!doctype html>
<html lang="en">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A simple guessing game">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chat Client</title>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="CSC 456/556 Fall 2015">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileColor" content="#3372DF">


    <link href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    
	<link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.2/material.indigo-pink.min.css">
	<link rel="stylesheet" href="http://www.getmdl.io/templates/article/styles.css">
	<script src="https://storage.googleapis.com/code.getmdl.io/1.0.2/material.min.js"></script>
		
  </head>
  <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base" ng-app="csc-456-app">
    <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header" ng-controller="HomeController">
      <header class="demo-header mdl-layout__header mdl-layout__header--scroll mdl-color--grey-100 mdl-color-text--grey-800">
        <div class="mdl-layout__header-row">
          <a href="/chat-client/" style="text-decoration:none"><span class="mdl-layout-title" style="color:darkslategray">Chat Client</span></a>
          <div class="mdl-layout-spacer"></div>
        </div>
      </header>
      <div class="demo-ribbon"></div>
	  <main class="demo-main mdl-layout__content">
		<div class="demo-container mdl-grid">
			<div class="mdl-cell mdl-cell--2-col mdl-cell--hide-tablet mdl-cell--hide-phone"></div>
				<div class="demo-content mdl-color--white mdl-shadow--4dp content mdl-color-text--grey-800 mdl-cell mdl-cell--8-col">
					<h3>Login to Chat</h3>
					<h4>new test 3:48</h4>
					<div class="mdl-card mdl-shadow--2dp">
						<div class="mdl-card__supporting-text">
							<form method="post" action="users" class="ng-pristine ng-valid">
								<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
									<input class="mdl-textfield__input" type="text" id="firstname" name="firstname">
									<label class="mdl-textfield__label" for="firstname">First Name</label>
								</div>
								<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
									<input class="mdl-textfield__input" type="text" id="lastname" name="lastname">
									<label class="mdl-textfield__label" for="lastname">Last Name</label>
								</div>
								<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
									<input class="mdl-textfield__input" type="text" id="username" name="username">
									<label class="mdl-textfield__label" for="username">Username</label>
								</div>
								<div>
									<button type="submit" class="mdl-button mdl-js-button mdl-button--primary">Start Chatting</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	  </main>
  </body>
</html>
