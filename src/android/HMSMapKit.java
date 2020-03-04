package cordova.hms.map.kit;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class HMSMapKit extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if(action.equals("addTwoNumbers")) {
            Double first = args.getDouble(0);
            Double second = args.getDouble(1);
            this.addTwoNumbers(first,second, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void addTwoNumbers(Double first,Double second, CallbackContext callbackContext) {
        if (first != null && second != null) {
            callbackContext.success(""+(first+second));
        } else {
            callbackContext.error("Expected two non-empty double argument.");
        }
    }
}
