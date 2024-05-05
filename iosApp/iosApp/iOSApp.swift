import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    var rootHolder: RootHolder {
        appDelegate.getRootHolder()
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView(rootComponet: rootHolder.root)
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                    debugPrint("Swift onResume")
                    LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
                    debugPrint("Swift onPause")
                    LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
                    debugPrint("Swift onStop")
                    LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification)) { _ in
                    debugPrint("Swift onDestroy")
                    LifecycleRegistryExtKt.destroy(rootHolder.lifecycle)
                }
		}
	}
}

class RootHolder: ObservableObject {

    let lifecycle: LifecycleRegistry
    let root: RootComponent
    
    init() {
        lifecycle = koin.lifecycleRegistry
        root = koin.rootComponent
        LifecycleRegistryExtKt.create(lifecycle)
    }
    
    deinit {
        LifecycleRegistryExtKt.destroy(lifecycle)
    }

}

class AppDelegate: NSObject, UIApplicationDelegate {
    var rootHolder: RootHolder? = nil
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        startKoin()
        rootHolder = RootHolder()
        
        return true
    }
    
    func getRootHolder() -> RootHolder {
        if(rootHolder == nil) {
            rootHolder = RootHolder()
        }
        return rootHolder!
    }
}
