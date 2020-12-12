package org.example;

import java.util.ArrayList;

public class DataStruct {
    public String name;
    public Double val;
    public String unit;

    static public Double[] extractDataStructVal(ArrayList<DataStruct> struct)
    {
        try
        {
            if(struct.size() > 0)
            {
                Double[] values = new Double[struct.size()];
                for (int i = 0; i < struct.size(); i++) {
                    values[i] = struct.get(i).val;
                }
                return values;
            }
            else
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (NullPointerException e) {
            System.out.println("DataStruct error: This not an array");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("DataStruct error: The array is empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
            return new Double[0];
    }

    static public String[] extractDataStructName(ArrayList<DataStruct> struct)
    {
        try {
            if (struct.size() > 0) {
                String[] string = new String[struct.size()];
                for (int i = 0; i < struct.size(); i++) {
                    string[i] = struct.get(i).name.toString();
                }
                return string;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (NullPointerException e) {
            System.out.println("DataStruct error: This not an array");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("DataStruct error: The array is empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    static public String[] extractDataStructUnit(ArrayList<DataStruct> struct)
    {
        try {
            if (struct.size() > 0) {
                String[] string = new String[struct.size()];
                for (int i = 0; i < struct.size(); i++) {
                    string[i] = struct.get(i).unit.toString();
                }
                return string;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        } catch (NullPointerException e) {
            System.out.println("DataStruct error: This not an array");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("DataStruct error: The array is empty");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
