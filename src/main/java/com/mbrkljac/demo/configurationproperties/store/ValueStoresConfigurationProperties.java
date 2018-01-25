package com.mbrkljac.demo.configurationproperties.store;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "store")
public class ValueStoresConfigurationProperties {

	List<String> stringList;
	List<String> commaSeparatedList;
	List<DescriptionObject> descriptionObjects;

	@ToString
	@Getter
	@Setter
	public static class DescriptionObject {
		private String name;
		private String description;
		//Getters and setters

	}

	private Map<String, String> stringStringMap;

	private List<Map<String, String>> mapList;
	private Map<String, List<String>> stringListMap;
	private Map<String, DescriptionObject> stringDescriptionObjectMap;
	private Map<String, List<DescriptionObject>> stringDescriptionObjectsMap;

	//Getters and setters
	//Setters are not needed in case collection is initialized
}
