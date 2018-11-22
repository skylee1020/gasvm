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

import ga.Chromosome;
import ga.NumberChromosome;

import java.io.IOException;


public class GASVMChromosome extends ga.Chromosome {
    private boolean svm_selection;
    private boolean feature_selection;
    private boolean instance_selection;

    private double c_default = Double.MIN_VALUE;
    private int d_default = 10;
    private double g_default = Double.MIN_VALUE;
    private double c_min = Double.MIN_VALUE;;
    private double c_max = Double.MIN_VALUE;;
    private int c_gene_length = Integer.MIN_VALUE;
    private int d_min = Integer.MIN_VALUE;;
    private int d_max = Integer.MIN_VALUE;;
    private int p_gene_length = Integer.MIN_VALUE;
    private double g_min = Double.MIN_VALUE;;
    private double g_max = Double.MIN_VALUE;;
    private int g_gene_length = Integer.MIN_VALUE;
    private String trainFileName;
    private String testFileName;
    private String validationFileName;
    private int kernel_type;
    private LearningUnit learningUnit;
    private int max_count_features;
    private int max_count_instances;

    public int gene_feature[];
    //public int gene_feature_length;
    public int gene_instance[];
    //public int gene_instance_length;
    public int gene_svmparam_c[];
    public int gene_svmparam_d[];
    public int gene_svmparam_g[];
    //public int gene_svmparam_length;

    public int fitnessTrain;
    public int fitnessValidation;

    public double fitness_l;
    public double fitnessTrain_l;
    public double fitnessValidation_l;

    public GASVMChromosome(String trainFileName, String testFileName, String validationFileName,
                           boolean svm_selection, boolean feature_selection, boolean instance_selection,
                           int kernel_type,
                                double c_min, double c_max, int c_gene_length,
                                int d_min, int d_max, int p_gene_length,
                                double g_min, double g_max, int g_gene_length,
                                double c_default, int d_default, double g_default
                           ) throws IOException {

        this.trainFileName = trainFileName;
        this.testFileName = testFileName;
        this.validationFileName = validationFileName;

        this.svm_selection = svm_selection;
        this.feature_selection = feature_selection;
        this.instance_selection = instance_selection;

        this.kernel_type = kernel_type;
        this.c_min = c_min;
        this.c_max = c_max;
        this.c_gene_length = c_gene_length;

        this.d_min = d_min;
        this.d_max = d_max;
        this.p_gene_length = p_gene_length;

        this.g_min = g_min;
        this.g_max = g_max;
        this.g_gene_length = g_gene_length;

        this.c_default = c_default;
        this.d_default = d_default;
        this.g_default = g_default;

        this.learningUnit = new LearningUnit(trainFileName, testFileName, validationFileName);
        this.max_count_features = learningUnit.getMax_count_features();
        this.max_count_instances = learningUnit.getMax_count_instances();
        validateParams();
        initGene();
    }

    private GASVMChromosome(String trainFileName, String testFileName, String validationFileName,
                           boolean svm_selection, boolean feature_selection, boolean instance_selection,
                           int kernel_type,
                                double c_min, double c_max, int c_gene_length,
                                int d_min, int d_max, int d_gene_length,
                                double g_min, double g_max, int g_gene_length,
                                double c_default, double p_default, double g_default,
                           LearningUnit _unit
                           ) {

        this.trainFileName = trainFileName;
        this.testFileName = testFileName;
        this.validationFileName = validationFileName;

        this.svm_selection = svm_selection;
        this.feature_selection = feature_selection;
        this.instance_selection = instance_selection;

        this.kernel_type = kernel_type;
        this.c_min = c_min;
        this.c_max = c_max;
        this.c_gene_length = c_gene_length;

        this.d_min = d_min;
        this.d_max = d_max;
        this.p_gene_length = p_gene_length;

        this.g_min = g_min;
        this.g_max = g_max;
        this.g_gene_length = g_gene_length;

        this.c_default = c_default;
        this.d_default = d_default;
        this.g_default = g_default;

        this.learningUnit = _unit;
        this.max_count_features = learningUnit.getMax_count_features();
        this.max_count_instances = learningUnit.getMax_count_instances();
    }

    private void validateParams() {
        if (!(kernel_type >=0 && kernel_type <= 5)) {
            throw new IllegalArgumentException("wrong svm_type. should be >= 0 and <= 5, kernel_type:" + kernel_type);
        }
        if ( svm_selection ) {
            if ( !(c_max > c_min) || !(d_max > d_min) || !(g_max > g_min) ) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void initGene() {
        if ( svm_selection ) {
            gene_svmparam_c = new int[c_gene_length];
            gene_svmparam_g = new int[g_gene_length];
            gene_svmparam_d = new int[p_gene_length];
        }
        if ( feature_selection ) {
            gene_feature = new int[max_count_features];
        }
        if ( instance_selection ) {
            gene_instance = new int[max_count_instances];
        }
    }

    public void countFitness() {
        double c_param, g_param;
        int d_param;
        int[] exclude_feature, exclude_instance;
        if ( svm_selection ) {
            c_param = getMappedValue(c_min, c_max, gene_svmparam_c, -1);
            d_param = (int)getMappedValue(d_min, d_max, gene_svmparam_d, -1);
            g_param = getMappedValue(g_min, g_max, gene_svmparam_g, -1);
        } else {
            c_param = c_default;
            d_param = d_default;
            g_param = g_default;
        }
        if ( feature_selection ) {
            exclude_feature = getExcludedIndex(gene_feature);
        } else {
            exclude_feature = new int[0];
        }

        if ( instance_selection ) {
            exclude_instance = getExcludedIndex(gene_instance);
        } else {
            exclude_instance = new int[0];
        }
        try {
            AccuracyResult result = this.learningUnit.getModelPrediction(this.kernel_type, d_param, g_param, c_param, false, null, exclude_feature, exclude_instance);
            //System.out.println("[] fitness:" + result.test);

            fitness = (int)result.test;
            fitnessTrain = (int)result.train;
            fitnessValidation = (int)result.validation;

            fitness_l = result.test;
            fitnessTrain_l = result.train;
            fitnessValidation_l = result.validation;

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            System.exit(1);
        }
    }

    private int[] getExcludedIndex(int[] _gene_target ) {
        int[] exclude_feature;
        int excluded_count = 0;
        for ( int i =0 ; i < _gene_target.length ; i++ ) {
            if ( _gene_target[i] == 0 ) {
                //if excluded
                excluded_count++;
            }
        }
        if ( excluded_count == 0 ) {
            exclude_feature = new int[0];
        } else {
            exclude_feature = new int[excluded_count];
            for ( int i =0, exclude_index = 0 ; i < _gene_target.length ; i++ ) {
                if ( _gene_target[i] == 0 ) {
                    //if excluded
                    exclude_feature[exclude_index++] = i;
                }
            }
        }
        return exclude_feature;
    }

    public static double getMappedValue(double min, double max, int[] gene, int decimalLength) {
        long realValue = 0L;
        long factor = 1L;
        for(int i = 0; i < gene.length; i++)
        {
            realValue += factor * (long)gene[i];
            factor *= 2L;
        }
        long fromMin = 0l;
        long fromMax = (1 << gene.length)- 1;
        double val =  (double)(realValue - fromMin) / (fromMax - fromMin) * (max-min) + min;
        if ( decimalLength > 0 ) {
            int tFactor = 10;
            for (int i =0 ; i < decimalLength ; i++ ) {
                tFactor = tFactor * 10;
            }
            return (double)java.lang.Math.round(val * tFactor) / tFactor;
        } else {
            return val;
        }
    }

    public Chromosome[] crossover(Chromosome chromosome, double d) {
        GASVMChromosome orginalGene2 = (GASVMChromosome)chromosome;
        GASVMChromosome anumberchromosome[] = new GASVMChromosome[2];
        anumberchromosome[0] = (GASVMChromosome)copy();
        anumberchromosome[1] = (GASVMChromosome)chromosome.copy();

        if ( svm_selection) {
            //c parameter
            if(d < Math.random() * 100D) {
                System.arraycopy(this.gene_svmparam_c, 0, anumberchromosome[0].gene_svmparam_c, 0, this.gene_svmparam_c.length );
                System.arraycopy(orginalGene2.gene_svmparam_c, 0, anumberchromosome[1].gene_svmparam_c, 0, orginalGene2.gene_svmparam_c.length );
            } else {
                crossover(this.gene_svmparam_c, orginalGene2.gene_svmparam_c, anumberchromosome[0].gene_svmparam_c, anumberchromosome[0].gene_svmparam_c );
            }
            //p parameter
            if(d < Math.random() * 100D) {
                System.arraycopy(this.gene_svmparam_d, 0, anumberchromosome[0].gene_svmparam_d, 0, this.gene_svmparam_d.length );
                System.arraycopy(orginalGene2.gene_svmparam_d, 0, anumberchromosome[1].gene_svmparam_d, 0, orginalGene2.gene_svmparam_d.length );
            } else {
                crossover(this.gene_svmparam_d, orginalGene2.gene_svmparam_d, anumberchromosome[0].gene_svmparam_d, anumberchromosome[0].gene_svmparam_d);
            }
            //g parameter
            if(d < Math.random() * 100D) {
                System.arraycopy(this.gene_svmparam_g, 0, anumberchromosome[0].gene_svmparam_g, 0, this.gene_svmparam_g.length );
                System.arraycopy(orginalGene2.gene_svmparam_g, 0, anumberchromosome[1].gene_svmparam_g, 0, orginalGene2.gene_svmparam_g.length );
            } else {
                crossover(this.gene_svmparam_g, orginalGene2.gene_svmparam_g, anumberchromosome[0].gene_svmparam_g, anumberchromosome[0].gene_svmparam_g);
            }
        }

        if ( instance_selection ) {
            if(d < Math.random() * 100D) {
                System.arraycopy(this.gene_instance, 0, anumberchromosome[0].gene_instance, 0, this.gene_instance.length );
                System.arraycopy(orginalGene2.gene_instance, 0, anumberchromosome[1].gene_instance, 0, orginalGene2.gene_instance.length );
            } else {
                crossover(this.gene_instance, orginalGene2.gene_instance, anumberchromosome[0].gene_instance, anumberchromosome[0].gene_instance);
            }
        }

        if ( feature_selection ) {
            if(d < Math.random() * 100D) {
                System.arraycopy(this.gene_feature, 0, anumberchromosome[0].gene_feature, 0, this.gene_feature.length );
                System.arraycopy(orginalGene2.gene_feature, 0, anumberchromosome[1].gene_feature, 0, orginalGene2.gene_feature.length );
            } else {
                crossover(this.gene_feature, orginalGene2.gene_feature, anumberchromosome[0].gene_feature, anumberchromosome[0].gene_feature);
            }
        }

        return anumberchromosome;
    }

    public static void crossover(int[] originalGene1, int[] originalGene2, int[] newGene1, int[] newGene2) {
        int i = (int)(Math.random() * (double)originalGene1.length);
        int j;
        for(j = 0; j < i; j++) {
            newGene1[j] = originalGene1[j];
            newGene2[j] = originalGene2[j];
        }

        for(; j < originalGene1.length; j++) {
            newGene2[j] = originalGene1[j];
            newGene1[j] = originalGene2[j];
        }
    }

    public static void mute(int[] targetGene, double probability) {
        for(int i = 0; i < targetGene.length; i++) {
            if(Math.random() * 100D < probability) {
                if(targetGene[i] == 1) {
                    targetGene[i] = 0;
                } else {
                    targetGene[i] = 1;
                }
            }
        }
    }

    public void mute(double d) {
        if ( svm_selection ) {
            mute(gene_svmparam_c, d);
            mute(gene_svmparam_d, d);
            mute(gene_svmparam_g, d);
        }
        if ( feature_selection ) {
            mute(gene_feature, d);
        }
        if ( instance_selection ) {
            mute(gene_instance, d);
        }
    }

    private static void randomize(int[] _target) {
        for(int i = 0; i < _target.length; i++) {
            if(Math.random() < 0.5D) {
                _target[i] = 0;
            } else {
                _target[i] = 1;
            }
        }
    }

    /**
     * a simple utility function to fill it with 1 or 0
     * @param _target
     * @param excluded  true -> all 0
     *                  false -> all 1
     */
    private static void setAll(int[] _target, boolean excluded) {
        for(int i = 0; i < _target.length; i++) {
            _target[i] = excluded? 0 : 1;
        }
    }
    /**
     * So that we can distinguish the randomization logic easily.
     * Up to this point, GASVMChromosome needs to override this logic.
     */
    public void init_randomize(boolean selectionInitRandom) {
        if ( svm_selection ) {
            randomize(gene_svmparam_c);
            randomize(gene_svmparam_g);
            randomize(gene_svmparam_d);
        }

        if ( feature_selection ) {
            if ( selectionInitRandom ) {
                randomize(gene_feature);
            } else {
                setAll(gene_feature, false);
            }

        }
        if ( instance_selection ) {
            if ( selectionInitRandom ) {
                randomize(gene_instance);
            } else {
                setAll(gene_instance, false);
            }
        }
    }

    public void randomize() {
        if ( svm_selection ) {
            randomize(gene_svmparam_c);
            randomize(gene_svmparam_g);
            randomize(gene_svmparam_d);
        }

        if ( feature_selection ) {
            randomize(gene_feature);
        }
        if ( instance_selection ) {
            randomize(gene_instance);
        }
    }

    public Chromosome copy() {
        LearningUnit _unit = this.learningUnit.copy();
        GASVMChromosome newGene = new GASVMChromosome(this.trainFileName, this.testFileName, this.validationFileName,
                this.svm_selection, this.feature_selection, this.instance_selection, this.kernel_type,
                this.c_min,  this.c_max, this.c_gene_length, this.d_min, this.d_max,  this.p_gene_length,
                this.g_min, this.g_max, this.g_gene_length, this.c_default, this.d_default, this.g_default, _unit);
        newGene.fitness = this.fitness;
        newGene.fitnessTrain = this.fitnessTrain;
        newGene.fitnessValidation = this.fitnessValidation;
        if ( svm_selection ) {
            newGene.gene_svmparam_c = new int[this.gene_svmparam_c.length];
            newGene.gene_svmparam_g = new int[this.gene_svmparam_g.length];
            newGene.gene_svmparam_d = new int[this.gene_svmparam_d.length];
            System.arraycopy(this.gene_svmparam_c, 0, newGene.gene_svmparam_c, 0, this.gene_svmparam_c.length);
            System.arraycopy(this.gene_svmparam_g, 0, newGene.gene_svmparam_g, 0, this.gene_svmparam_g.length);
            System.arraycopy(this.gene_svmparam_d, 0, newGene.gene_svmparam_d, 0, this.gene_svmparam_d.length);
        }
        if ( feature_selection ) {
            newGene.gene_feature = new int[this.gene_feature.length];
            System.arraycopy(this.gene_feature, 0, newGene.gene_feature, 0, this.gene_feature.length);
        }
        if ( instance_selection ) {
            newGene.gene_instance = new int[this.gene_instance.length];
            System.arraycopy(this.gene_instance, 0, newGene.gene_instance, 0, this.gene_instance.length);
        }
        return newGene;
    }

    public String getContent() {
        StringBuffer sb = new StringBuffer();
        //System.out.println("[gasvm.GASVMChromosome getContent, svm_selection:" + svm_selection);
        if ( svm_selection ) {
            sb.append("c:" + get_C() +",");
            sb.append("d:" + (int)get_D() +",");
            sb.append("g:" + get_G() );
        } else {
            sb.append("No svm_selection");
        }
        getContentLastPart(sb);
        return sb.toString();
    }

    public void getContentLastPart(StringBuffer sb) {
        if ( feature_selection ) {
            int[] exclude_feature = getExcludedIndex(gene_feature);
            sb.append(";");
            for (int i = 0; i < exclude_feature.length; i++) {
                int d = exclude_feature[i];
                sb.append(d);
                if ( i != (exclude_feature.length-1) ) {
                    sb.append(",");
                }
            }
        } else {
            sb.append(";No feature_selection");
        }
        if ( instance_selection ) {
            int[] exclude_instance = getExcludedIndex(gene_instance);
            sb.append(";");
            for (int i = 0; i < exclude_instance.length; i++) {
                int d = exclude_instance[i];
                sb.append(d);
                if ( i != (exclude_instance.length-1) ) {
                    sb.append(",");
                }
            }
        } else {
            sb.append(";No instance_selection");
        }
    }

    public double get_G() {
        return getMappedValue(g_min, g_max, gene_svmparam_g, LearningProperties.roundDigitCount);
    }

    public double get_D() {
        return getMappedValue(d_min, d_max, gene_svmparam_d, LearningProperties.roundDigitCount);
    }

    public double get_C() {
        return getMappedValue(c_min, c_max, gene_svmparam_c, LearningProperties.roundDigitCount);
    }

    public double getFitnessToSort() {
        return fitness_l;
    }
}