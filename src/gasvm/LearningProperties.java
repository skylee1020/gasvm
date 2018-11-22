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

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;


public class LearningProperties {
    private static String configDir = PropertyUtil.getDirStringPropertis("gasvm.propertyDir", ".");
    private static Properties props;

    public static String svm_traindata_dir;
    public static String svm_predictdata_dir;
    public static String svm_model_dir;
    public static int svm_kernel_type;

    public static boolean debug;
    public static boolean debug_LearningApp;
    public static boolean debug_TestRandomFitness;
    public static boolean debug_GenerateGene;
    public static boolean debug_svmTrain;

    public static String generation_logfileDir;
    public static String generation_logfile;
    public static String generation_logfile_svm_specific;

    public static boolean feature_selection;
    public static boolean instance_selection;
    public static boolean svm_selection;

    public static String train_file;
    public static String test_file;
    public static String validation_file;

    public static double c_default;
    public static int d_default;
    public static double g_default;

    public static double c_min;
    public static double c_max;
    public static int d_min;
    public static int d_max;
    public static double g_min;
    public static double g_max;
    public static int c_gene_length;
    public static int d_gene_length;
    public static int g_gene_length;

    public static double crossover_percent;
    public static double mutataion_percent;
    public static int population_size;
    public static int generation;

    public static int svm_toy_size;

    public static int roundDigitCount;

    public static boolean random_initialize_selection = true;
    public static boolean elitism_second_mutate_cross;
    public static boolean elitism_first_mutate;
    public static boolean gasvm_elitism;
    public static boolean gasvm_cross_elitism;

    static {
        try {
            String filename = configDir + File.separator + "gasvm.properties";
            File file = new File(filename);
            if (file.exists()) {
                props = new Properties();
                FileInputStream in = new FileInputStream(file);
                props.load(in);
            }
        } catch (Exception e) {
        }

        init();
    }

    private static void init() {
        svm_traindata_dir = PropertyUtil.getDirStringPropertis("svm.traindata.dir", "C:\\work\\dataAnalysis\\gasvm\\data\\", props);
        svm_predictdata_dir = PropertyUtil.getDirStringPropertis("svm.predictdata.dir", "C:\\work\\dataAnalysis\\gasvm\\data\\", props);
        svm_model_dir = PropertyUtil.getDirStringPropertis("svm.model.dir", "C:\\work\\dataAnalysis\\gasvm\\data\\", props);
        svm_kernel_type = PropertyUtil.getIntParamValue("svm.kernel_type", 0, props);

        debug = PropertyUtil.getBooleanParamValue("gasvm.debug", true, props);
        debug_LearningApp = PropertyUtil.getBooleanParamValue("gasvm.debug_LearningApp", true, props);
        debug_TestRandomFitness = PropertyUtil.getBooleanParamValue("gasvm.debug_TestRandomFitness", false, props);
        debug_GenerateGene = PropertyUtil.getBooleanParamValue("gasvm.debug_GenerateGene", false, props);
        //debug_svmTrain = PropertyUtil.getBooleanParamValue("gasvm.debug_svmTrain", true, props);

        generation_logfile = PropertyUtil.getStringPropertis("gasvm.generation_logfile", "my_gasvm_logfile.log", props);
        generation_logfile_svm_specific = PropertyUtil.getStringPropertis("gasvm.generation_logfile_svm_specific", "my_gasvm_logfile_svm.log", props);
        generation_logfileDir = PropertyUtil.getDirStringPropertis("gasvm.generation_logfileDir", "C:\\work\\dataAnalysis\\gasvm\\data\\", props);
        feature_selection = PropertyUtil.getBooleanParamValue("gasvm.feature_selection", true, props);
        instance_selection = PropertyUtil.getBooleanParamValue("gasvm.instance_selection", false, props);
        svm_selection = PropertyUtil.getBooleanParamValue("gasvm.svm_selection", false, props);

        train_file = PropertyUtil.getStringPropertis("gasvm.train_file", "sample_train.1.txt", props);
        test_file = PropertyUtil.getStringPropertis("gasvm.test_file", "sample_test.1.txt", props);
        validation_file = PropertyUtil.getStringPropertis("gasvm.validation_file", "sample_validation.1.txt", props);

        c_default = PropertyUtil.getDoubleParamValue("gasvm.c_default", 140.0d, props);
        d_default = PropertyUtil.getIntParamValue("gasvm.d_default", 20, props);
        g_default = PropertyUtil.getDoubleParamValue("gasvm.g_default", 50.0d, props);

        c_min = PropertyUtil.getDoubleParamValue("gasvm.c_min", 1.0d, props);
        c_max = PropertyUtil.getDoubleParamValue("gasvm.c_max", 200.0d, props);
        d_min = PropertyUtil.getIntParamValue("gasvm.d_min", 1, props);
        d_max = PropertyUtil.getIntParamValue("gasvm.d_max", 30, props);
        g_min = PropertyUtil.getDoubleParamValue("gasvm.g_min", 1.0d , props);
        g_max = PropertyUtil.getDoubleParamValue("gasvm.g_max", 250.0d, props);

        c_gene_length = PropertyUtil.getIntParamValue("gasvm.c_gene_length", 10, props);
        d_gene_length = PropertyUtil.getIntParamValue("gasvm.d_gene_length", 6, props);
        g_gene_length = PropertyUtil.getIntParamValue("gasvm.g_gene_length", 10, props);

        crossover_percent = PropertyUtil.getDoubleParamValue("gasvm.crossover_percent", 70.0d, props);
        mutataion_percent = PropertyUtil.getDoubleParamValue("gasvm.mutataion_percent", 05.0d, props);
        population_size = PropertyUtil.getIntParamValue("gasvm.population_size", 15, props);
        generation = PropertyUtil.getIntParamValue("gasvm.generation", 20, props);

        roundDigitCount = PropertyUtil.getIntParamValue("gasvm.round_digit_count", 6, props);
        svm_toy_size = PropertyUtil.getIntParamValue("svm.svm_toy_size", 500, props);
        /**
         * get the value from the gasvm property file
         */
        random_initialize_selection = PropertyUtil.getBooleanParamValue("gasvm.random.initialize.selection", true, props);

        elitism_second_mutate_cross = PropertyUtil.getBooleanParamValue("gasvm.elitism.second.mutate.cross", false, props);
        elitism_first_mutate = PropertyUtil.getBooleanParamValue("gasvm.elitism.first.mutate", false, props);
        gasvm_elitism = PropertyUtil.getBooleanParamValue("gasvm.elitism", true, props);
        gasvm_cross_elitism = PropertyUtil.getBooleanParamValue("gasvm.cross.elitism", false, props);

        showProperties();
        validateProperties();
    }

    private static void validateProperties() {
        if ( population_size < 2 ) {
            throw new IllegalArgumentException("gasvm.population_size should be greater than 1");
        }
    }

    private static void showProperties() {
        System.out.println("svm_traindata_dir:" + svm_traindata_dir);
        System.out.println("svm_predictdata_dir:" + svm_predictdata_dir);
        System.out.println("svm_model_dir:" + svm_model_dir);
        System.out.println("svm_kernel_type:" + svm_kernel_type);
        System.out.println("debug:" + debug);
        System.out.println("debug_LearningApp:" + debug_LearningApp);
        System.out.println("generation_logfile:" + generation_logfile);
        System.out.println("generation_logfile_svm_specific:" + generation_logfile_svm_specific);
        System.out.println("generation_logfileDir:" + generation_logfileDir);
        System.out.println("feature_selection:" + feature_selection);
        System.out.println("instance_selection:" + instance_selection);
        System.out.println("svm_selection:" + svm_selection);
        System.out.println("train_file:" + train_file);
        System.out.println("test_file:" + test_file);
        System.out.println("validation_file:" + validation_file);
        System.out.println("c_default:" + c_default);
        System.out.println("d_default:" + d_default);
        System.out.println("g_default:" + g_default);
        System.out.println("c_min:" + c_min);
        System.out.println("c_max:" + c_max);
        System.out.println("d_min:" + d_min);
        System.out.println("d_max:" + d_max);
        System.out.println("g_min:" + g_min);
        System.out.println("g_max:" + g_max);
        System.out.println("c_gene_length:" + c_gene_length);
        System.out.println("d_gene_length:" + d_gene_length);
        System.out.println("g_gene_length:" + g_gene_length);
        System.out.println("crossover_percent:" + crossover_percent);
        System.out.println("mutataion_percent:" + mutataion_percent);
        System.out.println("population_size:" + population_size);
        System.out.println("generation:" + generation);
        System.out.println("roundDigitCount:" + roundDigitCount);
        System.out.println("svm_toy_size:" + svm_toy_size);
        /**
         * show it for just checking
         */
        System.out.println("random_initialize_selection:" + random_initialize_selection);
        System.out.println("gasvm_elitism:" + gasvm_elitism);
        System.out.println("elitism_second_mutate_cross:" + elitism_second_mutate_cross);
        System.out.println("elitism_first_mutate:" + elitism_first_mutate);
        System.out.println("gasvm_cross_elitism:" + gasvm_cross_elitism);
    }
}
