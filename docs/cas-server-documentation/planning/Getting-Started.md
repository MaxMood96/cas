---
layout: default
title: CAS - Getting Started Guide
category: Planning
---

{% include variables.html %}

# Getting Started

We want to start by saying thank you for using CAS.

This document provides a high-level guide on how to get started with a CAS server deployment.
The sole focus of the guide is to describe the process
that must be followed and adopted by CAS deployers in order to arrive at a successful
and sustainable architecture and deployment.
       
## Before You Begin

It is very important that you start with [this document](/cas/developer/Contributor-Guidelines.html) first. This will help you understand how
the CAS project runs and operates. You will learn how to get involved with the project, how to ask for help, 
how changes are managed and released, what the project expects from you and what you can expect from CAS itself. 

## Ask for Help

A CAS deployment is often seen as mission critical. Its deployment success, health status and general upkeep is vital to an organization's
daily routine and business. Furthermore, as an identity provider that operates in the domain of access management, it plays an important
and sensitive role in the overall security architecture of one's deployment. Therefore, you may need *extra expert* help
from specialists and experts that know the software in and out and can help you with gotchas, deployment best practices and practical upgrade guidelines.
Free support only works up to a certain degree, and generally is best for personal use and/or hobbyists.

For best results, [ask for help](/cas/Support.html).

## Collect Use Cases

It is very important that you document, catalog and analyze your desired use cases and requirements prior to the deployment.
Once you have a few ideas, please discuss and share those with the [CAS community](/cas/Support.html)
to learn about common trends, practices and patterns
that may already have solved the same issues you face today. 

<div class="alert alert-warning">:warning: <strong>KISS</strong><p>In general, avoid designing and/or adopting
use cases and workflows that heavily alter the CAS internal components, induce a heavy burden on your management
and maintenance of the configuration or re-invent the CAS software and its supported protocols. All options 
add to maintenance cost and headache.</p></div>

## Study Architecture

Understand what CAS is and can do. This will help you develop a foundation to realize which of your use cases 
and requirements may already be possible with CAS. Take a look at the fundamentals 
of the [CAS architecture](Architecture.html)
to see what options and choices might be available for deployments and application integrations.

Likewise, it's equally important that you study the list of 
CAS [supported protocols and specifications](../protocol/Protocol-Overview.html).

## Review Blog

From time to time, blog posts appears on the [Apereo Blog](https://apereo.github.io/)
that might become useful as you are thinking about requirements and evaluating features.
It is generally recommended that you follow the blog and keep up with project news and 
announcements as much as possible, and do not shy away from writing and contributing your own blog posts, 
experiences and updates throughout your CAS deployment.

## Prepare Environment

Study the [installation requirements](Installation-Requirements.html) for the deployment environment.

## Deploy CAS

It is recommended to build and deploy CAS locally using the [WAR Overlay method](../installation/WAR-Overlay-Installation.html). 
This approach does not require the adopter to *explicitly* download any version of CAS, but 
rather utilizes the overlay mechanism to combine CAS original artifacts and local 
customizations to further ease future upgrades and maintenance.

**Note**: Do NOT clone or download the CAS codebase directly. That is ONLY required if you
wish to contribute to the development of the project. 

It is **VERY IMPORTANT** that you try to get a functional baseline working before doing anything else.
Avoid making ad-hoc changes right away to customize the deployment. Stick with the CAS-provided defaults
and settings and make changes **one step at a time**. Keep track of process and applied changes
in source control and tag changes as you make progress. 

## Customize

This is where use cases get mapped to CAS features. Browse the documentation to find the closest match and apply.
Again, it is important that you stick with the CAS baseline as much as possible:

- Avoid making ad-hoc changes to the software internals.
- Avoid making manual changes to core configuration components such as Spring and Spring Webflow.
- Avoid making one-off bug fixes to the deployment, should you encounter an issue.

As noted previously, all such strategies lead to headache and cost.
 
Instead, try to warm up to the following suggestions:

- Bug fixes and small improvements belong to the core CAS software. Not your deployment. Make every attempt to report issues, 
contribute fixes and patches and work with the CAS community to solve issues once and for all.
- Certain number of internal CAS components are made difficult to augment and modify. In most cases, this approach is
done on purpose to steer you away from dangerous and needlessly complicated changes. If you come across a need
and have a feature or use case in mind whose configuration and implementation requires modifications to the core internals
of the software, discuss that with the CAS community and attempt to build the enhancement directly into the CAS software,
rather than treating it as a snowflake.

To summarize, only make changes to the deployment configuration if they are truly and completely specific to your needs.
Otherwise, try to generalize and contribute back to keep maintenance costs down. 
Repeatedly, failure to comply with this strategy
will likely lead to disastrous results in the long run.

## Troubleshooting

The [troubleshooting guide](../installation/Troubleshooting-Guide.html) might have some answers 
for issues you may have run into and it generally tries to describe a strategy useful for troubleshooting
and diagnostics. You may also seek assistance from the [CAS community](/cas/Mailing-Lists.html).
   
## Test

Invest in test automation. A lot. It can be boring and redundant and yet, it will bring you peace of mind, 
fewer sleepless nights, and an overall healthier and more agile attitude towards upgrades. Furthermore, 
don’t test too late. Many CAS deployment projects proceed such that by the time the deployment and/or upgrade and its testing have finished, 
the CAS software itself has become EOL. Try not to go to production with a version that is declared dead for maintenance. 
Remember that the CAS project puts out release candidates in the hopes of getting early feedback, allowing you to test early. 

If you wait too long, then you will have waited too long. So, don't wait long.
