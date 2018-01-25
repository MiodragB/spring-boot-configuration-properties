package com.mbrkljac.demo.configurationproperties.initialsetup;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@ConfigurationProperties(prefix = "catalog")
@ToString
@Getter
public class InitialSetupConfigurationProperties {

	private String name;

	public void setName(final String name) {
		this.name = name + "SomethingSomething";
	}

	//Getters and Setters

}
