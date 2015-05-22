#import <Cordova/CDVWebViewDelegate.h>

@interface InjectWebViewDelegate: CDVWebViewDelegate
  @property int webViewLoads;
  - (void)webViewDidFinishLoad:(UIWebView*)theWebView;
  - (void)injectJavascriptString:(NSString*)resource intoWebView:(UIWebView*)webView;
  - (void)injectJavascriptFile:(NSString*)resource intoWebView:(UIWebView*)webView;

@end


