package ayds.spotisong.aurora.song.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ayds.spotisong.aurora.R;
import ayds.spotisong.aurora.otherdetails.OtherInfoActivity;
import ayds.spotisong.aurora.song.model.Song;
import ayds.spotisong.aurora.song.presenter.SearchSongPresenter;

import static ayds.spotisong.aurora.otherdetails.OtherInfoActivity.EXTRA_ALBUM;
import static ayds.spotisong.aurora.otherdetails.OtherInfoActivity.EXTRA_ARTIST;
import static ayds.spotisong.aurora.otherdetails.OtherInfoActivity.EXTRA_NAME;
import static ayds.spotisong.aurora.otherdetails.OtherInfoActivity.EXTRA_URL;

public class SearchSongActivity extends AppCompatActivity implements SearchSongView {

  private EditText queryTextView;
  private Button goButton;
  private Button openInSpotifyButton;
  private Button viewMoreInfoButton;
  private TextView songDescriptionTextView;
  private View buttonsLayout;

  private SearchSongPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.search_song_main);

    SearchSongViewModule.getInstance().setSearchSongView(this, getApplicationContext());

    bindViews();

    initListeners();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public String getQuery() {
    return queryTextView.getText().toString();
  }

  @Override public void setSongDescription(String description) {
    runOnUiThread(() -> songDescriptionTextView.setText(description));
  }

  @Override public void enableActionButtons() {
    runOnUiThread(() -> buttonsLayout.setVisibility(View.VISIBLE));
  }

  @Override public void disableActionButtons() {
    runOnUiThread(() -> buttonsLayout.setVisibility(View.VISIBLE));
  }

  @Override public void setSearchSongPresenter(SearchSongPresenter presenter) {
    this.presenter = presenter;
  }

  @Override public void openExternalUrl(String url) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(url));
    startActivity(intent);
  }

  @Override public void goToOtherDetails(Song song) {
    Intent intent = new Intent(this, OtherInfoActivity.class);
    intent.putExtra(EXTRA_NAME, song.getSongName());
    intent.putExtra(EXTRA_ARTIST, song.getArtistName());
    intent.putExtra(EXTRA_ALBUM, song.getAlbumName());
    intent.putExtra(EXTRA_URL, song.getUrl());
    startActivity(intent);
  }

  private void bindViews() {
    queryTextView = findViewById(R.id.queryTextView);
    goButton = findViewById(R.id.goButton);
    openInSpotifyButton = findViewById(R.id.openInSpotifyButton);
    viewMoreInfoButton = findViewById(R.id.viewMoreInfoButton);
    songDescriptionTextView = findViewById(R.id.songDescriptionTextView);
    buttonsLayout = findViewById(R.id.buttonsLayout);
  }

  private void initListeners() {
    goButton.setOnClickListener(e -> presenter.onSongSearch());

    openInSpotifyButton.setOnClickListener(e -> presenter.onOpenUrl());

    viewMoreInfoButton.setOnClickListener(e -> presenter.onOtherInfo());
  }
}
