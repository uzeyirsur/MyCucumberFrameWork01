package stepDefs;

import io.cucumber.java.en.Given;
import org.junit.Assert;
import utilities.DBUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Question01StepDef {
    @Given("customer id {int} first name {string} last name {string} city {string} country {string} total amount {string} should match with the DB record")
    public void  customer_id_first_name_last_name_city_country_total_amount_should_match_with_the_DB_record(int customer_id, String firstName,String lastName, String city, String country, String totalAmountStr){
        String query = "SELECT cu.customer_id,first_name,last_name,city,country,SUM(amount) \n" +
                "FROM customer cu\n" +
                "INNER JOIN address a\n" +
                "ON a.address_id = cu.address_id\n" +
                "INNER JOIN city ci\n" +
                "ON ci.city_id = a.city_id\n" +
                "INNER JOIN country co\n" +
                "ON co.country_id = ci.country_id\n" +
                "INNER JOIN payment p\n" +
                "ON cu.customer_id = p. customer_id\n" +
                "GROUP BY cu.customer_id, cu.first_name, cu.last_name, ci.city, co.country\n" +
                "ORDER BY sum DESC\n" +
                "LIMIT 1;";
        List<Map<String,Object>> resultMap = DBUtils.getQueryResultMap(query);
        Assert.assertEquals(resultMap.get(0).get("customer_id"),customer_id);
        Assert.assertEquals(resultMap.get(0).get("first_name"),firstName);
        Assert.assertEquals(resultMap.get(0).get("last_name"),lastName);
        Assert.assertEquals(resultMap.get(0).get("city"),city);
        Assert.assertEquals(resultMap.get(0).get("country"),country);

        BigDecimal totalAmount = new BigDecimal(totalAmountStr);
        Assert.assertEquals(resultMap.get(0).get("sum"),totalAmount);


    }
}

