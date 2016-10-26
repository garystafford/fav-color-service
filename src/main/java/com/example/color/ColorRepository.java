package com.example.color;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


//@RepositoryRestResource(collectionResourceRel = "colors", path = "colors")
public interface ColorRepository extends MongoRepository<Color, String> {

    Color findByColor(@Param("color") String color);
}
