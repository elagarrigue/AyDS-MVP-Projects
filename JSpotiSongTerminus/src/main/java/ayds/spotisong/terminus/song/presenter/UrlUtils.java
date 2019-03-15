package ayds.spotisong.terminus.song.presenter;

import java.awt.*;
import java.net.URI;

class UrlUtils {

  static void openExternalUrl(String url) throws Exception {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      Desktop.getDesktop().browse(new URI(url));
    }
  }
}
