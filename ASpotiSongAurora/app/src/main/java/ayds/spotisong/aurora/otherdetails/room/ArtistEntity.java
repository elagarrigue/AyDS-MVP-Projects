package ayds.spotisong.aurora.otherdetails.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ArtistEntity {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String artist;
  private String bio;
  private String image_url;
  private int source;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }

  public int getSource() {
    return source;
  }

  public void setSource(int source) {
    this.source = source;
  }
}
