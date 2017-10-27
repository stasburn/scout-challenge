package com.autoscout24.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.autoscout24.domain.Advert;
import com.autoscout24.domain.AdvertRepository;
import com.autoscout24.domain.Fuel;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertController {

    private static final String template = "BMW i3";
    private final AtomicLong counter = new AtomicLong();


    AdvertRepository advertRepository;

    @RequestMapping(value = "/adverts", method = RequestMethod.GET)
    public List<Advert> list() {
        return new ArrayList<Advert>();
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public Advert create() throws Exception {

        return null;
    }

    @RequestMapping(value = "/advert", method = RequestMethod.DELETE)
    public Advert delete() throws Exception {
        return null;//throw new AdNotFoundException();
    }

    @RequestMapping(value = "/advert", method = RequestMethod.PUT)
    public Advert update() throws Exception {
        return null;//throw new AdNotFoundException();
    }
}