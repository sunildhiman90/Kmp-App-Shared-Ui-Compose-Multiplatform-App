//
//  Koin.swift
//  iosApp
//
//  Created by Sunil Kumar on 16/01/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp


private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}


func startKoin() {
    let koinApplication = DependencyInjectionKt.doInitKoinIOS()
    _koin = koinApplication.koin
}
