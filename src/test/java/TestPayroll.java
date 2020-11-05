import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.hamcrest.*;
import org.junit.*;

public class TestPayroll {
    private int empId;
    @Before
    public void setup() throws Exception{
        RestAssured.baseURI="http://localhost";
        RestAssured.port=4000;
        empId=1;
    }

    public Response getEmployeeList(){
        Response response=RestAssured.get("/employees/list");
        System.out.println(response.getBody());
        return response;
    }
//    @Test
//    public void OnCallinggetEmployeeList_ShouldReturnEmployeeList(){
//        Response employeeList=getEmployeeList();
//        System.out.println("string is "+employeeList.asString());
//        employeeList.then().body("id", Matchers.hasItems(1,6));
//        employeeList.then().body("name", Matchers.hasItem("Prajwal"));
//    }

    @Test
    public void CheckPostMethod() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\":\"xyz\",\"salary\":\"8000\"}")
                .when().post("/employees/create")
                .then()
                .body("id",Matchers.any(Integer.class));
    }

//    @Test
//    public void CheckInsertMethod(){
//        RestAssured.given()
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .body("{\"name\":\"abc\",\"salary\":\"700000\"}")
//                .when()
//                .put("/employees/update/"+empId)
//                .then()
//                .body("id",Matchers.any(Integer.class))
//                .body("name",Matchers.is("abc"))
//                .body("salary",Matchers.is("700000"));
//
//    }

}
