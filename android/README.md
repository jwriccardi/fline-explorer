# F Line Tribute — tablet app

A one-Activity WebView shell around `app/src/main/assets/fline.html`
(the fully self-contained offline bundle of the piece — React, all 24
station scenes, and both typefaces embedded; zero network use). The
manifest requests **no internet permission**.

## Run it

1. Open this `android/` folder in Android Studio (File ▸ Open).
2. If Studio complains the Gradle wrapper jar is missing (only the
   text of the wrapper config ships here), either accept Studio's
   offer to fix it, or run `gradle wrapper` once, or point
   Settings ▸ Build Tools ▸ Gradle at a local/bundled distribution.
3. Plug in the tablet (developer mode + USB debugging on), pick it in
   the device dropdown, press **Run**.

## Kiosk polish

- The app already keeps the screen awake and hides the system bars
  (swipe from an edge shows them briefly).
- To stop visitors leaving the app, use Android's screen pinning:
  Settings ▸ Security ▸ Pin app, then pin it from Recents.
- Landscape is locked in the manifest (`sensorLandscape`).

## Updating the artwork

Replace `app/src/main/assets/fline.html` with a newer bundle and
press Run again. To preview with the painted mockup bezel instead of
full-bleed, load `file:///android_asset/fline.html?device=0`.
