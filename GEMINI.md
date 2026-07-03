# Gemini Instructions for F Line Explorer

You are working on the F Line Explorer, an offline Android WebView application that renders a surreal, interactive F Line journey.

## Critical Architecture Constraints

This codebase is unique. **Do not attempt to make changes assuming a standard React build pipeline.**

The entire application logic is contained within a monolithic, pre-bundled HTML file located at `android/app/src/main/assets/fline.html`. 

- There is **no node environment**, no `package.json`, and no Webpack/Vite build pipeline.
- You must make all web app edits directly to `android/app/src/main/assets/fline.html`.

## Inspecting the Codebase

1. Run the `unpack.py` script (`python3 unpack.py`). 
2. This will dump the unbundled javascript and `template.html` into `unpacked_bundle/`.
3. **Read the files in `unpacked_bundle/` to understand the logic.** The React logic (`DCLogic` class) is mostly inside `unpacked_bundle/template.html`.

## Editing the Codebase

1. After you've identified what needs to change by reading the `unpacked_bundle/` code, **you must make your targeted edits directly to the monolithic `fline.html` file**.
2. **WARNING: Line length.** `fline.html` has extremely long lines (up to 1MB of text on a single line).
3. **DO NOT** use standard regex tools like `grep_search` or `grep` on `fline.html`. They may silently fail, hang, or truncate results. Use short Python scripts to search or count occurrences.
4. **DO NOT** use sed.
5. **DO** use the `multi_replace_file_content` tool or write a dedicated Python script to replace text safely.
6. **BE CAREFUL WITH ESCAPING.** The entire template is embedded as an HTML string. Quotes are escaped (`max=\"3600\"`) and JSON props are HTML encoded (`&quot;max&quot;:3600`).
7. **Verify your edits** by writing a Python script to count substring occurrences before and after making your change.

## Building and Deploying

To build the Android app and install it on a connected device via `adb`:

```bash
cd android
./gradlew installDebug
```

Always ensure `git status` is clean before making risky replacements in `fline.html`, as it's very easy to break the bundle with a stray character!
