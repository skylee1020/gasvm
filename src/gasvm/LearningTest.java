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

import java.io.*;
import java.util.Arrays;

public class LearningTest {
    public static void main(String[] args) {
        try {
            //doCase1();
            //doCase2();
            //doCase3();
            //doCase4();
            //doCase5();
            //doCase6();
            //doCase7();
            //doCase8();
            //doCase9();
            //doCase10();
            //doCase11();
            //doCase12();
            doCase14();
            //doCase15();
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private static void doCase12() {
        int count = 100;
        int Value = 4;
        double x_boundary_start = 0.1d;
        double x_boundary_end = 0.5d;
        double y_boundary_start = 0.6d;
        double y_boundary_end = 0.95d;
        PrintStream fw = getWriter("generated_data2.txt");
        for ( int i = 0 ; i < count ; i++) {
            fw.println(Value + "\t" + "1:\t" + (x_boundary_start + ( x_boundary_end- x_boundary_start) * java.lang.Math.random()) + "\t2:\t" + (y_boundary_start + ( y_boundary_end- y_boundary_start) * java.lang.Math.random()) );
        }
    }

    public static java.io.PrintStream getWriter(String fileName) {
        File f = new File(fileName);
        java.io.FileWriter pw;
        try {
            java.io.PrintStream ps = new PrintStream( new java.io.FileOutputStream(f, true));
            return ps;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return System.out;
        }
    }

    private static void doCase10() {
        double val1 = 204.394332d, val2 = 202.294330;

        System.out.println("LearningApp.round(204.394332d, 3)" + LearningApp.round(204.394332d, 3));
        System.out.println("LearningApp.round(204.394332d, 4)" + LearningApp.round(204.394332d, 4));
        System.out.println("LearningApp.round(202.294380, 4)" + LearningApp.round(202.294380, 4));
        System.out.println("LearningApp.round(202.294380, 5)" + LearningApp.round(202.294380, 5));
        System.out.println("LearningApp.round(202.294380, 6)" + LearningApp.round(202.294380, 6));
        System.out.println("LearningApp.round(202.294380, 7)" + LearningApp.round(202.294380, 7));
    }

    private static void doCase9() {
        try {
            BufferedReader fp = new BufferedReader(new FileReader(LearningProperties.svm_traindata_dir + "sample_test.1.original.txt"));
            DataOutputStream output1 = new DataOutputStream(new FileOutputStream(LearningProperties.svm_traindata_dir + "sample_test.1.original_part1.txt"));
            DataOutputStream output2 = new DataOutputStream(new FileOutputStream(LearningProperties.svm_traindata_dir + "sample_test.1.original_part2.txt"));
            DataOutputStream output3 = new DataOutputStream(new FileOutputStream(LearningProperties.svm_traindata_dir + "sample_test.1.original_part3.txt"));
            while(true)
            {
                String line = fp.readLine();
                if(line == null) break;
                double num = Math.random();
                if ( num < 0.6d) {
                    output1.writeBytes(line);
                    output1.writeBytes("\n");
                } else if ( num < 0.8d) {
                    output2.writeBytes(line);
                    output2.writeBytes("\n");
                } else {
                    output3.writeBytes(line);
                    output3.writeBytes("\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }

    private static void doCase7() {
        //int[] gene1 = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1};
        //int[] gene2 = { 1, 1, 1, 0, 0, 0, 0, 0, 0, 1};
        int[] gene1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] gene2 = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] newGene1 = new int[10];
        int[] newGene2 = new int[10];
        GASVMChromosome.crossover(gene1, gene2,newGene1, newGene2);
        for (int i = 0; i < newGene2.length; i++) {
            System.out.println("gene1:" + gene1[i] + ", gene2:" + gene2[i] + ", newGene1:" + newGene1[i] + ", newGene2:" + newGene2[i]);
        }
    }

    private static void doCase8() {
        //int[] gene1 = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1};
        //int[] gene2 = { 1, 1, 1, 0, 0, 0, 0, 0, 0, 1};
        int[] gene1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        GASVMChromosome.mute(gene1, 10.0d);
        for (int i = 0; i < gene1.length; i++) {
            //System.out.println("gene1:" + gene1[i] + ", gene2:" + gene2[i] + ", newGene1:" + newGene1[i] + ", newGene2:" + newGene2[i]);
            System.out.println("gene1:" + gene1[i] );
        }
    }

    private static void doCase6() {
        double c_min = 1.0d;
        double c_max = 11.0d;
        //int[] gene = { 0, 1, 0, 1, 1, 0, 0, 1};
        //int[] gene = { 0, 0, 0, 0, 0, 0, 0, 0};
        //int[] gene = { 1, 1, 1, 1, 1, 1, 1, 1};
        int[] gene = { 1, 1, 0, 0, 0, 0, 0, 0};
        double value = GASVMChromosome.getMappedValue(c_min, c_max, gene, 6);

        System.out.println("value:" + value);
        System.out.println("rounded value:" + java.lang.Math.round(value));
        System.out.println("rounded to 4th, value:" + java.lang.Math.round(value * 10000) / 10000d);
    }

    public static void doCase1() throws IOException {
        //LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        LearningUnit unit = new LearningUnit("sample_4_train.txt", "sample_4_test.txt", "sample_4_validation.txt");
        //System.out.println("[1 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 2, 5, 140, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 3, 10, 100, true);

        //System.out.println("[2 generation] unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false); ");
        //unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 0, 5, 198, 165, false);");
        //unit.getModelPrediction( 2, 5, 198, 165, false, null, new int[0], new int[0]);
        System.out.println("[2 generation] unit.getModelPrediction( 0, 5, 198, 165, false);");
        int[] _exclude_feature = new int[1];
        _exclude_feature[0] = 3;
        unit.getModelPrediction( 0, 10, 115.186706d, 73.363636d, false, null,_exclude_feature, new int[0]);
    }

    public static void doCase11() throws IOException {
        //LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        LearningUnit unit = new LearningUnit("crm_t.txt", "crm_p.txt", "crm_v.txt");
        //System.out.println("[1 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 2, 5, 140, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 3, 10, 100, true);

        //System.out.println("[2 generation] unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false); ");
        //unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 0, 5, 198, 165, false);");
        //unit.getModelPrediction( 2, 5, 198, 165, false, null, new int[0], new int[0]);
        System.out.println("[2 generation] unit.getModelPrediction( 0, 5, 198, 165, false);");
        //int[] _exclude_feature = new int[1];
        //_exclude_feature[0] = 3;
        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, null, new int[0], new int[0]);
    }

    public static void doCase13() throws IOException {
        //LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        LearningUnit unit = new LearningUnit("crm_t.txt", "crm_p.txt", "crm_v.txt");
        //System.out.println("[1 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 2, 5, 140, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 3, 10, 100, true);

        //System.out.println("[2 generation] unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false); ");
        //unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 0, 5, 198, 165, false);");
        //unit.getModelPrediction( 2, 5, 198, 165, false, null, new int[0], new int[0]);
//        System.out.println("doCase13 _ftest1_");
//        int[] _exclude_feature = new int[3];
//        _exclude_feature[0] = 0;
//        _exclude_feature[1] = 1;
//        _exclude_feature[2] = 2;
//        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest1_", _exclude_feature, new int[0]);
//
//        int[] _exclude_feature2 = new int[2];
//        _exclude_feature2[0] = 0;
//        _exclude_feature2[1] = 1;
//        System.out.println("doCase13 _ftest2_");
//        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", _exclude_feature2, new int[0]);

        System.out.println("doCase13 exclude feature");
//        int[] _exclude_instance1 = new int[1];
//        for (int i = 0; i < _exclude_instance1.length; i++) {
//            _exclude_instance1[i] = i;
//        }
        int[] _features = new int[1];
        _features[0] = 0;
        int[] _instances = new int[2];
        _instances[0] = 3;
        _instances[1] = 1;
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", _features, new int[0]);
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", new int[0], new int[0]);
        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ie_", new int[0], _instances);
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", new int[0], _exclude_instance1);

    }

        public static void doCase14() throws IOException {
        //LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        //LearningUnit unit = new LearningUnit("n_hana_t", "n_hana_p", "n_hana_v");
            LearningUnit unit = new LearningUnit("hana_t", "hana_p", "hana_v");
            //LearningUnit unit = new LearningUnit("n_hana_t", "n_hana_p", "n_hana_v");
        //System.out.println("[1 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 2, 5, 140, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 2, 5, 140, true);");
        //unit.getModelPrediction( 2, 3, 10, 100, true);

        //System.out.println("[2 generation] unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false); ");
        //unit.getModelPrediction( 0, 16.07936507936508d, 157.26392961876832d, 49.826001955034215d, false);
        //System.out.println("[2 generation] unit.getModelPrediction( 0, 5, 198, 165, false);");
        //unit.getModelPrediction( 2, 5, 198, 165, false, null, new int[0], new int[0]);
//        System.out.println("doCase13 _ftest1_");
//        int[] _exclude_feature = new int[3];
//        _exclude_feature[0] = 0;
//        _exclude_feature[1] = 1;
//        _exclude_feature[2] = 2;
//        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest1_", _exclude_feature, new int[0]);
//
//        int[] _exclude_feature2 = new int[2];
//        _exclude_feature2[0] = 0;
//        _exclude_feature2[1] = 1;
//        System.out.println("doCase13 _ftest2_");
//        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", _exclude_feature2, new int[0]);

        System.out.println("doCase14 all");
//        int[] _exclude_instance1 = new int[1];
//        for (int i = 0; i < _exclude_instance1.length; i++) {
//            _exclude_instance1[i] = i;
//        }
        int[] _features = new int[4];
        _features[0] = 24;
        _features[1] = 27;
        _features[2] = 28;
        _features[3] = 36;

        int[] _instances = new int[3];
        _instances[0] = 1;
        _instances[1] = 4;
        _instances[2] = 9;
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", _features, new int[0]);
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", new int[0], new int[0]);
        unit.getModelPrediction( 2, 11, 21.50588d, 43.92157d, false, "_ex4_", _features, _instances);
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", new int[0], _exclude_instance1);

    }

    public static void doCase15() throws IOException {

        LearningUnit unit = new LearningUnit("hana_t", "hana_p", "hana_v");

        System.out.println("doCase15 excluded");
        int[] _exclude_feature = new int[4];
        _exclude_feature[0] = 24;
        _exclude_feature[1] = 27;
        _exclude_feature[2] = 28;
        _exclude_feature[3] = 36;
//        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest1_", _exclude_feature, new int[0]);
//
//        int[] _exclude_feature2 = new int[2];
//        _exclude_feature2[0] = 0;
//        _exclude_feature2[1] = 1;
//        System.out.println("doCase13 _ftest2_");
//        unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", _exclude_feature2, new int[0]);

        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", _features, new int[0]);
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", new int[0], new int[0]);
        //unit.getModelPrediction( 2, 11, 21.50588d, 43.92157d, true, "_ie_", _exclude_feature, new int[0]);
        unit.getModelPrediction( 2, 11, 185.56318d, 43.92157d, true, "_ie2_", _exclude_feature, new int[0]);
        //unit.getModelPrediction( 2, 30, 121.606061d, 170.237537d, false, "_ftest2_", new int[0], _exclude_instance1);

    }

    public static void doCase2() {
        int[] targetIndex = { 2, 5, 6, 7, 10, 12, 29, 32, 34};
        System.out.println("find 0 ?" + Arrays.binarySearch(targetIndex, 0) );
        System.out.println("find 2 ?" + Arrays.binarySearch(targetIndex, 2) );
        System.out.println("find 7 ?" + Arrays.binarySearch(targetIndex, 7) );
        System.out.println("find 11 ?" + Arrays.binarySearch(targetIndex, 11) );
        System.out.println("find 34 ?" + Arrays.binarySearch(targetIndex, 34) );
        System.out.println("find 44 ?" + Arrays.binarySearch(targetIndex, 44) );
    }

    public static void doCase3() throws IOException {
        LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        int[] exclude_feature_index = { 3, 1 };
        int[] exclude_instance_index = { 2, 7, 6 };
        unit.getCurrentProblem(exclude_feature_index , exclude_instance_index);
    }

    public static void doCase4() throws IOException {
        LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        int[] exclude_feature_index = new int[0];
        int[] exclude_instance_index = { 2, 7, 6 };
        unit.getCurrentProblem(exclude_feature_index , exclude_instance_index);
    }

    public static void doCase5() throws IOException {
        LearningUnit unit = new LearningUnit("sample_train.1.txt", "sample_test.1.txt", "sample_validation.1.txt");
        int[] exclude_feature_index = { 3, 1 };
        int[] exclude_instance_index = new int[0];
        unit.getCurrentProblem(exclude_feature_index , exclude_instance_index);
    }

}

