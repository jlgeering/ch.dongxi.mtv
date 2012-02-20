package ch.dongxi.mtv

import groovyx.gpars.actor.Actor;
import groovyx.gpars.actor.DynamicDispatchActor;

class UpdateService {

	static transactional = false

	Actor myActor = new DynamicDispatchActor({
		when {String msg -> println 'A String'; Thread.sleep(3000); println "lala"}
		when {Double msg -> println 'A Double'}
		when {msg -> println 'A something ...';stop()}
	})

	public UpdateService() {
		println "starting my actor"
		println "actor is active:" + myActor.isActive()
		myActor.start()
	}

	def work() {
		println "queing up more work"
		println "actor is active:" + myActor.isActive()
		myActor << "test"
	}

}
