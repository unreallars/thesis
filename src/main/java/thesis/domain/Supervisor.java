package thesis.domain;

import java.util.List;

public class Supervisor {
  private final Integer id;
  private final String username;
  private final String mail;
  private final List<SpecialField> specialFields;
  private final List<Link> links;
  private final List<Topic> topics;

  public Supervisor(int id, String username, String mail, List<SpecialField> specialFields,
                    List<Link> links, List<Topic> topics) {
    this.id = id;
    this.username = username;
    this.mail = mail;
    this.specialFields = specialFields;
    this.links = links;
    this.topics = topics;
  }

  public List<SpecialField> getSpecialFields() {
    return specialFields;
  }

  public int getId() {
    return id;
  }


  public List<Link> getLinks() {
    return links;
  }


  public List<Topic> getTopics() {
    return topics;
  }


  public String getUsername() {
    return username;
  }


  public String getMail() {
    return mail;
  }

}
