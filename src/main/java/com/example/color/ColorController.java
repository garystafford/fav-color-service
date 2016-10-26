package com.example.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
public class ColorController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ColorRepository colorRepository;

//    @RequestMapping(value = "/colors/{color}", method = RequestMethod.POST)
//    public ResponseEntity<Color> setColor(@PathVariable(value = "color") String color) {
//        List<String> colorList = ColorList.getColors();
//        color = color.toLowerCase().trim();
//        if (!colorList.contains(color)) { // return 400 wrong choice
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        Color newColor = new Color(color);
//        colorRepository.save(newColor);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newColor); // return 201 without payload
//    }

    @RequestMapping(value = "/choices", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getColors() {
        List<String> results = ColorList.getColors();
        return ResponseEntity.status(HttpStatus.OK).body(results); // return 200 with payload
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ResponseEntity<List<ColorCount>> getCounts() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("color").count().as("count"),
                project("count").and("color").previousOperation(),
                sort(Sort.Direction.ASC, "color")
        );

        // convert the aggregation result into a List
        AggregationResults<ColorCount> groupResults
                = mongoTemplate.aggregate(aggregation, Color.class, ColorCount.class);
        List<ColorCount> results = groupResults.getMappedResults();

        return ResponseEntity.status(HttpStatus.OK).body(results); // return 200 with payload

    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public ResponseEntity<ColorCount> getFavorite() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("color").count().as("count"),
                project("count").and("color").previousOperation(),
                sort(Sort.Direction.DESC, "count"),
                limit(1)
        );

        AggregationResults<ColorCount> groupResults =
                mongoTemplate.aggregate(aggregation, Color.class, ColorCount.class);
        ColorCount result = groupResults.getMappedResults().get(0);

        return ResponseEntity.status(HttpStatus.OK).body(result); // return 200 with payload
    }

    @RequestMapping(value = "/seeder", method = RequestMethod.GET)
    public ResponseEntity<List<Color>> seedSampleData() {

        colorRepository.deleteAll();
        List<Color> colorSeedData = ColorSeedData.getColors();
        colorRepository.save(colorSeedData);
        return ResponseEntity.status(HttpStatus.OK).body(null); // return 200 without payload
    }
}

