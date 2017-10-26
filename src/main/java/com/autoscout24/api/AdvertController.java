package com.autoscout24.api;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import com.autoscout24.domain.Advert;
import com.autoscout24.domain.Fuel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertController {

    private static final String template = "BMW i3";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/test")
    public Advert greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Advert(counter.incrementAndGet(),
                String.format(template, name), Fuel.DIESEL, 18000, false, 5000, LocalDate.now());
    }
}