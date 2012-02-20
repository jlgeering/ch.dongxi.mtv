<!DOCTYPE html>
<html>
<head>
	<title><g:layoutTitle default="dongxi.mtv" /></title>
	<link href="${resource(dir:'css',file:'screen.css')}" media="screen, projection" rel="stylesheet" type="text/css" />
	<link href="${resource(dir:'css',file:'print.css')}"  media="print"              rel="stylesheet" type="text/css" />
	<!--[if lt IE 8]>
		<link href="${resource(dir:'css',file:'ie.css')}" media="screen, projection" rel="stylesheet" type="text/css" />
	<![endif]-->
	<%--<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />--%>
	<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
	<g:layoutHead />
	<g:javascript library="application" />
</head>
<body class="bp one-col">
	<%--<div id="spinner" class="spinner" style="display:none;">
		<img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
	</div>
	<div id="grailsLogo"><a href="http://grails.org"><img src="${resource(dir:'images',file:'grails_logo.png')}" alt="Grails" border="0" /></a></div>--%>
	<div id="container">
		<header>
			<div id="top-menu">
				<div id="account">
					<ul>
						<li>[</li>
						<li><a href="#" class="login">all</a></li>
						<li><a href="#" class="login">en</a></li>
						<li><a href="#" class="login">es</a></li>
						<li>]</li>
						<li><a href="#" class="login">Sign in</a></li>
					</ul>
				</div>
				<ul>
					<li><g:link mapping="home">Home</g:link></li>
					<li><g:link controller="series">Series</g:link></li>
					<li><g:link controller="series" action="list" params="[lang:'en']">Series [EN]</g:link></li>
					<li><g:link controller="series" action="list" params="[lang:'es']">Series [ES]</g:link></li>
				</ul>
			</div>
    	</header>
		<div id="content"><g:layoutBody /></div>
		<footer>&copy; Jean-Luc Geering</footer>
	</div>
</body>
</html>