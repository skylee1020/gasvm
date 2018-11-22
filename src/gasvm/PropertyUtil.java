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


public class PropertyUtil {    

    public static String getDirStringPropertis(String propertyName, String defaultValue) {
        return getDirStringPropertis(propertyName, defaultValue, null);
    }

    public static String getDirStringPropertis(String propertyName, String defaultValue, Properties props) {
        try {
            String tmp = getStringPropertis(propertyName, defaultValue, props);
            if ( !tmp.endsWith(File.separator)) {
                return tmp + File.separator;
            }
            return tmp;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return defaultValue;
        }
    }

    public static String getStringPropertis(String propertyName, String defaultValue) {
        return getStringPropertis(propertyName, defaultValue, null);
    }

    public static String getStringPropertis(String propertyName, String defaultValue, Properties props) {
        if ( props != null ) {
            String tmp = props.getProperty(propertyName);
            if ( tmp != null ) {
                return tmp;
            }
        }

        try {
            String tmp = System.getProperty(propertyName);
            if ( tmp != null ) {
                return tmp;
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return defaultValue;
    }

    public static boolean getBooleanParamValue(String _targetValue, boolean defaultValue) {
        return getBooleanParamValue(_targetValue, defaultValue, null);
    }

    public static boolean getBooleanParamValue(String _targetValue, boolean defaultValue, Properties props) {
        String targetValue = null;

        if ( props != null ) {
            targetValue = props.getProperty(_targetValue);
        }

        try {
            if ( targetValue == null ) {
                targetValue = System.getProperty(_targetValue);
            }
        } catch (Exception ex) {
            return defaultValue;
        }

        if ( targetValue == null ) {
            return defaultValue;
        }

        try {
            return Boolean.valueOf(targetValue).booleanValue();
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static double getDoubleParamValue(String _targetValue, double defaultValue, Properties props) {
        String targetValue = null;

        if ( props != null ) {
            targetValue = props.getProperty(_targetValue);
        }

        try {
            if ( targetValue == null ) {
                targetValue = System.getProperty(_targetValue);
            }
        } catch (Exception ex) {
            return defaultValue;
        }

        if ( targetValue == null ) {
            return defaultValue;
        }

        try {
            return Double.valueOf(targetValue).doubleValue();
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static int getIntParamValue(String _targetValue, int defaultValue, Properties props) {
        String targetValue = null;

        if ( props != null ) {
            targetValue = props.getProperty(_targetValue);
        }

        try {
            if ( targetValue == null ) {
                targetValue = System.getProperty(_targetValue);
            }
        } catch (Exception ex) {
            return defaultValue;
        }

        if ( targetValue == null ) {
            return defaultValue;
        }

        try {
            return Integer.valueOf(targetValue).intValue();
        } catch (Exception ex) {
            return defaultValue;
        }
    }
}
