package ch.dongxi.mtv

import com.mongodb.BasicDBObject;

class SeriesController {

	TheTvDbService theTvDbService

	def index = {
		redirect action: 'list'
	}

	def add = {
		def results = []
		if (params.q) {
			results = theTvDbService.search(params.q)
		}
		//		if (results.size == 1) {
		//			redirect(action:show, id: results[0].id)
		//		}
		//		else {
		[results: results]
		//		}
	}

	def list = {
		[all: Series.collection.find().sort(series_name:1)]
	}

	def show = {
		if (!params.id) {
			return redirect(action:'add')
		}
		def id = params.id as Integer
		def series = Series.collection.findOne(thetvdb_com_series_id:id)
		def seasons = []
		series.seasons.eachWithIndex { count, i ->
			seasons << [
						count:count,
						episodes: Episode.collection.find(thetvdb_com_series_id:id, season:i).sort(number:1),
						banner: (series["banner_season_${i}_file_id"]?: '')
					]
		}
		[
			series: series,
			seasons: seasons,
			episodes: Episode.collection.find(thetvdb_com_series_id:id).sort(season:1, number:1),
			banners: Banner.collection.find(thetvdb_com_series_id:id).sort(lang: 1, type:1, subtype:1, season:1, rating:-1)
		]
	}
	def update = {
		if (params.id) {
			theTvDbService.asyncUpdate(params.id as Integer)
			redirect(action:'list')
			//			return redirect(action:'show', id:params.id)
		}
		else {
			return redirect(action:'list')
		}
	}
	def banner = {
		if (params.id) {
			theTvDbService.asyncUpdateBanners(params.id as Integer)
			redirect(action:'list')
		}
		else {
			return redirect(action:'list')
		}
	}
}
