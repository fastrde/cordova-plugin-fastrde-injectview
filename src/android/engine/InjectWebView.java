package de.fastr.phonegap.plugins;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.AttributeSet;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.io.IOException;
import java.io.InputStream;

public class InjectWebView extends SystemWebView {

    private Context context;
    public InjectWebView(Context context){
        super(context);
        this.context = context;
        Injecter.getInstance().setWebView(this);
        Log.w("inject", "InjectWebView");
    }

    public InjectWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void javascriptString(String string){
        this.loadUrl("javascript: " + string);
    }

    private void javascriptFile(String scriptFile, CallbackContext callbackContext){

        AssetManager assetManager = this.context.getAssets();
        try {
            InputStream ims = assetManager.open(scriptFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        callbackContext.success(scriptFile);
        //callbackContext.error("Expected one non-empty string argument.");

    }

}
