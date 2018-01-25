package com.mbrkljac.demo;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBasicDataTypesConfigurationPropertiesApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void printAllCustomConfigurationPropertiesBeansValues() {
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ConfigurationProperties.class);
		beans.entrySet().stream().filter(entry -> entry.getValue().getClass().getName().startsWith("com.mbrkljac"))
			.forEach(entry -> System.out.println(entry + System.lineSeparator()));
	}
}
