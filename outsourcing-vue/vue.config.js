module.exports = {
  devServer: {
    // proxy: {
    //   '/Platform': {
    //     target: 'http://hair405.natapp1.cc',
    //     pathRewrite: {
    //       '^/Platform': ''
    //     },
    //     changeOrigin: true
    //   }
    // },
    proxy: {
      '/Platform': {
        target: 'http://192.168.1.222:8080',
        pathRewrite: {
          '^/Platform': ''
        },
        changeOrigin: true
      }
    },
    // proxy: {
    //   '/Platform': {
    //     target: 'http://localhost:9000',
    //     changeOrigin: true
    //   }
    // },
    disableHostCheck: true
  },
  publicPath: './'
}
