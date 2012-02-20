<meta name="layout" content="main" />

<h1>Series${params.lang ? ' [' + params.lang + ']' : ''}</h1>

<ul class="nav">
	<li><g:link controller="series" action="add">add a series</g:link></li>
</ul>



<%--<div class="box">--%>
<%--		<g:form method="get" action="add" class="bp">--%>
<%--		Add: <input type="text" name="q" value="${params.q}"/> <input type="submit"/>--%>
<%--	</g:form>--%>
<%--</div>--%>
<div class="box">
<g:each var="series" in="${all}">
	<div style="clear:both;">
		<div class="series-banner-wide">
			<g:link action="show" id="${series.thetvdb_com_series_id}">
				<g:if test="${series.banner_file_id}">
					<img alt="${series.series_name}" src="${createLink(controller:'assets', action:'serve', id: series.banner_file_id)}">
				</g:if>
				<g:else>
					<div class="placeholder">
						<span>${series.series_name}</span>
					</div>
				</g:else>
			</g:link>
		</div>
	</div>
</g:each>
</div>
