---
layout: default
title: CSS & JavaScript - User Interface Customization - CAS
category: User Interface
---

{% include variables.html %}

# CSS

The default styles are all contained in one single file located in `src/main/resources/static/css/cas.css`. This 
location is set in `cas-theme-default.properties`. CAS by default uses [Material.io](https://material.io/) library 
and design specification as a base for its user experience.

If you would like to create your own `css/my.css file`, for example, you will need to update `cas.standard.css.file` key in that file.

```bash
cas.standard.css.file=/css/cas.css
cas.standard.js.file=/js/cas.js
```
   
Please note that,

- Multiple CSS or JavaScript files can be defined and separated by a comma.
- The order of the files is important. The files are loaded in the order they are defined.
- CAS will always load a `/css/custom.css` and `/js/custom.js` by default to allow for overrides and customizations.

## Responsive Design

CSS media queries bring responsive design features to CAS which would allow the adopter to focus 
on one theme for all appropriate devices and platforms. These queries are defined in the 
same `cas.css` file. They follow the Twitter Bootstrap breakpoints and grid.

# JavaScript

If you need to add some JavaScript, feel free to append to `src/main/resources/static/js/cas.js`.

You can also create your own custom JavaScript file and include it in `scripts.html` like so:

```html
<script type="text/javascript" src="/js/my.js"></script>
```

If you are developing themes per service, each theme also has the ability 
to specify a custom `cas.js` file under the `cas.standard.js.file` setting.

Most importantly, the following JavaScript libraries are utilized by CAS automatically:

* [JQuery](https://jquery.com/)
* [Bootstrap for grid / flex utilities](https://getbootstrap.com/docs/4.5/getting-started/contents/#css-files)
* [Material.io](https://material.io/)

## Checking CAPSLOCK

CAS will display a brief warning when the CAPSLOCK key is turned on during the typing 
of the credential password. This check is enforced by the `cas.js` file.

## Browser Cookie Support

For CAS to honor a single sign-on session, the browser MUST support and accept cookies. CAS will notify the
user if the browser has turned off its support for cookies. This behavior is controlled via the `cas.js` file.

## Preserving Anchor Fragments

Anchors/fragments may be lost across redirects as the server-side handler of the form post 
ignores the client-side anchor, unless appended to the form POST url. This is needed if you 
want a CAS-authenticated application to be able to use anchors/fragments when bookmarking. CAS 
is configured by default to preserve anchor fragments where and when specified. There is 
nothing further for you to do.

## WebJARs for JavaScript/CSS Libraries

The CAS application packages third party static resources inside the CAS webapp rather 
than referencing CDN links so that CAS may be deployed on 
networks with limited internet access.

The Third party static resources are packaged in "WebJAR" jar files and served up via the servlet `3.0` feature 
that merges any folders under `META-INF/resources` in web application jars with the application's web root.

### Building WebJARs

You can search for webjars at https://webjars.org. There are three flavors of WebJARs that you 
can read about but the NPM and Bower types can be created automatically for any version 
(if they don't already exist) as long as there exists an NPM or Bower package for the 
web resources you want to use. Click the "Add a webjar" button and follow 
the instructions. If customizing the UI in an overlay, the deployer can add webjars as 
dependencies to their overlay project and reference the URLs of the resource directly 
in an html file.
