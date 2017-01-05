package com.example.color;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ColorController colorController;

    @Before
    public void setup() {
        // sample test data
        Map choices = new HashMap();
        choices.put("Red", "3");
        choices.put("Yellow", "2");
        choices.put("Blue", "14");
        choices.put("Green", "8");
        choices.put("Orange", "11");
        choices.put("Purple", "5");
        choices.put("Black", "5");
        choices.put("White", "1");
        choices.put("Gray", "4");
        colorController.seedData(choices);
    }

    @Test
    public void getColorsReturnsListOfAllColorChoices() throws Exception {
        String expectedColorList =
                "{\"choices\":[\"Black\",\"Blue\",\"Gray\",\"Green\",\"Orange\",\"Purple\",\"Red\",\"White\",\"Yellow\"]}";
        ResponseEntity<String> responseEntity =
                this.restTemplate.getForEntity("/choices", String.class);
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(responseEntity.getBody()).isEqualTo(expectedColorList);

    }

    @Test
    public void postColorReturnsNewColor() throws Exception {
        String expectedColor = "Test";
        Color color = new Color(expectedColor);
        ResponseEntity<Color> responseEntity =
                this.restTemplate.postForEntity("/colors", color, Color.class);
        assertThat(responseEntity.getStatusCode().value() == 201);
        assertThat(responseEntity.getBody().getColor()).isEqualTo(expectedColor);
    }

    @Test
    public void getResultsReturnsListOfExpectedColorCounts() throws Exception {
        String expectedColor = "Blue";
        int expectedCount = 14;
        ParameterizedTypeReference<Map<String, List<ColorCount>>> typeRef =
                new ParameterizedTypeReference<Map<String, List<ColorCount>>>() {
                };
        ResponseEntity<Map<String, List<ColorCount>>> responseEntity =
                this.restTemplate.exchange("/results", HttpMethod.GET, null, typeRef);
        LinkedHashMap body = ((LinkedHashMap) responseEntity.getBody());
        Collection colorCountCollection = body.values();
        ArrayList colorCountArray = (ArrayList) colorCountCollection.toArray()[0];
        ColorCount colorCount = (ColorCount) colorCountArray.get(0);
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(colorCount.getColor()).isEqualTo(expectedColor);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

    @Test
    public void getResultsCountReturnsSumOfCounts() throws Exception {
        int expectedCount = 53;
        ResponseEntity<ColorCountFavorite> responseEntity =
                this.restTemplate.getForEntity("/results/count", ColorCountFavorite.class);
        ColorCountFavorite colorCount = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

    @Test
    public void getFavoriteReturnsColorsWithHighestCount() throws Exception {
        String expectedColor = "Blue";
        int expectedCount = 14;
        ParameterizedTypeReference<Map<String, List<ColorCount>>> typeRef =
                new ParameterizedTypeReference<Map<String, List<ColorCount>>>() {
                };
        ResponseEntity<Map<String, List<ColorCount>>> responseEntity =
                this.restTemplate.exchange("/favorite", HttpMethod.GET, null, typeRef);
        LinkedHashMap body = ((LinkedHashMap) responseEntity.getBody());
        Collection colorCountCollection = body.values();
        ArrayList colorCountArray = (ArrayList) colorCountCollection.toArray()[0];
        ColorCount colorCount = (ColorCount) colorCountArray.get(0);
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(colorCount.getColor()).isEqualTo(expectedColor);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

    @Test
    public void getFavoriteCountReturnsHighestCount() throws Exception {
        int expectedCount = 14;
        ResponseEntity<ColorCountFavorite> responseEntity =
                this.restTemplate.getForEntity("/favorite/count", ColorCountFavorite.class);
        ColorCountFavorite colorCount = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

    @Test
    public void getSimulationReturnsExpectedMessage() throws Exception {
        String expectedResponse =
                "{\"message\":\"random simulation data created\"}";
        ResponseEntity<String> responseEntity =
                this.restTemplate.getForEntity("/simulation", String.class);
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
    }

}
