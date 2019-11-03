module.exports = {
  devServer: {
    // proxy: {
    //   '/Platform': {
    //     target: 'http://192.168.137.111:8088',
    //     pathRewrite: {
    //       '^/Platform': ''
    //     },
    //     changeOrigin: true
    //   }
    // }
    proxy: {
      '/Platform': {
        target: 'http://localhost:9000',
        changeOrigin: true
      }
    }
  }
}
