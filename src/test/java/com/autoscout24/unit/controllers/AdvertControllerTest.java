package com.autoscout24.unit.controllers;

import com.autoscout24.api.AdvertController;
import com.autoscout24.api.exceptions.IllegalAdvertStateException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.Fuel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdvertController.class)
//@ComponentScan(basePackages = "com.autoscout24")
public class AdvertControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AdvertController advertController;

    @Test
    public void test_ads_list() throws Exception {
        Advert ad = new Advert("BMW i3", Fuel.GAS.type(), 20000,true,0, LocalDate.now());
        List<Advert> allAds = singletonList(ad);
        given(advertController.list()).willReturn(allAds);

        mvc.perform(get("/adverts")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(ad.getTitle())));
    }

    @Test
    public void test_create_ad_new_car_with_mileage_and_firstRegistration() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : true, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(2)));;
    }

    @Test
    public void test_create_ad() throws Exception {
        Advert ad = new Advert("BMW i3", Fuel.ELECTRIC.type(), 25000,true,0, LocalDate.now());
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : false, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";
        given(advertController.create(ad)).willReturn(ad);

        final String content = mapper.writeValueAsString(ad);
        mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(ad.getTitle())));
    }

    @Test
    public void test_title_is_mandatory() throws Exception {
        String missingTitleRequest = "{\"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : false, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";
        Advert ad = mapper.readValue(missingTitleRequest, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        mvc.perform(post("/advert").content(missingTitleRequest)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors.[0].field", is("title")));
    }

    @Test
    public void test_create_ad_unsupported_fuel() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Samogonka\", \"price\": 10000, \"new\" : true}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(1)));;
    }

}
