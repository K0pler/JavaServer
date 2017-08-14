package webserver;

public class MyWebletConfigs {
	String url;
	@SuppressWarnings("rawtypes")
	Class cls;
	
	@SuppressWarnings("rawtypes")
	public MyWebletConfigs (String url, Class cls) {
		this.url = url;
		this.cls = cls;
	}
	
	static MyWebletConfigs[] myWebletConfigs = new MyWebletConfigs[] {
		
			// Add one MyWeblet per line
			
			//e.g.
			
			new MyWebletConfigs ("/HelloWorld", apps.HelloWorldMyWeblet.class),
			new MyWebletConfigs ("/ProcessName", apps.NameProcessor.class),
	};
	
	static String[] serverStartupClasses = {
			"apps.MyServerStartup"
	};
}
