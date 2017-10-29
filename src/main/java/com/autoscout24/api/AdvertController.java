package com.autoscout24.api;

import com.autoscout24.api.exceptions.AdNotFoundException;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.repositories.AdvertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class AdvertController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @RequestMapping(value = "/adverts", method = RequestMethod.GET)
    public List<Advert> list() {
        List<Advert> res = new ArrayList<>();
        advertRepository.findAll().forEach(res::add);

        res.sort(Comparator.comparing(Advert::getId));
        return res;
    }

    @RequestMapping(value = "/advert/{id}", method = RequestMethod.GET)
    public Advert get(@PathVariable("id") String id) {
        return advertRepository.findOne(id);
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public Advert create(@Validated @RequestBody Advert ad) throws Exception {
        ad.setId(null); //id field is ignored during create operation.
        log.info("incoming post request " + ad);
        final Advert saved = advertRepository.save(ad);
        return saved;
    }

    @RequestMapping(value = "/advert/{id}", method = RequestMethod.DELETE)
    public Advert delete(@PathVariable("id") String id) throws Exception {
        final Advert adToDelete = advertRepository.findOne(id);
        if(adToDelete == null){
            throw new AdNotFoundException();
        }
        advertRepository.delete(adToDelete);
        return adToDelete;
    }

    @PutMapping("/advert/{id}")
    public Advert update(@Validated @RequestBody Advert ad, @PathVariable("id") String id) throws Exception {

        final Advert storedAd = advertRepository.findOne(id);
        if(storedAd == null){
            throw new AdNotFoundException();
        }
        advertRepository.save(ad);
        return ad;
    }
}