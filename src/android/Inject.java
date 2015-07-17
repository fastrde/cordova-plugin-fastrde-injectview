    package de.fastr.phonegap.plugins;

    import android.content.Context;
    import android.content.res.AssetManager;
    import android.util.Log;
    import android.webkit.WebView;
    import android.webkit.WebViewClient;

    import org.apache.cordova.CordovaInterface;
    import org.apache.cordova.CordovaPlugin;
    import org.apache.cordova.CallbackContext;

    import org.apache.cordova.CordovaWebView;
    import org.apache.cordova.CordovaWebViewEngine;
    import org.apache.cordova.engine.SystemWebView;
    import org.apache.cordova.engine.SystemWebViewClient;
    import org.apache.cordova.engine.SystemWebViewEngine;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;

    /**
     * This class echoes a string called from JavaScript.
     */
    public class Inject extends CordovaPlugin {
        @Override
        public void initialize(CordovaInterface cordova, CordovaWebView webView) {
            super.initialize(cordova, webView);
            Injecter.getInstance().setCordova(cordova);
        }

        @Override
        public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
            if (action.equals("javascriptFile")){
                String script = args.getString(0);
                Injecter.getInstance().injectJavascriptFile(script);
                return true;
            }else if (action.equals("javascriptString")){
                String script = args.getString(0);
                Injecter.getInstance().injectJavascriptString(script);
            }else if (action.equals("getCookies")){
                String domain = args.getString(0);
								JSONObject json = new JSONObject();
                String value = Injecter.getInstance().getCookies(domain);
	
								json.put("domain", domain);
								json.put("cookies", value); 
								callbackContext.success(json);
            }else if (action.equals("getCookie")){
                String domain = args.getString(0);
                String name = args.getString(1);
								JSONObject json = new JSONObject();
                String value = Injecter.getInstance().getCookie(domain, name);

								json.put("domain", domain);
								json.put("name", name);
								json.put("value", value); 
								callbackContext.success(json);
            }
            return false;
        }
    }

