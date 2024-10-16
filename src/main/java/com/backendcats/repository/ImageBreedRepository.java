package com.backendcats.repository;

import com.backendcats.dto.BreedDto;
import com.backendcats.dto.ImageBreedDto;
import com.backendcats.util.RestTemplateService;
import com.backendcats.util.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ImageBreedRepository {


    @Autowired
    private Environment env;

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    RestTemplate restTemplate;

    public ArrayList<ImageBreedDto> GetImagesBreedsById(String imagesbybreedid) {


        HttpEntity<String> headerList = restTemplateService.CreateHeaderList(env.getProperty("application.api-cat.token"),"");

        String uri = env.getProperty("application.api-cat.url") + "/images/search?limit=100&breed_id="+imagesbybreedid;


        ResponseEntity<ArrayList<ImageBreedDto>> requestRestTemplate =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        headerList,
                        new ParameterizedTypeReference<ArrayList<ImageBreedDto>>() {}
                );

        if(requestRestTemplate.getStatusCodeValue() == 200) {
            return requestRestTemplate.getBody();
        }
        else {
            throw new ErrorException(env.getProperty("constans.request-error-api-cat"));
        }
    }

}
