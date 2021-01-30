var webpack = require("webpack")
module.exports = {
  css: {
    loaderOptions: {
      scss: {
        prependData: `
              @import "~@/assets/styles/variables.scss";
                `,
      },
    },
  },
  outputDir: "../src/main/resources/static",
  devServer: {
    proxy: "http://localhost:8080",
  },
  configureWebpack: {
    devtool: "source-map",
    plugins: [
      new webpack.ProvidePlugin({
        "window.Quill": "quill/dist/quill.js",
        Quill: "quill/dist/quill.js",
      }),
    ],
  },
  pages: {
    index: {
      entry: "src/main.js",
      template: "public/index.html",
      title: "index Page",
      filename: "index.html",
    },
  },
  transpileDependencies: ["vuetify", "vuetify-dialog"],
  filenameHashing: false,
  chainWebpack: (config) => {
    config.module
      .rule("eslint")
      .use("eslint-loader")
      .tap((options) => {
        options.fix = true // auto-fix 옵션
        return options
      })
    ;["vue-modules", "vue", "normal-modules", "normal"].forEach((match) => {
      config.module
        .rule("sass")
        .oneOf(match)
        .use("sass-loader")
        .tap((opt) =>
          Object.assign(opt, {
            prependData: `@import "~@/assets/styles/variables.scss"`,
          })
        )
    })
  },
}
