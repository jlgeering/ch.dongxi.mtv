package ch.dongxi.mtv

import java.net.URL;

import org.bson.types.ObjectId;

class Image {

	ObjectId id

	Integer thetvdb_com_series_id
	Integer thetvdb_com_banner_id
	
	String type
	String subtype
	
	String lang

	Float   rating
	Integer rating_count

	Integer season

	String path

	ObjectId file_id
	
	URL getSource() {
		return ('http://thetvdb.com/banners/' + path).toURL()
	}

	public String toString() {
		"Image(lang: ${lang}, type: ${type}, subtype: ${subtype})"
	}

	static mapWith = "mongo"

	static transients = ['source']

    static constraints = {
		season(nullable: true)
    }
}
