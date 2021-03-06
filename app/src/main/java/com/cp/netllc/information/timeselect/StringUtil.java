package com.cp.netllc.information.timeselect;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 功能描述: 字符串处理工具类
 * 作者:chenwei
 * 时间:2016/8/9
 * 版本:1.0
 ***/
public class StringUtil {
    public  static String errorMsg ="";
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str){
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
    public static String changeFormat(double distance){
        float distanceValue = Math.round((distance));
        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(distance) +"";//format 返回的是字符串
        return distanceString;
    }

    /**
     * Returns true if the string is not null and more then 0-length.

     * @return false if str is null or zero length
     */

    public static boolean isNotEmpty(CharSequence str){
        if (str == null || str.length() == 0)
            return false;
        else
            return true;
    }
    //判断手机号
    public static boolean isMobileNum(String mobiles) {
//        Pattern p = Pattern.compile("^0?[18][34578]\\d{9}$");
        Pattern p = Pattern.compile("^1[3-5,7,8]{1}[0-9]{9}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    //判断邮政编码
    public static boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    // 判断一个字符串是否含有数字

    public static boolean hasDigit(String content) {

        boolean flag = false;

        Pattern p = Pattern.compile(".*\\d+.*");

        Matcher m = p.matcher(content);

        if (m.matches())

            flag = true;

        return flag;

    }


    /**
     * 可以判断出“       ”
     * @param str
     * @return
     */
    public static boolean isEmptyIncludeSpace(CharSequence str){
        if(isEmpty(str)){
            return true;
        }else {
            str=str.toString().trim();
            if(isEmpty(str)){
                return true;
            }else {
                return false;
            }
        }
    }
    public static  long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
/*    *//**
     * 设置hint提示语
     * @param text
     * @return
     *//*
    public static SpannableStringBuilder getSpannable(String text, Context context, int start, int end){
        SpannableStringBuilder spannable=new SpannableStringBuilder(text);
        CharacterStyle span_1=new ForegroundColorSpan(context.getResources().getColor(R.color.color_red1));
        CharacterStyle span_2=new ForegroundColorSpan(context.getResources().getColor(R.color.color_primary_b3));
        spannable.setSpan(span_1, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(span_2, end, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }*/

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
	    /*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	    联通：130、131、132、152、155、156、185、186
	    电信：133、153、180、189、（1349卫通）
	    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
	    */

        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };
    private static String toHexString(byte[] b) {
        //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {

        try {

            PackageManager manager = context.getPackageManager();

            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            String version = info.versionName;

            return  version;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
  public static String ChangeSumSplit(double sum){
      int i=0;
      String money="";
      String[] list=new String[5];
      long sums=(long)sum;


      while (sums>=1000){
            list[i]=sums%1000==0?"000":sums%1000+"";
            sums=sums/1000;

            i++;
        }
      for(int a=0;a<i;a++){

          money=money+","+list[i-a-1];
      }

          return sums+money;
  }

    public static String getStrMoney(double money){

        String total= String.format("%.2f",money);

        String pre=total.substring(0,total.length()-3);

        String end=total.substring(pre.length(),total.length());

        String total_coat="0.00";

        try {
            total_coat= StringUtil.ChangeSumSplit(Long.parseLong(pre))+end;
        }catch (Exception e){
        }

        return total_coat;
    }
    public static String ChangeNumber(double sum){
        int i=0;
        int s = (int) ((sum-(int)sum)/0.01);
        String substring;
        if(s==0){
            substring=".00";
        }else {
            String s1 = Double.toString(sum);
            substring = s1.substring(s1.indexOf("."));
            if(substring.length()==2){
                substring=substring+0;
            }else if(substring.length()>=3){
                substring = s1.substring(s1.indexOf("."),s1.indexOf(".")+3);
            }
        }


        String money="";
        String[] list=new String[5];
        long sums=(long)sum;


        while (sums>=1000){
            list[i]=sums%1000==0?"000":sums%1000+"";
            sums=sums/1000;

            i++;
        }
        for(int a=0;a<i;a++){

            money=money+","+list[i-a-1];
        }

        return sums+money+substring;
    }
    public static String ChangeSumSplit(long sum){
        int i=0;
        String money="";
        String[] list=new String[5];
        long sums=sum;


        while (sums>=1000){
            list[i]=sums%1000==0?"000":sums%1000+"";
            sums=sums/1000;

            i++;
        }
        for(int a=0;a<i;a++){

            money=money+","+list[i-a-1];
        }

        return sums+money;
    }








    private static String getLastTwoChar(String name){
        if (!TextUtils.isEmpty(name)){
            if (name.trim().length()>2){
                return name.trim().substring(name.trim().length()-2);
            }
            return name;
        }
        return "";
    }

    private static String getFirstChar(String name){
        if (name == null || name.trim().length() == 0){
            return "";
        }
        return name.trim().substring(0,1);
    }

    private static String getWordFirstChar(String name){
        String nameResult = "";
        if (name!=null){
            String[] allWord = name.split(" ");
            if (allWord!=null && allWord.length>0){
                for (int i=0;i<allWord.length;i++){
                    if (allWord[i]!=null && allWord[i].length()>0){
                        nameResult = nameResult + allWord[i].substring(0,1);
                        if (nameResult.length()>=2){
                            return nameResult;
                        }
                    }
                }
            }
        }
        return nameResult;
    }

}
