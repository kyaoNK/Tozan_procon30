package user.example.com.tozandatacollectapp.CameraRemote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSendJSON {

	String strPostUrl;

	public HttpSendJSON(String strPostUrl){
		this.strPostUrl = strPostUrl;
	}

	public String post(String method){

		String JSON = "{     \"method\": \"" + method + "\",     \"params\": [],     \"id\": 1,     \"version\": \"1.0\" } ";
		return send(JSON);

	}

	/*public String post(String method,String[] params){

		String param = "";

		for(int n = 0;n < params.length;n++){
			if(param.length() != 0) param += ",";
			param += params[n];
		}
		
		return post(method,param);

	}*/

    public String post(String method, String params) {

		if(params.isEmpty()) return post(method);
		String JSON = "{     \"method\": \"" + method + "\",     \"params\": [" + params + "],     \"id\": 1,     \"version\": \"1.0\" } ";
        return send(JSON);
	}

	private String send(String JSON){

		HttpURLConnection con = null;
        StringBuffer response = new StringBuffer();
        try {

            URL url;
			OutputStreamWriter out;
			int count = 0;

			while(true){

				try{

					url = new URL(strPostUrl);
		            con = (HttpURLConnection) url.openConnection();	
		            
		            
		            con.setDoOutput(true);
		            con.setRequestMethod("POST");	
		            con.setRequestProperty("Accept-Language", "jp");	

		            
		            con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");

		            
		            con.setRequestProperty("Content-Length", String.valueOf(JSON.length()));

		            
		            out = new OutputStreamWriter(con.getOutputStream());
		            out.write(JSON);
		            out.flush();
		            con.connect();

					break;

				}catch(Exception e){

					e.printStackTrace();

				}
			}

            
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                
                
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                
                while ((line = bufReader.readLine()) != null) {
                    response.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                
                
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                
                con.disconnect();
            }
		}
		
		
		return response.toString();
	}

}