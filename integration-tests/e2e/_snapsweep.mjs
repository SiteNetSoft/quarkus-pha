import { chromium } from 'playwright';
import fs from 'node:fs';
const [oldCssPath, newCssPath, pagesPath, baseOut, newOut] = process.argv.slice(2);
const OLD = fs.readFileSync(oldCssPath);
const NEW = fs.readFileSync(newCssPath);
const PAGES = JSON.parse(fs.readFileSync(pagesPath, 'utf8'));
const SNAP = () => {
  const els = document.querySelectorAll('*');
  const res = [];
  els.forEach((el, i) => {
    const cs = getComputedStyle(el);
    let acc = '';
    for (let j = 0; j < cs.length; j++) {
      const prop = cs[j];
      if (prop.startsWith('--')) continue;
      acc += prop + ':' + cs.getPropertyValue(prop) + ';';
    }
    let h = 0;
    for (let k = 0; k < acc.length; k++) { h = (h * 31 + acc.charCodeAt(k)) | 0; }
    res.push(el.tagName + '#' + i + '=' + h);
  });
  return res;
};
async function run(cssBytes, label) {
  const out = {};
  const b = await chromium.launch();
  const ctx = await b.newContext({ viewport: { width: 1280, height: 900 } });
  await ctx.route('**/patternfly-docs-bundle.min.css*', (r) =>
    r.fulfill({ status: 200, contentType: 'text/css', body: cssBytes }));
  let n = 0;
  for (const p of PAGES) {
    const pg = await ctx.newPage();
    try {
      await pg.goto('http://localhost:9090' + p, { waitUntil: 'networkidle', timeout: 20000 });
    } catch {
      await pg.goto('http://localhost:9090' + p, { waitUntil: 'load', timeout: 20000 }).catch(() => {});
    }
    await pg.addStyleTag({ content: '*, *::before, *::after { animation: none !important; transition: none !important; }' }).catch(() => {});
    await pg.waitForTimeout(400);
    await pg.evaluate(() => document.getAnimations().forEach(a => a.cancel())).catch(() => {});
    out[p] = await pg.evaluate(SNAP);
    await pg.close();
    n++;
    if (n % 20 === 0) console.log(label, n + '/' + PAGES.length);
  }
  await b.close();
  return out;
}
const base = await run(OLD, 'base');
fs.writeFileSync(baseOut, JSON.stringify(base));
console.log('base done', Object.keys(base).length, 'pages');
const neu = await run(NEW, 'cand');
fs.writeFileSync(newOut, JSON.stringify(neu));
console.log('cand done', Object.keys(neu).length, 'pages');
