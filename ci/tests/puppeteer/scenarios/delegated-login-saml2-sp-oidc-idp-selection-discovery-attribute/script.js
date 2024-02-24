const puppeteer = require("puppeteer");
const cas = require("../../cas.js");
const assert = require("assert");

(async () => {
    const browser = await puppeteer.launch(cas.browserOptions());
    const page = await cas.newPage(browser);
    await startWithCasSp(page);
    await browser.close();
})();

async function startWithCasSp(page) {
    const service = "https://localhost:9859/anything/cas";
    await cas.gotoLogout(page);
    await cas.waitForTimeout(page);
    await cas.gotoLogin(page, service);
    await cas.assertVisibility(page, "#selectProviderButton");
    await cas.submitForm(page, "#providerDiscoveryForm");
    await cas.waitForTimeout(page);
    await cas.type(page, "#username", "casuser");
    
    await cas.submitForm(page, "#discoverySelectionForm");
    await cas.waitForTimeout(page);
    await cas.loginWith(page);
    await cas.waitForTimeout(page);
    const ticket = await cas.assertTicketParameter(page);
    const body = await cas.doRequest(`https://localhost:8443/cas/p3/serviceValidate?service=${service}&ticket=${ticket}`);
    await cas.log(body);
    assert(body.includes("<cas:user>casuser</cas:user>"));
}
