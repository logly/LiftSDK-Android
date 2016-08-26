/**
 * Logly lift.json API
 * Logly lift.json API
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package jp.co.logly.Lift;

import jp.co.logly.ApiInvoker.ApiException;
import jp.co.logly.Lift.Result.InlineResponse200;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DefaultApi
 */
public class DefaultApiTest {

    private final DefaultApi api = new DefaultApi();

    
    /**
     * Liftレコメンデーション結果検索
     *
     * Liftレコメンデーション結果検索 注：返される結果数はLogly Lift コンソールの方で設定するが、表示はエリアの空きがある分だけ表示される
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void requestLiftTest() throws ApiException {
        Long adspotId = null;
        Long widgetId = null;
        String url = null;
        String ref = null;
        String toplevel = null;
        // InlineResponse200 response = api.requestLift(adspotId, widgetId, url, ref, toplevel);

        // TODO: test validations
    }
    
}
