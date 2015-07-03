#import "CDVInjectWebViewDelegate.h"

@implementation InjectWebViewDelegate



- (void)injectJavascriptString:(NSString*)resource intoWebView:(UIWebView*)webView {
    [webView stringByEvaluatingJavaScriptFromString:resource];
}

- (void)injectJavascriptFile:(NSString*)resource intoWebView:(UIWebView*)webView {
    NSString *jsPath = [[NSBundle mainBundle] pathForResource:resource ofType:@"js"];
    NSString *js = [NSString stringWithContentsOfFile:jsPath encoding:NSUTF8StringEncoding error:NULL];
    [webView stringByEvaluatingJavaScriptFromString:js];
}

- (void)injectPlugin:(NSString*)resource intoWebView:(UIWebView*)webView {
    [self injectJavascriptFile:resource intoWebView:webView];
    
}

- (NSArray*)parseCordovaPlugins{
    NSString *jsPath = [[NSBundle mainBundle] pathForResource:@"www/cordova_plugins" ofType:@"js"];
    NSString *js = [NSString stringWithContentsOfFile:jsPath encoding:NSUTF8StringEncoding error:NULL];
    NSScanner *scanner = [NSScanner scannerWithString:js];
    [scanner scanUpToString:@"[" intoString:nil];
    NSString *substring = nil;
    [scanner scanUpToString:@"];" intoString:&substring];
    substring = [NSString stringWithFormat:@"%@]", substring];
    
    NSError* localError;
    NSData* data = [substring dataUsingEncoding:NSUTF8StringEncoding];
    NSArray* pluginObjects = [NSJSONSerialization JSONObjectWithData:data options:0 error:&localError];
    return pluginObjects;
}
/*
 * This function is a viewcontroller function and get not called here
 * Copy & Paste this function to MainViewController.m
 */
/*
 - (void)viewDidLayoutSubviews {
 NSLog(@"viewDidLayoutSubviews");
 NSNotificationCenter *nc = [NSNotificationCenter defaultCenter];
 [nc postNotificationName:@"InjectViewDidLayoutSubviews" object:self];
 }
 */
- (void)webViewDidFinishLoad:(UIWebView*)theWebView
{
    NSLog(@"webViewDidFinishLoad");
    [super webViewDidFinishLoad:theWebView];
    if (--self.webViewLoads == 0){
        NSArray* pluginObjects = [self parseCordovaPlugins];
        
        NSLog(@"injecting cordova %@", pluginObjects);
        
        [self injectJavascriptFile:@"www/cordova" intoWebView:theWebView];
        [self injectJavascriptFile:@"www/cordova_plugins" intoWebView:theWebView];
        //[self injectJavascriptFile:@"www/plugins/de.fastr.phonegap.plugins.injectView/www/inject"  intoWebView:theWebView];
        
        for (NSDictionary* pluginParameters in pluginObjects) {
            NSString* file = pluginParameters[@"file"];
            NSString* path = [NSString stringWithFormat:@"www/%@", file];
            NSLog(@"Injecting %@", path);
            path = [path stringByDeletingPathExtension];
            [self injectJavascriptFile:path intoWebView:theWebView];
        }
        [self injectJavascriptFile:@"www/js/index" intoWebView:theWebView];
    }
    NSDictionary *userInfo = @{@"webView" : theWebView};
    NSNotificationCenter *nc = [NSNotificationCenter defaultCenter];
    [nc postNotificationName:@"InjectWebViewDidFinishLoad" object:self userInfo:userInfo];
}

- (void)webViewDidStartLoad:(UIWebView *)webView {
    NSLog(@"webViewDidStartLoad");
    [super webViewDidStartLoad:webView];
    self.webViewLoads++;
    NSDictionary *userInfo = @{@"webView" : webView};
    NSNotificationCenter *nc = [NSNotificationCenter defaultCenter];
    [nc postNotificationName:@"InjectWebViewDidStartLoad" object:self userInfo:userInfo];
}


- (void)webView:(UIWebView*)webView didFailLoadWithError:(NSError*)error {
    NSLog(@"didFailLoadWithError");
    [super webView:webView didFailLoadWithError:error];
    self.webViewLoads--;
    NSDictionary *userInfo = @{@"error" : error};
    NSNotificationCenter *nc = [NSNotificationCenter defaultCenter];
    [nc postNotificationName:@"InjectDidFailLoadWithError" object:self userInfo:userInfo];
    
}
@end