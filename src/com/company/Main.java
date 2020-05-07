package com.company;
import javax.swing.*;
import java.util.Scanner;
import java.lang.Math;

public class Main {


    public static void main(String[] args) throws Exception {
	// write your code here
        Scanner in = new Scanner(System.in);

        System.out.println("super simple calculator for arabic and roman numbers between 1 and 10");
        System.out.println("enter arabic number then space then the operation (+/ -/ */ or /(division)) then another space and the second number");
        System.out.println("both numbers must be either arabic or roman, mixed roman arabic calculations are not supported");
        System.out.println("enjoy!");
        String inputString=in.nextLine();
        String delimeter=" ";
        String[] operands;
        operands = inputString.split(delimeter);
        compute(operands);
        
    }

    public static int computeInt(String[] operands)
    {
        String operation=operands[1];
        //System.out.println(operands[1]);
        if (operation.contains("+"))
        {
            return checkInt(operands[0])+checkInt(operands[2]);
        }else if (operation.contains("-"))
        {
            return checkInt(operands[0])-checkInt(operands[2]);
        }else if (operation.contains("*"))
        {
            return checkInt(operands[0])*checkInt(operands[2]);
        }else if (operation.contains("/"))
        {
            return checkInt(operands[0])/checkInt(operands[2]);
        } else
        {
            return -1;
        }
    }

    public static RomanNumeral computeRoman(String[] operands) throws Exception {
        String operation=operands[1];
        if (operation.contains("+"))
        {
            return checkRoman(operands[0]).add(checkRoman(operands[2]));
        }else if (operation.contains("-"))
        {
            return checkRoman(operands[0]).subtract(checkRoman(operands[2]));
        }else if (operation.contains("*"))
        {
            return checkRoman(operands[0]).multiplyBy(checkRoman(operands[2]));
        }else if (operation.contains("/"))
        {
            return checkRoman(operands[0]).divideBy(checkRoman(operands[2]));
        } else
        {
            String error ="err";
            return new RomanNumeral(error);
        }
    }

    public static void compute(String[] operands) throws Exception {
        if (checkOperation(operands[1])==-1)
        {
            System.out.println("illegal operation");

        };
        if ((checkInt(operands[0])!=-1) && (checkInt(operands[2])!=-1))
        {
            int resultInt = computeInt(operands);
            System.out.println(resultInt);
            return;
        }else if ((checkRoman(operands[0]).arabic()!=-1)&&(checkRoman(operands[2]).arabic()!=-1))
        {
            RomanNumeral resultRom=computeRoman(operands);
            System.out.println(resultRom);
            return;
        }else {
            System.out.println("can't compute this");
            return;
        }
    }

    public static int checkOperation(String operation)
    {
        String legalOperations="+ - / *";
        if  (legalOperations.contains(operation))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public static int checkInt(String operand)
    {
        try {
            int result= Integer.parseInt(operand);
            if (result>10)
            {
                return - 1;
            } else if (result<1)
            {
                return -1;
            }
            else
            {
                return result;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static RomanNumeral checkRoman(String operand)
    {
       RomanNumeral newRoman = new RomanNumeral(operand);
       if (newRoman.arabic()>10)
       {
           return new RomanNumeral("err");
       }
       else
       {
           return newRoman;
       }


    }

}

class RomanNumeral
{
    private String roman;
    private int arabic=0;
    private static final String[] romans={"0", "I", "II","III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    public RomanNumeral(String input) {
        String check=input.toUpperCase();
        //System.out.println("in the constructor "+check);
        int ind=indexOf(check);
        //System.out.println("index in the constructor "+ind);
        if ((ind>0)&&(ind<=10))
        {
            roman = romans[ind];
            arabic=ind;
        } else 
        {
            roman="ERR";
            arabic=-1;
        }
    }
    private static int indexOf(String check)
    {
        for(int i=0; i<romans.length; i++)
        {
            if (romans[i].contains(check))
            {
                if (romans[i].length()==check.length())
                {
                    return i;
                }
            }
        }
        return 0;
    }
    private String arabicToRoman(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }
    public int arabic()
    {
        return arabic;
    }
    public String roman()
    {
        return roman;
    }
    public RomanNumeral add(RomanNumeral otherrn)
    {
        //System.out.println(otherrn.arabic());
        this.arabic=this.arabic +otherrn.arabic();
        this.roman=arabicToRoman(this.arabic);
        return this;
    }
    public RomanNumeral subtract(RomanNumeral otherrn) throws Exception {

        if (this.arabic-otherrn.arabic<=0)
        {
            throw new Exception("no negative roman numerals supported");
        }
        this.arabic=this.arabic-otherrn.arabic();
        roman=arabicToRoman(arabic);
        return this;
    }

    public RomanNumeral multiplyBy(RomanNumeral otherrn)
    {
        //System.out.println(this.arabic);
        this.arabic=this.arabic*otherrn.arabic();
        //System.out.println(otherrn.arabic());
        //System.out.println(this.arabic);
        this.roman=arabicToRoman(this.arabic);
        return this;
    }
    public RomanNumeral divideBy(RomanNumeral otherrn)
    {
        //integer division only
        this.arabic=this.arabic/otherrn.arabic();
        this.roman=arabicToRoman(this.arabic);
        return this;
    }

    public String toString()
    {
        return roman;
    }

}
