package com.reeflog.reeflogapi;

import com.reeflog.reeflogapi.utils.BuildInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReefLogApiApplicationTests {

	@Autowired
	BuildInfoService buildInfoService;


	@Test
	public void NoTest(){

		System.out.println("Hello World, there is no tests here !");
		System.out.println("Application name : " + buildInfoService.getApplicationName() + ", version = " + buildInfoService.getBuildVersion());

	}


}
