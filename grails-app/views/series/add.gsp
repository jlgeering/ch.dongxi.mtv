<meta name="layout" content="main" />

<h1>Add a Series</h1>

<ul class="nav">
	<li><g:link controller="series">back</g:link></li>
</ul>


<div>
	<g:form method="get" action="add">
		<input type="text" name="q" value="${params.q}"/>
		<input type="submit"/>
	</g:form>
</div>
<div>
	<g:each var="series" in="${results}">
		<div>[${series.lang}]
			<g:link action="update" id="${series.id}">update</g:link> <g:link action="show" id="${series.id}">${series.name}</g:link>
		</div>
	</g:each>
</div>
