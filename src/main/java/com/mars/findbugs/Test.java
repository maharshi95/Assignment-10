package com.mars.findbugs;

import com.sun.xml.internal.ws.server.provider.AsyncProviderInvokerTube;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by maharshigor on 23/07/16.
 */
public class Test {
	public void handle(String target, Request baseRequest, final HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		if (request.getAttribute(CONTEXT_ATTRIBUTE) == null)
		{
			final AsyncProviderInvokerTube.AsyncWebServiceContext asyncContext = baseRequest.startAsync();
			request.setAttribute(CONTEXT_ATTRIBUTE, asyncContext);
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					if (dispatch)
						asyncContext.dispatch();
					else
						asyncContext.complete();
				}
			}).run();
		}
		super.handle(target, baseRequest, request, response);
	}
}
