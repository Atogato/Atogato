describe('Main Page render', () => {
  it('Visits the main page', () => {
    cy.visit('/')
  })
})

describe('Form Page render', () => {
  it('Visits the project form page', () => {
    cy.visit('/project/form')
  })

  it('Visits the artist form page', () => {
    cy.visit('/artist/form')
  })
})
