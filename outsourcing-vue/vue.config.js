module.exports = {
  devServer: {
    proxy: {
      '/Platform': {
        target: 'http://192.168.1.184:8080',
        pathRewrite: {
          '^/Platform': ''
        },
        changeOrigin: true
      }
    }
    // proxy: {
    //   '/Platform': {
    //     target: 'http://localhost:9000',
    //     changeOrigin: true
    //   }
    // }
  }
}
