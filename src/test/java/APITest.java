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
    private final User user = User.getRandomUser();
    private String userID;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "public-api/";
    }

    @Test
    public void postUser() {
        SoftAssert softAssert = new SoftAssert();
        ValidatableResponse response = RestAssured
                .given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .post("users")
                .then()
                .spec(getResponseSpecification());

        String name = getUserInfo(response, "data.name");
        String email = getUserInfo(response, "data.email");
        String gender = getUserInfo(response, "data.gender");
        String statusCode = getUserInfo(response, "code");

        softAssert.assertEquals(user.getName(), name, "Posted user name is not the same as expected");
        softAssert.assertEquals(user.getEmail(), email, "Posted email is not the same as expected");
        softAssert.assertEquals(user.getGender(), gender, "Gender type is not as expected");
        softAssert.assertEquals(Integer.parseInt(statusCode), HttpStatus.SC_CREATED, "User is not created");
        softAssert.assertAll();

        userID = getUserInfo(response,"data.id");
    }

    @Test(priority = 1)
    public void getUser() {
        ValidatableResponse response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("users/" + userID)
                .then()
                .spec(getResponseSpecification());

        String statusCode = getUserInfo(response, "code");
        Assert.assertEquals(Integer.parseInt(statusCode), HttpStatus.SC_OK, "User is not found");
    }

    @Test(priority = 2)
    public void deleteUser() {
        SoftAssert softAssert = new SoftAssert();
        ValidatableResponse response = RestAssured
                .given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .post("users")
                .then()
                .log()
                .ifError();

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("users/" + getUserInfo(response, "data.id"))
                .then()
                .log()
                .ifError();

        softAssert.assertEquals(getUserInfo(response, "code"), HttpStatus.SC_OK,
                "No user found");

        RestAssured
                .given()
                .spec(getRequestSpecification())
                .when()
                .delete("users/" + getUserInfo(response, "data.id"))
                .then()
                .spec(getResponseSpecification());

        String deleteStatusCode = getUserInfo(response, "code");
        softAssert.assertEquals(deleteStatusCode, 204);

        RestAssured
                .when()
                .get("users/" + getUserInfo(response, "data.id"))
                .then();

        String actual = getUserInfo(response, "data.message");
        String statusCode = getUserInfo(response, "code");
        softAssert.assertEquals(actual, "Resource not found");
        softAssert.assertEquals(statusCode, HttpStatus.SC_NOT_FOUND, "User is not deleted");
    }

    private String getUserInfo(ValidatableResponse response, String path) {
        return response.extract().response().jsonPath().getString(path);
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