package com.zpg.trumptweet.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zpg.trumptweet.config.ApplicationProperties;
import com.zpg.trumptweet.domain.User_payment;
import com.zpg.trumptweet.repository.User_paymentRepository;
import com.zpg.trumptweet.service.User_paymentService;
import com.zpg.trumptweet.web.rest.vm.ManagedUserVM;

import io.github.jhipster.config.JHipsterProperties;

/**
 * Service Implementation for managing User_payment.
 */
@Service
@Transactional
public class User_paymentServiceImpl implements User_paymentService{

    private final Logger log = LoggerFactory.getLogger(User_paymentServiceImpl.class);
    
    private final User_paymentRepository user_paymentRepository;
    
    @Autowired
    private Environment env;


    public User_paymentServiceImpl(User_paymentRepository user_paymentRepository) {
        this.user_paymentRepository = user_paymentRepository;
    }

    /**
     * Save a user_payment.
     *
     * @param user_payment the entity to save
     * @return the persisted entity
     */
    @Override
    public User_payment save(User_payment user_payment) {
        log.debug("Request to save User_payment : {}", user_payment);
        User_payment result = user_paymentRepository.save(user_payment);
        return result;
    }

    /**
     *  Get all the user_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User_payment> findAll(Pageable pageable) {
        log.debug("Request to get all User_payments");
        Page<User_payment> result = user_paymentRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one user_payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public User_payment findOne(Long id) {
        log.debug("Request to get User_payment : {}", id);
        User_payment user_payment = user_paymentRepository.findOne(id);
        return user_payment;
    }

    /**
     *  Delete the  user_payment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete User_payment : {}", id);
        user_paymentRepository.delete(id);
    }

	@Override
	public String createCustomerToken(ManagedUserVM managedUserVM) {
		String privateKey=env.getProperty("application.pandapay-sk");
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(privateKey, "");
		provider.setCredentials(AuthScope.ANY, credentials);
		  
		HttpClient client = HttpClientBuilder.create()
		  .setDefaultCredentialsProvider(provider)
		  .build();
		String url = "https://api.pandapay.io/v1/customers";
		HttpPost post = new HttpPost(url);
		
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("email", managedUserVM.getEmail()));
		urlParameters.add(new BasicNameValuePair("source", managedUserVM.getToken()));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());

		BufferedReader rd = null;
		try {
			rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String data = result.toString();
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;

			try {
				jsonObject = (JSONObject) parser.parse(data);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      
		String customerID = "";
		customerID = (String) jsonObject.get("id");

		return customerID;
	}
}
