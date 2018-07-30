# lift-sdk

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>jp.co.logly</groupId>
    <artifactId>lift-sdk</artifactId>
    <version>0.9.11</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "jp.co.logly:lift-sdk:0.9.11"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/lift-sdk-0.9.11.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import jp.co.logly.ApiInvoker.*;
import jp.co.logly.ApiInvoker.auth.*;
import jp.co.logly.Lift.Result.*;
import jp.co.logly.Lift.DefaultApi;

import java.io.File;
import java.util.*;

public class DefaultApiExample {

    public static void main(String[] args) {
        
        DefaultApi apiInstance = new DefaultApi();
        Long adspotId = 789L; // Long | Lift adspot ID
        Long widgetId = 789L; // Long | Lift wiget ID
        String url = "url_example"; // String | キーとなるページ URL (MDL)
        String ref = "ref_example"; // String | リファラーURL（通常Mobileでは必要なし）
        String toplevel = "items"; // String | jsonトップレベルhash名: 通常は'items'を指定
        try {
            InlineResponse200 result = apiInstance.requestLift(adspotId, widgetId, url, ref, toplevel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#requestLift");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://l.logly.co.jp*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**requestLift**](docs/DefaultApi.md#requestLift) | **GET** /lift.json | Liftレコメンデーション結果検索


## Documentation for Models

 - [InlineResponse200](docs/InlineResponse200.md)
 - [InlineResponse200Items](docs/InlineResponse200Items.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



