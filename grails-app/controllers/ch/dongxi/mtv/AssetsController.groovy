package ch.dongxi.mtv

import org.bson.types.ObjectId;

import com.mongodb.gridfs.GridFS;

class AssetsController {

	ImageFileService imageFileService

	def serve = { FileCommand cmd ->
		
		if(!cmd.hasErrors()) {
			def file = imageFileService.get(cmd.id)
			if (file) {
				log.debug "serving file: " + file
				response.contentType   = file.contentType
				response.contentLength = file.length
				file.writeTo(response.outputStream)
				return 200
			}
		}
		
		return 404
	}

}
class FileCommand {
	ObjectId id
	static constraints = {
		id(nullable:false)
	}
}