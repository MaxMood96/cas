---
layout: default
title: CAS - Attribute Resolution
category: Attributes
---

{% include variables.html %}

# JDBC Attribute Resolution

CAS does allow for attributes to be retrieved from a variety of SQL databases.

Support is enabled by including the following dependency in the WAR overlay:

{% include_cached casmodule.html group="org.apereo.cas" module="cas-server-support-jdbc" %}

To learn how to configure database drivers, [please see this guide](../installation/JDBC-Drivers.html).

{% include_cached casproperties.html properties="cas.authn.attribute-repository.jdbc" %}

JDBC attribute sources can be defined based on the following mechanics.

## Single Row

Designed to work against a table where there is a mapping of one row to one user.
An example of this table format would be:

| uid      | first_name | last_name | email                |
|----------|------------|-----------|----------------------|
| `jsmith` | `John`     | `Smith`   | `jsmith@example.org` |

## Multi Row

Designed to work against a table where there is a mapping of one row to one user.
An example of this table format would be:

| uid      | attr_name    | attr_value           |
|----------|--------------|----------------------|
| `jsmith` | `first_name` | `John`               |
| `jsmith` | `last_name`  | `Smith`              |
| `jsmith` | `email`      | `jsmith@example.org` |

You will need to define column mappings
in your configuration to map the `attr_name` column to the `attr_value` column

# Examples

Suppose CAS is configured to authenticate against Active Directory. The account whose details are defined below
authenticates via `sAMAccountName`.

| Attribute        | Value        |
|------------------|--------------|
| `sAMAccountName` | `johnsmith`  |
| `cn`             | `John Smith` |

## Example #1

If the resolver is configured to use `sAMAccountName` as the attribute for the principal id, then when authentication is complete the resolver attempts
to construct attributes from attribute repository sources, it sees `sAMAccountName` as the attribute and sees the principal id is to
be created by `sAMAccountName`. So it would remove the `sAMAccountName` from the attributes.
The final result is a principal whose id is `johnsmith` who has a `cn` attribute of `John Smith`.

## Example #2

If the resolver is configured to use `cn` as the attribute for the principal id, then when authentication is complete the resolver attempts to
construct attributes from attribute repository sources. It then sees `sAMAccountName` as the attribute and sees the principal id is to be created by `cn`.
So it would remove the `cn` from the attributes. The final result is a principal whose id is `John Smith`
who has a `sAMAccountName` attribute of `johnsmith`.

# Multitenancy

Configuration settings for SQL attribute resolution can be specified in a multitenant environment.
Please [review this guide](../multitenancy/Multitenancy-Overview.html) for more information.
