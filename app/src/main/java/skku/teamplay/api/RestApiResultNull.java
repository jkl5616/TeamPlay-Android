
package skku.teamplay.api;

/**
 * Created by woorim on 2018. 5. 29..
 */

public class RestApiResultNull extends RestApiResult {
    private static final String TAG = RestApiResultNull.class.getSimpleName();
    public static final int ERROR = 0;
    public static final int HTTP_POST_FAILED = 2;
    public static final int PARSE_FAILED = 1;
    private int errorCode;

    public static RestApiResult get(int code) {
        // prints stack trace that this was made for debugging
        RestApiResultNull apiResult = new RestApiResultNull();
        apiResult.setErrorCode(code);
        return apiResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
