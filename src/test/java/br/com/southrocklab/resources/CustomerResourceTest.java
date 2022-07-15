package br.com.southrocklab.resources;

import br.com.southrocklab.ApplicationTests;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;

import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerResourceTest extends ApplicationTests{
	
	String customer_id;
	String customer_name;
	String customer_last_name;
	
	@BeforeClass
	public static void urlbase() {
		RestAssured.port=9090;
	}
    @Test
    public void deve_salvar_novo_customer_no_sistema() {
    	Response response = 
					given()
						.contentType("application/json")
						.body("{\"birthDate\" : \"2001-04-04\", \"lastName\" : \"Moreira\", \"name\": \"Vinicius\"}")
					.when()
						.post("/customer");
		
				response
					.then()
						.statusCode(200)
						.log().all();
}
    
    @Test
    public void deve_retornar_status_400_quando_salvar_customer_ja_salvo() {
    	Response response = 
				given()
					.contentType("application/json")
					.body("{\"birthDate\" : \"2001-04-17\", \"lastName\" : \"Moreira\", \"name\": \"Vinicius\"}")
				.when()
					.post("/customer");
		
			response.then()
				.log().all()
				.body("message", containsString("Customer already exists"))
				.statusCode(400);
    }

    @Test
    public void deve_retornar_status_400_quando_salvar_customer_com_birth_date_maior_que_hoje() {
    	Response response = 
				given()
					.contentType("application/json")
					.body("{\"birthDate\" : \"2023-04-17\", \"lastName\" : \"Moreira\", \"name\": \"Vinicius\"}")
				.when()
					.post("/customer");
		
			response.then()
				.log().all()
				.body("birthDate", containsString("Birth date must be smaller than today"))
				.statusCode(400);
    }

    @Test
    public void deve_procurar_customer_pelo_name_e_last_name() {
    	salvandoValores();
    	System.out.println(customer_last_name+""+customer_name);
    	Response response = 
    				given()
    					.contentType("application/json")
    					.pathParam("name", customer_name)
    					.pathParam("last_name", customer_last_name)
    					.when()
    					.get("customer/{name}/{last_name}");
    	
    			response
    				.then()	
    					.log().all()
    					.statusCode(200);
    		
    			response
    				.then()
    					.body("name", containsString("Moreira"));
    			response
    				.then()
    					.body("lastName", containsString("Martins"));
    	
    }

    @Test
    public void deve_retornar_status_404_quando_buscar_customer_por_name_e_last_name_inexistente() {
    	Response response = 
    				given()
    					.contentType("application/json")
    					.when()
    					.get("customer/nome/sobrenome");
    	
    			response
    				.then()	
    					.log().all()
    					.body("message", containsString("Customer not found"))
    					.body("error", containsString("Not Found"))
    					.statusCode(404);
    }

    @Test
    public void deve_atualizar_o_last_name_de_um_customer() {
    	salvandoValores();
    	String valorAtualizado = "valorAtualizado";
    	Response 
    		response = 
				given()
					.contentType("application/json")
					.body("{\"birthDate\" : \"2001-04-17\", \"lastName\" : \""+valorAtualizado+"\", \"name\": \"Vinicius\"}")
					.pathParam("id", customer_id)
				.when()
					.put("/customer/{id}");
    	
    		response
    			.then()
    				.body("lastName", containsString(valorAtualizado));
	
			response
				.then()
					.statusCode(200)
					.log().all();
			
			
    }

    @Test
    public void deve_retornar_status_404_ao_atualizar_um_customer_com_id_nao_salvo_no_sistema() {
    	String valorAtualizado = "valorAtualizado";
    	Response 
    		response = 
				given()
					.contentType("application/json")
					.body("{\"birthDate\" : \"2001-04-17\", \"lastName\" : \""+valorAtualizado+"\", \"name\": \"Vinicius\"}")
					.pathParam("id", 10)
				.when()
					.put("/customer/{id}");
	
    	response
		.then()	
			.body("message", containsString("Customer not found"))
			.statusCode(404)
			.body("error", containsString("Not Found"))
			.log().all();
    }

    @Test
    public void deve_deletar_um_customer_salvo_no_sistema() {
    	salvandoValores();
    	String valorAtualizado = "valorAtualizado";
    	Response 
    		response = 
				given()
					.contentType("application/json")
					.pathParam("id", customer_id)
				.when()
					.delete("/customer/{id}");
	
			response
				.then()
					.statusCode(204)
					.log().all();
    }

    @Test
    public void deve_retornar_status_404_ao_deletar_um_customer_com_id_nao_salvo_no_sistema() {
    	String valorAtualizado = "valorAtualizado";
    	Response 
    		response = 
				given()
					.contentType("application/json")
				.when()
					.delete("/customer/10");
	
			response
				.then()
					.statusCode(404)
					.body("error", containsString("Not Found"))
					.log().all();
			
    }
    
    private void salvandoValores() {
    	Response response = 
				given()
					.contentType("application/json")
					.body("{\"birthDate\" : \"2001-04-16\", \"lastName\" : \"Martins\", \"name\": \"Moreira\"}")
				.when()
				.post("/customer");
		
				response.then()
					.statusCode(200)
					.log().all();
				
			String id = response.jsonPath().getString("id");
			String name = response.jsonPath().getString("name");
			String last_name = response.jsonPath().getString("lastName");
			
			
			customer_id = id;
			customer_last_name = last_name;
			customer_name = name;
    }
}
