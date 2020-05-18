package Hadoop.Sprachvergleichung;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.util.regex.Pattern;

public class Map extends Mapper<Object, Text, Text, IntWritable> {

	private final static IntWritable _length = new IntWritable();
	private Text _key = new Text();
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		
		for (String data : value.toString().split("[\\p{Punct}]")) {  

			FileSplit filePath = (FileSplit) context.getInputSplit();
			String fileFolderName = filePath.getPath().getParent().getName();
			_key.set(fileFolderName + " - " + data + " - ");
			//byte wordBytes[] = data.toString().getBytes("UTF-8");
			//String test = new String(wordBytes, "UTF-8");
			//int o = data.length();
			//String a = data.toString();
			//int a2 = a.length();
			_length.set(data.length());
			context.write(_key, _length);
		}
	}
}