const puppeteer = require("puppeteer");
const path = require("path");
const cas = require("../../cas.js");
const assert = require("assert");

(async () => {
    const body = {"configuredLevel": "INFO"};
    await ["org.springframework.webflow"].forEach((p) =>
        cas.doRequest(`https://localhost:8443/cas/actuator/loggers/${p}`, "POST",
            {"Content-Type": "application/json"}, 204, JSON.stringify(body, undefined, 2)));

    const browser = await puppeteer.launch(cas.browserOptions());
    const page = await cas.newPage(browser);
    const response = await cas.goto(page, "https://localhost:8443/cas/idp/metadata");

    assert(response.ok());
    
    try {
        await cas.gotoLogin(page);
        await cas.waitForTimeout(page);

        await cas.updateDuoSecurityUserStatus("duocode");
        await cas.goto(page, "http://localhost:9443/simplesaml/module.php/core/authenticate.php?as=default-sp");
        await cas.waitForTimeout(page, 5000);
        await cas.screenshot(page);
        await cas.loginWith(page, "duocode", "Mellon");
        await cas.waitForTimeout(page, 5000);
        await cas.screenshot(page);

        await cas.loginDuoSecurityBypassCode(page,"duocode");
        await cas.waitForTimeout(page, 5000);
        await cas.screenshot(page);

        await cas.waitForTimeout(page);
        await cas.assertInnerTextContains(page, "#content p", "status page of SimpleSAMLphp");
        await cas.assertVisibility(page, "#table_with_attributes");
        const authData = JSON.parse(await cas.innerHTML(page, "details pre"));
        console.dir(authData, {depth: null, colors: true});
        await cas.removeDirectoryOrFile(path.join(__dirname, "/saml-md"));
    } finally {
        await cas.screenshot(page);
    }
    await browser.close();

})();
