/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.adaptors.trusted.authentication.principal;

import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Principal;
import org.springframework.util.Assert;

public final class PrincipalBearingCredentials implements Credentials {

    /** Unique id for serialization */
    private static final long serialVersionUID = -3779730112251585974L;

    /** The trusted principal. */
    private final Principal principal;

    public PrincipalBearingCredentials(final Principal principal) {
        Assert.notNull(principal, "principal cannot be null");
        this.principal = principal;
    }

    /**
     * Get the previously authenticated Principal.
     * 
     * @return authenticated Principal
     */
    public Principal getPrincipal() {
        return this.principal;
    }

    public String toString() {
        return "[name: " + this.principal.toString() + "]";
    }
}
