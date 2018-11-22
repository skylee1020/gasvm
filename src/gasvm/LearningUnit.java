/*
#Copyright (c) 2018 <Kyoung-jae Kim, Kichun Lee, and Hyunchul Ahn>
#
#All rights reserved under BSD License.
#
#Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
#
#
# - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
#
# - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
#
# - Neither the name of the Samsung Electronics Co., Ltd nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
#
#
#THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package gasvm;

import svm.lib.svm_problem;
import svm.lib.svm;
import svm.lib.svm_model;
import svm.lib.svm_node;

import java.io.*;
import java.util.Arrays;

import svm.tool.svm_train;
import svm.tool.svm_predict;


public class LearningUnit {
    private String trainFileName;
    private String testFileName;
    private String validationFileName;
    private svm_problem svm_whole_train_problem;
    private svm_model model;
    private int max_count_features;
    private int max_count_instances;

    public LearningUnit(String trainFileName, String testFileName, String validationFileName) throws IOException {
        this.trainFileName = trainFileName;
        this.testFileName = testFileName;
        this.validationFileName = validationFileName;
        init();
    }

    private LearningUnit(String trainFileName, String testFileName, String validationFileName,
                         svm_problem svm_whole_train_problem, int max_count_features, int max_count_instances ) {
        this.trainFileName = trainFileName;
        this.testFileName = testFileName;
        this.validationFileName = validationFileName;
        this.svm_whole_train_problem = svm_whole_train_problem;
        this.max_count_features = max_count_features;
        this.max_count_instances = max_count_instances;
    }

    private void init() throws IOException {
        initProblem();
    }

    private void initProblem() throws IOException {
         svm_whole_train_problem = svm_train.readSVMProblem(trainFileName);
        max_count_features = svm_whole_train_problem.max_x_index;
        max_count_instances = svm_whole_train_problem.l;
        System.out.println("! trainFileName:"+trainFileName);
        System.out.println("! testFileName:"+testFileName);        
        System.out.println("! validationFileName:"+validationFileName);
        System.out.println("! max_count_features:"+max_count_features);
        System.out.println("! max_count_instances:"+max_count_instances);
        
    }

    public LearningUnit copy() {
        LearningUnit _unit = new LearningUnit(this.trainFileName, this.testFileName, this.validationFileName,
                this.svm_whole_train_problem, this.max_count_features, this.max_count_instances);
        return _unit;
    }

    public int getMax_count_features() {
        return max_count_features;
    }

    public int getMax_count_instances() {
        return max_count_instances;
    }

    private AccuracyResult getModelPrediction(String argv[], boolean saveResult, String saveFilePrefix, svm_problem currentProb, int[] exclude_features, int[] exclude_instances) throws IOException {
        svm_train svmTrain = new svm_train();
        svmTrain.parse_command_line(argv);

        svmTrain.setSVMProblem(currentProb);
        String error_msg = svm.svm_check_parameter(currentProb, svmTrain.param );

        int empty_items[]=null;
		if(error_msg != null) {
			System.err.print("Error: "+error_msg+"\n");
			System.exit(1);
		}
        if ( LearningProperties.debug_TestRandomFitness) {
            //test
            //System.out.println("[gasvm] LearningUnit.getModelPrediction testing... random");
            AccuracyResult accuracyResult = new AccuracyResult(Math.random() * 100, Math.random() * 100, Math.random() * 100 );
            return accuracyResult;
        }

        if ( LearningProperties.debug_LearningApp ) {
            System.out.println("doing svm train");
            //System.out.println("svm parameter");
            //for (int i = 0; i < argv.length; i++) {
            //    String s = argv[i];
            //    System.out.println(s);
            //}
            System.out.println("saveResult:" + saveResult + ", saveFilePrefix:" + saveFilePrefix);
        }

        /**
         * need to use new-problem! @_@;
         */
        //model = svm.svm_train(svm_whole_train_problem,svmTrain.param);
        model = svm.svm_train(currentProb,svmTrain.param);
        if ( saveResult ) {
            svm.svm_save_model(getModelFileName(saveFilePrefix + trainFileName) ,model);
        }

        double accuracy_train = getAccuracy(trainFileName, saveResult, saveFilePrefix, exclude_features, exclude_instances);
        double accuracy_test = getAccuracy(testFileName, saveResult, saveFilePrefix, exclude_features, empty_items);
        double accuracy_validation = getAccuracy(validationFileName, saveResult, saveFilePrefix, exclude_features, empty_items);
        AccuracyResult result = new AccuracyResult(accuracy_train, accuracy_test, accuracy_validation );

        if ( LearningProperties.debug_LearningApp ) {
            System.out.println("accuracy_train:" + accuracy_train);
            System.out.println("accuracy_test:" + accuracy_test);
            System.out.println("accuracy_validation:" + accuracy_validation);
        }

        return result;


    }


    public static BufferedReader getExcludedData(String targetFile, int[] exclude_features, int[] exclude_instnaces) {
        return null;
    }

    private double getAccuracy(String targetFile, boolean saveResult, String _saveFilePrefix, int[] exclude_features, int[] exclude_instances) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(svm_predict.svmpredict_dataFileDirPath + targetFile));
        DataOutputStream output = null;
        if ( saveResult ) {
            output = new DataOutputStream(new FileOutputStream(svm_predict.svmpredict_dataFileDirPath + getPredictionFileName(_saveFilePrefix + targetFile)));
        }
        double accuracy_train = svm_predict.predict(input,output,model,0, exclude_features, exclude_instances);

        try {
            if ( input != null ) {
                input.close();
            }
        } catch ( Throwable t) {
        }

        try {
            if ( output != null ) {
                output.close();
            }
        } catch ( Throwable t) {
        }

        return accuracy_train;
    }

    private static String getModelFileName(String _trainFileName) {
        return _trainFileName + ".model_gasvm";
    }
    private static String getPredictionFileName(String targetDataFileName) {
        return targetDataFileName + ".prediction_gasvm";
    }

    /**
     *
     * @param t svm_type
     * @param p
     * @param g
     * @param c
     * @param saveModel
     * @return
     * @throws IOException
     */
    public AccuracyResult getModelPrediction(int t, int d, double g, double c, boolean saveModel, String saveFilePrefix, int[] exclude_features, int[] exclude_instances) throws IOException {
        if ( LearningProperties.debug_LearningApp) {
            System.out.println("[gasvm] LearningUnit.getModelPrediction t:" + t + ",d:" + d + ",g:" + g + ",c:" + c);
            if ( exclude_features == null ) {
                System.out.println("no excluded feature");
            } else {
                System.out.println("excluded feature index:");
                for (int i = 0; i < exclude_features.length; i++) {
                    int exclude_feature = exclude_features[i];
                    System.out.print("" + exclude_feature + "," );
                }
                System.out.println("");
            }
            if ( exclude_instances == null ) {
                System.out.println("no excluded instance");
            } else {
                System.out.println("excluded instance index:");
                for (int i = 0; i < exclude_instances.length; i++) {
                    int _ins = exclude_instances[i];
                    System.out.print("" + _ins + ",");
                }
                System.out.println("");
            }
        }
        //System.out.println("rounded param value p:" + Math.round(p) + ", g:" + Math.round(g) + ", c:" + Math.round(c));
        String[] params = getSVMParameter(t, d,g,c);
        //String[] params = getSVMParameter(t, Math.round(p),Math.round(g),Math.round(c));
        return getModelPrediction(params, saveModel, saveFilePrefix, getCurrentProblem(exclude_features, exclude_instances), exclude_features, exclude_instances);
    }

    private String[] getSVMParameter(int t, int d, double g, double c) {
        String[] params = new String[9];
        int tmpIndex = 0;
        params[tmpIndex++] = "-t";
        params[tmpIndex++] = Integer.toString(t);
        params[tmpIndex++] = "-d";
        params[tmpIndex++] = Integer.toString(d);
        params[tmpIndex++] = "-c";
        params[tmpIndex++] = Double.toString(c);
        params[tmpIndex++] = "-g";
        params[tmpIndex++] =  Double.toString((double) 1/ g);
        if ( LearningProperties.debug_LearningApp) {
            System.out.println("gasvm.LearningUnit -g " + Double.toString((double) 1/ g) );
        }
        params[tmpIndex++] = trainFileName;
        return params;
    }

//    private svm_problem getCurrentProblem() {
//        return svm_whole_train_problem;
//    }

    /**
     *
     * @param exclude_features  the index of feature to exclude from the learning.
     *                          it starts with 0, coming up to (max_features-1)
     *
     * @param exclude_instances the index of instance to exclude from the learning.
     *                          it starts with 0, coming up to (max_instances-1)
     * @return reorganized svm_problem
     */
    public svm_problem getCurrentProblem(int[] exclude_features, int[] exclude_instances) {
        if ( exclude_features == null || exclude_instances == null ) {
            throw new IllegalArgumentException("Required non-null int[] parameter");
        }
        if ( exclude_features.length == 0 && exclude_instances.length == 0  ) {
            return svm_whole_train_problem;
        }

        if ( exclude_features.length != 0 ) {
            Arrays.sort(exclude_features);
            if ( exclude_features[exclude_features.length-1] >= max_count_features ) {
                throw new IllegalArgumentException("Required excluded feature inddex less than max_count_features");
            }
        }

        if ( exclude_instances.length != 0 ) {
            Arrays.sort(exclude_instances);
            if ( exclude_instances[exclude_instances.length-1] >= max_count_instances ) {
                throw new IllegalArgumentException("Required excluded instance inddex less than max_count_instances");
            }
        }

        svm_problem new_prob = new svm_problem();

        new_prob.max_index = svm_whole_train_problem.max_index;
        new_prob.max_x_index = svm_whole_train_problem.max_x_index - exclude_features.length;
        //System.out.println("new_prob.max_index:" + new_prob.max_index);
        //System.out.println("new_prob.max_x_index:" + new_prob.max_x_index);
        //System.out.println("exclude_features.length:" + exclude_features.length);
        new_prob.l = svm_whole_train_problem.l - exclude_instances.length;


        //step 1. adjust y value to new one
        new_prob.y = new double[new_prob.l];
        new_prob.x = new svm_node[new_prob.l][];
        for ( int i = 0 ; i < new_prob.l ; i++) {
            /**
             * The previous logic was ok. Just tested it.
             */
            //new_prob.x[i] = new svm_node[new_prob.max_index];
            new_prob.x[i] = new svm_node[new_prob.max_x_index];
        }

        for ( int i = 0, new_instance_index = 0 ; i < svm_whole_train_problem.l ; i++ ) {
            /**
             * if this instance is to be excluded
             */
            if ( exclude_instances.length != 0 &&
                    Arrays.binarySearch(exclude_instances, i) > -1) {
                continue;
            } else {
                /**
                 * this instance is included
                 */
                int new_feature_index = 0;
                for ( int j=0 ; j < max_count_features ; j++ ) {
                    if ( exclude_features.length != 0 &&
                            Arrays.binarySearch(exclude_features, j) > -1 ) {
                    } else {
                        /**
                         * this feature is included
                         */
                        svm_node newOneNode = new svm_node();
                        /**
                         * have it start with 1
                         */
                        /**
                         * having indexes lined up with increase of 1
                         * y_val 1:x_1 2:x_2 5:x_5
                         *  -> preparing this case of x_5
                         * previous:
                         * newOneNode.index = new_feature_index + 1;
                         */
                        //newOneNode.index = new_feature_index + 1;
                        newOneNode.index = svm_whole_train_problem.x[i][j].index;
                        //System.out.println("i:" + i + ", j" + j + ", new_feature_index:" + new_feature_index);


                        newOneNode.value = svm_whole_train_problem.x[i][j].value;
                        new_prob.x[new_instance_index][new_feature_index++] = newOneNode;
                    }
                }
                /**
                 * set y value for the new problem!!
                 */
                new_prob.y[new_instance_index] = svm_whole_train_problem.y[i];
                new_instance_index++;
            }
        }

        /**
         * We should use new problem!!
         */
        //return svm_whole_train_problem;
        return new_prob;
    }

}
