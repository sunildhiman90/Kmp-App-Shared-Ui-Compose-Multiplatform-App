import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    let rootComponet: RootComponent
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(rootComponent: rootComponet)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    let rootComponet: RootComponent
    
    var body: some View {
        ComposeView(rootComponet: rootComponet)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



