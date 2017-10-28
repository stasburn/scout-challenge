package com.autoscout24.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.autoscout24.api.exceptions.IllegalAdvertStateException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.repositories.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertController {

    private static final String template = "BMW i3";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    AdvertRepository advertRepository;

    @RequestMapping(value = "/adverts", method = RequestMethod.GET)
    public List<Advert> list() {
        return new ArrayList<Advert>();
    }

    @RequestMapping(value = "/advert", method = RequestMethod.GET)
    public Advert get() {
        return null;
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public Advert create() throws Exception {
        throw new IllegalAdvertStateException();
//        return null;
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