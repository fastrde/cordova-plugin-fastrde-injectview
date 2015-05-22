package de.fastr.phonegap.plugins;

import android.content.Context;
import android.util.Log;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

/**
 * Created by fabian on 22.05.15.
 */
public class InjectWebViewEngine extends SystemWebViewEngine {

    /** Used when created via reflection. */
    public InjectWebViewEngine(Context context, CordovaPreferences preferences) {
        this(new InjectWebView(context));
        //super(context, preferences);
        Log.w("inject", "InjectWebViewEngine");

    }

    public InjectWebViewEngine(SystemWebView webView) {
        super(webView);
        webView.setWebViewClient(new InjectWebViewClient(this));
    }

}