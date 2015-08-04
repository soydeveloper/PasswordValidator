package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpTools {

	private List<String> cookies;
	private HttpsURLConnection conn;
	private final String USER_AGENT = "Mozilla/5.0";

	public HttpTools(){
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
	}

	public String GetPageContent(String url) throws Exception {

		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();

		conn.setRequestMethod("GET");
		conn.setUseCaches(false);

		//FORZAMOS QUE PAREZCA UN BROWSER
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		if (cookies != null) {
			for (String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//GUARDAR LAS COOKIES
		setCookies(conn.getHeaderFields().get("Set-Cookie"));

		return response.toString();
	}

	public String getFormParams(String html, String tagName, FormField username, FormField password) throws UnsupportedEncodingException {

		//CREAMOS UN OBJETO JSOUP PARA MANEJAR EL HTML FACILMENTE
		Document doc = Jsoup.parse(html);

		//BUSCAMOS EL FORMULARIO CON EL TAG PASADO POR PARAMETRO
		Element loginform = doc.getElementById(tagName);
		//SACAMOS TODOS LOS TAGS INPUT QUE ENCONTREMOS DENTRO DEL FORMULARIO
		Elements inputElements = loginform.getElementsByTag("input");
		List<String> paramList = new ArrayList<String>();
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");

			//SI EL INPUT ES DE USUARIO AÑADIMOS EL USUARIO PASADO POR PARAMETRO
			if (key.equals(username.getFieldName()))
				value = username.getValue();
			//SI EL INPUT ES DE CONTRASEÑAS AÑADIMOS EL PASSWORD PASADO POR PARAMETRO
			else if (key.equals(password.getFieldName()))
				value = password.getValue();
			paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
		}

		StringBuilder result = new StringBuilder();
		for (String param : paramList) {
			if (result.length() == 0) {
				result.append(param);
			} else {
				result.append("&" + param);
			}
		}
		return result.toString();
	}

	public String sendPost(String url, String postParams) throws Exception {

		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();

		//VAMOS A MENTIR Y HACER CREER QUE SOMOS UN NAVEGADOR
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");

		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

		conn.setDoOutput(true);
		conn.setDoInput(true);

		//HACEMOS EL POST
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		BufferedReader in = 
				new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	public List<String> getCookies() {
		return cookies;
	}

	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

}

