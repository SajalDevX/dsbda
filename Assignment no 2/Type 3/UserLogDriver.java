package mrLogFile_demo;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.conf.Configuration;

public class UserLogDriver {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: UserLogDriver <input path> <output path>");
            System.exit(1);
        }

        try {

            JobConf job = new JobConf(UserLogDriver.class);
            job.setJobName("MaxIPFinder");

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            job.setMapperClass(UserLogMapper.class);
            job.setReducerClass(UserLogReducer.class);

            job.setInputFormat(TextInputFormat.class);
            job.setOutputFormat(TextOutputFormat.class);

            Path input = new Path(args[0]);
            Path output = new Path(args[1]);

            // Delete old output automatically
            FileSystem fs = FileSystem.get(new Configuration());
            if (fs.exists(output)) {
                fs.delete(output, true);
            }

            FileInputFormat.setInputPaths(job, input);
            FileOutputFormat.setOutputPath(job, output);

            JobClient.runJob(job);

            System.out.println("Job Finished Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
