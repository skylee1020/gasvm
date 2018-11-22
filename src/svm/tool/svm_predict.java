package svm.tool;

import java.io.*;
import java.util.*;

import svm.lib.svm;
import svm.lib.svm_node;
import svm.lib.svm_model;
import svm.lib.svm_parameter;

public class svm_predict {
    /**
     * Add sky1020 2005/08/24
     */
    //public static final String svmpredict_dataFileDirPath =
    // gasvm.PropertyUtil.getDirStringPropertis("svm.predictdata.dir", "D:\\work\\svmNew\\svm2.8\\data\\from2.6\\");
    public static final String svmpredict_dataFileDirPath =
            gasvm.LearningProperties.svm_predictdata_dir;

	private static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

	public static double predict(BufferedReader input, DataOutputStream output, svm_model model, int predict_probability, int[] exclude_features, int[] exclude_instances) throws IOException
	{
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		int[] labels=new int[nr_class];
		double[] prob_estimates=null;

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				System.out.print("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
                if ( output != null) {
                    output.writeBytes("labels");
                }
				for(int j=0;j<nr_class;j++) {
                    if ( output != null) {
                        output.writeBytes(" "+labels[j]);
                    }
                }
                if ( output != null) {
                    output.writeBytes("\n");
                }
			}
		}

        int i_id=0;

		while(true)
		{
			String line = input.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens()/2;
            /**
             * change sky1020 2006/01/11
             */
            int _feature_count = exclude_features == null ? m : m-exclude_features.length;
			svm_node[] x = new svm_node[_feature_count];

            int f_id=0;
            int tmpindex = 0;
            double tmpvalue = 0;

            /**
             * change sky1020 2006/01/11
             */
            for(int j=0;j<m;j++)
			{
                tmpindex = atoi(st.nextToken());
                tmpvalue = atof(st.nextToken());

                /**
                 * change sky1020 2006/01/11
                 */
                if ( exclude_features != null && exclude_features.length != 0 &&
                        Arrays.binarySearch(exclude_features, j) > -1 ) {
                } else {
                    /**
                     * this feature is included
                     */
                    x[f_id] = new svm_node();
                    x[f_id].index = tmpindex;
                    x[f_id].value = tmpvalue;
                    ++f_id;
                }
            }

			double v;
            /**
             * change sky1020 2006/01/11
             */
            if ( exclude_instances != null && exclude_instances.length != 0 &&
                    Arrays.binarySearch(exclude_instances, i_id) > -1) {
                i_id++;
                continue;
            } else {
                if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
                {
                    v = svm.svm_predict_probability(model,x,prob_estimates);
                    if ( output != null) {
                        output.writeBytes(v+" ");
                    }
                    for(int j=0;j<nr_class;j++) {
                        if ( output != null) {
                            output.writeBytes(prob_estimates[j]+" ");
                        }
                    }
                    if ( output != null) {
                        output.writeBytes("\n");
                    }
                }
                else
                {
                    v = svm.svm_predict(model,x);
                    if ( output != null) {
                        output.writeBytes(v+"\n");
                    }
                }
            }
			if(v == target)
				++correct;
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;

            ++i_id;
		}
		System.out.print("Accuracy = "+(double)correct/total+
				 "% ("+correct+"/"+total+") (classification)\n");
		System.out.print("Mean squared error = "+error/total+" (regression)\n");
		System.out.print("Squared correlation coefficient = "+
			((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
			((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+" (regression)\n"
			);
        /**
         * Add sky1020 2005/09/12
         */
        return (double)correct/total;
	}

	public static double predict(BufferedReader input, DataOutputStream output, svm_model model, int predict_probability) throws IOException {
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		int[] labels=new int[nr_class];
		double[] prob_estimates=null;

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				System.out.print("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
                if ( output != null) {
                    output.writeBytes("labels");
                }
				for(int j=0;j<nr_class;j++) {
                    if ( output != null) {
                        output.writeBytes(" "+labels[j]);
                    }
                }
                if ( output != null) {
                    output.writeBytes("\n");
                }
			}
		}
		while(true)
		{
			String line = input.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}

			double v;
			if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
			{
				v = svm.svm_predict_probability(model,x,prob_estimates);
                if ( output != null) {
                    output.writeBytes(v+" ");
                }
				for(int j=0;j<nr_class;j++) {
                    if ( output != null) {
                        output.writeBytes(prob_estimates[j]+" ");
                    }
                }
                if ( output != null) {
                    output.writeBytes("\n");
                }
			}
			else
			{
				v = svm.svm_predict(model,x);
                if ( output != null) {
                    output.writeBytes(v+"\n");
                }
			}

			if(v == target)
				++correct;
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;
		}
		System.out.print("Accuracy = "+(double)correct/total+
				 "% ("+correct+"/"+total+") (classification)\n");
		System.out.print("Mean squared error = "+error/total+" (regression)\n");
		System.out.print("Squared correlation coefficient = "+
			((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
			((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+" (regression)\n"
			);
        /**
         * Add sky1020 2005/09/12
         */
        return (double)correct/total ;
	}

	private static void exit_with_help()
	{
        System.err.print("usage: svm.tool.svm_predict [options] test_file model_file output_file\n"
	        +"options:\n"
                +"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n");
		System.exit(1);
	}

	public static void main(String argv[]) throws IOException
	{
		int i, predict_probability=0;

		// parse options
		for(i=0;i<argv.length;i++)
		{
			if(argv[i].charAt(0) != '-') break;
			++i;
			switch(argv[i-1].charAt(1))
			{
			        case 'b':
					predict_probability = atoi(argv[i]);
					break;
				default:
					System.err.print("unknown option\n");
					exit_with_help();
			}
		}
		if(i>=argv.length)
			exit_with_help();
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader(svmpredict_dataFileDirPath + argv[i]));
			DataOutputStream output = new DataOutputStream(new FileOutputStream(svmpredict_dataFileDirPath + argv[i+2]));
			svm_model model = svm.svm_load_model(svm.svm_model_dataFileDirPath + argv[i+1]);
			predict(input,output,model,predict_probability);
		} 
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
            exit_with_help();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			exit_with_help();
		}
	}
}
