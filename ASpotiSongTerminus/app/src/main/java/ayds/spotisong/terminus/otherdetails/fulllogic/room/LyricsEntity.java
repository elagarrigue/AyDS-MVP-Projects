package ayds.spotisong.terminus.otherdetails.fulllogic.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LyricsEntity {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String song;
  private String text;
  private String image_url;
  private int source;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSong() {
    return song;
  }

  public void setSong(String song) {
    this.song = song;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
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
