
// For supporting browser history callback if navigation library supports this
config.devServer = {
  ...config.devServer, // Merge with other devServer settings
  "historyApiFallback": true,
  //"https": true, // this is deprecated
  "server": {
      //"type": "https",
  },
  "headers": {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
      "Access-Control-Allow-Headers": "X-Requested-With, content-type, Authorization"
    }
};
