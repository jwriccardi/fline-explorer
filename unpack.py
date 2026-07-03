import json
import base64
import gzip
import os
import re

html_path = 'android/app/src/main/assets/fline.html'
out_dir = 'unpacked_bundle'

if not os.path.exists(out_dir):
    os.makedirs(out_dir)

with open(html_path, 'r', encoding='utf-8') as f:
    content = f.read()

manifest_match = re.search(r'<script type="__bundler/manifest">(.*?)</script>', content, re.DOTALL)
template_match = re.search(r'<script type="__bundler/template">(.*?)</script>', content, re.DOTALL)

with open(os.path.join(out_dir, 'template.html'), 'w', encoding='utf-8') as f:
    f.write(template_match.group(1) if template_match else "")

if manifest_match:
    manifest = json.loads(manifest_match.group(1))

    for uuid, entry in manifest.items():
        data = base64.b64decode(entry['data'])
        if entry.get('compressed'):
            data = gzip.decompress(data)
        
        ext = 'js'
        if entry['mime'] == 'text/css':
            ext = 'css'
        elif entry['mime'] == 'image/png':
            ext = 'png'
        elif entry['mime'] == 'image/jpeg':
            ext = 'jpg'
        elif entry['mime'] == 'image/svg+xml':
            ext = 'svg'
        elif entry['mime'] == 'application/json':
            ext = 'json'
            
        out_path = os.path.join(out_dir, f"{uuid}.{ext}")
        with open(out_path, 'wb') as f:
            f.write(data)
        
        print(f"Unpacked {out_path} ({entry['mime']})")
