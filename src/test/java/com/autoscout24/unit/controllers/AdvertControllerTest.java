package com.autoscout24.unit.controllers;

import com.autoscout24.api.exceptions.AdNotFoundException;
import com.autoscout24.api.AdvertController;
import com.autoscout24.api.exceptions.IllegalAdvertStateException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.Fuel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class AdvertControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdvertController advertController;

    @Test
    public void test_ads_list() throws Exception {
        Advert ad = new Advert(1,"BMW i3", Fuel.GAS, 20000,true,0, LocalDate.now());
        List<Advert> allAds = singletonList(ad);
        given(advertController.list()).willReturn(allAds);

        mvc.perform(get("/adverts")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(ad.getTitle())));
    }

    @Test
    public void test_create_ad_newcar_with_mileage() throws Exception {
        Advert ad = new Advert(1,"BMW i3", Fuel.GAS, 20000,true,5000, LocalDate.now());
        given(advertController.create()).willThrow(new IllegalAdvertStateException());

        mvc.perform(post("/advert")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

    }



}
