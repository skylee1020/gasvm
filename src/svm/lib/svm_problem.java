package svm.lib;

import svm.lib.svm_node;

public class svm_problem implements java.io.Serializable
{
	public int l;
	public double[] y;
	public svm_node[][] x;

    /**
     * Add sky1020 2005/09/09
     * for setting gamma value by default
     */
    public int max_index;
    public int max_x_index;
}
