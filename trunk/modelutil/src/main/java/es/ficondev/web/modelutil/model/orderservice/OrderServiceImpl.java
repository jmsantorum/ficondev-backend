package es.ficondev.web.modelutil.model.orderservice;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.model.order.Order;
import es.ficondev.web.modelutil.model.order.OrderDao;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	private static final String GOOGLE_MAPS_API = "http://maps.googleapis.com/maps/api/directions/";
	private static final String ORIGIN = "Coruña,A";

	@Override
	public String calculateDistribution(List<Order> orders) {

		String query = "xml?origin=" + ORIGIN + "&destination=" + orders.get(0).getClient().getDirection() + "&sensor=false&region=es";
		
		if (orders.size() > 1) {
			query += "&waypoints=optimize:true";
			
			for(int i=1; i<orders.size(); i++) {
				query += "|" + orders.get(i).getClient().getDirection(); 
			}
		}
		
		GetMethod method = null;
		try {
			method = new GetMethod(GOOGLE_MAPS_API + URLEncoder.encode(query,"UTF-8"));
		} catch (UnsupportedEncodingException e) {

		}

		try {
			HttpClient client = new HttpClient();
			int statusCode = client.executeMethod(method);

			if (statusCode == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();

				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(in);
				Element responseElement = document.getRootElement();

				return parseMapsResult(responseElement);
			}
		} catch (Exception e) {
		} finally {
			if (method != null)
				method.releaseConnection();
		}

		return "no-result";
	}
	
	private String parseMapsResult(Element directionsResponse) {
		String output = "";
		Element result = directionsResponse.getChild("status");
		if (result.getTextTrim().equals("OK")) {
			Element route = directionsResponse.getChild("route");
			List<Element> legs = route.getChildren("leg");
			for (Element leg : legs) {
				Element duration = leg.getChild("duration");
				Element distance = leg.getChild("distance");
				Element startAddress = leg.getChild("start_address");
				Element endAddress = leg.getChild("end_address");
				
				output = String.format("%s - %s : %s : %s\n", startAddress.getTextTrim(), 
						endAddress.getTextTrim(), distance.getTextTrim(), duration.getTextTrim());
			}
		} else {
			output = result.getTextTrim();
		}
		
		return output;
	}

}
