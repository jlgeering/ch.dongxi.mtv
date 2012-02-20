package ch.dongxi.mtv

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFS;

import groovy.util.XmlSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import groovyx.gpars.actor.Actor;
import groovyx.gpars.actor.DynamicDispatchActor;


class TheTvDbService {

	static final String API_KEY = '6B9E2DE0E8C897B9';

	static transactional = false

	ImageFileService imageFileService

	Actor seriesUpdater = new DynamicDispatchActor({
		when {Integer seriesId -> update(seriesId)}
	})

	Actor imagesUpdater = new DynamicDispatchActor({
		when {Integer seriesId -> updateImages(seriesId)}
	})

	public TheTvDbService() {
		seriesUpdater.start()
		imagesUpdater.start()
	}

	def search(def seriesName) {
		def url = "http://www.thetvdb.com/api/GetSeries.php?seriesname=$seriesName"
		def data = new XmlSlurper().parse(url)
		return data.Series.collect { [id: it.seriesid.text(), name: it.SeriesName.text(), lang: it.language.text() ] }
	}

	def getBase(def seriesId) {
		def url = "http://www.thetvdb.com/api/$API_KEY/series/$seriesId/banners.xml"
		def data = new XmlSlurper().parse(url)
		def series
		data.Series.with {
			series = new Series(
					series_name:    SeriesName.text(),
					thetvdb_com_id: id.text(),
					tv_com_id:      SeriesID.text(),
					imdb_id:        IMDB_ID.text(),
					lang:           Language.text(),
					lastupdated:    lastupdated.text())
		}
		return series
	}

	def loadImages(Integer seriesId) {
		def url = "http://www.thetvdb.com/api/$API_KEY/series/$seriesId/banners.xml"
		def data = new XmlSlurper().parse(url)
		//		debug(data)
//		def images = []
		// TODO
		data.Banner.list().each { banner ->
			banner.with {
				def image = new Image(
						thetvdb_com_series_id:  seriesId,
						thetvdb_com_banner_id:  id.text(),
						type:                   BannerType.text(),
						subtype:                BannerType2.text(),
						lang:                   Language.text(),
						rating:                 Rating.text()?:0,
						rating_count:           RatingCount.text()?:0,
						path:                   BannerPath.text())
				if (image.type == 'season') {
					image.season = (banner.Season.text() as Integer)
				}
				image.save(flush:true)
//				images << image
			}
		}
//		return images
	}

	def getAll(def seriesId) {
		def url = "http://www.thetvdb.com/api/$API_KEY/series/$seriesId/all/en.xml"
		def data = new XmlSlurper().parse(url)
		def series
		def episodes = []
		data.Series.with {
			series = new Series(
					series_name:           SeriesName.text(),
					thetvdb_com_series_id: id.text(),
					tv_com_id:             SeriesID.text(),
					imdb_series_id:        IMDB_ID.text(),
					lang:                  Language.text(),
					lastupdated:           lastupdated.text())
		}
		data.Episode.list().each { episode ->
			episode.with {
				def e = new Episode(
						thetvdb_com_series_id:  seriesid.text(),
						thetvdb_com_season_id:  seasonid.text(),
						thetvdb_com_episode_id: id.text(),
						lang:                   Language.text(),
						series_name:            series.series_name,
						episode_name:           EpisodeName.text(),
						imdb_series_id:         series.imdb_series_id,
						imdb_episode_id:        IMDB_ID.text(),
						production_code:        ProductionCode.text(),
						season:                 SeasonNumber.text(),
						number:                 EpisodeNumber.text(),
						absolute:               absolute_number.text()?:0,
						lastupdated:            lastupdated.text())
				if (e.season > 0) {
					episodes << e
				}
			}
		}
		return [series:series,episodes:episodes]
	}

	def asyncUpdate(Integer seriesId) {
		seriesUpdater << seriesId
	}

	def update(Integer seriesId) {
		log.info 'Updating ' + seriesId
		def updated = getAll(seriesId)
		Series.collection.remove(thetvdb_com_series_id: seriesId)
		Episode.collection.remove(thetvdb_com_series_id: seriesId)
		updated.episodes.each { episode ->
			episode.save(flush:true)
		}

		def seasons = []
		Episode.collection.group(
				new BasicDBObject(['season': true]),
				new BasicDBObject(['thetvdb_com_series_id': seriesId]),
				new BasicDBObject(['count': 0]),
				'function(obj,prev) { prev.count += 1; }')
				.each {
					seasons[it.season as Integer] = it.count as Integer
				}
		updated.series.seasons = seasons.collect { it?:0 }
		updated.series.save(flush:true)
		
		asyncUpdateImages(seriesId)
		log.info 'Done updating ' + seriesId
	}
	
	def asyncUpdateImages(Integer seriesId) {
		imagesUpdater << seriesId
	}

	def updateImages(Integer seriesId) {
		log.info "Updating images for Series(id: ${seriesId})"
		def series = Series.findByThetvdb_com_series_id(seriesId)
		// update banners
		Image.collection.remove(thetvdb_com_series_id: seriesId)
		loadImages(seriesId)
		Image.withTransaction {
			def banner = Image.withCriteria(uniqueResult:true) {
//				eq('thetvdb_com_series_id', seriesId)
//				eq('type', 'series')
//				eq('subtype', 'graphical')
				order('rating','desc')
			}
			log.info("found a banner? " + banner)
		if (banner) {
			// get GORM object
//			banner = Image.findByThetvdb_com_banner_id(banner.thetvdb_com_banner_id)
		//	imageFileService.download(banner)
			log.info "setting banner for series " + series.series_name + " to " + banner
			//series['banner_file_id'] = banner['file_id']
			//series.save(flush:true)
		}
		}
//		def banner = Image.collection.find(
//				thetvdb_com_series_id:seriesId,
//				type: 'series',
//				subtype: 'graphical').sort(rating:-1).limit(1)?.next()
//		series.seasons.eachWithIndex { s, i ->
//			println "for season " + i
//			def seasonBanner = Banner.collection.find(thetvdb_com_series_id:seriesId, type: 'season', subtype: 'season', season: i, lang: 'en').sort(rating:-1).limit(1).next()
//			if (seasonBanner) {
//				// get GORM object
//				seasonBanner = Banner.findByThetvdb_com_banner_id(seasonBanner.thetvdb_com_banner_id)
//				imageFileService.download(seasonBanner)
//				series["banner_season_${i}_file_id"] = seasonBanner['file_id']
//				series.save(flush:true)
//			}
//		}
		log.info "Done updating images for Series(id: ${seriesId})"
	}

	private void debug(def data) {
		println XmlUtil.serialize(new StreamingMarkupBuilder().bind{ mkp.yield data })
	}
	
	private getBanner(def banner) {
		
	}
}
