import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.hamcrest.*;
import org.junit.*;

import java.util.*;

public class TestPayroll {
    private int empId;
    @Before
    public void setup() throws Exception{
        RestAssured.baseURI="http://localhost";
        RestAssured.port=4001;
        empId=1;
    }

    public Response getEmployeeList(){
        Response response=RestAssured.get("/employees/list");
        System.out.println(response.getBody());
        return response;
    }
    @Test
    public void OnCallinggetEmployeeList_ShouldReturnEmployeeList(){
        Response employeeList=getEmployeeList();
        System.out.println("string is "+employeeList.asString());
        employeeList.then().body("id", Matchers.hasItems(1,6));
        employeeList.then().body("name", Matchers.hasItem("Varun"));
    }

    @Test
    public void test1_CheckPostMethod() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\":\"xyz\",\"salary\":\"8000\"}")
                .when().post("/employees/create")
                .then()
                .body("id",Matchers.any(Integer.class));
    }


    @Test
    public void test2_CheckMultiplePostMethodThreads() {
        String[] name={"Hari","Giri","Varun"};
        String[] salary={"1000","2000","3000"};

        for(int i=0;i<3;i++)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name[i]);
            map.put("salary",salary[i]);
            Runnable task = () -> {
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(map)
                        .when().post("/employees/create");
            };
            Thread t=new Thread(task);
            t.start();
            try {
                t.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
