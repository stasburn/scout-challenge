package com.autoscout24.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.autoscout24.Application;
import com.autoscout24.domain.Advert;
import com.autoscout24.domain.repositories.AdvertRepository;
import com.autoscout24.domain.Fuel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
//@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=key", "amazon.aws.secretkey=keySecret" })
public class AdvertRepositoryIntegrationTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private DynamoDBMapper mapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    AdvertRepository advertRepository;

    private static final int EXPECTED_PRICE = 20000;

    @Before
    public void setup() throws Exception {

        try {
            mapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest createTableRequest = mapper.generateCreateTableRequest(Advert.class);

            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(createTableRequest);
        } catch (ResourceInUseException e) {
            log.info(Advert.class.getSimpleName() + " table already exist, ignore exception");
        }

       mapper.batchDelete(advertRepository.findAll());
    }

    @Test
    public void saved_advert_with_expected_price_then_found() {
        Advert ad = new Advert("BMW i3", Fuel.GAS.type(), 20000,true,0, LocalDate.now());
        advertRepository.save(ad);

        List<Advert> result = (List<Advert>) advertRepository.findAll();
        assertTrue("Not empty", result.size() > 0);
        assertTrue("Contains item with expected cost", EXPECTED_PRICE == result.get(0).getPrice());
    }
}
