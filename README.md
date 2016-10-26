[![Build Status](https://travis-ci.org/garystafford/fav-color-service.svg?branch=master)](https://travis-ci.org/garystafford/fav-color-service)

# Favorite Color Service

_Work in Progress_

## Introduction

Simple Spring Boot RESTful microservice, backed by MongoDB, part of an upcoming article on CI/CD with Spring Boot, HashiCorp product-line, and AWS.

## Quick Start

To clone, build, test, and run the Favorite Color service locally. Requires MongoDB to be pre-installed and running on port `27017`.

```bash
git clone https://github.com/garystafford/fav-color-service.git
cd fav-color-service
./gradlew clean cleanTest build && \
    java -jar build/libs/fav-color-0.1.0.jar
```

## Service Endpoints
Out of the box, the service runs on `localhost`, port `8091`. By default, the service looks for MongoDB on `localhost`, port `27017`.

- Purge and Add New Sample Data: <http://localhost:8091/seeder>
- List Color Choices: <http://localhost:8091/colors>
- Submit a Favorite Color: <http://localhost:8091/colors/color_choice>
- View Summary of Results: <http://localhost:8091/results>
- View Favorite Color: <http://localhost:8091/results/favorite>
- Service Health:<http://localhost:8091/health>
- Service Metrics:<http://localhost:8091/metrics>
- Other [Spring Actuator](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready) endpoints: `/mappings`, `/env`, `/configprops`, etc...
