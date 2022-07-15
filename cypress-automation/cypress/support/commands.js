// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
Cypress.Commands.add("cadastrarDadosLogin", (username, password,name) => {
	cy.writeFile("./fixtures/dadosLogin.json", { usuario: username, senha:password, nome: name});
	cy.readFile("./fixtures/dadosLogin.json").then((json) => {
		expect(json.usuario, {log:false}).to.equal(username, {log:false});
		expect(json.senha, {log:false}).to.equal(password, {log:false});
	});
});
Cypress.Commands.add("getUserLogin", (data) => {

	cy.readFile('./fixtures/dadosLogin.json').its('usuario').then((user)=>{
		cy.get(data).type(user, {log:false})
	})
});
Cypress.Commands.add("getUserPass", (data) => {

	cy.readFile('./fixtures/dadosLogin.json').its('usuario').then((user)=>{
		cy.get(data).type(user,{log:false})
	})
});
Cypress.Commands.add("getNomeValidacao", () => {

	cy.readFile('./fixtures/dadosLogin.json').its('usuario').then((user)=>{
		cy.contains(user)
	})
});

