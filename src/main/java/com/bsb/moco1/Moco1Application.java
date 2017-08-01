package com.bsb.moco1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;





@RestController
@SpringBootApplication
public class Moco1Application {

	
    @RequestMapping(value = {"/hello",""})
    public String helloboot(){

        return "hello boot !!" ;
    }
	
    
    @RequestMapping(value = "/testpost1", method = RequestMethod.POST)  
    public String testpost1() {  
        System.out.println("hello  test post");  
        return "hello test post";  
    } 
    
    
    @RequestMapping(value = "/testpost2", method = RequestMethod.POST)
    @ResponseBody
    public Object testpost2(@RequestBody Object data){

        //System.out.println("data:"+data);
       // Object json = JSONObject.toJSON(data);
        String str = JSON.toJSONString(data);
        System.out.println("str:"+str);
        
        return str;
    }
    
        
	public static void main(String[] args) {
		SpringApplication.run(Moco1Application.class, args);
	}
}
