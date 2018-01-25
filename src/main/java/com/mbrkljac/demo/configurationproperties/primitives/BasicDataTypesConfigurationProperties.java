package com.mbrkljac.demo.configurationproperties.primitives;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple property binding
 */
@ToString
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "basic")
public class BasicDataTypesConfigurationProperties {

	private int anInt;
	private long aLong;
	private float aFloat;
	private double aDouble;
	//private boolean aBoolean;
	private char aChar;

	private Integer integerWrapper;
	private Double doubleWrapper;
	private Character characterWrapper;
	private Boolean booleanWrapper;

	private String string;

	private BigInteger bigInteger;
	private BigDecimal bigDecimal;

	//Getters and Setters
}
