package com.example.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
public class ColorController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ColorRepository colorRepository;

    @RequestMapping(value = "/choices", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<String>>> getChoices() {
        List<String> results = ColorList.getColors();
        return new ResponseEntity<>(Collections.singletonMap("choices", results), HttpStatus.OK);
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<ColorCount>>> getResults() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("color").count().as("count"),
                project("count").and("color").previousOperation(),
                sort(Sort.Direction.ASC, "color")
        );

        AggregationResults<ColorCount> groupResults
                = mongoTemplate.aggregate(aggregation, Color.class, ColorCount.class);
        List<ColorCount> results = groupResults.getMappedResults();
        return new ResponseEntity<>(Collections.singletonMap("results", results), HttpStatus.OK);
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<ColorCount>>> getFavorite() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("color").count().as("count"),
                match(Criteria.where("count").is(getFavoriteCountInt())),
                project("count").and("color").previousOperation(),
                sort(Sort.Direction.DESC, "color")
        );

        AggregationResults<ColorCount> groupResults
                = mongoTemplate.aggregate(aggregation, Color.class, ColorCount.class);
        List<ColorCount> results = groupResults.getMappedResults();
        return new ResponseEntity<>(Collections.singletonMap("results", results), HttpStatus.OK);
    }

    @RequestMapping(value = "/favorite/count", method = RequestMethod.GET)
    public ResponseEntity<ColorCountFavorite> getFavoriteCount() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("color").count().as("count"),
                project("count"),
                sort(Sort.Direction.DESC, "count"),
                limit(1)
        );

        AggregationResults<ColorCountFavorite> groupResults =
                mongoTemplate.aggregate(aggregation, Color.class, ColorCountFavorite.class);
        ColorCountFavorite result = groupResults.getMappedResults().get(0);

        return ResponseEntity.status(HttpStatus.OK).body(result); // return 200 with payload
    }

    private int getFavoriteCountInt() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("color").count().as("count"),
                project("count"),
                sort(Sort.Direction.DESC, "count"),
                limit(1)
        );

        AggregationResults<ColorCountFavorite> groupResults =
                mongoTemplate.aggregate(aggregation, Color.class, ColorCountFavorite.class);
        int result = groupResults.getMappedResults().get(0).getCount();

        return result;
    }

    @RequestMapping(value = "/simulation", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> seedData() {

        colorRepository.deleteAll();
        ColorSeedData colorSeedData = new ColorSeedData();
        colorSeedData.setRandomColors();
        colorRepository.save(colorSeedData.getColors());
        Map<String, String> result = new HashMap<>();
        result.put("message", "simulation data created");
        return ResponseEntity.status(HttpStatus.OK).body(result); // return 200 with payload
    }

    // used by unit tests to create a known data set
    public void seedData(Map colorList) {

        colorRepository.deleteAll();
        ColorSeedData colorSeedData = new ColorSeedData();
        colorSeedData.colorsFromMap(colorList);
        colorRepository.save(colorSeedData.getColors());
    }
}
