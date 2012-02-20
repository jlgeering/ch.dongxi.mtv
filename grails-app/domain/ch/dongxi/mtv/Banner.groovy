package ch.dongxi.mtv

import java.net.URL;

import org.bson.types.ObjectId;

class Banner {

	ObjectId id

	Integer thetvdb_com_series_id
	Integer thetvdb_com_banner_id
	
	String type
	String subtype
	
	String lang

	Float   rating
	Integer rating_count

	String path

	def getThumbnail() {
		return '_cache/' + path
	}
	
	URL getSource() {
		return ('http://thetvdb.com/banners/' + path).toURL()
	}

	public String toString() {
		"Banner(lang: ${lang}, type: ${type}, subtype: ${subtype})"
	}

	static mapWith = "mongo"

	static transients = ['thumbnail','source']

	static constraints = {
	}

}
