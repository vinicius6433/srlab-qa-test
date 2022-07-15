package br.com.southrocklab.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.southrocklab.ApplicationTests;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CardResourceTest extends ApplicationTests {
	String card_id;

	@BeforeClass
	public static void urlbase() {
		RestAssured.port = 9090;
	}

	 @Test
	public void deve_salvar_novo_card_no_sistema() {
		Response response = given().contentType("application/json").body(
				"{ \"brand\": \"MASTER\", \"customerId\": 1, \"cvc\": \"901\", \"expirationMoth\": 9, \"expirationYear\": 2025, \"holderName\": \"Vinicius\", \"number\": \"5165734389002316\" }")
				.when().post("/card");

		response.then().statusCode(200).log().all();

	}

    @Test
	public void deve_retornar_status_400_quando_salvar_card_com_cvv_maior_que_999() {
		Response response = given().contentType("application/json").body(
				"{ \"brand\": \"MASTER\", \"customerId\": 0, \"cvc\": \"10000\", \"expirationMoth\": 9, \"expirationYear\": 2025, \"holderName\": \"Vinicius\", \"number\": \"5165734389002316\" }")
				.when().post("/card");

		response.then().log().all().body("cvc", containsString("cvc must be between 001 to 999")).statusCode(400);
	}

    @Test
	public void deve_retornar_status_400_quando_salvar_card_com_expiration_month_maior_que_12() {
		Response response = given().contentType("application/json").body(
				"{ \"brand\": \"MASTER\", \"customerId\": 0, \"cvc\": \"709\", \"expirationMoth\": 13, \"expirationYear\": 2025, \"holderName\": \"Vinicius\", \"number\": \"5165734389002316\" }")
				.when().post("/card");

		response.then().log().all().body("expirationMoth", containsString("Expiration month must be less then 12"))
				.statusCode(400);
	}

    @Test
	public void deve_retornar_status_400_quando_salvar_card_com_expiration_year_menor_que_2022() {
		Response response = given().contentType("application/json").body(
				"{ \"brand\": \"MASTER\", \"customerId\": 0, \"cvc\": \"709\", \"expirationMoth\": 11, \"expirationYear\": 2021, \"holderName\": \"Vinicius\", \"number\": \"5165734389002316\" }")
				.when().post("/card");

		response.then().log().all()
				.body("expirationYear", containsString("Expiration year must be greater than the current one"))
				.statusCode(400);
	}

    @Test
	public void deve_retornar_status_400_quando_salvar_card_com_number_de_15_digitos() {
		Response response = given().contentType("application/json").body(
				"{ \"brand\": \"MASTER\", \"customerId\": 0, \"cvc\": \"709\", \"expirationMoth\": 11, \"expirationYear\": 2025, \"holderName\": \"Vinicius\", \"number\": \"516573438900231\" }")
				.when().post("/card");

		response.then().log().all().body("number", containsString("Number must have a 16 numbers")).statusCode(400);
	}

	@Test
	public void deve_deletar_um_card_salvo_no_sistema() {
		criarCard();
		
		Response response = 
				given()
					.contentType("application/json")
					.pathParam("id", card_id)
				.when()
					.delete("/card/{id}");

			response
				.then()
					.statusCode(204)
					.log().all();
	}

	@Test
	public void deve_retornar_status_404_ao_deletar_um_card_com_id_nao_salvo_no_sistema() {
		Response response = 
			given()
				.contentType("application/json")
			.when()
				.delete("/card/10");

		response
			.then()
				.statusCode(404)
				.body("error", containsString("Not Found"))
				.log().all();
	}

	private void criarCard() {

		
		Response response = 
		given().contentType("application/json").body(
				"{ \"brand\": \"MASTER\", \"customerId\": 1, \"cvc\": \"901\", \"expirationMoth\": 9, \"expirationYear\": 2025, \"holderName\": \"Vinicius\", \"number\": \"5165734389002316\" }")
				.when().post("/card");

		response.then().statusCode(200).log().all();

		String id = response.jsonPath().getString("id");
		
		
		card_id = id;
	}

}
