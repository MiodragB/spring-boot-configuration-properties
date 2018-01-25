package com.mbrkljac.demo.configurationproperties.custommapping;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomMappingsConfigurationProperties {

	private LocalDate localDate;

}
