import {
	celular,
	cidade,
	dadosAleatorios,
	email,
	endereco,
	estado,
	nome,
	password,
	postal,
	sobrenome,
	username,
} from "./elements";

const el = require("./elements").ELEMENTS;
require("chance");
class CasosDeTeste {
	validacoes() {
		cy.visit("");
		cy.get("#menuUser").click();
		cy.get(".create-new-account").click();

		//username
		cy.get(el.username).click();

		//email
		cy.get(el.email).click();
		cy.get(el.grid).contains("Username field is required");

		//password
		cy.get(el.password).type(" ");
		cy.get(el.grid).contains("Email field is required");
		cy.get(el.grid).contains("- Use 4 character or longer").should("to.exist");
		cy.get(el.grid).contains("- Use maximum 12 character").should("to.exist");
		cy.get(el.grid)
			.contains("- Including at least one lower letter")
			.should("to.exist");
		cy.get(el.grid)
			.contains("- Including at least one upper letter")
			.should("to.exist");
		cy.get(el.grid)
			.contains("- Including at least one number")
			.should("to.exist");
		//confirmpassword
		cy.get(el.confirmpassword).click();
		cy.get(el.grid).contains("Password field is required").should("to.exist");
		// primeiro nome
		cy.get(el.firstname).type(" ");
		cy.get(el.grid)
			.contains("Confirm password field is required")
			.should("to.exist");
		cy.get(el.grid).contains("- Use 2 character or longer").should("to.exist");
		cy.get(el.grid).contains("- Use maximum 30 character").should("to.exist");

		//sobrenome
		cy.get(el.lastname).type(" ");
		cy.get(el.grid).contains("- Use 2 character or longer").should("to.exist");
		cy.get(el.grid).contains("- Use maximum 30 character").should("to.exist");

		// numero de celular
		cy.get(el.cellphone).type("0");
		cy.get(el.grid).contains("- Use maximum 20 character").should("to.exist");
		// cidade
		cy.get(el.city).click();
		cy.get(el.grid).contains("- Use maximum 25 character").should("to.exist");

		// endereço
		cy.get(el.adress).click();
		cy.get(el.grid).contains("- Use maximum 50 character").should("to.exist");

		// estado
		cy.get(el.state).click();
		cy.get(el.grid).contains("- Use maximum 10 character");

		// código postal
		cy.get(el.zip).click();
		cy.get(el.grid).contains("- Use maximum 10 character");
	}

	cadastro() {
		// todos os dados passados no cadastro são 100% aleatórios
		dadosAleatorios();
		cy.visit("");
		cy.get("#menuUser").click();
		cy.get(".create-new-account").click();
		//username
		cy.get(el.username).type(username);

		//email
		cy.get(el.email).type(email);

		//password
		cy.get(el.password).type(password, { log: false });
		//confirmpassword
		cy.get(el.confirmpassword).type(password, { log: false });
		// primeiro nome
		cy.get(el.firstname).type(nome);

		//sobrenome
		cy.get(el.lastname).type(sobrenome);

		// numero de celular
		cy.get(el.cellphone).type(celular);
		// cidade
		cy.get(el.city).type(cidade);

		// endereço
		cy.get(el.adress).type(endereco);
		// *** OBS *** COUNTRY NÃO ESTÁ FUNCIONANDO, POSSÍVEL FALHA DE BANCO DE DADOS E LENTIDÃO NA API.
		// estado
		cy.get(el.state).type(estado);

		// código postal
		cy.get(el.zip).type(postal);

		cy.get(el.checkbox).click();
		cy.wait(1000);
		cy.get("#register_btnundefined").click({ force: true });
		// Site extremamente lento, API extremamente demorada, por isso o wait tão demorado
		cy.contains("New user created successfully.", { timeout: 100000 });
		cy.cadastrarDadosLogin(username, password, nome);
	}

	login() {
		cy.visit("");
		cy.get("#menuUser").click();
		cy.getUserLogin(el.usernameLogin);
		cy.getUserPass(el.passwordLogin);
		cy.get("#sign_in_btnundefined").click();
	}

	excluir() {
		cy.get("#menuUserLink").click();
		cy.get('#loginMiniTitle > [translate="My_account"]').click();
		cy.get(".deleteMainBtnContainer").click();
		cy.get(".deleteRed").click();
	}
}

export default new CasosDeTeste();
