pkg:
	./gradlew clean war

deploy:
	docker cp build/libs/api.war jetty:/var/lib/jetty/webapps/
	docker restart jetty

runFT:
	./gradlew clean cucumber \
      -Dparsec.conf.env.context=$(config) \
      -DserviceUrl="http://$(host)/api/demo/v1/" \
      -Dcucumber.options="--tags @$(tag)"
