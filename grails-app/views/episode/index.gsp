<html>
    <head>
    	<meta name="layout" content="main" />
    </head>
    <body>
        <div>
        	<g:form method="get">
        		<input type="text" name="q" value="${params.q}"/>
        		<input type="submit"/>
        	</g:form>
        </div>
        <div>
        <table>
        	<thead>
        		<th>Series</th>
        		<th>S</th>
        		<th>E</th>
        		<th>Title</th>
        		<th>TheTVDB</th>
        		<th>IMDb</th>
        	</thead>
        	<tbody>
         	<g:each var="episode" in="${episodes}">
         	<tr>
        		<td>${episode.series_name}</td>
        		<td>${episode.season}</td>
        		<td>${episode.number}</td>
        		<td>${episode.episode_name}</td>
        		<td>
        			<a href="http://thetvdb.com/?tab=series&id=${episode.thetvdb_com_series_id}" target="_blank">series</a>
        			<a href="http://thetvdb.com/?tab=season&seriesid=${episode.thetvdb_com_series_id}&seasonid=${episode.thetvdb_com_season_id}" target="_blank">season</a>
        			<a href="http://thetvdb.com/?tab=episode&seriesid=${episode.thetvdb_com_series_id}&seasonid=${episode.thetvdb_com_season_id}&id=${episode.thetvdb_com_episode_id}" target="_blank">episode</a>
        		</td>
        		<td>
        			<g:if test="${episode.imdb_series_id}">
        				<a href="http://www.imdb.com/title/${episode.imdb_series_id}" target="_blank">series</a>
        			</g:if>
        		    <g:if test="${episode.imdb_episode_id}">
        				<a href="http://www.imdb.com/title/${episode.imdb_episode_id}" target="_blank">episode</a>
        			</g:if>
        		</td>
        	</tr>
        	</g:each>
        	</tbody>
        </table>
        </div>
        <div>
        </div>
    </body>
</html>
