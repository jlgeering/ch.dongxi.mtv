package ch.dongxi.mtv

import java.net.URL;

import org.bson.types.ObjectId;

import com.mongodb.gridfs.GridFS;

class ImageFileService {

	static transactional = false
	
	def images_fs = new GridFS(Image.collection.DB, 'images')

	def get(ObjectId id) {
		return images_fs.findOne(id)
	}

	def update(Image image) {
		def old_id = image.file_id
//		String filename = image.thetvdb_com_banner_id
		// remove all versions
//		banner_fs.remove(filename)
		image.source.withInputStream { input ->
			def file = images_fs.createFile(input)
			file.contentType = 'image/jpeg'
//			file.filename = filename
			file.save()
			log.debug "file id: " + file.id
			image.file_id = file.id
			image.save(flush:true)
		}
		if (old_id) {
			images_fs.remove(old_id)
		}
	}

	def clear(Image image) {
		def file_id = image.file_id
		if (file_id) {
			log.info "removing file of ${image} file: ${file_id}"
			images_fs.remove(file_id)
			image.file_id = null
			image.save(flush:true)
		}
	}

//	def getBanner(String filename) {
//		return images_fs.findOne(filename)
//	}


//	def updateBanner(String filename, URL source) {
//		banner_fs.remove(filename)
//		log.debug "downloading banner from: $source"
//		source.withInputStream { input ->
//			def file = banner_fs.createFile(input)
//			file.contentType = 'image/jpeg'
//			file.filename = filename
//			file.save()
//			log.debug "file: " + file
//			log.debug "file id: " + file.id
//			return file.id
//		}
//	}


//	def get(Banner banner) {
//		return images_fs.findOne(banner['file_id'])
//	}
	

}
