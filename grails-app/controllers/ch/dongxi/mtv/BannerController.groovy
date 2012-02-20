package ch.dongxi.mtv

import org.bson.types.ObjectId;

import com.mongodb.gridfs.GridFS;

class BannerController {

	ImageFileService imageFileService

	def select = { BannerCommand cmd ->
		if(cmd.hasErrors()) {
			redirect controller: 'series'
		}
		else {
			Banner banner = Banner.findByThetvdb_com_banner_id(cmd.id)
			if (banner) {
				if (banner.type == 'series') {
					Series series = Series.findByThetvdb_com_series_id(banner.thetvdb_com_series_id)
					log.info "setting banner for series " + series.series_name + " to " + banner
					series['banner_file_id'] = banner['file_id']
					series.save(flush:true)
				}

				redirect controller: 'series', action: 'show', id: banner.thetvdb_com_series_id
			}
			else {
				redirect controller: 'series'
			}
		}

	}

	def download = { BannerCommand cmd ->
		if(cmd.hasErrors()) {
			redirect controller: 'series'
		}
		else {
			Banner banner = Banner.findByThetvdb_com_banner_id(cmd.id)
			log.info "downloading file for " + banner
			if (banner) {
				imageFileService.download(banner)
				redirect controller: 'series', action: 'show', id: banner.thetvdb_com_series_id
			}
			else {
				redirect controller: 'series'
			}
		}
	}

	def delete = { BannerCommand cmd ->
		if(cmd.hasErrors()) {
			redirect controller: 'series'
		}
		else {
			Banner banner = Banner.findByThetvdb_com_banner_id(cmd.id)
			if (banner) {
				imageFileService.remove(banner)
				redirect controller: 'series', action: 'show', id: banner.thetvdb_com_series_id
			}
			else {
				redirect controller: 'series'
			}
		}

	}
}
class BannerCommand {
	Integer id
	static constraints = {
		id(nullable:false)
	}
}
