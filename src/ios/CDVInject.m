#import "CDVInject.h"
#import "AppDelegate.h"

@implementation Inject

- (void)pluginInitialize{
    NSLog(@"Inject Initialized");
 
    AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    webViewDelegate = [[InjectWebViewDelegate alloc] initWithDelegate:appDelegate.viewController];
    appDelegate.viewController.webView.delegate = webViewDelegate;
}

- (void)addInject:(CDVInvokedUrlCommand*)command {
	CDVPluginResult* pluginResult = nil;
  NSString* resource = [command.arguments objectAtIndex:0];

    [self.injects addObject:resource];

	pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];	
}


- (void)javascriptString:(CDVInvokedUrlCommand*)command {
  CDVPluginResult* pluginResult = nil;
  NSString* resource = [command.arguments objectAtIndex:0];
  NSLog(@"javascriptString %@", resource);
 
    [((InjectWebViewDelegate*)webViewDelegate) injectJavascriptString:resource intoWebView:self.webView];

	pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];	
}

-(void)javascriptFile:(CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = nil;
    NSString* resource = [command.arguments objectAtIndex:0];
    NSLog(@"javascriptFile %@", resource);

    [((InjectWebViewDelegate*)webViewDelegate) injectJavascriptFile:resource intoWebView:self.webView];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@""];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];

}

@end
