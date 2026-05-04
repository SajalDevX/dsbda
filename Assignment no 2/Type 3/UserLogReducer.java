package mrLogFile_demo;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class UserLogReducer extends MapReduceBase
        implements Reducer<Text, IntWritable, Text, IntWritable> {

    private int maxCount = 0;
    private String maxIP = "";

    @Override
    public void reduce(Text key, Iterator<IntWritable> values,
            OutputCollector<Text, IntWritable> output,
            Reporter reporter) throws IOException {

        int sum = 0;

        while (values.hasNext()) {
            sum += values.next().get();
        }

        // Track maximum
        if (sum > maxCount) {
            maxCount = sum;
            maxIP = key.toString();
        }
    }

    // Runs once after all reducers finish
    @Override
    public void close() throws IOException {

        System.out.println("=================================");
        System.out.println("MAX FREQUENCY IP: " + maxIP);
        System.out.println("COUNT: " + maxCount);
        System.out.println("=================================");
    }
}
