package thesis.domain;

import java.util.List;

public class Supervisor {
  private final int id;
  private final String name;
  private final String mail;
  private final List<SpecialField> specialFields;
  private final List<File> files;
  private final List<Link> links;
  private final List<Topic> topics;

  public Supervisor(int id, String name, String mail, List<SpecialField> specialFields,
                    List<File> files, List<Link> links, List<Topic> topics) {
    this.id = id;
    this.name = name;
    this.mail = mail;
    this.specialFields = specialFields;
    this.files = files;
    this.links = links;
    this.topics = topics;
  }

  public List<SpecialField> getSpecialFields() {
    return specialFields;
  }

  public int getId() {
    return id;
  }


  public List<File> getFiles() {
    return files;
  }


  public List<Link> getLinks() {
    return links;
  }


  public List<Topic> getTopics() {
    return topics;
  }


  public String getName() {
    return name;
  }


  public String getMail() {
    return mail;
  }

}
