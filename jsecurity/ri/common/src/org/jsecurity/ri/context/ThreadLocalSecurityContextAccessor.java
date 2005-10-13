/*
 * Copyright (C) 2005 Jeremy Haile
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307
 * USA
 *
 * Or, you may view it online at
 * http://www.opensource.org/licenses/lgpl-license.php
 */

package org.jsecurity.ri.context;

import org.jsecurity.authz.AuthorizationContext;
import org.jsecurity.session.Session;
import org.jsecurity.ri.util.ThreadContext;
import org.jsecurity.context.SecurityContextAccessor;

/**
 * Implementation of {@link SecurityContextAccessor} that retrieves security context information
 * from a thread local variable using the {@link ThreadContext} class.
 *
 * @author Jeremy Haile
 * @since 0.1
 */
public class ThreadLocalSecurityContextAccessor implements SecurityContextAccessor {

    /**
     * Retrieves the current user's session from the thread local context.
     * @return the current user's session.
     */
    public Session getSession() {
        return (Session) ThreadContext.get( ThreadContext.SESSION_THREAD_CONTEXT_KEY );
    }

    /**
     * Retrieves the current user's authorization context from the thread local context.
     * @return the current user's authorization context.
     */
    public AuthorizationContext getAuthorizationContext() {
        return (AuthorizationContext) ThreadContext.get( ThreadContext.AUTHCONTEXT_THREAD_CONTEXT_KEY );
    }


    /**
     * Invalidates the user's current security context by stopping the session and removing the authorization
     * context from the thread local context.
     */
    public void invalidate() {

        // Stop the current session if one exists
        Session session = getSession();
        if( session != null ) {
            getSession().stop();
        }

        // Remove the authorization context from the thread context
        if( ThreadContext.containsKey( ThreadContext.AUTHCONTEXT_THREAD_CONTEXT_KEY ) ) {
            ThreadContext.remove( ThreadContext.AUTHCONTEXT_THREAD_CONTEXT_KEY );
        }

    }
}