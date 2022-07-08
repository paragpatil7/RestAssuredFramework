package basic.testcase;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapibase.BaseClass;

import io.restassured.response.Response;
import resourses.pojoclasses.CreateRepoPOJO;
import utility.Authn;
import utility.CommonUtilFunctions;
import utility.ExtentReporterNG;
import utility.RestFWLogger;
import utility.createURL;
import utility.payloadGenerator;

public class createRepoTest {
	
	Response response;
	ObjectMapper objectMapper; 
	String endPoint = createURL.getBaseURI("/user/repos");
	String bearer_Token = Authn.getBearerToken();
	
	
	@Test(priority=1)
	public void createRepositoryTestCase() throws IOException {
		RestFWLogger.initLogger();
		RestFWLogger.startTestCase("createRepositoryTestCase");
		
		RestFWLogger.info("Step 1 : Generating Payload String");	
		String req_Payload = payloadGenerator.generateStringPayload("createRepo.json");

		RestFWLogger.info("Step 2 : Executing Create repo API");	
		response = BaseClass.postRequest(endPoint, req_Payload, bearer_Token);
		String responseString = response.getBody().asString();
		
		RestFWLogger.info("Step 3 : Validating repository name");
		Assert.assertEquals(CommonUtilFunctions.getResponseKeyValue(req_Payload, "name"), CommonUtilFunctions.getResponseKeyValue(responseString, "name"));
	
		RestFWLogger.info("Step 4 : Validating repository Description");
		Assert.assertEquals(CommonUtilFunctions.getResponseKeyValue(req_Payload, "description"), CommonUtilFunctions.getResponseKeyValue(responseString, "description"));
		RestFWLogger.info("Assertion on Repo Description is Done....");
		
		RestFWLogger.endTestCase();
	}
	
	@DataProvider(name = "test1")
	   public static Object[][] primeNumbers() {
	      return new Object[][] {{"Api-testing-restcall-8"}, {"Api-testing-restcall-9"}};
	   }
	
	@Test(dataProvider = "test1",priority=2)
	public void GetRepo(String RepoName) throws IOException {
		PropertyConfigurator.configure("log4j.properties");
		RestFWLogger.startTestCase("GetRepo");
		RestFWLogger.info("Starting Get Repo Test for Repo "+RepoName);
		String GetRepoEndPoint = createURL.getBaseURI("/repos/paragpatil7/") + RepoName;
		RestFWLogger.info(GetRepoEndPoint);
		RestFWLogger.info("Executing Get repo API");
		response = BaseClass.getRequest(GetRepoEndPoint, bearer_Token);
		Assert.assertEquals(CommonUtilFunctions.getStatusCode(response), 200);
		RestFWLogger.info(CommonUtilFunctions.getStatusMessage(response));
		RestFWLogger.info("Assertion on Get Repo Done....");
		RestFWLogger.endTestCase();
	}
	
	
	

	
	
}
