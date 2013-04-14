// Copyright 2006, 2007, 2008, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.corelib.components;

import java.util.List;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.internal.InternalMessages;

/**
 * Standard validation error presenter. Must be enclosed by a
 * {@link org.apache.tapestry5.corelib.components.Form} component. If errors are present, renders a
 * div element around a banner message and around an unnumbered list of
 * error messages. Renders nothing if the {@link org.apache.tapestry5.ValidationTracker} shows no
 * errors.
 * 
 * @see Form
 * @tapestrydoc
 */
public class Errors
{
    /**
     * The banner message displayed above the errors. The default value is "You must correct the
     * following errors before
     * you may continue.".
     */
    @Parameter("message:default-banner")
    private String banner;

    // Allow null so we can generate a better error message if missing
    @Environmental(false)
    private ValidationTracker tracker;

    void beginRender(MarkupWriter writer)
    {
        if (tracker == null)
            throw new RuntimeException(InternalMessages.encloseErrorsInForm());
        
        if (!tracker.getHasErrors())
            return;
        
        writer.element("div", "class", "alert alert-error");
        //<div class="notification error">

        //<a href="#" class="close-notification" title="Hide Notification" rel="tooltip">x</a>
        writer.element("button", "type", "button", "class", "close", "data-dismiss", "alert");
        writer.write("x");
        writer.end();
        
        //<h4>Error notification</h4>
        writer.element("strong");
        writer.write(banner);
        writer.end();
    	
    	//<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vulputate, sapien quis fermentum luctus, libero.</p>
        List<String> errors = tracker.getErrors();

        if (!errors.isEmpty())
        {
            // Only write out the <UL> if it will contain <LI> elements. An empty <UL> is not
            // valid XHTML.

            writer.element("ul");

            for (String message : errors)
            {
                writer.element("li");
                writer.write(message);
                writer.end();
            }

            writer.end(); // ul
        }
        
    	//</div>
    	writer.end(); // div
       
    }
}
