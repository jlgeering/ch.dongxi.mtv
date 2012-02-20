class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/list/$lang" (controller: 'series', action: 'list') {
			constraints {
				// apply constraints here
			}
		}

//		"/"(view:"/index")
		name home: "/" (controller: 'series', action: 'list')
		"500" (view:'/error')
	}
}
