export const ELEMENTS = {
	grid: "#formCover",

	usernameLogin: '[a-hint="Username"] > .inputContainer > .ng-pristine',
	passwordLogin: '[a-hint="Password"] > .inputContainer > .ng-pristine',
	username:
		':nth-child(2) > [a-hint="Username"] > .inputContainer > .ng-pristine',
	email: '[sec-name="userEmail"] > .inputContainer > .ng-pristine',
	password:
		':nth-child(3) > [a-hint="Password"] > .inputContainer > .ng-pristine',
	confirmpassword:
		'[a-hint="Confirm password"] > .inputContainer > .ng-pristine',
	firstname: '[sec-name="userFirstName"] > .inputContainer > .ng-pristine',
	lastname: '[sec-name="userLastName"] > .inputContainer > .ng-pristine',
	cellphone:
		":nth-child(2) > :nth-child(3) > .ng-isolate-scope > .inputContainer > .ng-pristine",
	city: '[sec-name="userCity"] > .inputContainer > .ng-valid',
	country: '[sec-name="userCountry"] > .inputContainer > .ng-pristine',

	adress: '[sec-name="userAdress"] > .inputContainer > .ng-pristine',
	state: '[sec-name="userState"] > .inputContainer > label',
	zip: "#formCover > :nth-child(3) > :nth-child(4) > .ng-isolate-scope > .inputContainer > .ng-pristine",
	checkbox:
		'[sec-name="registrationAgreement"] > .inputContainer > .ng-pristine',
};
export let nome,
	sobrenome,
	email,
	password,
	number,
	username,
	celular,
	cidade,
	postal,
	estado,
	endereco,
	first;
export function dadosAleatorios() {
	first = chance.first();
	nome = chance.name({ nationality: "en" });
	sobrenome = chance.name({ middle: true });
	email = chance.email();
	password = "Mec0ntr4t4";
	number = chance.integer({ min: -20, max: 20 });
	username = first + number;
	celular = chance.phone({ country: "br" });
	cidade = chance.city();
	postal = chance.zip();
	estado = chance.state();
	endereco = chance.address();
}
