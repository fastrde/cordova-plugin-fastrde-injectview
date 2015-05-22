#import <Cordova/CDV.h>
#import "CDVInjectWebViewDelegate.h"

@interface Inject: CDVPlugin<UIWebViewDelegate>{
    CDVWebViewDelegate* webViewDelegate;
}
	@property (nonatomic, strong) NSMutableArray* injects;
	- (void)addInject:(CDVInvokedUrlCommand*)command;
	- (void)javascriptString:(CDVInvokedUrlCommand*)command;
	- (void)javascriptFile:(CDVInvokedUrlCommand*)command;
@end
