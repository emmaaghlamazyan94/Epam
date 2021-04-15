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
    User user;
    private int userID;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.basePath = "public-api/";
    }

    @Test
    public void postUser() {
        ValidatableResponse response = post();

        String name = getUserInfo(response, "data.name");
        String email = getUserInfo(response, "data.email");
        String gender = getUserInfo(response, "data.gender");
        int statusCode = getUserCode(response, "code");
        String status = getUserInfo(response, "data.status");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(name, user.getName(), "Posted user name is not the same as expected");
        softAssert.assertEquals(email, user.getEmail(), "Posted email is not the same as expected");
        softAssert.assertEquals(gender, user.getGender(), "Gender type is not as expected");
        softAssert.assertEquals(status, user.getStatus(), "Status is not as " + user.getStatus());
        softAssert.assertEquals(statusCode, HttpStatus.SC_CREATED, "User is not created");
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void getUser() {
        post();
        ValidatableResponse response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("users/" + userID)
                .then()
                .spec(getResponseSpecification());

        int statusCode = getUserCode(response, "code");
        Assert.assertEquals(statusCode, HttpStatus.SC_OK, "User is not found");
    }

    @Test(priority = 2)
    public void deleteUser() {
        post();
        ValidatableResponse response = RestAssured
                .given()
                .spec(getRequestSpecification())
                .when()
                .delete("users/" + userID)
                .then()
                .spec(getResponseSpecification());

        SoftAssert softAssert = new SoftAssert();
        int deleteStatusCode = getUserCode(response, "code");
        softAssert.assertEquals(deleteStatusCode, 204);

        response = RestAssured
                .when()
                .get("users/" + userID)
                .then();

        String actual = getUserInfo(response, "data.message");
        int statusCode = getUserCode(response, "code");
        softAssert.assertEquals(actual, "Resource not found");
        softAssert.assertEquals(statusCode, HttpStatus.SC_NOT_FOUND, "User is not deleted");
        softAssert.assertAll();
    }

    private String getUserInfo(ValidatableResponse response, String path) {
        return response.extract().response().jsonPath().getString(path);
    }

    private int getUserCode(ValidatableResponse response, String path) {
        return response.extract().response().jsonPath().getInt(path);
    }

    private ValidatableResponse post() {
        user = User.getRandomUser();
        ValidatableResponse response = RestAssured
                .given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .post("users")
                .then()
                .spec(getResponseSpecification());
        userID = getUserCode(response, "data.id");
        return response;
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