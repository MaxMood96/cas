const puppeteer = require("puppeteer");
const cas = require("../../cas.js");
const assert = require("assert");

(async () => {
    const browser = await puppeteer.launch(cas.browserOptions());
    const page = await cas.newPage(browser);
    const service = "https://localhost:9859/anything/adaptive";
    await cas.gotoLogin(page, service);
    await cas.waitForTimeout(page);
    await cas.loginWith(page);
    await cas.waitForTimeout(page);
    await cas.assertInnerTextContains(page, "#loginErrorsPanel p", "authentication attempt is determined to be risky");

    const body = await cas.extractFromEmailMessage(browser);
    await cas.log(`Email message body is: ${body}`);
    assert(body.includes("casuser with score 1.00"));
    await browser.close();
})();
