package ch.dongxi.mtv

import java.io.File;

import groovy.util.XmlSlurper;

class HomeController {

	TheTvDbService theTvDbService
	
    def index = {
		theTvDbService.search("How")
		[]
	}
}
