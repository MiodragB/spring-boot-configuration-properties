package com.mbrkljac.demo.configurationproperties.hierarchical;

import java.util.Collections;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Miodrag Brkljac
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "hierarchical")
public class HierarchicalConfigurationProperties {

	private String description;
	private Level level;

	@Getter
	@Setter
	@ToString
	public static class Level {
		private String levelName;
		private String description;
		private NestedLevel nestedLevel;
	}

	@Getter
	@Setter
	@ToString
	public static class NestedLevel {
		private String levelName;
		private String description;
	}

	@NestedConfigurationProperty
	private ExternalNestedProperties externalNestedProperties;

	//Getters and Setters

}
