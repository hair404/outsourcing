module.exports = {
  devServer: {
    proxy: {
      '/Platform': {
        target: 'http://freetunnel.cc:20286',
        pathRewrite: {
          '^/Platform': ''
        },
        changeOrigin: true
      }
    }
    // proxy: {
    //   '/Platform': {
    //     target: 'http://47.94.212.141:8080',
    //     changeOrigin: true
    //   }
    // }
  },
  publicPath: './'
}
