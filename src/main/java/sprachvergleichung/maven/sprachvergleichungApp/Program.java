package Hadoop.Sprachvergleichung;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class Program {
	
	public static void main(String[] args) throws Exception  {
		
		Configuration conf = new Configuration();
		BasicConfigurator.configure();
		//System.setProperty("hadoop.home.dir", "/");
		Job job = Job.getInstance(conf, "word count");
		// set General Class
		job.setJarByClass(Program.class);
		// set MapReduce and Secondary Classes
		job.setMapperClass(Map.class);
		job.setNumReduceTasks(1);
		job.setReducerClass(ResultReducer.class);
		// set Output Classes
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		// set Map Output Classes
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		// set Comparator Class
		job.setSortComparatorClass(Comparator.class);
		// set Input and Output Paths
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileInputFormat.setInputDirRecursive(job, true);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
