# Favorite Color Service

_Work in Progress_

## Introduction

Simple Spring Boot RESTful microservice, backed by MongoDB, part of an upcoming article on Spring Boot, HashiCorp product-line, and AWS.

## Quick Start

To clone and run the Favorite Color service locally. Requires MongoDB is pre-installed and running on `27017`.

```bash
git clone https://github.com/garystafford/fav-color-service.git
cd fav-color-service
./gradlew clean build && \
    java -jar build/libs/fav-color-0.1.0.jar
```

## Service Endpoints
Out of the box, the service runs on `localhost`, port `8091`. By default, the service looks for MongoDB on `localhost`, port `27017`.

- Purge Data and Add Sample Data: <http://localhost:8091/seeder>
- List Color Choices: <http://localhost:8091/colors>
- Add Your Favorite Color Choice: <http://localhost:8091/colors/color_choice>
- View All Results: <http://localhost:8091/results>
- View Favorite Color: <http://localhost:8091/results/favorite>
- Service Health:<http://localhost:8091/health>
- Service Metrics:<http://localhost:8091/metrics>
- Other endpoints: `/mappings`, `/env`, `/configprops`, etc...
