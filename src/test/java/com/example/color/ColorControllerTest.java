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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RestClientTest(ColorController.class)
public class ColorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        this.restTemplate.getForEntity("/seeder", String.class);
    }

    @Test
    public void getColorsReturnsColorList() throws Exception {
        String expectedColorList = "[\"green\",\"red\",\"yellow\",\"blue\",\"orange\",\"purple\",\"gray\",\"white\",\"black\"]";
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/colors", String.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody()).isEqualTo(expectedColorList);

    }

    @Test
    public void setColorValidChoiceReturnsColor() throws Exception {
        String expectedColor = "blue";
        ResponseEntity<Color> responseEntity = this.restTemplate.getForEntity("/colors/" + expectedColor, Color.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(responseEntity.getBody().getColor()).isEqualTo(expectedColor);
    }

    @Test
    public void setColorInvalidChoiceThrowsException() throws Exception {
        String badColorChoice = "foo";
        ResponseEntity<Color> responseEntity = this.restTemplate.getForEntity("/colors/" + badColorChoice, Color.class);
        assertThat(responseEntity.getStatusCode().is4xxClientError());
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    public void getCountsReturnsListOfColorCounts() throws Exception {
        String expectedColor = "black";
        int expectedCount = 5;
        ParameterizedTypeReference<List<ColorCount>> typeRef = new ParameterizedTypeReference<List<ColorCount>>() {
        };
        ResponseEntity<List<ColorCount>> responseEntity = this.restTemplate.exchange("/results", HttpMethod.GET, null, typeRef);
        ColorCount colorCount = responseEntity.getBody().get(0);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(colorCount.getColor()).isEqualTo(expectedColor);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

    @Test
    public void getFavoriteReturnsColorCountWithMaxCount() throws Exception {
        String expectedColor = "purple";
        int expectedCount = 12;
        ResponseEntity<ColorCount> responseEntity = this.restTemplate.getForEntity("/results/favorite", ColorCount.class);
        ColorCount colorCount = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
        assertThat(colorCount.getColor()).isEqualTo(expectedColor);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

}