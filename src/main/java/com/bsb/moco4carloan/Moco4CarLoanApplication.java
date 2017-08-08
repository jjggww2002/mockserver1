package com.bsb.moco4carloan;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;




@EnableAsync
@RestController
@SpringBootApplication
public class Moco4CarLoanApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Value("${blacklist.sleep}")
	private long blackListSleepTime;
	
	@Value("${dataplatform.sleep}")
	private long dataPlatformSleepTime;
	
	@Value("${creditdecision.sleep}")
	private long creditDecisionSleepTime;
	
	@Value("${callbackcreditdecision.sleep}")
	private long callbackCreditDecisionSleepTime;
	
	@Value("${callback.creditdecision.url}")
	private String callbackCreditDecisionURL;
	
    @RequestMapping(value = {"/hello",""})
    public String helloboot(){

        return "hello boot !!" ;
    }
	
    
    @RequestMapping(value = "/testpost1", method = RequestMethod.POST)  
    public String testpost1() {  
        System.out.println("hello  test post");  
        Object obj = callbackCreditDecision1();
        System.out.println("obj:" + obj); 
        return "hello test post";  
    } 
    
    
    @RequestMapping(value = "/blacklist", method = RequestMethod.POST)
    @ResponseBody
    public BlackListResponse checkBlackList(@RequestBody Object data) throws Exception{

    	logger.info("checkBlackList() is called.");  
    	
/*
        String input = JSON.toJSONString(data); 
        logger.debug("input:" + input);
        
        JSONObject jsonObject = JSON.parseObject(input);
        logger.debug("mobile:" + jsonObject.get("mobile") );

        
        JSONObject jsonObject2 = (JSONObject) JSON.toJSON(data);
        logger.debug("mobile:" + jsonObject2.get("mobile") );

*/        
        
        Thread.sleep(blackListSleepTime);
        BlackListResponse response = new BlackListResponse();
        response.setRespcd("0000");
        response.setResptx("验证通过");
        return response;
    }
    
    @RequestMapping(value = "/dataplatform", method = RequestMethod.POST)
    @ResponseBody
    public String checkDataPlatform(@RequestBody Object data) throws Exception{

    	logger.info("checkDataPlatform() is called.");  

        Thread.sleep(dataPlatformSleepTime);

        return "000";
    }    
    
    
    @RequestMapping(value = "/creditdecision", method = RequestMethod.POST)
    @ResponseBody
    public CreditDecisionResponse checkCreditDecision(@RequestBody Object data) throws Exception{

    	logger.info("checkCreditDecision() is called.");  

    	JSONObject jsonObject = (JSONObject) JSON.toJSON(data);
    	String applyNo = (String) jsonObject.get("applyNo");
    	String batchNo = (String) jsonObject.get("batchNo");
    	String certifiId = (String) jsonObject.get("certifiId");
    	String certifiType = (String) jsonObject.get("certifiType");
    	String mobile = (String) jsonObject.get("mobile");
    	String name = (String) jsonObject.get("name");
    	
        logger.debug("applyNo:" + applyNo );
        logger.debug("batchNo:" + batchNo );
        logger.debug("certifiId:" + certifiId );
        logger.debug("certifiType:" + certifiType );
        logger.debug("mobile:" + mobile );
        logger.debug("name:" + name );
    	
        
        //callbackCreditDecision1();
        callbackCreditDecision(applyNo,batchNo,certifiId,certifiType,mobile,name);
        
        Thread.sleep(creditDecisionSleepTime);

        CreditDecisionResponse response = new CreditDecisionResponse();
        response.setErorcd("0000");
        response.setErortx("success");
        return response;
    }  
    
    
    private Object callbackCreditDecision(String applyNo, String batchNo, String certifiId, String certifiType, String mobile, String name) throws Exception{
    	logger.info("callbackCreditDecision() is called.");
    	
    	Thread.sleep(callbackCreditDecisionSleepTime);
    	
    	String url = this.callbackCreditDecisionURL;  
        JSONObject postData = new JSONObject();  
        postData.put("appNo", applyNo); 
        postData.put("batchNo", batchNo); 
        postData.put("certifiId", certifiId); 
        postData.put("certifiType", certifiType); 
        postData.put("mobile", mobile); 
        postData.put("name", name); 
        postData.put("approve_pro", 0); 
        postData.put("creditStcd", "S101003,S101008"); 
        postData.put("dayRate", 0.0005); 
        postData.put("debtStcd", "F101003,F101006"); 
        postData.put("decisionAmt", 50000); 
        postData.put("decisionResult", "2"); 
        postData.put("decisionTime","20170804 15:39:13"); 
        postData.put("financPlanAmt", 0); 
        postData.put("fixedDeposiAmt", 0); 
        postData.put("incomeStcd", "C101001,C101004"); 
        postData.put("manual_pro", 0); 
        postData.put("oxyAcctBalance", 0); 
        postData.put("reject_pro", 0); 
        postData.put("residentStcd", "L101001,L101003"); 
        postData.put("totalAssetsAmt", 0);
        postData.put("totalDebetAmt", 0);
        
    	RestTemplate restTemplate = new RestTemplate();
    	JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody(); 
        return json.toJSONString();
        
 
        
       
    }
    
    @Async
    private Object callbackCreditDecision1(){
    	logger.info("callbackCreditDecision1() is called.");
    	String url = "http://localhost:8080/blacklist";  
        JSONObject postData = new JSONObject();  
        postData.put("prcscd", "ckblak"); 
        postData.put("trantp", "4"); 
        postData.put("reqflg", "app"); 
        postData.put("custna", "request for post"); 
        postData.put("idtftp", "request for post"); 
        postData.put("idtfno", "request for post"); 
        postData.put("cardno", "request for post"); 
        postData.put("mobile", "request for post"); 
        postData.put("ipadress", "request for post"); 
        
    	RestTemplate restTemplate = new RestTemplate();
    	JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody(); 
        return json.toJSONString();
        
 
        
       
    }
    
	public static void main(String[] args) {
		SpringApplication.run(Moco4CarLoanApplication.class, args);
	}
}
