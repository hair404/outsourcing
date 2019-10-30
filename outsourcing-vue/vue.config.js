module.exports = {
  devServer: {
    proxy: {
      '/Platform': {
        target: 'http://localhost:9000',
        changeOrigin: true
      }
    }
  }
}
