# F Line Explorer

An offline Android WebView application that renders a surreal, interactive journey on the NYC Subway F Line.

## Architecture

This application consists of a lightweight Android wrapper (written in Kotlin) that hosts a fullscreen WebView. The core application logic, visuals, and UI are entirely contained within a monolithic HTML bundle located at `android/app/src/main/assets/fline.html`.

- **Android App:** Standard Android project in the `android/` directory. Builds using standard Gradle commands.
- **WebView Bundle:** `fline.html` is a standalone, bundled HTML file containing all assets, fonts, JavaScript logic, and CSS styles.

## Development

Because the core application logic is pre-bundled into `fline.html`, development requires modifying this file directly. 

### Modifying the Web App (`fline.html`)

1. The React Component defining the app's behavior (the `DCLogic` class) is embedded inside `fline.html` as a string within a `<script type="text/x-dc" data-dc-script="" data-props="...">` tag.
2. Because it is embedded within an HTML tag string, you must be extremely careful with character escaping. For example:
   - Quotes in HTML attributes inside the script are escaped: `max=\"3600\"`
   - JSON quotes inside `data-props` are HTML encoded: `&quot;max&quot;:3600`
3. We have included an `unpack.py` script that extracts the various components of the bundle (including the `DCLogic` script, base64 assets, and the template) into an `unpacked_bundle/` directory. **This is highly recommended for reading and understanding the code before making edits.** 
4. However, after unpacking and analyzing the code, you must make your targeted edits directly in `android/app/src/main/assets/fline.html`. **Do not attempt to repack `unpacked_bundle`**, as there is no build pipeline to do so. Use targeted replacements against `fline.html`.

### Building and Deploying

To build the Android app and install it on a connected device via adb:

```bash
cd android
./gradlew installDebug
```

## AI Agent Notes

If you are an AI assistant helping with future development:
- **Do not use standard regex tools (`grep_search`, `grep`) to search the bundle directly.** `fline.html` contains extremely long lines (up to 1MB of text on a single line). Tools like `grep_search` may silently fail, hang, or truncate results. Use short Python scripts to search or count occurrences.
- To understand the React logic, run `python3 unpack.py` and read the extracted files in `unpacked_bundle/`, particularly `template.html` and the extracted JavaScript files.
- When making edits to `fline.html`, use the `multi_replace_file_content` tool or write a dedicated Python script to replace text safely. Verify your edits by counting substring occurrences before and after. 
- The codebase relies heavily on the NYC sunrise/sunset calculation to control the "Auto" sky. 
- Always ensure `git status` is clean before making risky replacements in `fline.html`, as it's very easy to break the bundle with a stray character.
