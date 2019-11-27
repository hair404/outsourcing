module.exports = {
  devServer: {
    // proxy: {
    //   '/Platform': {
    //     target: 'http://192.168.1.223:8080',
    //     pathRewrite: {
    //       '^/Platform': ''
    //     },
    //     changeOrigin: true
    //   }
    // }
    proxy: {
      '/Platform': {
        target: 'http://47.94.212.141:8080',
        changeOrigin: true
      }
    }
  },
  publicPath: './'
}
