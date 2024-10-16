package com.backendcats.repository;

import com.backendcats.dto.BreedDto;
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
public class BreedRepository {


    @Autowired
    private Environment env;

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    RestTemplate restTemplate;


    public ArrayList<BreedDto> GetAllBreeds() {


        HttpEntity<String> headerList = restTemplateService.CreateHeaderList(env.getProperty("application.api-cat.token"),"");

        String uri = env.getProperty("application.api-cat.url") + "/breeds";


        ResponseEntity<ArrayList<BreedDto>> requestRestTemplate =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        headerList,
                        new ParameterizedTypeReference<ArrayList<BreedDto>>() {}
                );

        if(requestRestTemplate.getStatusCodeValue() == 200) {
            return requestRestTemplate.getBody();
        }
        else {
            throw new ErrorException(env.getProperty("constans.request-error-api-cat"));
        }
    }

    public BreedDto GetBreedById(String breedid) {


        HttpEntity<String> headerList = restTemplateService.CreateHeaderList(env.getProperty("application.api-cat.token"),"");

        String uri = env.getProperty("application.api-cat.url") + "/breeds"+breedid;


        ResponseEntity<BreedDto> requestRestTemplate =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        headerList,
                        BreedDto.class
                );

        if(requestRestTemplate.getStatusCodeValue() == 200) {
            return requestRestTemplate.getBody();
        }
        else {
            throw new ErrorException(env.getProperty("constans.request-error-api-cat"));
        }
    }

    public ArrayList<BreedDto> GetBreedByFilter(String filter) {


        HttpEntity<String> headerList = restTemplateService.CreateHeaderList(env.getProperty("application.api-cat.token"),"");

        String uri = env.getProperty("application.api-cat.url") + "/breeds/search?q="+filter;


        ResponseEntity<ArrayList<BreedDto>> requestRestTemplate =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        headerList,
                        new ParameterizedTypeReference<ArrayList<BreedDto>>() {}
                );

        if(requestRestTemplate.getStatusCodeValue() == 200) {
            return requestRestTemplate.getBody();
        }
        else {
            throw new ErrorException(env.getProperty("constans.request-error-api-cat"));
        }
    }

}
