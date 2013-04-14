package es.ficondev.web.backend.web.components.layout;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

@Import(stylesheet={
        "classpath:/com/trsvax/bootstrap/assets/bootstrap/css/bootstrap.css",
        "classpath:/com/trsvax/bootstrap/assets/bootstrap/css/bootstrap-responsive.css",
        "context:/css/style.css"
        },
library={
        "classpath:/com/trsvax/bootstrap/assets/bootstrap/js/bootstrap.js"
        }
)
public class Layout {		
	@Property
	@Parameter(required = true, defaultPrefix = "message")
	private String pageTitle;
}