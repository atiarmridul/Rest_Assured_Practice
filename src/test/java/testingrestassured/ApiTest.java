package testingrestassured;

import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void getCategories() {
        String endpoint = "http://127.0.0.1/api_testing/category/read.php";
        var response = given().when().get(endpoint).then();
        response.log().body();
    }

    @Test
    public void createProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/create.php";
        String body = """
                {
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 12,
                "category_id": 3
                }
                """;
        var response = given().body(body).when().post(endpoint).then();
        response.log().body();
    }

    @Test
    public void getProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/read_one.php";
        var response =
                given().
                        queryParam("id", 1000).
                        when().
                        get(endpoint).
                        then();
        response.log().body();
//        given().
//                queryParam("id", 1000).
//                when().
//                get(endpoint).
//                then().
//                assertThat().
//                statusCode(200).
//                body("id",equalTo("1000")).
//                body("name",equalTo("Water Bottle"));
    }

    @Test
    public void getProducts() {
        String endpoint = "http://127.0.0.1/api_testing/product/read.php";
        given().
                when().
                get(endpoint).
                then().
                log().
                body().
                assertThat().
                statusCode(200).
                body("records.size()", greaterThanOrEqualTo(5)).
                body("records.id",everyItem(notNullValue())).
                body("records.name",everyItem(notNullValue())).
                body("records.description",everyItem(notNullValue())).
                body("records.price",everyItem(notNullValue())).
                body("records.category_id",everyItem(notNullValue())).
                body("records.category_name",everyItem(notNullValue()));


    }


    @Test
    public void updateProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/update.php";
        String body = """
                {
                "id": 19,
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 15,
                "category_id": 3
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    public void deleteProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/delete.php";
        String body = """
                {
                "id": 19
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    public void createSerializedProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/create.php";
        Product product = new Product("Mridul", "Description Mridul", 100, 3);
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();

    }

}



