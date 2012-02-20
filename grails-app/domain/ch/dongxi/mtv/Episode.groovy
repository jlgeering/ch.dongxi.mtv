package ch.dongxi.mtv

import  org.bson.types.ObjectId

class Episode {

	ObjectId id
	
	Integer thetvdb_com_series_id
	Integer thetvdb_com_season_id
	Integer thetvdb_com_episode_id

	String lang
	
	String series_name
	String episode_name
	
	String imdb_series_id
	String imdb_episode_id
	
	String production_code
	
	Integer season
	Integer number
	Integer absolute
	
	Long lastupdated

	String toString() {
		return "$series_name [${String.format('%03d', absolute)}] [${String.format('%02d', season)}x${String.format('%02d', number)}] $episode_name (id:$thetvdb_com_series_id/$thetvdb_com_season_id/$thetvdb_com_episode_id, production_code:$production_code, lastupdated:$lastupdated)"
	}
	
	String getTitle() {
		return "$series_name [${String.format('%03d', absolute?:0)}] [${String.format('%02d', season)}x${String.format('%02d', number)}] $episode_name"
	}
	
	static mapWith = "mongo"

	static constraints = {
	}

	static transients = ['title']

}
