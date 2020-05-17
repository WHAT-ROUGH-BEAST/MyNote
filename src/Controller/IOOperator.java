package Controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * 
 * @author 18069
 * 序列化功能
 */

public class IOOperator
{
	private static LinkedHashMap<String, List<String>> filter = new LinkedHashMap<>();
	static 
	{
		// TODO: 补上类型过滤
		filter.put("Img",	new ArrayList<String>(Arrays.asList("*.jpg", "*.png", "*.bmp", "*.gif")));
		filter.put("Attach",new ArrayList<String>(Arrays.asList("*.*")));
		filter.put("Audio", new ArrayList<String>(Arrays.asList("*.mp3", "*.m4a", "*.wma", "*.wav")));
	}
	
	//
	public static String getResourceImg(URL url) throws Exception
	{
		File file;

		file = new File(url.toURI());
		return importDataFile(file);
	}
	// 
	public static String ChooseFile(Window window, String actionType) throws Exception
	{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to import");
        fileChooser.getExtensionFilters().add(
        		new FileChooser.ExtensionFilter(actionType, filter.get(actionType)));
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile == null) 
        	throw new NullPointerException();
        
        return importDataFile(selectedFile);
	}
	
	//
	public static String importDataFile(File file) throws Exception
	{
		try
		{
			if (file.length() > 100*1024*1024)
				throw new Exception("File too big");
			
			byte[] data = Files.readAllBytes(file.toPath());
			// base64data可以不依赖地址保存文件数据
			String base64data = Base64.getEncoder().encodeToString(data);
			return base64data;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
}