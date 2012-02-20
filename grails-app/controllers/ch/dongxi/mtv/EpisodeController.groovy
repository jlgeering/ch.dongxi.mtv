package ch.dongxi.mtv

class EpisodeController {
	def index = {
		[episodes: Episode.collection.find().sort(series_name:1, season:1, number:1)]
	}
}
