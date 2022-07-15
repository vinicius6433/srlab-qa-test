// describe => conjunto de IT's, é uma forma de organização, posso ter mais de um describe
// .type() => escreve no elementos
// force : true, ele ignora todos os elementos antes de serem carregados
// log escreve um relatório
// should => deve
// contains => contenha um trecho

require("cypress-xpath");
require("chance");
import CasosDeTeste from "../support/pages/CasosDeTeste";
describe("Sauce Demo", () => {
	beforeEach(() => {
		// Login.login()
	});
	afterEach(() => {
		cy.clearCookies();
	});
	it.only("Cadastro de conta", () => {
		// tudo foi feito em um único fluxo respeitando o CRUD.
		CasosDeTeste.validacoes();
		CasosDeTeste.cadastro();
		CasosDeTeste.login();
		CasosDeTeste.excluir();
	});
});
