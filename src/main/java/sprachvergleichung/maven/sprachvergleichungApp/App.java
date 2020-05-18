package sprachvergleichung.maven.sprachvergleichungApp;

import java.io.IOException;
import java.util.Arrays;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class App {
	public static class IntComparator extends WritableComparator {

		public IntComparator() {
			super(IntWritable.class);
		}

		@Override
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {

			byte bytes2[] = Arrays.copyOfRange(b1, s1 + 1, l1 + s1 + 1);
			int test2 = new String(bytes2).length();
			byte bytes22[] = Arrays.copyOfRange(b2, s2 + 1, l2 + s2 + 1);
			int test22 = new String(bytes22).length();
			return Integer.compare(test2, test22) * (-1);
			
		}
	}

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable length = new IntWritable();
		private Text word = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

			for (String data : value.toString().split(" |\\,|\\.")) {

				FileSplit z = (FileSplit) context.getInputSplit();
				String path = z.getPath().getParent().getName();
				word.set(path + " - " + data + " - ");
				byte bytes[] = word.toString().getBytes("UTF-8");
				String test = new String(bytes, "UTF-8");
				length.set(test.length());
				context.write(word, length);
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {

			for (IntWritable intWritable : values) {
				context.write(key, intWritable);
			}
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
		BasicConfigurator.configure();
		Job job = Job.getInstance(conf, "word count");
		// set General Class
		job.setJarByClass(App.class);
		// set MapReduce and Secondary Classes
		job.setMapperClass(TokenizerMapper.class);
		job.setNumReduceTasks(1);
		job.setReducerClass(IntSumReducer.class);
		// set Output Classes
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		// set Map Output Classes
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		// set Comparator Class
		job.setSortComparatorClass(IntComparator.class);
		// set Input and Output Paths
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		FileInputFormat.setInputDirRecursive(job, true);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
