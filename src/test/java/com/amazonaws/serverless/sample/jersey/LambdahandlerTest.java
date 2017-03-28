package com.amazonaws.serverless.sample.jersey;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.sample.jersey.LambdaHandler;
import com.amazonaws.serverless.sample.jersey.model.JsonHelper;
import com.amazonaws.services.lambda.runtime.Context;



/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdahandlerTest {


    private static Context createContext() {
	TestContext ctx = new TestContext();
	//	ctx.setFunctionName("UpdateTv");

	return ctx;
    }

    @BeforeClass
    public static void createInput() throws IOException {
    }


    @Test
    public void oneChannel() {
    LambdaHandler handler = new LambdaHandler();
	AwsProxyRequest req = new AwsProxyRequest();
	req.setPath("pets");
	req.setHttpMethod("GET");
	
	Context ctx = createContext();

	AwsProxyResponse rep = handler.handleRequest(req, ctx);
	Assert.assertNotNull("reply expected ", rep);
	System.out.println(JsonHelper.logJson(rep));
    }

  
}
