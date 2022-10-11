module.exports = {
  publicPath: './',
  outputDir: 'dist',
  assetsDir: 'static',

  chainWebpack: config => {
    config
      .plugin('html')
      .tap(args => {
        args[0].title= '你要的标题'
        return args
      })
  }
  
}
