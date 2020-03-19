# parsec_demo_webapp

[![Build Status](https://travis-ci.com/xyl3m/parsec_demo_webapp.svg?branch=master)](https://travis-ci.com/xyl3m/parsec_demo_webapp) [![codecov](https://codecov.io/gh/xyl3m/parsec_demo_webapp/branch/master/graph/badge.svg)](https://codecov.io/gh/xyl3m/parsec_demo_webapp)


## Commands
### Create testing database in Docker.
`docker run --name postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres`

### Setup web server for testing in Docker.
`docker run --name jetty -p 58080:8080 -d jetty:9.4-jre8`

### Packaging
`make pkg`

### Deploy
`make deploy`

### Run integration test
`make runFT config=dev.conf host=localhost:58080 tag=users`
