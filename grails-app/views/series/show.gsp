<meta name="layout" content="main" />
	

<g:if test="${series.banner_file_id}">
	<img alt="${series.series_name}" src="${createLink(controller:'assets', action:'serve', id: series.banner_file_id)}" class="series-banner-wide" />
</g:if>
<g:else>
	<h1>${series.series_name}</h1>
</g:else>
	
<ul class="nav">
	<li><g:link action="update" id="${series.thetvdb_com_series_id}">images</g:link></li>
	<li><g:link action="update" id="${series.thetvdb_com_series_id}">update [EN]</g:link></li>
	<li><g:link action="update" id="${series.thetvdb_com_series_id}">update [ES]</g:link></li>
	<li><g:link action="banner" id="${series.thetvdb_com_series_id}">update banner</g:link></li>
</ul>



<div class="box">
<%--	<div class="series-banner-wide">--%>
<%--		<g:if test="${series.banner_file_id}">--%>
<%--			<img alt="${series.series_name}" src="${createLink(controller:'banner', action:'serve', id: series.banner_file_id)}">--%>
<%--		</g:if>--%>
<%--		<g:else>--%>
<%--			<div class="placeholder">--%>
<%--				<span>${series.series_name}</span>--%>
<%--			</div>--%>
<%--		</g:else>--%>
<%--	</div>--%>
	<div>
		<a href="http://thetvdb.com/?tab=series&id=${series.thetvdb_com_series_id}" target="_blank">
			<img alt="thetvdb" src="/images/tvdb.png" />
		</a>
	</div>
	<g:if test="${series.imdb_series_id}">
		<div><a href="http://www.imdb.com/title/${series.imdb_series_id}" target="_blank"><img alt="IMDb" src="/images/imdb.png" /></a></div>
	</g:if>
	<div>${series}</div>
</div>
<div class="box">
	<h1>${series.series_name} [${series.lang}]</h1>
	<h2>Seasons</h2>
	<g:each status="i" var="season" in="${seasons}">
	<g:if test="${season.count}">
	<div style="clear: both;">
		<h3 id="season-${ i }">Season ${ i } (${season.count} episodes)</h3>
		<div>Season 
			<g:each status="si" var="s" in="${seasons}">
			<g:if test="${s.count}"><a href="#season-${ si }">${ si }</a></g:if>
			</g:each>
		</div>
		<div style="float:left;">
			<g:if test="${season.banner}">
				<img alt="${series.series_name} season ${i}" src="${createLink(controller:'assets', action:'serve', id: season.banner)}" />
			</g:if>
		</div>
		<div><table style="width:500px;">
			<thead>
				<th>SxE</th>
				<th>Title</th>
				<th>TheTVDB</th>
				<th>IMDb</th>
			</thead>
			<tbody>
			<g:each var="episode" in="${season.episodes}">
				<tr>
					<td>[${formatNumber(number:episode.season, format:'00')}x${formatNumber(number:episode.number, format:'00')}]</td>
					<td>${episode.episode_name}</td>
					<td>
						<a href="http://thetvdb.com/?tab=season&seriesid=${episode.thetvdb_com_series_id}&seasonid=${episode.thetvdb_com_season_id}" target="_blank">season</a>
						<a href="http://thetvdb.com/?tab=episode&seriesid=${episode.thetvdb_com_series_id}&seasonid=${episode.thetvdb_com_season_id}&id=${episode.thetvdb_com_episode_id}" target="_blank">episode</a>
					</td>
					<td>
						<g:if test="${episode.imdb_episode_id}">
							<a href="http://www.imdb.com/title/${episode.imdb_episode_id}" target="_blank">episode</a>
						</g:if>
					</td>
				</tr>
			</g:each>
			</tbody>
		</table></div>
	</div>
	</g:if>
	</g:each>
</div>
<div class="box">
	<h2>Banners</h2>
	<g:each var="banner" in="${banners}">
		<div>
			<g:if test="${banner.file_id}">
				${ banner.lang } / ${ banner.type } / ${ banner.subtype }${ banner.season ? ' / ' + banner.season : '' } rating ${ banner.rating } (${ banner.rating_count })
				<g:link controller="banner" action="select" id="${ banner.thetvdb_com_banner_id }">select</g:link>
				<g:link controller="banner" action="delete" id="${ banner.thetvdb_com_banner_id }">delete</g:link>
				<br/>
				<img alt="TODO" src="${createLink(controller:'assets', action:'serve', id: banner.file_id)}">
			</g:if>
			<g:if test="${!banner.file_id}">
				<g:link controller="banner" action="download" id="${ banner.thetvdb_com_banner_id }">${ banner.lang } / ${ banner.type } / ${ banner.subtype }${ banner.season ? ' / ' + banner.season : '' } rating ${ banner.rating } (${ banner.rating_count })</g:link>
			</g:if>
			
		</div>
	</g:each>
</div>

