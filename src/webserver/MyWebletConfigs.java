package webserver;

public class MyWebletConfigs {
	String url;
	Class cls;
	
	public MyWebletConfigs (String url, Class cls) {
		this.url = url;
		this.cls = cls;
	}
	
	static MyWebletConfigs[] myWebletConfigs = new MyWebletConfigs[] {
		
			// Add one MyWeblet per line
			
			//e.g.
			
			new MyWebletConfigs ("/HelloWorld", apps.HelloWorldMyWeblet.class),
	};
}
