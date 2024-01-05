import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    var rootHolder: RootHolder {
        appDelegate.rootHolder
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
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        lifecycle.subscribe(callbacks: LifecycleCallbacksImpl())
        let homeViewModel = HomeViewModel()
        
        root = DefaultRootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            homeViewModel: homeViewModel
        )
        
        LifecycleRegistryExtKt.create(lifecycle)
    }
    
    deinit {
        LifecycleRegistryExtKt.destroy(lifecycle)
    }

}

class AppDelegate: NSObject, UIApplicationDelegate {
    let rootHolder: RootHolder = RootHolder()
}
