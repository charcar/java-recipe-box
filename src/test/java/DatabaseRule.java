import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
  }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTagsQuery = "DELETE FROM tags *;";
      String deleteIngredientsQuery = "DELETE FROM ingredients *;";
      con.createQuery(deleteTagsQuery).executeUpdate();
      con.createQuery(deleteIngredientsQuery).executeUpdate();
    }
  }
}
