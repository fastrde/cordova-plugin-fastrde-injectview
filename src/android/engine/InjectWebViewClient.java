package de.fastr.phonegap.plugins;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

/**
 * Created by fabian on 21.05.15.
 */
public class InjectWebViewClient extends SystemWebViewClient{

    public InjectWebViewClient(SystemWebViewEngine parentEngine) {
        super(parentEngine);
        Injecter.getInstance().setViewClient(this);
        Log.w("inject", "InjectWebViewClient");
    }
		public String getCookies(String siteName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
				return cookies;
		}

    public String getCookie(String siteName,String CookieName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        String[] temp=cookies.split(";");
        for (String ar1 : temp ){
            if(ar1.contains(CookieName)){
                String[] temp1=ar1.split("=");
                CookieValue = temp1[1];
            }
        }
        return CookieValue;
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
