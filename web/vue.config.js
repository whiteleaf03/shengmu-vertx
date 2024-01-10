const {defineConfig} = require('@vue/cli-service')
const webpack = require('webpack')

module.exports = defineConfig({
    transpileDependencies: true,
    publicPath: `./`,
    devServer:{
      port: 8080
    },
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                _: "lodash",
                L: 'leaflet'
            }),

        ]
    }
})
