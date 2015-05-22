package de.fastr.phonegap.plugins;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

/**
 * Created by fabian on 22.05.15.
 */
public class Injecter {
    private static Injecter instance;
    private InjectWebViewClient client;
    private CordovaInterface cordova;
    private InjectWebView webView;

    private Injecter() {};
    public static Injecter getInstance () {
        if (Injecter.instance == null) {
            Injecter.instance = new Injecter();
        }
        return Injecter.instance;
    }

    public void setWebView(InjectWebView webView){
        this.webView = webView;
    }

    public void setViewClient(InjectWebViewClient client){
        this.client = client;
    }

    public void setCordova(CordovaInterface cordova){
        this.cordova = cordova;
    }

    public void injectJavascriptString(String script){
        this.webView.loadUrl("javascript: " + script);
        //callbackContext.success(script);
    }

    public void injectJavascriptFile(String scriptFile){
        Context context=this.cordova.getActivity().getApplicationContext();
        AssetManager assetManager = context.getAssets();
        try {
            Log.w("Inject", "Injecting " + scriptFile);
            InputStream ims = assetManager.open(scriptFile + ".js");
            String content = fileToString(ims);
            this.injectJavascriptString(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //callbackContext.success(scriptFile);
        //callbackContext.error("Expected one non-empty string argument.");
    }


    public void parseCordovaPlugins(){
        Context context = this.cordova.getActivity().getApplicationContext();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream ims = assetManager.open("www/cordova_plugins.js");
            String content = fileToString(ims);
            content = content.replaceAll("^.[^\\[]*(\\[.*\\])[^\\]]*$", "$1");
            JSONArray plugins = new JSONArray(content);
            for (int i =0 ; i < plugins.length(); i ++) {
                JSONObject plugin = plugins.getJSONObject(i);

                String path = "www/" + plugin.getString("file");
                path = path.substring(0, path.length() - 3);
                Log.w("Inject", path);
                this.injectJavascriptFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String fileToString(InputStream ims) throws IOException{
        List<String> parts = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ims));
        StringBuilder out = new StringBuilder();
        String part = "", line, content, commands[];

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("//.*$", "");
            out.append(line);
        }

        content = out.toString();
        content = content.replaceAll("/\\*[^*]*\\*/", "");

        return  content;
    }

}
