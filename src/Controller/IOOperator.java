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
	private static LinkedHashMap<String, String[]> filter = new LinkedHashMap<>();
	static 
	{
		// TODO: 补上类型过滤
		filter.put("Img",	new String[]{"All Files", "*.*"});
		filter.put("Attach",new String[]{"All Files", "*.*"});
		filter.put("Audio", new String[]{"All Files", "*.*"});
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
        fileChooser.setSelectedExtensionFilter(
        		new FileChooser.ExtensionFilter(filter.get(actionType)[0], filter.get(actionType)[1]));
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
			
			String type = Files.probeContentType(file.toPath());
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