const puppeteer = require("puppeteer");
const cas = require("../../cas.js");
const assert = require("assert");

(async () => {
    const browser = await puppeteer.launch(cas.browserOptions());
    const page = await cas.newPage(browser);
    const response = await cas.goto(page, "http://localhost:8500/ui/dc1/services/cas/instances");
    await cas.waitForTimeout(page);

    assert(response.ok());
    await cas.click(page, "div.header a");
    await page.waitForResponse((response) => response.status() === 200);
    await cas.waitForTimeout(page);
    await browser.close();
})();
