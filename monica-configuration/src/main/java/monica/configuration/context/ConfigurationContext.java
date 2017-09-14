package monica.configuration.context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.yaml.snakeyaml.Yaml;

import monica.configuration.entity.YamlClient;
import monica.configuration.entity.YamlMonica;
import monica.configuration.entity.YamlRouter;

/**
 * {Threadsafe}
 *  * 
 * @author lucy@polarcoral.com
 *
 * 2017-08-29
 *
 */
public class ConfigurationContext {
	private static String yamlBaseFilePath = "yaml";
	
	public static ConcurrentHashMap<String, Object> propMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * need to adapt to different registry framework.  below just be customized to zookeeper
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void loadYamlServerConfig() throws FileNotFoundException, IOException{		
		Yaml yaml = new Yaml();
		YamlMonica yamlMonica = yaml.loadAs(getClassLoader().getResource(yamlBaseFilePath+File.separator+"monica.yaml").openStream(), YamlMonica.class);
		propMap.putIfAbsent("yamlMonica", yamlMonica);
		propMap.putIfAbsent("serverString", yamlMonica.toServerString());
		propMap.putIfAbsent("storage", yamlMonica.getStorage());
	}
	
	
	public static void loadYamlClientConfig() throws FileNotFoundException, IOException{
		Yaml yaml = new Yaml();
		YamlClient yamlClient = yaml.loadAs(getClassLoader().getResource(yamlBaseFilePath+File.separator+"client.yaml").openStream(), YamlClient.class);
		propMap.putIfAbsent("client", yamlClient);
		propMap.putIfAbsent("serverString", yamlClient.toServerString());
	}
	
	public static List<String> loadYamlRouterConfig() throws FileNotFoundException, IOException{
		Yaml yaml = new Yaml();
		YamlRouter yamlRouter = yaml.loadAs(getClassLoader().getResource(yamlBaseFilePath+File.separator+"router.yaml").openStream(), YamlRouter.class);
		return yamlRouter.toStringList();
	}
	
	
	private static ClassLoader getClassLoader(){
		return ConfigurationContext.class.getClassLoader();
	}
	
	public static void main(String args[]){	
		try {
			//loadYamlServerConfig();
			//YamlMonica monica = (YamlMonica)propMap.get("yamlMonica");
			List<String> rules = loadYamlRouterConfig();
			System.out.println("zk --------  "+rules.get(0));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
