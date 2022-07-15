const { defineConfig } = require("cypress");

module.exports = defineConfig({
  reporter: "mochawesome",
  reporterOptions:{
    reportDir: "cypress/reports",
    overwrite: true,
    html:true,
    json:false,
    timestamp:"mmddyyyy_HHMMss",
  },
  e2e: {
    baseUrl:"https://www.advantageonlineshopping.com/#/",
    viewportWidth: 1366,
    viewportHeight: 768,
    defaultCommandTimeout: 30000,
    chromeWebSecurity:false,
  },
});




