import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void tag_instantiatesCorrectly_true() {
    Tag newTag = new Tag("Italian");
    assertTrue(newTag instanceof Tag);
  }

}
