//If you don't want to include a polyfill, you can use an empty module like this:
//config.resolve.fallback = { "os": false, "path": false }
config.resolve = {
    fallback: {
        "fs": false,
        "path": false,
        "crypto": false,
    }
};

//alternatively, if you want to use them
//config.resolve = {
//    fallback: {
//        path: require.resolve("path-browserify"),
//        os: require.resolve("os-browserify/browser")
//    }
//}
//config.resolve.fallback = { "path": require.resolve("path-browserify"), "os": require.resolve("os-browserify/browser") }