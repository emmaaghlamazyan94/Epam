import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class APITest {
    User user = User.getRandomUser();
    SoftAssert softAssert = new SoftAssert();
    int userId;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "public-api/";
    }

    @Test
    public void postUser() {
        ValidatableResponse response = RestAssured
                .given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .post("users")
                .then()
                .spec(getResponseSpecification());

        userId = response.extract().response().jsonPath().getInt("data.id");

        String name = response.extract().response().jsonPath().getString("data.name");
        String email = response.extract().response().jsonPath().getString("data.email");
        String gender = response.extract().response().jsonPath().getString("data.gender");
        int statusCode = response.extract().response().jsonPath().getInt("code");

        softAssert.assertEquals(user.getName(), name, "Posted user name is not the same as expected");
        softAssert.assertEquals(user.getEmail(), email, "Posted email is not the same as expected");
        softAssert.assertEquals(user.getGender(), gender, "Gender type is not as expected");
        softAssert.assertEquals(statusCode, HttpStatus.SC_CREATED, "User is not created");
        softAssert.assertAll();

        response.extract().response().prettyPrint();
    }

    @Test(priority = 1)
    public void getUser() {
        ValidatableResponse response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("users/" + userId)
                .then()
                .spec(getResponseSpecification());

        int statusCode = response.extract().response().jsonPath().getInt("code");
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "User is not found");
        response.extract().response().prettyPrint();
    }

    @Test(priority = 2)
    public void deleteUser() {
        ValidatableResponse response = RestAssured
                .given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .post("users")
                .then();

        userId = response.extract().response().jsonPath().getInt("data.id");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("users/" + userId)
                .then();

        RestAssured
                .given()
                .spec(getRequestSpecification())
                .when()
                .delete("users/" + userId)
                .then()
                .spec(getResponseSpecification());

        int deleteStatusCode = response.extract().response().jsonPath().getInt("code");
        softAssert.assertEquals(deleteStatusCode, 204);

        RestAssured
                .when()
                .get("users/" + userId)
                .then();

        String actual = response.extract().response().jsonPath().getString("data.message");
        int statusCode = response.extract().response().jsonPath().getInt("code");
        softAssert.assertEquals(actual, "Resource not found");
        softAssert.assertEquals(statusCode, HttpStatus.SC_NOT_FOUND, "User is not deleted");
    }

    private RequestSpecification getRequestSpecification() {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        return specBuilder
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization",
                        "Bearer e552dd0f1328c320f1f99ceb3663aa4b3f2b58da7222385921e24c8a2c97f3d8")
                .build();
    }

    private ResponseSpecification getResponseSpecification() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        return builder
                .expectContentType(ContentType.JSON)
                .build();
    }
}