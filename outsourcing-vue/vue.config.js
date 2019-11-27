module.exports = {
  devServer: {
    proxy: {
      '/Platform': {
        target: 'http://hair405.natapp1.cc',
        pathRewrite: {
          '^/Platform': ''
        },
        changeOrigin: true
      }
    },
    disableHostCheck: true
    // proxy: {
    //   '/Platform': {
    //     target: 'http://47.94.212.141:8080',
    //     changeOrigin: true
    //   }
    // }
  },
  publicPath: './'
}
