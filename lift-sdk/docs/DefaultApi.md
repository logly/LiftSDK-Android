# DefaultApi

All URIs are relative to *https://l.logly.co.jp/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**requestLift**](DefaultApi.md#requestLift) | **GET** /lift.json | Liftレコメンデーション結果検索


<a name="requestLift"></a>
# **requestLift**
> InlineResponse200 requestLift(adspotId, widgetId, url, ref, toplevel)

Liftレコメンデーション結果検索

Liftレコメンデーション結果検索 注：返される結果数はLogly Lift コンソールの方で設定するが、表示はエリアの空きがある分だけ表示される

### Example
```java
// Import classes:
//import jp.co.logly.ApiInvoker.ApiException;
//import jp.co.logly.Lift.DefaultApi;


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
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **adspotId** | **Long**| Lift adspot ID |
 **widgetId** | **Long**| Lift wiget ID |
 **url** | **String**| キーとなるページ URL (MDL) |
 **ref** | **String**| リファラーURL（通常Mobileでは必要なし） | [optional]
 **toplevel** | **String**| jsonトップレベルhash名: 通常は&#39;items&#39;を指定 | [optional] [default to items]

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

