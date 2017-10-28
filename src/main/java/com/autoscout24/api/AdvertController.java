package com.autoscout24.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.autoscout24.api.exceptions.IllegalAdvertStateException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.repositories.AdvertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Advert get(@PathVariable String id) {
        return advertRepository.findOne(id);
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public Advert create(@Validated @RequestBody Advert ad) throws Exception {
        ad.setId(null); //id field is ignored during create operation.
        log.info("incoming post call " + ad);
        final Advert saved = advertRepository.save(ad);
        return saved;
    }

    @RequestMapping(value = "/advert", method = RequestMethod.DELETE)
    public Advert delete(@PathVariable String id) throws Exception {
        final Advert adToDelete = advertRepository.findOne(id);
        advertRepository.delete(adToDelete);//throw new AdNotFoundException();
        return adToDelete;
    }

    @RequestMapping(value = "/advert", method = RequestMethod.PUT)
    public Advert update(@RequestBody Advert ad) throws Exception {
        advertRepository.save(ad);
        return ad;
    }
}