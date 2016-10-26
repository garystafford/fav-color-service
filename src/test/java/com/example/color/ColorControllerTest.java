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
public class ColorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        this.restTemplate.getForEntity("/seeder", String.class);
    }

    @Test
    public void getColorsReturnsListOfColorChoices() throws Exception {
        String expectedColorList = "[\"Green\",\"Red\",\"Yellow\",\"Blue\",\"Orange\",\"Purple\",\"Gray\",\"White\",\"Black\"]";
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/choices", String.class);
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(responseEntity.getBody()).isEqualTo(expectedColorList);

    }

    @Test
    public void setColorReturnsNewColor() throws Exception {
        String expectedColor = "Blue";
        Color color = new Color(expectedColor);
        ResponseEntity<Color> responseEntity = this.restTemplate.postForEntity("/colors", color, Color.class);
        assertThat(responseEntity.getStatusCode().value() == 201);
        assertThat(responseEntity.getBody().getColor()).isEqualTo(expectedColor);
    }

    @Test
    public void getCountsReturnsColorCounts() throws Exception {
        String expectedColor = "Black";
        int expectedCount = 5;
        ParameterizedTypeReference<List<ColorCount>> typeRef = new ParameterizedTypeReference<List<ColorCount>>() {
        };
        ResponseEntity<List<ColorCount>> responseEntity = this.restTemplate.exchange("/results", HttpMethod.GET, null, typeRef);
        ColorCount colorCount = responseEntity.getBody().get(0);
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(colorCount.getColor()).isEqualTo(expectedColor);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

    @Test
    public void getFavoriteReturnsMaxCountColor() throws Exception {
        String expectedColor = "Purple";
        int expectedCount = 12;
        ResponseEntity<ColorCount> responseEntity = this.restTemplate.getForEntity("/favorite", ColorCount.class);
        ColorCount colorCount = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode().value() == 200);
        assertThat(colorCount.getColor()).isEqualTo(expectedColor);
        assertThat(colorCount.getCount()).isEqualTo(expectedCount);
    }

}