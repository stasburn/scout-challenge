package com.autoscout24.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.autoscout24.api.exceptions.IllegalAdvertStateException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.repositories.AdvertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String template = "BMW i3";
    private final AtomicLong counter = new AtomicLong();

    final AdvertRepository advertRepository;

    @Autowired
    public AdvertController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @RequestMapping(value = "/adverts", method = RequestMethod.GET)
    public List<Advert> list() {
        return new ArrayList<Advert>();
    }

    @RequestMapping(value = "/advert", method = RequestMethod.GET)
    public Advert get() {
        return null;
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public Advert create(@RequestBody Advert ad) throws Exception {
        log.info("incoming post call " + ad);
        return ad;
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