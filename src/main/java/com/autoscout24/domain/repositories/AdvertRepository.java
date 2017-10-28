package com.autoscout24.domain.repositories;

import com.autoscout24.domain.Advert;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
public interface AdvertRepository extends CrudRepository<Advert, String> {


}
