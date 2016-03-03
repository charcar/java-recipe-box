import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void tag_instantiatesCorrectly_true() {
    Tag newTag = new Tag("Italian");
    assertTrue(newTag instanceof Tag);
  }

  @Test
  public void getName_returnsName_string() {
    Tag newTag = new Tag("Italian");
    assertEquals("Italian", newTag.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfTagsAreTheSame() {
    Tag newTag = new Tag("Breakfast");
    Tag newTag2 = new Tag("Breakfast");
    assertTrue(newTag.equals(newTag2));
  }

  @Test
  public void save_savesTagToDatabase_true() {
    Tag newTag = new Tag("Lunch");
    newTag.save();
    assertTrue(newTag.all().contains(newTag));
  }

  @Test
  public void getId_returnsId() {
    Tag newTag = new Tag("lunch");
    newTag.save();
    Tag savedTag = Tag.find(newTag.getId());
    assertTrue(newTag.getId() == savedTag.getId());
  }

  @Test
  public void find_returnsTagFromDatabase() {
    Tag newTag = new Tag("Italian");
    newTag.save();
    assertEquals(newTag, Tag.find(newTag.getId()));
  }

  @Test
  public void update_updatesTagProperties() {
    Tag newTag = new Tag("Bulgarian");
    newTag.save();
    newTag.update("Romainian");
    assertEquals("Romainian", newTag.getName());
  }

  @Test
  public void delete_removesTagFromDatabase_true() {
    Tag newTag = new Tag("Latvian");
    newTag.save();
    assertTrue(Tag.all().contains(newTag));
    newTag.delete();
    assertFalse(Tag.all().contains(newTag));
  }

}
