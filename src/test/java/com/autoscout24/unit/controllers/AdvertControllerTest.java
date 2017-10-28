package com.autoscout24.unit.controllers;

import com.autoscout24.api.AdvertController;
import com.autoscout24.api.exceptions.IllegalAdvertStateException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.Fuel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdvertController.class)
//@ComponentScan(basePackages = "com.autoscout24")
public class AdvertControllerTest {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AdvertController advertController;

    @Test
    public void get_empty_list() throws Exception {
        List<Advert> allAds = Lists.emptyList();
        given(advertController.list()).willReturn(allAds);

        mvc.perform(get("/adverts")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void get_all_ads() throws Exception {
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
    public void create_ad_new_car_with_mileage_and_firstRegistration() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : true, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(2)));
    }

    @Test
    public void create_ad_new_car_with_just_mileage() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : true, \"mileage\": 1000}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        final ResultActions resultActions = mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(1)));
    }

    @Test
    public void create_ad_new_car_with_just_firstRegistration() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : true, \"mileage\": 1000}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        final ResultActions resultActions = mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(1)));
    }

    //TODO: add test for just mileage and test for just firstRegistration

    @Test
    public void create_ad() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : false, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        final ResultActions perform = mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON));
        log.info(perform.andReturn().getResponse().getContentAsString());
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(ad.getTitle())));
    }

    @Test
    public void title_is_mandatory() throws Exception {
        String missingTitleRequest = "{\"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : false, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";
        Advert ad = mapper.readValue(missingTitleRequest, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        mvc.perform(post("/advert").content(missingTitleRequest)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors.[0].field", is("title")));
    }

    //TODO: check all mandatory fields
    //test for all empty fields for new car
    //test for used car without mileage and firstRegistration and without mandatory fields

    @Test
    public void create_ad_unsupported_fuel() throws Exception {
        String body = "{\"title\": \"BMW i3\", \"fuel\": \"Samogonka\", \"price\": 10000, \"new\" : true}";
        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.create(ad)).willReturn(ad);

        mvc.perform(post("/advert").content(body)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors", hasSize(1)));
    }

    @Test
    public void delete_ad() throws Exception {

        final String idToDelete = "39c55672-c683-4823-a28c-07c229adae2b";
        String body = "{\"id\":\"39c55672-c683-4823-a28c-07c229adae2b\", \"title\": \"BMW i3\", \"fuel\": \"Gasoline\", \"price\": 10000, \"new\" : false, \"mileage\": 1000, \"firstRegistration\": \"2017-01-01\"}";

        Advert ad = mapper.readValue(body, Advert.class);
        given(advertController.delete(idToDelete)).willReturn(ad);

        mvc.perform(delete("/advert/"+ idToDelete)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(ad.getTitle())));
    }

}
