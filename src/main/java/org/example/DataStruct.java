package org.example;

import java.util.ArrayList;

public class DataStruct {
    public String name;
    public Double val;

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
            System.out.println("Das ist kein Array");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Das Array ist leer.");
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
            System.out.println("Das ist kein Array");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Das Array ist leer.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
