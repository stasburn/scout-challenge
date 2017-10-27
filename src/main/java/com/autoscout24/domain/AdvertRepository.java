package com.autoscout24.domain;

import java.util.List;

public interface AdvertRepository {

    List<Advert> getAllAdverts();
    Advert getAdvert(int id);

}
