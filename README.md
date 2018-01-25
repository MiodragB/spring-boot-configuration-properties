# Spring Boot @ConfigurationProperties


@ConfigurationProperties is a really handy Spring Boot feature that allows you to map group of properties from properties file (either .yml or .properties) to java object.
Internally, spring boot relies heavily on this feature, but it can also make your life a lot easier and code cleaner


## Initial setup
Initial configuration is really easy, we need properties file and a class on which we will map properties.

catalog.properties:
```properties
catalog.version= cars
```
catalog.yml:
```yaml
catalog:
  version: cars
```

Java class:
```java
@Component
@ConfigurationProperties(value = "demo.catalog", ignoreUnknownFields = false)
@PropertySource("classpath:initialsetup/catalog.properties")
public class DemoConfigurationProperties {

	private String version;

	//Getters and Setters
}
```
``@Componet`` is needed to register this class as a bean  
``@PropertySource`` points to location of properties file. This anotation is not needed in case you are reading properties from default spring boot property files (application.yml/application-profile.yml or equivalent application.properties/application-profile.properties versions)  
``@ConfigurationProperties``  

* ``value`` specifies prefix for properties, it can be omitted in case mapping is done from the root (prefix does not exist).
Besides ``value`` and its alias ``value`` there is couple of more params for customizing ``@ConfigurationProperties``:
* ``ignoreInvalidFields``. As per javadoc: Flag to indicate that when binding to this object invalid fields should be ignored. Invalid means invalid according to the binder that is used, and usually this means fields of the wrong type (or that cannot be coerced into the correct type). We will discuss binders later in this article. Default value for this property is false.
* ``ignoreNestedProperties`` is a flag to indicate that when binding to this object fields with periods in their names should be ignored. Default value is false. Section 3 [todo] will explain how to map nested properties if you don't want to ignore them.  
* ``ignoreUnknownFields`` is a flag to indicate that when binding to this object unknown fields should be ignored. Default value is true. Note: ignoreUnknownFields relates to fields that exist in file but there is no corresponding field in java class. In case field exist in java class and not in file, it will have it's default value.
## Mapping primitive values, Java wrapper types and Strings  
Mapping of primitives, wrapper types and strings is quite straightforward and easy. There is only couple of things to have in mind:

* Spring does not know how to map primitive boolean value. If you wan't boolean, you will have to use a wrapper class.
* char, Char, and String can but don't need to be enclosed with single/double quotations. `"aString"`,`'aString'`, and `string` will end up being mapped the same. If you wan't quotations explicitly you need to escape them
* java.math BigInteger and BigDecimal are also supported out of the box.
```java
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

	//Setters
	//Setters are needed for mapping nested properties. Spring does not need getters for mapping basic data types. Although in most cases you will need them

}
```
```yaml
basic:
  anInt: 123
  aLong: 123456789
  aFloat: 1000.22
  aDouble: 1000.22
  aBolean: true
  aChar: 'c'
  integerWrapper: 123
  doubleWrapper: 1000.22
  characterWrapper: 'C'
  booleanWrapper: true
  string: 'aString'
  bigInteger: 123000000000000000000000000000000
  bigDecimal: 123000000000000000000000000000000.99999999999999999999999999999999
```

## Mapping hierarchical group of properties
Hierarchical properties are quite common. They can be mapped using static nested classes, external classes, or they can be ignored.

Example:
```java
@Configuration
@ConfigurationProperties(prefix = "hierarchical")
public class HierarchicalConfigurationProperties {

	private String description;
	private Level level;

	public static class Level {
		private String levelName;
		private String description;
		private NestedLevel nestedLevel;
		//Getters and Setters
		//Both getters and setters are needed for mapping nested properties
	}

	public static class NestedLevel {
		private String levelName;
		private String description;
		//Getters and Setters
		//Both getters and setters are needed for mapping nested properties

	}

	@NestedConfigurationProperty
	public ExternalNestedProperties externalNestedProperties;
	//Getters and Setters
	//Both getters and setters are needed for mapping nested properties

}
```
```java
public class ExternalNestedProperties {

	private String description;
	//Getters and Setters
}
```

```yaml
hierarchical:
  description: illustration of hierarchical properties
  level:
    levelName: First level
    description: nested configuration properties can be represented by nested static class
    nestedLevel:
      levelName: Second level
      description: You can have as much nested levels as you like
  externalNestedProperties:
    description: To map nested properties you can use external class also. This can be quite convinient if you have some common set of properties used on multiple places
```

If you want to ignore nested properties just set ignoreNestedProperties to false as mentioned in introduction.

## Mapping Collections
```java
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

	//Getters and setters
	//Setters are not needed in case collection is initialized
}
```
```yaml
store:
  stringList:
  - value1
  - value2
  commaSeparatedList: value3, value4
  descriptionObjects:
  -
    name: List of nested objects
    description: You can also quite easily map list of objects
  -
    name: List of nested objects
    description: You can also quite easily map list of objects
```


## Mapping Maps
```java
	private Map<String, String> stringStringMap;
```

```yaml
  stringStringMap:
        key1: value1
        key2: value2
```

## Custom data types
In order to map a value to a custom data type you need to register a converter:
```java
@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if(source==null){
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }
}
```
```java
@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomMappingsConfigurationProperties {

	private LocalDate localDate;
	//Setter
}
```
```yaml
custom.localDate: 03-25-2016
```
## Combinations
You can make combinations in any way you like. For example:

```java
	private List<Map<String, String>> mapList;
	private Map<String, List<String>> stringListMap;
	private Map<String, DescriptionObject> stringDescriptionObjectMap;
	private Map<String, List<DescriptionObject>> stringDescriptionObjectsMap;
```
```yaml
  mapList:
    - key1: value1
      key2: value2
    - key3: value3
      key4: value4
  stringListMap:
    key1:
      - listValue1
      - listValue2
  stringDescriptionObjectMap:
    key1:
        name: descriptionObjectName
        description: description
  stringDescriptionObjectsMap:
    key1:
      -
       name: name 1
       description: description 1
      -
       name: name 2
       description: description 2
```
## Property naming (nestedLevel nested-level itd)
Because keys can be defined in various formats and certain sources have some limitations, Spring Boot uses a relaxed binder.

| Name        | Example           |
| ------------- |:-------------:|
|uniform|foo.id - foo.first-name - foo.last-name
|camel case|foo.id - foo.firstName - foo.lastName
|underscore|foo.id - foo.first_name - foo.last_name
|upper case|FOO_ID - FOO_FIRST-NAME - FOO_LAST-NAME

## Default values
Note that contrary to `@Value` annotation, SpEL expressions are not evaluated. You can assign default values in java code if you like, or add some business logic inside setter.

## Configuration metadata
Enables generating metadata for your @ConfigurationProperties. This metadata is aimed at providing option for your IDE to offer contextual help and “code completion” as you are working with application.properties or application.yml files.

To add it to your application or library just add following maven dependency:
```       <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-configuration-processor</artifactId>
          </dependency>
```

If needed addtional information about configuration metadata you can find on the [official documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html)
