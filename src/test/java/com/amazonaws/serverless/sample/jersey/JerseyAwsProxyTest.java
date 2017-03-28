package com.amazonaws.serverless.sample.jersey;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyAwsProxyServletRequestFactory;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.serverless.sample.jersey.LambdaHandler;
import com.amazonaws.serverless.sample.jersey.model.JsonHelper;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.services.lambda.runtime.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Unit test class for the Jersey AWS_PROXY default implementation
 */
public class JerseyAwsProxyTest {
    private static final String CUSTOM_HEADER_KEY = "x-custom-header";
    private static final String CUSTOM_HEADER_VALUE = "my-custom-value";
    private static final String AUTHORIZER_PRINCIPAL_ID = "test-principal-" + UUID.randomUUID().toString();


    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ResourceConfig app = new ResourceConfig().packages("com.amazonaws.serverless.proxy.test.jersey")
                                                            .register(new AbstractBinder() {
                                                                @Override
                                                                protected void configure() {
                                                                    bindFactory(JerseyAwsProxyServletRequestFactory.class)
                                                                            .to(HttpServletRequest.class)
                                                                            .in(RequestScoped.class);
                                                                }
                                                            });
    private static JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler = JerseyLambdaContainerHandler.getAwsProxyHandler(app);

    private static Context lambdaContext = new MockLambdaContext();

    @Test
    public void headers_getHeaders_echo() {
        LambdaHandler handler = new LambdaHandler();

        AwsProxyRequest request = new AwsProxyRequestBuilder("/pets", "GET")
                .json()
                .header(CUSTOM_HEADER_KEY, CUSTOM_HEADER_VALUE)
                .build();

        AwsProxyResponse output = handler.handleRequest(request, lambdaContext);
        assertEquals(200, output.getStatusCode());
        assertEquals("application/json", output.getHeaders().get("Content-Type"));

        System.out.println("xxx" + JsonHelper.logJson(output));;
    }

 
}