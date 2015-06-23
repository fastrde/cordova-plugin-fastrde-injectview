package de.fastr.phonegap.plugins;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.WebView;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fabian on 21.05.15.
 */
public class InjectWebViewClient extends SystemWebViewClient{

    public InjectWebViewClient(SystemWebViewEngine parentEngine) {
        super(parentEngine);
        Injecter.getInstance().setViewClient(this);
        Log.w("inject", "InjectWebViewClient");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Injecter.getInstance().injectJavascriptFile("www/cordova");
        Injecter.getInstance().injectJavascriptFile("www/cordova_plugins");
        //Injecter.getInstance().injectJavascriptFile("www/plugins/de.fastr.phonegap.plugins.injectView/www/inject");
        Injecter.getInstance().parseCordovaPlugins();
        //TODO: Hier das automatische laden einbauen
        Injecter.getInstance().injectJavascriptFile("www/js/index");
    }
}
