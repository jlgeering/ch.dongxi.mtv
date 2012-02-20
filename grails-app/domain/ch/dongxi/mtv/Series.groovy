package ch.dongxi.mtv

import java.util.List;

import org.bson.types.ObjectId;

class Series {

	ObjectId id

	String series_name
	
	Integer thetvdb_com_series_id

	String tv_com_id
	String imdb_series_id
	
	String lang
	
	Long lastupdated
	
	int[] seasons = []
	
	String toString() {
		return "Series(name:$series_name, thetvdb_com_id:$thetvdb_com_series_id, tv_com_id:$tv_com_id, imdb_id:$imdb_series_id, lang:$lang, lastupdated:$lastupdated)"
	}
	
	def hasBanner() {
		return this['banner_file_id'] as Boolean
	}
	
	def hasSeasonBanner(int season) {
		return this["banner_season_${season}_file_id"] as Boolean
	}
	
	static mapWith = "mongo"

    static constraints = {
    }
}
	