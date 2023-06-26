import { defineConfig } from 'cypress'

export default defineConfig({
  projectId: 'projectA',
  component: {
    specPattern: 'src/*/**/*.cy.{js,jsx,ts,tsx}',
    devServer: {
      framework: 'next',
      bundler: 'webpack',
    },
  },

  e2e: {
    specPattern: 'cypress/tests/**/*.cy.{js,jsx,ts,tsx}',
    baseUrl: 'http://localhost:3000/',
    setupNodeEvents(on, config) {
      // implement node event listeners here
      require('@cypress/code-coverage/task')(on, config)
      // include any other plugin code...

      // It's IMPORTANT to return the config object
      // with any changed environment variables
      return config
    },
  },
})
